(ns hospital.collections
  (:use [clojure pprint]))

(println "\n\n\nVECTOR")
(defn test-vector
  []
  (let [g-queue [111 222]]
    (println g-queue)
    (println (conj g-queue 333))
    (println (conj g-queue 444))
    (println (pop g-queue)))
  )

(test-vector)

(println "\n\n\nLIST")
(defn test-list
  []
  (let [g-queue '(111 222)]
    (println g-queue)
    (println (conj g-queue 333))
    (println (conj g-queue 444))
    (println (pop g-queue)))
  )

(test-list)

(println "\n\n\nSET")
(defn test-set
  []
  (let [g-queue #{111 222}]
    (println g-queue)
    (println (conj g-queue 111))
    (println (conj g-queue 333))
    (println (conj g-queue 444))
    ;(println (pop g-queue))
    )
  )

(test-set)


(println "\n\n\nQUEUE")
(defn test-queue
  []
  (let [g-queue (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println (seq g-queue))
    (println (seq (conj g-queue "333")))
    (println (seq (pop g-queue)))
    (println (peek g-queue))
    (pprint g-queue)
    )
  )

(test-queue)