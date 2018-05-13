(ns thefox.server
  (:require [thefox.core :refer [recipient-keys]]
            [thefox.request :as request]))

;;
; Handles server-server communications (federation)

(defn consume
  "Consumes an incoming federated request."
  ([body] (consume body {}))
  ([body headers]
    ; TODO
    ))

(defn local?
  "Determines whether an Object is local based on its ID."
  [object]
  (or
    ; if an Object has no ID, we consider it local
    ; because it was probably just made
    (nil? (:id object))
    ; TODO: consider it local if no host in url (need an url lib)
    ; TODO: consider if local if the host is the configured host (need to handle config)
    ))

(defn should-forward?
  "Rules: https://www.w3.org/TR/activitypub/#inbox-forwarding
  Assuming that you've already checked step 1 (whether this is the first time
  the server sees this Activity)."
  [activity]
  ; TODO
  )

(defn lookup
  "Dereferences an object based on its ID/URI.
  If provided an URI, look that up.
  Otherwise, dereference the :id of the provided object."
  ; TODO: handle local things
  [object]
  (if (string? object)
    (request/parse object)
    (lookup (:id object))))
