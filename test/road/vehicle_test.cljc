(ns road.vehicle-test
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.spec.test.alpha :as stest]
   [clojure.test :refer [are deftest is testing]]
   [road.vehicle :as rv]))

(deftest data-spec-generators
  (testing "generation of values conforming to data specs."
    (are [spec] (= 10 (count (gen/sample (s/gen spec) 10)))
      ::rv/wmi
      ::rv/vds
      ::rv/vis
      ::rv/vin
      ::rv/manufacturer
      ::rv/vehicle)))

(deftest check-fns
  (testing "function specs conformance"
    (is (= {:total 1
            :check-passed 1}
           (stest/summarize-results
            (stest/check `[rv/decode]))))))

(deftest mazda-6-test
  (is (= #::rv{:vin "JMZGJ627661337940"
               :wmi "JMZ"
               :vds "GJ6276"
               :vis "61337940"
               :manufacturer
               #:road.vehicle.manufacturer{:id "JMZ"
                                           :name "Mazda"
                                           :country "Japan"
                                           :region "Asia"}}
         (rv/decode "JMZGJ627661337940"))))