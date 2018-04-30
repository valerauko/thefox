(ns thefox.util)

(defn in?
  "Does coll contain elem?"
  [coll elem]
  (some #(= elm %) coll))

(defn vectorize
  "If x is not a collection, wraps it in a vector."
  [x]
  (if (coll? x) x [x]))

(defn into-vec
  "Combines two things into one vector. Both may or may not be collections."
  [x y]
  (into (vectorize x) (vectorize y)))

(defn uniq-vec
  "Turns a thing into a vector of unique things."
  [x]
  (distinct (vectorize x)))
