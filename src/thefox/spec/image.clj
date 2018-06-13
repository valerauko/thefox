(ns thefox.spec.image
  (:require [clojure.spec.alpha :as s
            [thefox.spec.object]))

(s/def ::type "Image")
(s/def ::image (s/and :thefox.spec.object/object
                      (s/keys :req [::type])))
