(ns thefox.spec.link
  (:require [clojure.spec.alpha :as s]
            [thefox.core :refer :all]))

(defn str-uri?
  "Checks whether a string can be a valid URI."
  [string]
  (try (uri? (new java.net.URI string))
       (catch Exception e false)))

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
  (s/keys :req [(keyword "thefox.spec.object" "@context") ::type]
          :opt [::href ::hreflang ::rel ::height ::width ::preview
                :thefox.spec.object/mediaType :thefox.spec.object/name
                :thefox.spec.object/nameMap]))
