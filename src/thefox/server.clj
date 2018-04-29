(ns thefox.server
  (:require [thefox.request]))

;;
; Handles server-server communications (federation)

(defn consume
  """
  Consumes an incoming federated request.
  """
  ([body] (consume body {}))
  ([body headers]
    ; TODO
    ))

(defn deref
  """
  Dereferences an object based on its ID/URI.
  If provided an URI, look that up.
  Otherwise, dereference the :id of the provided object.
  """
  [object]
  (if (string? object)
    ; TODO: (request object)
    (deref (:id object))))
