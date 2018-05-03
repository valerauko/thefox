(ns thefox.activity)

;;
; Ref. https://www.w3.org/TR/activitystreams-vocabulary

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
        (select-keys object recipient-keys)))))