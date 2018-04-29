(ns thefox.request)

(def user-agent
  "TheFox/0.0.1")

(def content-type
  ; TODO: application/activity+json is valid too
  "application/ld+json; profile=\"https://www.w3.org/ns/activitystreams\"")

(def allowed-schemes
  "At this point only handle HTTP(S)"
  ["http" "https"])
