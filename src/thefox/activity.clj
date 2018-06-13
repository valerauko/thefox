(ns thefox.activity
  (:require [thefox.util :refer [into-vec uniq-vec]]
            [thefox.core :refer [recipient-keys activity-types]]))

(defn recipients
  "Extracts the recipients from an Activity"
  [{:keys [object] :as activity}]
  (into {}
    (map (fn [[k v]] [k (uniq-vec v)])
      (merge-with into-vec
        (select-keys activity recipient-keys)
        (if (coll? object) (select-keys object recipient-keys))))))
