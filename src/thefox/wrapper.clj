(ns thefox.wrapper
  (:require [thefox.signatures :as sign]
            [clojure.string :as str]))

(defn verify-header-signature
  [request user-lookup]
  (let [sig-header (-> request :headers :signature)
        sig-fields (into {} (map #(str/split % #"=")
                                 (str/split sig-header #",")))
        to-check (str/split (get "headers" (keys sig-fields) "date") #" ")]
    nil))

(defn wrapper
  [handler options]
  (fn [request]
    (verify-header-signature request (:user-lookup options))))

(defn signing-string
  [all-headers which-headers]
  (str/join "\n"
    (map
      (fn [header] (str header ": " (get header all-headers)))
      which-headers)))
