
(ns stack-workflow.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [stack-workflow.comp.sidebar :refer [comp-sidebar]]))

(defn render [store]
  (fn [state mutate!]
    (div
      {:style (merge ui/global)}
      (comp-sidebar)
      (div {:style ui/button} (comp-text (pr-str store) nil)))))

(def comp-container (create-comp :container render))
