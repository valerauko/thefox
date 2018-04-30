(ns thefox.server
  (:require [thefox.core :refer [:recipient-keys]]
            [thefox.request]
            [thefox.util :refer [:into-vec :uniq-vec]]))

;;
; Handles server-server communications (federation)

(defn consume
  "Consumes an incoming federated request."
  ([body] (consume body {}))
  ([body headers]
    ; TODO
    ))

(defn recipients
  "Extracts the recipients from an Activity"
  [{:keys [object] :as activity}]
  (into {}
    (map (fn [[k v]] [k (uniq-vec v)])
      (merge-with into-vec
        (select-keys activity recipient-keys)
        (select-keys object recipient-keys)))))

(defn deref
  "Dereferences an object based on its ID/URI.
  If provided an URI, look that up.
  Otherwise, dereference the :id of the provided object."
  [object]
  (if (string? object)
    ; TODO: (request object)
    (deref (:id object))))
