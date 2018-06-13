(ns thefox.spec.actor
  (:require [clojure.spec.alpha :as s]
            [thefox.core :refer [actor-types]]))

(s/def ::type (into #{} actor-types))
(s/def ::actor (s/and ::object
                      (s/keys :req [::type])))
