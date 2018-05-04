(ns thefox.client
  (:require [thefox.core :refer :all]
            [thefox.activity :as activity]
            [thefox.util :refer :all]
            [cheshire.core :refer [parse-string]]))

;;
; Handles stuff incoming from clients
; Only considering incoming POST.
; https://www.w3.org/TR/activitypub/#client-to-server-interactions
; TODO: validation for missing fields

(defn consume
  "Consumes an incoming client request."
  ([body] (consume body {}))
  ; headers are not used at this point, but will be for signature validation
  ([body headers]
    (let [object (parse-string body true)
          type (:type object)]
      (cond
        ; if an Activity comes in, return it
        (in? activity-types type) object
        ; if it's an Object, then it should be wrapped in a Create
        (in? object-types type) (activity/create object)
        ; otherwise no idea what it is so return error
        :else { :error "Unsupported object received." }))))
