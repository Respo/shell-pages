
(ns stack-workflow.core
  (:require [respo.core :refer [render! clear-cache!]]
            [stack-workflow.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [respo-router.util.listener :refer [listen! parse-address]]
            [stack-workflow.routes :as routes]))

(defonce store-ref (atom {}))

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [new-store (case
                    op
                    :router/nav
                    (assoc
                      @store-ref
                      :router
                      (parse-address op-data routes/dict))
                    @store-ref)]
    (println new-store)
    (reset! store-ref new-store)))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn -main! []
  (enable-console-print!)
  (render-app!)
  (listen! routes/dict dispatch! routes/mode)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!"))

(defn on-jsload! []
  (clear-cache!)
  (render-app!)
  (println "code update."))

(set! (.-onload js/window) -main!)
