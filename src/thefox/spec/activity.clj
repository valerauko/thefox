(ns thefox.spec.activity
  (:require [clojure.spec.alpha :as s
            [thefox.spec.object]
            [thefox.spec.actor]))

(s/def ::actor (uri-or-things? :thefox.spec.actor/actor))
(s/def ::instrument (uri-or-things? :thefox.spec.actor/actor))
(s/def ::object (uri-or-things? :thefox.spec.object/object))
(s/def ::target (uri-or-things? :thefox.spec.object/object))
(s/def ::result (uri-or-things? :thefox.spec.object/object))
(s/def ::origin (uri-or-things? :thefox.spec.object/object))

(s/def ::activity
  (s/and
    ::object
    (s/keys :opt [::actor ::object ::target ::result ::origin ::instrument])))
