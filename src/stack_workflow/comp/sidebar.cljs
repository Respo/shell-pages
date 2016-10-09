
(ns stack-workflow.comp.sidebar
  (:require [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [respo-ui.style :as ui]))

(defn render [] (fn [state mutate!] (div {} (comp-text "sidebar" nil))))

(def comp-sidebar (create-comp :sidebar render))
