(ns thefox.spec.collection-page
  (:require [clojure.spec.alpha :as s]
            [thefox.spec.util :refer :all]
            [thefox.spec.collection :as collection]))

(s/def ::type "CollectionPage")
(s/def ::collectionPage
  (s/and :thefox.spec.object/object
         (s/keys :req [::type ::partOf]
                 :opt [::next ::prev])))
(s/def ::current ::collectionPage)
(s/def ::first ::collectionPage)
(s/def ::last ::collectionPage)
(s/def ::next ::collectionPage)
(s/def ::pref ::collectionPage)
(s/def ::partOf (uri-or-thing? ::collection/collection))
