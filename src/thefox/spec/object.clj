(ns thefox.spec.object
  (:require [clojure.spec.alpha :as s]
            [thefox.spec.util :refer :all]
            [thefox.spec.image]
            [thefox.spec.link]
            [thefox.spec.actor]
            [thefox.spec.collection]))

; HACK: you can't type in keywords containing @ so it takes hacky workarounds
(def context-kw "thefox.spec.object" "@context"))
; HACK: worse even while spec accepts it when provided to register a spec,
; it can't look it up without using eval around the (s/def)
(eval `(s/def ~context-kw (uri-or-things? map?)))

(s/def ::lang-string
  ; Eg. {:en Hello :sp Hola}. Strictly speaking an rdf:langString.
  ; All Objects are expected to have their keys keywordized.
  (s/map-of keyword? string?))

; FIXME: type can be an array and that should be handled
(s/def ::type string?)

(s/def ::content string?)
(s/def ::contentMap ::lang-string)

(s/def ::name string?)
(s/def ::nameMap ::lang-string)

(s/def ::summary string?)
(s/def ::summaryMap ::lang-string)

(s/def ::startTime inst?)
(s/def ::endTime inst?)
(s/def ::published inst?)
(s/def ::updated inst?)

(s/def ::duration
  (s/and string? #(re-matches duration-regex %)))

(s/def ::object
  (s/keys :req [context-kw ::type]
          :opt [
            ; object fields
            ::attachment ::context ::inReplyTo ::preview ::tag
            ; actor fields
            ::attributedTo ::audience ::generator ::to ::bto ::cc ::bcc
            ; string fields
            ::content ::contentMap ::name ::nameMap ::summary ::summaryMap
            ::mediaType
            ; date fields
            ::startTime ::endTime ::published ::updated ::duration
            ; specific object-only fields
            ::icon ::image ::location ::replies ::url
          ]))

(s/def ::attachment (uri-or-things? ::object))
(s/def ::context (uri-or-thing? ::object))
(s/def ::inReplyTo (uri-or-thing? ::object))
(s/def ::preview (uri-or-thing? ::object))
(s/def ::tag (uri-or-things? ::object))

(s/def ::image (uri-or-thing? :thefox.spec.image/image))
(s/def ::icon (uri-or-thing? :thefox.spec.image/image))

(s/def ::attributedTo (uri-or-things? :thefox.spec.actor/actor))
(s/def ::audience (uri-or-things? :thefox.spec.actor/actor))
(s/def ::generator (uri-or-things? :thefox.spec.actor/actor))
(s/def ::to (uri-or-things? :thefox.spec.actor/actor))
(s/def ::bto (uri-or-things? :thefox.spec.actor/actor))
(s/def ::cc (uri-or-things? :thefox.spec.actor/actor))
(s/def ::bcc (uri-or-things? :thefox.spec.actor/actor))

(s/def ::location (uri-or-thing? ::object))
(s/def ::url (uri-or-things? :thefox.spec.link/link))

(s/def ::replies (uri-or-thing? :thefox.spec.collection/collection))
