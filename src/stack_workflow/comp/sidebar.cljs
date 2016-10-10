
(ns stack-workflow.comp.sidebar
  (:require [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [respo-ui.style :as ui]))

(defn on-route [path] (fn [e dispatch!] (dispatch! :router/nav path)))

(defn render []
  (fn [state mutate!]
    (div
      {}
      (div {:event {:click (on-route "/")}} (comp-text "/" nil))
      (div
        {:event {:click (on-route "/about.html")}}
        (comp-text "about.html" nil))
      (div
        {:event {:click (on-route "/post/a.html")}}
        (comp-text "post/a.html" nil))
      (div
        {:event {:click (on-route "/post/b.html")}}
        (comp-text "post/b.html" nil)))))

(def comp-sidebar (create-comp :sidebar render))
