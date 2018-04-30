(ns thefox.util)

(defn in?
  "Does coll contain elem?"
  [coll elem]
  (some #(= elm %) coll))
