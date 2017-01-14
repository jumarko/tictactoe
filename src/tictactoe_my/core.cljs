(ns tictactoe-my.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "I changed some test")

;; define your app data so that it doesn't get over-written on reload

(defn new-board [n]
  (vec (repeat n (vec (repeat n 0)))))

(defonce app-state (atom {:text "Welcome to tic tac toe"
                          :board (new-board 3)}))

(defn tictactoe []
  [:center
   [:h1 (:text @app-state)]
   [:svg
    {:view-box "0 0 3 3"
     :width 500
     :height 500}
    (for [i (range (count (:board @app-state)))
          j (range (count (:board @app-state)))]
      [:rect {:width 0.9
              :height 0.9
              :fill "green"
              :x i
              :y j}])
    ]])

(reagent/render-component [tictactoe]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (swap! app-state assoc-in [:text] "Hi")
  )
