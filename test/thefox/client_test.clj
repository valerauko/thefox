(ns thefox.client-test
  (:require [clojure.test :refer :all]
            [thefox.client :refer :all]))

(deftest wrap-create-test
  (let [object { "@context" "https://www.w3.org/ns/activitystreams"
                 :attributedTo "https://example.com/person"
                 :to "https://example.com/other-person"
                 :published "2018-05-03T12:27:00Z" }
        subject (wrap-create object)]
    (testing "It returns a Create Activity"
      (is (= "Create" (:type subject)))
      (is (= "https://www.w3.org/ns/activitystreams" (get subject "@context"))))
    (testing "It copies the Object's attributedTo to its actor"
      (is (= (:attributedTo object) (:actor subject))))
    (testing "It copies the Object's published field"
      (is (= (:published object) (:published subject))))
    (testing "It copies all the recipients from the Object"
      ; REVIEW: not sure if should test all the fields and various combinations
      (is (= (:to object) (:to subject))))))

(deftest consume-test
  (testing "Given an Activity, it returns it in a symbol-keyed hashmap"
    (is (= "Create" (-> "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"type\":\"Create\",\"object\":{\"attributedTo\":\"https://example.com/person\",\"to\":\"https://example.com/other-person\",\"published\":\"2018-05-03T12:27:00Z\",\"type\":\"Note\"},\"actor\":\"https://example.com/person\",\"published\":\"2018-05-03T12:27:00Z\",\"to\":\"https://example.com/other-person\"}"
                        consume :type))))
  (testing "Given an Object, it returns it wrapped in a Create Activity"
    (is (= "Create" (-> "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"attributedTo\":\"https://example.com/person\",\"to\":\"https://example.com/other-person\",\"published\":\"2018-05-03T12:27:00Z\",\"type\":\"Note\"}"
                        consume :type))))
  (testing "Given other things, it returns an error."
    (is (contains? (consume "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"type\":\"Link\"}")
                   :error))))
