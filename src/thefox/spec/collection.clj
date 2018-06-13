(ns thefox.spec.collection
  (:require [clojure.spec.alpha :as s]
            [thefox.core :refer [collection-types]]
            [thefox.spec.util :refer :all]))

(s/def ::type (into #{} collection-types))
(s/def ::totalItems nat-int?)
(s/def ::items (uri-or-things? :thefox.spec.object/object))
; it can be sorted by just about anything so can't really validate that
(s/def ::orderedItems (uri-or-things? :thefox.spec.object/object))
(s/def ::collection
  (s/and ::object
         (s/keys :req [::type]
                 :opt [::totalItems ::current ::first ::last
                       ::items ::orderedItems])))
