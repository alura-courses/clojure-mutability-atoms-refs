(ns hospital.logic)

(defn fits-in-queue?
  [hospital department]
  (-> hospital
      (get,,, department)
      count,,,
      (<,,, 5))
  )

(defn arrived-at
  [hospital department person]

    (if (fits-in-queue? hospital department)
      (update hospital department conj person)
      (throw (ex-info "The queue is full" {:trying-to-add person})))
  )

(defn arrived-at-with-pauses
  [hospital department person]
  (Thread/sleep (* (rand) 2000))
  (if (fits-in-queue? hospital department)
    (do
      ;(Thread/sleep 1000)
      (update hospital department conj person))
    (throw (ex-info "The queue is full" {:trying-to-add person})))
  )

(defn arrived-at-with-pauses-and-log
  [hospital department person]
  (println "Trying to add person" person)
  (Thread/sleep (* (rand) 2000))
  (if (fits-in-queue? hospital department)
    (do
      ;(Thread/sleep 1000)
      (println "Performing update for person" person)
      (update hospital department conj person))
    (throw (ex-info "The queue is full" {:trying-to-add person})))
  )

(defn was-attended-to
  [hospital department]
  (update hospital department pop)
  )


(defn complete-was-attended-to
  [hospital department]
  {:patient (update hospital department peek)
   :hospital (update hospital department pop)}
  )

(defn complete-was-attended-to-with-juxtaposition
  [hospital department]
  (let [queue (get hospital department)
        peek-pop (juxt peek pop)
        [patient updated-queue] (peek-pop queue)
        updated-hospital (update hospital assoc department updated-queue)]

    {:patient patient
     :hospital   updated-hospital})
  )

(defn next-patient
  [hospital department]
  (-> hospital
      department
      peek)
  )

(defn transfer
  [hospital from to]

  (let [patient (next-patient hospital from)]
    (-> hospital
        (was-attended-to from)
        (arrived-at to patient))
    )
  )