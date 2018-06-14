(ns thefox.spec.actor
  (:require [clojure.spec.alpha :as s]
            [thefox.core :refer [actor-types]]))

(s/def ::type (into #{} actor-types))
(s/def ::actor (s/and :thefox.spec.object/object
                      (s/keys :req [::type])))
