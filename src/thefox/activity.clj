(ns thefox.activity
  (:require [thefox.util :refer [into-vec uniq-vec]]
            [thefox.core :refer [default-context recipient-keys]]))

(defn recipients
  "Extracts the recipients from an Activity"
  [{:keys [object] :as activity}]
  (into {}
    (map (fn [[k v]] [k (uniq-vec v)])
      (merge-with into-vec
        (select-keys activity recipient-keys)
        (if (coll? object) (select-keys object recipient-keys))))))

; REVIEW: What to do with timestamps? Should we add published automatically?

(defn make-activity
  [type actor object options]
  (merge
    (or (get object "@context") default-context)
    { :type type :actor actor :object object }
    options))

(defn announce
  [actor object options]
  (make-activity "Announce" actor object options))

; some more meaningful Announce aliases
(def share announce)
(def recommend announce)

(defn create
  [object options]
  (make-activity "Create" (:attributedTo object) object
    (merge (select-keys object recipient-keys)
           options)))

(defn update
  [actor object options]
  (make-activity "Update" actor object options))

(defn delete
  "The distinction between Delete and Remove is that Delete implies the
  destruction of the Object, while Remove shows that it is no longer a
  member of a Collection."
  [actor object options]
  (make-activity "Delete" actor object options)

(defn undo
  "Undo is basically Delete for Activities.
  Please don't ask what should happen if you Undo an Undo."
  [activity]
  (make-activity "Undo"
    (:actor activity)
    (:id activity)
    options))

; Using upvote/downvote instead of the ActivityPub spec's Like/Dislike
; because Dislike is prone to be mistaken for Undoing a Like, which it is not
; intended to be. Upvote/downvote makes the distinction more obvious.
; Like/Dislike are aliased.
; https://www.w3.org/TR/activitystreams-vocabulary/#inverse
(defn upvote
  [actor object options]
  (make-activity "Like" actor object options))

(def like upvote)

(defn downvote
  [actor object options]
  (make-activity "Dislike" actor object options))

(def dislike downvote)

(defn listen
  [actor object options]
  (make-activity "Listen" actor object options))

(defn read
  [actor object options]
  (make-activity "Read" actor object options))

(defn view
  [actor object options]
  (make-activity "View" actor object options))

(def watch view)
