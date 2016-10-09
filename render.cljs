
(ns ssr-stages.boot
  (:require
    [respo.alias :refer [html head title script style meta' div link body]]
    [respo.render.html :refer [make-html make-string]]
    [stack-workflow.comp.container :refer [comp-container]]
    [planck.core :refer [spit]]
    [planck.shell :refer [sh]]
    [respo-router.util.listener :refer [parse-address]]))

(defn use-text [x] {:attrs {:innerHTML x}})
(defn html-dsl [data html-content ssr-stages]
  (make-html
    (html {}
      (head {}
        (title (use-text (str "Stack Workflow")))
        (link {:attrs {:rel "icon" :type "image/png" :href "http://logo.respo.site/respo.png"}})
        (meta' {:attrs {:charset "utf-8"}})
        (meta' {:attrs {:name "viewport" :content "width=device-width, initial-scale=1"}})
        (meta' {:attrs {:id "ssr-stages" :content (pr-str ssr-stages)}})
        (style (use-text "body {margin: 0;}"))
        (style (use-text "body * {box-sizing: border-box;}"))
        (script {:attrs {:id "config" :type "text/edn" :innerHTML (pr-str data)}}))
      (body {}
        (div {:attrs {:id "app" :innerHTML html-content}})
        (script {:attrs {:src "/main.js"}})))))

(defn generate-html [router ssr-stages]
  (let [ tree (comp-container {:router router} ssr-stages)
         html-content (make-string tree)]
    (html-dsl {:build? true} html-content ssr-stages)))

(def dict {"post" ["post"], "about.html" [], "home" []})

(defn -main []
  (spit "target/index.html" (generate-html (parse-address "/" dict) #{:shell}))
  (spit "target/about.html" (generate-html (parse-address "/about.html" dict) #{:shell}))
  (sh "mkdir" "target/post/")
  (spit "target/post/a.html" (generate-html (parse-address "/post/a.html" dict) #{:shell}))
  (spit "target/post/b.html" (generate-html (parse-address "/post/b.html" dict) #{:shell})))

(-main)
