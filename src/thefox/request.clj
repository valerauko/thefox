(ns thefox.request
  (:require [clj-http.client :as http-client]
            [cheshire.core :refer [parse-string]]))

(def user-agent
  "TheFox/0.0.1")

(def content-type
  ; POST requests (eg. to the inbox) MUST be made with a Content-Type of
  ; application/ld+json; profile="https://www.w3.org/ns/activitystreams" and
  ; GET requests with an Accept header of
  ; application/ld+json; profile="https://www.w3.org/ns/activitystreams".
  ; Servers SHOULD interpret a Content-Type or Accept header of
  ; application/activity+json as equivalent to
  ; application/ld+json; profile="https://www.w3.org/ns/activitystreams"
  ; for server-to-server interactions.
  "application/ld+json; profile=\"https://www.w3.org/ns/activitystreams\"")

(def allowed-schemes
  "At this point only handle HTTP(S)"
  ["http" "https"])

(defn fetch
  [uri]
  (http-client/get uri {:accept content-type
                        :client-params {"http.useragent" user-agent}}))

(defn parse
  [uri]
  (-> uri fetch :body (parse-string true)))
