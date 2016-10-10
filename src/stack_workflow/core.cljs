
(ns stack-workflow.core
  (:require [respo.core :refer [render! clear-cache!]]
            [stack-workflow.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [respo-router.util.listener :refer [listen! parse-address]]
            [stack-workflow.routes :as routes]
            [stack-workflow.schema :as schema]
            [respo-router.core :refer [render-url!]]))

(defonce store-ref
 (let [store-el (.querySelector js/document "#store")]
   (if (some? store-el)
     (atom (pr-str (.-innerHTML store-el)))
     (atom
       (assoc
         schema/store
         :router
         (parse-address
           (str (.-pathname js/location) (.-search js/location))
           routes/dict))))))

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [new-store (case
                    op
                    :router/nav
                    (assoc
                      @store-ref
                      :router
                      (parse-address op-data routes/dict))
                    :router/route
                    (assoc @store-ref :router op-data)
                    @store-ref)]
    (println new-store)
    (reset! store-ref new-store)))

(defn render-router! []
  (render-url! (:router @store-ref) routes/dict routes/mode))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn -main! []
  (enable-console-print!)
  (render-app!)
  (render-router!)
  (listen! routes/dict dispatch! routes/mode)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (add-watch store-ref :router-changes render-router!)
  (println "app started!"))

(defn on-jsload! []
  (clear-cache!)
  (render-app!)
  (println "code update."))

(set! (.-onload js/window) -main!)
