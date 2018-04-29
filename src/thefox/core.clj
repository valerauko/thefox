(ns thefox.core)

(def activity-types
  "List of Activity types"
  '(Accept Add Announce Arrive Block Create Delete Dislike Flag Follow Ignore
    Invite Join Leave Like Listen Move Offer Question Reject Read Remove
    TentativeAccept TentativeReject Travel Undo Update View))

(def actor-types
  "List of Actor types"
  '(Application Group Organization Person Service))

(def object-types
  ; REVIEW: Links might be better of separate?
  "List of Object and Link types"
  '(Article Audio Document Event image Note Page Place Profile Relationship
    Tombstone Video))
