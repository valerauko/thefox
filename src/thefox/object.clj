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

(def context
  "Has to be either an URI, or an array of URIs and maps
   {@context uri?} {@context [uri? {@language lang?}]} etc"
  (s/or
    :uri str-uri?
    (s/coll-of (s/or :uri str-uri? :map map?))))

(s/def ::natural-string?
  "Eg. {en Hello sp Hola}"
  [key]
  {string? string?})

(defn link?
  [thing]
  (or
    {}))

(defn object?
  [thing]
  true)

(defn actor?
  [thing]
  )

(defn duration?
  [thing]
  true)

(def object
  {"@context" context
   :type string?
   :attachment object? ; or plural
   :attributedTo actor?
   :audience actor? ; or plural
   :content string?
   :contentMap ::natural-string?
   :context object?
   :name string?
   :nameMap ::natural-string?
   :startTime date-time?
   :endTime date-time?
   :generator object?
   :icon image?
   :image image?
   :inReplyTo object?
   :location place? ; or plural
   :preview object?
   :published date-time?
   :replies colletion?
   :summary string?
   :summaryMap ::natural-string?
   :tag object? ; or plural
   :updated date-time?
   :url link? ; or plural
   :to actor? ; or plural
   :bto actor? ; or plural
   :cc actor? ; or plural
   :bcc actor? ; or plural
   :mediaType string? ; mime type default text/html
   :duration duration?})

(def link
  {"@context" context
   :type "Link"
   :href uri?
   :hreflang lang?
   :rel rel?
   :mediaType string?
   :name string?
   :nameMap natural-string?
   :height pos-int?
   :width pos-int?
   :preview object?})

(def person
  (merge object
    {:type "Person"}))

(def application
  (merge object
    {:type "Application"}))

(def group
  (merge object
    {:type "Group"}))

(def organization
  (merge object
    {:type "Organization"}))

(def service
  (merge object
    {:type "Service"}))
