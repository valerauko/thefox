(ns thefox.actor
  (:require [thefox.server :as :server]))

;;
; ActivityPub Actors

(defn deref
  """
  Dereferences an Actor.
  Looks up the Actor by their ID/URI and returns the Actor retrieved.
  """
  [actor]
  (server/deref actor))
