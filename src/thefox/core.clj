(ns thefox.core)

(def activity-types
  "List of Activity types"
  '("Accept" "Add" "Announce" "Arrive" "Block" "Create" "Delete" "Dislike"
    "Flag" "Follow" "Ignore" "Invite" "Join" "Leave" "Like" "Listen" "Move"
    "Offer" "Question" "Reject" "Read" "Remove" "TentativeAccept"
    "TentativeReject" "Travel" "Undo" "Update" "View"))

(def actor-types
  "List of Actor types"
  '("Application" "Group" "Organization" "Person" "Service"))

(def object-types
  "List of Object types"
  '("Article" "Audio" "Document" "Event" "Image" "Note" "Page" "Place" "Profile"
    "Relationship" "Tombstone" "Video"))

(def link-types
  "List of Link types"
  '("Link" "Mention"))

(def recipient-keys
  "List of keys that may hold recipients"
  '(:to :bto :cc :bcc :audience))

(def default-context
  "The default ActivityPub context"
  { "@context" "https://www.w3.org/ns/activitystreams" })
