(ns thefox.object
  (:require [clojure.spec.alpha :as s]
            [thefox.core :refer :all]))

;;
; ActivityPub Objects and Links

(defn str-uri?
  "Checks whether a string can be a valid URI."
  [string]
  (try (uri? (new java.net.URI string))
       (catch Exception e false)))

(defn uri-or-thing?
  "Valid if a string URI or something that passes the given predicate."
  [f]
  (s/or
    :uri str-uri?
    :thing f))

(defn uri-or-things?
  "uri-or-thing? for collections"
  [f]
  (s/or
    :thing (uri-or-thing? f)
    :things (s/coll-of (uri-or-thing? f)))

; HACK: you can't type in keywords containing @ so it takes hacky workarounds
(def context-kw (keyword (str *ns*) "@context"))
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

; has to be a valid xml duration https://www.w3.org/TR/xmlschema11-2/#duration
; it's a pretty ridiculous regex but bear with me (copied from the spec above)
(def duration-regex
  #"^-?P(((\d+Y(\d+M)?(\d+D)?|(\d+M)(\d+D)?|(\d+D))(T((\d+H)(\d+M)?(\d+(\.\d+)?S)?|(\d+M)(\d+(\.\d+)?S)?|(\d+(\.\d+)?S)))?)|(T((\d+H)(\d+M)?(\d+(\.\d+)?S)?|(\d+M)(\d+(\.\d+)?S)?|(\d+(\.\d+)?S))))$")
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
(s/def ::context ::object)
(s/def ::inReplyTo ::object)
(s/def ::preview ::object)
(s/def ::tag (uri-or-things? ::object))

(s/def ::replies ::colletion)

(s/def :image/type "Image")
(s/def ::image (s/and ::object
                      (s/keys :req [:image/type])))
(s/def ::icon ::image)

(s/def :actor/type (into #{} actor-types))
(s/def ::actor (s/and ::object
                      (s/keys :req [:actor/type])))

(s/def :link/type (into #{} link-types))
(s/def ::href str-uri?)
; no idea how to validate language codes
(s/def ::hreflang string?)
; supposedly rfc5988/html5 specified relation
(s/def ::rel string?)
(s/def ::height pos-int?)
(s/def ::width pos-int?)
; Link is disjoint with Object
(s/def ::link
  (s/keys :req [context-kw :link/type]
          :opt [::href ::hreflang ::rel ::mediatype ::name ::nameMap
                ::height ::width ::preview]))

(s/def ::attributedTo (uri-or-things? ::actor))
(s/def ::audience (uri-or-things? ::actor))
(s/def ::generator (uri-or-things? ::actor))
(s/def ::to (uri-or-things? ::actor))
(s/def ::bto (uri-or-things? ::actor))
(s/def ::cc (uri-or-things? ::actor))
(s/def ::bcc (uri-or-things? ::actor))

(s/def ::location ::object)
(s/def ::url (uri-or-things? ::link))

(s/def :collection/type (into #{} collection-types))
(s/def ::totalItems nat-int?)
(s/def ::items (uri-or-things? ::object))
; it can be sorted by just about anything so can't really validate that
(s/def ::orderedItems (uri-or-things? ::object))
(s/def ::collection
  (s/and ::object
         (s/keys :req [:collection/type]
                 :opt [::totalItems ::current ::first ::last
                       ::items ::orderedItems])))

(s/def :collectionPage/type "CollectionPage")
(s/def ::collectionPage
  (s/and ::object
         (s/keys :req [::partOf]
                 :opt [::next ::prev])))
(s/def ::current ::collectionPage)
(s/def ::first ::collectionPage)
(s/def ::last ::collectionPage)
(s/def ::next ::collectionPage)
(s/def ::pref ::collectionPage)
(s/def ::partOf ::collection)
