(ns thefox.spec.image
  (:require [clojure.spec.alpha :as s]
            [thefox.core :refer :all]
            [thefox.spec.util :refer :all]
            [thefox.spec.object]))

(s/def ::type (into #{} link-types))
(s/def ::href str-uri?)
; no idea how to validate language codes
(s/def ::hreflang string?)
; supposedly rfc5988/html5 specified relation
(s/def ::rel string?)
(s/def ::height pos-int?)
(s/def ::width pos-int?)
; Link is disjoint with Object
(s/def ::link
  (s/keys :req [context-kw ::type]
          :opt [::href ::hreflang ::rel ::height ::width ::preview
                :thefox.spec.object/mediaType :thefox.spec.object/name
                :thefox.spec.object/nameMap]))
