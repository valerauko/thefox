(ns thefox.spec.activity
  (:require [clojure.spec.alpha :as s
            [thefox.spec.object :as obj]
            [thefox.spec.actor :as actor]))

(s/def ::actor (uri-or-things? ::actor/actor))
(s/def ::instrument (uri-or-things? ::actor/actor))
(s/def ::object (uri-or-things? ::obj/object))
(s/def ::target (uri-or-things? ::obj/object))
(s/def ::result (uri-or-things? ::obj/object))
(s/def ::origin (uri-or-things? ::obj/object))

(s/def ::activity
  (s/and
    :thefox.spec.object/object
    (s/keys :opt [::actor ::object ::target ::result ::origin ::instrument])))
