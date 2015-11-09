(ns clojure-presentation.demo.basic-test
  (:require [clojure.test :refer :all]
            [clojure-presentation.demo.basic :refer :all]))

(deftest divide?-test
  (testing "numbers that acutally divides others"
    (is (true? (divides? 5 10)))
    (is (true? (divides? 2 10)))
    (is (true? (divides? 1 10))))

  (testing "numbers that don't divides others"
    (is (false? (divides? 3 10)))
    (is (false? (divides? 2 1)))
    (is (false? (divides? 6 20)))))

(deftest prime?-test
  (testing "prime numbers"
    (is (prime? 5))
    (is (prime? 2))
    (is (prime? 11))
    (is (prime? 13))
    (is (prime? 73)))

  (testing "not prime numbers"
    (is (not (prime? 4)))
    (is (not (prime? 65)))
    (is (not (prime? 33)))))
