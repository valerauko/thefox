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

(defn deref
  "Dereferences an object based on its ID/URI.
  If provided an URI, look that up.
  Otherwise, dereference the :id of the provided object."
  ; TODO: handle local things
  ; REVIEW: it collides with clojure's deref, is that a problem?
  ;         if so, what to name it instead?
  [object]
  (if (string? object)
    (request/parse object)
    (deref (:id object))))
