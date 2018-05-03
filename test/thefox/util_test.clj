(ns thefox.util-test
  (:require [clojure.test :refer :all]
            [thefox.util :refer :all]))

(deftest in?-test
  (let [haystack [1 2 3]]
    (testing "It returns truthy if element is in collection"
      (is (in? haystack 1)))
    (testing "It returns nil if element is not present"
      (is (not (in? haystack 4))))))

(deftest vectorize-test
  (testing "It returns collections as-is"
    (let [thing '(1 2 3)]
      (is (= thing (vectorize thing)))))
  (testing "It wraps not-collections in a vector"
    (let [thing "foo"]
      (is (= [thing] (vectorize thing))))))

(deftest into-vec-test
  (testing "Merges two things into one list using vectorize"
    (let [x :foo y 1]
      (is (= [x y] (into-vec x [y]))))))

(deftest uniq-vec-test
  (testing "It vectorizes a single item"
    (let [x :foo]
      (is (= [x] (uniq-vec x)))))
  (testing "It makes collections unique"
    (is (= [1 2 3] (uniq-vec [1 2 2 3])))))
