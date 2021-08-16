(ns joonas.soy.core
  (:require [clojure.java.io :as io]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(defrecord Site [target renderer compiled output])

(defn with-renderer
  "Require and resolve render function"
  [params]
  (update params :renderer requiring-resolve))

(defn compile-html
  "Update site data with compiled HTML"
  [{:keys [renderer] :as params}]
  (log/info [:compile-html renderer])
  (assoc-in params [:compiled :html] (renderer)))

(defn output-html!
  "Output HTML to given target directory"
  [{:keys [target] {:keys [html]} :compiled :as params}]
  (log/info [:output-html! target])
  (let [file-path (str/join "/" (-> (str/split target #"/") (conj "index.html")))]
    (io/make-parents file-path)
    (spit file-path html)
    ;; report output handled ok
    (update params :output conj ::html)))

(defn output-css!
  "Handle Tailwind-based stylesheets"
  [{:keys [target] :as params}]
  (log/info [:output-css! target])
  (sh "npx" "tailwind" "-o" (str target "/styles.css"))
  ;; report output handled ok
  (update params :output conj ::css))

(defn generate-site!
  "Output the result of `render` to directory specified by `target`"
  [params]
  (try
    (-> (map->Site (with-renderer params))
         (compile-html)
         (output-html!)
         (output-css!))
     (log/info [:generate-site!->success])
     (System/exit 0)
    (catch Exception e
      (log/error [:generate-site!->failure e])
      (System/exit 1))))

(comment
  (generate-site! {:target "build" :renderer 'joonas.soy.render/render}))
