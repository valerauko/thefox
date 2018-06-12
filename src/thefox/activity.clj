(ns thefox.activity
  (:require [thefox.util :refer [into-vec uniq-vec]]
            [thefox.core :refer [recipient-keys activity-types]]
            [thefox.object :as obj]))

(def activity
  (merge obj/object
    {:type (into #{} activity-types)
     :actor obj/actor?
     :object obj/object?
     :target obj/object?
     :result obj/object?
     :origin obj/object?
     :instrument obj/object?}))

(defn recipients
  "Extracts the recipients from an Activity"
  [{:keys [object] :as activity}]
  (into {}
    (map (fn [[k v]] [k (uniq-vec v)])
      (merge-with into-vec
        (select-keys activity recipient-keys)
        (if (coll? object) (select-keys object recipient-keys))))))
