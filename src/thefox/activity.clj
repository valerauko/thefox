(ns thefox.activity)

;;
; Ref. https://www.w3.org/TR/activitystreams-vocabulary

(def context
  ""
  ; TODO: should inject language when applicable as
  ; { "@context" ["https://www.w3.org/ns/activitystreams"
  ;               { "@language" "en" }] }
  { "@context" "https://www.w3.org/ns/activitystreams" })
