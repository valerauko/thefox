(ns thefox.spec.link
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [thefox.core :refer :all]))

(defn uri-gen
  "URI generator for ::str-uri"
  []
  (gen/fmap str (s/gen uri?)))

(s/def ::str-uri
  (s/with-gen
    #(try (uri? (new java.net.URI %))
          (catch Exception e false))
    uri-gen))

(s/def ::type (into #{} link-types))
(s/def ::href ::str-uri)
; no idea how to validate language codes
(s/def ::hreflang string?)
; supposedly rfc5988/html5 specified relation
(s/def ::rel string?)
(s/def ::height pos-int?)
(s/def ::width pos-int?)
; Link is disjoint with Object
(s/def ::link
  (s/keys :req [(keyword "thefox.spec.object" "@context") ::type]
          :opt [::href ::hreflang ::rel ::height ::width
                :thefox.spec.object/mediaType :thefox.spec.object/preview
                :thefox.spec.object/name :thefox.spec.object/nameMap]))
