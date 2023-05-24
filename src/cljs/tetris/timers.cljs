(ns tetris.timers
  (:require [tetris.keys :as keys]
            [tetris.logic :as logic]))

(def step-timer (atom nil))

(def initial-step-interval-ms 1000)
(def step-interval-ms (atom initial-step-interval-ms))
(def speed-up-factor 0.80)

(defn establish-initial-step-timer! []
  (when-not @step-timer
    (reset! step-timer (.setInterval
                        js/window
                        (fn [] (keys/dispatch keys/down))
                        initial-step-interval-ms))))

(def next-tetrimino-scheduled (atom nil))
(def next-tetrimino-grace-ms 500)

(defn play-next-tetrimino
  [game-state before-game-state]
  (reset! next-tetrimino-scheduled
          (.setTimeout
           js/window
           #(do
              (reset! game-state (logic/play-next-tetrimino @game-state))
              (reset! next-tetrimino-scheduled nil)
              (when-let [active-step-timer @step-timer]
                (.clearTimeout js/window active-step-timer)
                (reset! step-timer (.setInterval
                                    js/window
                                    (fn [] (keys/dispatch keys/down))
                                    (Math/floor (* initial-step-interval-ms
                                                   (Math/pow speed-up-factor
                                                             (:game-level @game-state))))))))
           next-tetrimino-grace-ms))
  before-game-state)

(defn play-next-tetrimino-debounce! [game-state]
  (when @next-tetrimino-scheduled
    (.clearTimeout js/window @next-tetrimino-scheduled)
    (reset! next-tetrimino-scheduled (.setTimeout
                                      js/window
                                      #(do
                                         (reset! game-state (logic/play-next-tetrimino @game-state))
                                         (reset! next-tetrimino-scheduled nil))
                                      next-tetrimino-grace-ms))))

(defn clear-all! []
  (when-let [active-step-timer @step-timer]
    (.clearTimeout js/window active-step-timer)
    (reset! step-timer nil))

  (when-let [active-next-tetrimino-scheduled @next-tetrimino-scheduled]
    (.clearTimeout js/window active-next-tetrimino-scheduled)
    (reset! next-tetrimino-scheduled nil)))
