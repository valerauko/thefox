(ns thefox.spec.collection
  (:require [clojure.spec.alpha :as s]
            [thefox.spec.util :refer :all]
            [thefox.spec.collection]))

(s/def ::type "CollectionPage")
(s/def ::collectionPage
  (s/and ::object
         (s/keys :req [::type ::partOf]
                 :opt [::next ::prev])))
(s/def ::current ::collectionPage)
(s/def ::first ::collectionPage)
(s/def ::last ::collectionPage)
(s/def ::next ::collectionPage)
(s/def ::pref ::collectionPage)
(s/def ::partOf (uri-or-thing? :thefox.spec.collection/collection))
