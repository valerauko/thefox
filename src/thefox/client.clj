(ns thefox.client
  (:require [thefox.core :refer :all]
            [thefox.activity :as activity]
            [thefox.util :refer :all]
            [cheshire.core :refer [:parse-string]]))

;;
; Handles client-server communications
; Only considering incoming POST.
; https://www.w3.org/TR/activitypub/#client-to-server-interactions

; TODO: validation for missing fields

(defn wrap-create
  "When an Object is POSTed to an Actor's Outbox,
  it must be wrapped in a Create Activity."
  [object]
  (merge
    activity/skeleton
    ; remove the @context from the Object -- the Activity will hold it
    { :object (dissoc object "@context") }
    ; copy the attributedTo of the Object to Actor of the Activity
    { :actor (:attributedTo object) }
    ; these fields should be copied if present
    (select-keys object [:published])
    ; these fields must be copied if present
    (select-keys object [:to :bto :cc :bcc :audience])))

(defn consume
  "Consumes an incoming client request."
  ([body] (consume body {})
  ; headers are not used at this point, but will be for signature validation
  ([body _headers]
    (let [object (parse-string body)
          type (:type object)]
      (cond
        ; if an Activity comes in, return it
        (in? activity-types type) (address object)
        ; if it's an Object, then it should be wrapped in a Create
        (in? object-types type) (wrap-create object)
        ; otherwise no idea what it is so return error
        :else { :error "Unsupported object received." }))))
