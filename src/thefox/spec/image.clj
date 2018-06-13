(ns thefox.spec.image
  (:require [clojure.spec.alpha :as s]))

(s/def ::type "Image")
(s/def ::image (s/and :thefox.spec.object/object
                      (s/keys :req [::type])))
