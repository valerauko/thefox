(ns thefox.spec.util
  (:require [clojure.spec.alpha :as s]
            [thefox.spec.link :refer [str-uri?]]))

(defn uri-or-thing?
  "Valid if a string URI or something that passes the given predicate."
  [f]
  (s/or
    :uri str-uri?
    :link :thefox.spec.link/link
    :thing f))

(defn uri-or-things?
  "uri-or-thing? for collections"
  [f]
  (s/or
    :thing (uri-or-thing? f)
    :things (s/coll-of (uri-or-thing? f))))

; has to be a valid xml duration https://www.w3.org/TR/xmlschema11-2/#duration
; it's a pretty ridiculous regex but bear with me (copied from the spec above)
(def duration-regex
  #"^-?P(((\d+Y(\d+M)?(\d+D)?|(\d+M)(\d+D)?|(\d+D))(T((\d+H)(\d+M)?(\d+(\.\d+)?S)?|(\d+M)(\d+(\.\d+)?S)?|(\d+(\.\d+)?S)))?)|(T((\d+H)(\d+M)?(\d+(\.\d+)?S)?|(\d+M)(\d+(\.\d+)?S)?|(\d+(\.\d+)?S))))$")
