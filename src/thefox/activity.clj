(ns thefox.activity
  (:require [thefox.util :refer [into-vec uniq-vec]]
            [thefox.core :refer [default-context recipient-keys]]))

(defn recipients
  "Extracts the recipients from an Activity"
  [{:keys [object] :as activity}]
  (into {}
    (map (fn [[k v]] [k (uniq-vec v)])
      (merge-with into-vec
        (select-keys activity recipient-keys)
        (if (coll? object) (select-keys object recipient-keys))))))

(defn create
  "Makes a Create Activity for an Object"
  [object, options]
  (merge
    { :type "Create"
      :object object }
    (or (get object "@context") default-context)
    (select-keys object recipient-keys)
    (select-keys object [:published])
    options))
