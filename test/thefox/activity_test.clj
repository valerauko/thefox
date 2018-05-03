(ns thefox.activity-test
  (:require [clojure.test :refer :all]
            [thefox.util :refer [in?]]
            [thefox.activity :refer :all]))

(deftest recipients-test
  (let [activity { "@context" "https://www.w3.org/ns/activitystreams"
                   :type "Create"
                   :object { :type "Note"
                             :attributedTo "https://example.com/user"
                             :id "https://example.com/user/statuses/foo"
                             :published "2018-05-03T12:27:00Z"
                             :to ["https://www.w3.org/ns/activitystreams#Public"]
                             :bcc "https://example.com/other-user" }
                   :actor "https://example.com/user"
                   :published "2018-05-03T12:27:00Z"
                   :to ["https://www.w3.org/ns/activitystreams#Public"]
                   :cc ["https://example.com/user/followers"] }
        subject (recipients activity)]
    (testing "It collects the recipient fields from the Activity"
      (is (in? (:to subject) (-> activity :to first))))
    (testing "It collects the recipient fields from its Object"
      (is (in? (:bcc subject) (-> activity :object :bcc))))
    (testing "It makes the fields unique"
      (is (= (-> subject :to frequencies vals distinct) [1])))
    (let [activity (assoc activity :object
                                   "https://example.com/user/statuses/foo")
          subject (recipients activity)]
      (testing "If object is an URI, it does not get resolved"
        (is (not (contains? subject :bcc)))))))
