(ns tictactoe-my.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "I changed some test")

;; define your app data so that it doesn't get over-written on reload

(defn new-board [n]
  (vec (repeat n (vec (repeat n "B")))))

(def board-size 9)

(defonce app-state (atom {:text "Welcome to tic tac toe"
                          :board (new-board board-size)}))

(defn computer-move []
  (let [board (:board @app-state)
        remaining-spots (for [i (range board-size)
                              j (range board-size)
                              :when (= "B" (get-in board [j i]))]
                          [i j])
        move (rand-nth remaining-spots)
        path (into [:board] (reverse move))]
    (swap! app-state assoc-in path "C")))

(defn blank [i j]
  [:rect {:width 0.9
          :height 0.9
          :fill "grey"
          :x (+ 0.05 i)
          :y (+ 0.05 j)
          :on-click
          (fn rect-click [e]
            (swap! app-state assoc-in [:board j i] "P")
            (computer-move))}])

(defn circle [i j]
  [:circle
   {:r 0.45
    :fill "green"
    :cx (+ 0.5 i)
    :cy (+ 0.5 j)}])

(defn cross [i j]
  [:g {:stroke "darkred"
       :stroke-width 0.25
       :stroke-linecap "round"
       :transform
       (str "translate(" (+ 0.25 i) "," (+ 0.25 j) ") "
         "scale(0.5)")}
   [:line {:x1 0 :y1 0 :x2 1 :y2 1}]
   [:line {:x1 1 :y1 0 :x2 0 :y2 1}]])

(defn tictactoe []
  [:center
   [:h1 (:text @app-state)]
   (into 
     [:svg
      {:view-box (str  "0 0 " board-size " " board-size)
       :width 500
       :height 500}]
     (for [i (range board-size)
           j (range board-size)]
       (case (get-in @app-state [:board j i])
         "B" [blank i j]
         "P" [circle i j]
         "C" [cross i j])))
   [:p
    [:button
     {:on-click
      (fn new-game-click [e]
        (swap! app-state assoc :board (new-board board-size)))}
     "New Game"]]]
  )

(reagent/render-component [tictactoe]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  ;; (swap! app-state assoc-in [:board 0 0] 2)
  )
