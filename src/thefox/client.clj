(ns thefox.client
  (:require [thefox.core :refer :all]
            [thefox.util :refer :all]))

;;
; Handles client-server communications

(defn consume
  """
  Consumes an incoming client request.
  """
  ([body] (consume body {})
  ([body headers]
    ; TODO
    ))

(defn infer
  """
  Checks if an incoming request is an Activity, Actor, Object or something else
  """
  [object]
  (let [type (:type object)]
    (condp in? type
      activity-types :activity
      actor-types :actor
      object-types :object
      nil)) ; use nil for not invalid type things
