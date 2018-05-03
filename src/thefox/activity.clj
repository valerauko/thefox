(ns thefox.activity
  (:require [thefox.util :refer [into-vec uniq-vec]]
            [thefox.core :refer [recipient-keys]]))

(def skeleton
  {
    ; TODO: should inject language when applicable as
    ; { "@context" ["https://www.w3.org/ns/activitystreams"
    ;               { "@language" "en" }] }
    "@context" "https://www.w3.org/ns/activitystreams"
    :type "Activity" })

(defn recipients
  "Extracts the recipients from an Activity"
  [{:keys [object] :as activity}]
  (into {}
    (map (fn [[k v]] [k (uniq-vec v)])
      (merge-with into-vec
        (select-keys activity recipient-keys)
        (if (coll? object) (select-keys object recipient-keys))))))
