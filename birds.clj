;; bird problem

;; general
(def run-tests true)

;; Types of Birds and their freqs:
(def always-pick-freq 1.1)
(def likely-pick-freq 0.75)
(def sometimes-pick-freq 0.5)
(def hardly-pick-freq 0.33)
(def never-pick-freq 0.0)
;; todo: pick randomly later
;;(def random-pick-freq (random))

(defn occurence-freq [history]
  (let [matching-freq (filter (fn [x] (= x :picked)) history)
        freq-count (count matching-freq)]
    (/ freq-count (count history))))

(defn to-pick-or-not-to-pick [strat history]
  (let [history-freq (occurence-freq history)
        likelyhood (+ strat history-freq)]
    (if (>= likelyhood 1.0)
      :picked
      :no-picked)))

;; birds
(defn bird [strat]
  (fn [history]
    (to-pick-or-not-to-pick strat history)))

(defn always-bird [] (bird always-pick-freq))
(defn likely-bird [] (bird likely-pick-freq))
(defn sometimes-bird [] (bird sometimes-pick-freq))
(defn hardly-bird [] (bird hardly-pick-freq))
(defn never-bird [] (bird never-pick-freq))

(defn get-action-from-bird [name bird action]
  (let [res (bird action)]
    (println (str "running bird: " name " -- got result: " res))))

;; quick tests
(println (str "starting"))

(defn do-run-tests []
  (get-action-from-bird "always" (always-bird) test-data)
  (get-action-from-bird "likely" (likely-bird) test-data)
  (get-action-from-bird "sometimes" (sometimes-bird) test-data)
  (get-action-from-bird "hardly" (hardly-bird) test-data)
  (get-action-from-bird "never" (never-bird) test-data)
  ;; (get-action-from-bird (random-bird) test-data))
  )

(def test-data '(:picked :picked :no-picked :no-picked))
(when run-tests (do-run-tests))

;; simulate
(def birds [(always-bird)
            (likely-bird) (likely-bird)
            (sometimes-bird) (sometimes-bird) (sometimes-bird)
            (hardly-bird)
            (never-bird) (never-bird)])

;; fold through, acc'iming history events to pass through into the next call.
;; recur it several times to keep simulating over and over
;; (defn simulate
;;   ([] (simulate []))
;;   ([history] ...)

;; exit
(println (str "done"))
