(ns hospital.lesson4
  (:use [clojure pprint])
  (:require [hospital.model :as h.model])
  (:require [hospital.logic :as h.logic]))

(defn not-so-terrible-arrived-at!
  [hospital person]
  (swap! hospital h.logic/arrived-at :g-queue person)
  (println "After inserting" person)
  )

(defn simulates-a-day-in-parallel-with-mapv
  []
  (let [hospital (atom (h.model/new-hospital))
        patients ["111","222","333","444","555","666"]]

    (mapv #(.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital %)))) patients)

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

;(simulates-a-day-in-parallel-with-mapv)

(defn simulates-a-day-in-parallel-with-mapv-refactored
  []
  (let [hospital (atom (h.model/new-hospital))
        patients ["111","222","333","444","555","666"]
        will-start-thread #(.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital %))))]

    (mapv will-start-thread patients)

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

;(simulates-a-day-in-parallel-with-mapv-refactored)


(defn simulates-a-day-in-parallel-with-mapv-extracted
  []
  (let [hospital (atom (h.model/new-hospital))
        patients ["111","222","333","444","555","666"]
        will-start (will-start-thread hospital)]

    (mapv will-start patients)

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

;(simulates-a-day-in-parallel-with-mapv-extracted)

(defn will-start-thread
  [hospital patient]
   (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital patient)))))

(defn simulates-a-day-in-parallel-with-partial
  []
  (let [hospital (atom (h.model/new-hospital))
        patients ["111","222","333","444","555","666"]
        will-start (partial will-start-thread hospital)]

    (println (mapv will-start patients))

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

;(simulates-a-day-in-parallel-with-partial)

(defn simulates-a-day-in-parallel-with-doseq
  []
  (let [hospital (atom (h.model/new-hospital))
        patients (range 6)]

    (doseq [patient patients]
      (will-start-thread hospital patient))

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

;(simulates-a-day-in-parallel-with-doseq)

(defn simulates-a-day-in-parallel-with-dotimes
  []
  (let [hospital (atom (h.model/new-hospital))]

    (dotimes [patient 6]
      (will-start-thread hospital patient))

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))
    )
  )

(simulates-a-day-in-parallel-with-dotimes)