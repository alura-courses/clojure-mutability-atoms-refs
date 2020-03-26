(ns hospital.lesson3
  (:use [clojure pprint])
  (:require [hospital.model :as h.model])
  (:require [hospital.logic :as h.logic]))

(def my-name "william")
(def my-name "redefined")
(def my-name 12345)

(let [my-name "william"]
  ;task1
  ;task2
  (println my-name)
  ;;;;;
  (let [my-name "daniela"]
    ;task3
    ;task4
    (println my-name)
    )
  ;;;;;;;
  (println my-name)
  )

;(def hospital-grey (atom {}))

(defn test-atom
  []
  (let [hospital-grey (atom {:g-queue h.model/empty-queue})]
    (println hospital-grey)
    (pprint hospital-grey)
    (pprint (deref hospital-grey))
    (pprint @hospital-grey)

    (pprint (assoc @hospital-grey :lab1 h.model/empty-queue))
    (pprint @hospital-grey)

    (swap! hospital-grey assoc :lab1 h.model/empty-queue)
    (pprint @hospital-grey)
    (swap! hospital-grey assoc :lab2 h.model/empty-queue)
    (pprint @hospital-grey)

    ;normal immutable update, with dereference, no effect
    (update @hospital-grey :lab1 conj "111")

    (swap! hospital-grey update :lab1 conj "111")
    (pprint hospital-grey)
    )
  )

;(test-atom)

(defn terrible-arrived-at!
  [hospital person]
  (swap! hospital h.logic/arrived-at-with-pauses-and-log :g-queue person)
  (println "After inserting" person)
  )

(defn simulates-a-day-in-parallel
  []
  (let [hospital (atom (h.model/new-hospital))]
    (.start (Thread. (fn [] (terrible-arrived-at! hospital "111"))))
    (.start (Thread. (fn [] (terrible-arrived-at! hospital "222"))))
    (.start (Thread. (fn [] (terrible-arrived-at! hospital "333"))))
    (.start (Thread. (fn [] (terrible-arrived-at! hospital "444"))))
    (.start (Thread. (fn [] (terrible-arrived-at! hospital "555"))))
    (.start (Thread. (fn [] (terrible-arrived-at! hospital "666"))))

    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))
    )
  )

;(simulates-a-day-in-parallel)


(defn not-so-terrible-arrived-at!
  [hospital person]
  (swap! hospital h.logic/arrived-at :g-queue person)
  (println "After inserting" person)
  )

(defn simulates-a-day-in-parallel
  []
  (let [hospital (atom (h.model/new-hospital))]
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "111"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "222"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "333"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "444"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "555"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "666"))))

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

(simulates-a-day-in-parallel)