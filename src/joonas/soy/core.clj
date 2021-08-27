(ns joonas.soy.core
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(defrecord Site [env directives target renderer compiled output])

(defn preface
  "Transform params into Site record"
  [params]
  (-> params (update :renderer requiring-resolve) (map->Site)))

(comment
  (preface {:target     "build"
            :renderer   'joonas.soy.render/render-site
            :directives {:render-only [:html]}
            :env        {"NODE_ENV" "development"}}))

(defn compile-html
  "Update site data with compiled HTML"
  [{:keys [renderer] :as params}]
  (log/info [:compile-html renderer])
  (assoc-in params [:compiled :html] (renderer)))

(defn page-key->dir
  "Map a page keyword to a directory name"
  [pk]
  (if (= :site/root pk)
    "" ;; front page -> root
    (name pk)))

(defn ->target-path
  "Generate file path for a given page.
  Pages are specified as keywords and resolved via `page-key->dir`."
  [target page]
  (let [page*         (page-key->dir page)
        base-accessor (vec (str/split target #"/"))
        file-accessor (if (not= "" page*)
                        (conj base-accessor page* "index.html")
                        (conj base-accessor "index.html"))]
    (str/join "/" file-accessor)))

(comment
  (->target-path "build" :site/root)
  (->target-path "build" :about))

(defn output-html!
  "Output HTML to given target directory"
  [{:keys [target] {:keys [html]} :compiled :as params}]
  (let [target-paths (->> (keys html)
                          (map (juxt identity #(->target-path target %)))
                          (into {}))]
    (log/info [:output-html! target-paths])
    (doseq [[page path] target-paths]
      ;; create parent dir
      (io/make-parents path)
      ;; output HTML to given file path
      (spit path (get html page)))
    ;; report that output handled ok
    (update params :output conj ::html)))

(defn output-css!
  "Output Tailwind-based stylesheets to given target directory"
  [{:keys [target] {:strs [NODE_ENV] :or {NODE_ENV "development"}} :env :as params}]
  (log/info [:output-css! target])
  (let [env (str "NODE_ENV=" NODE_ENV)
        out (str target "/styles.css")
        cmd [env "npx" "tailwind" "-o" out]]
    (shell/sh "bash" "-c" (str/join " " cmd)))
  ;; report output handled ok
  (update params :output conj ::css))

(defn make?
  "Should step be called?"
  [step {{:keys [render-only]} :directives}]
  (or (empty? render-only) (some #{step} render-only)))

(comment
  (make? :html {})
  (make? :html {:directives {:render-only [:html]}}))

(defn make!
  "Wrap conditional compilation and file writing"
  [step params]
  (if (make? step params)
    (case step
      :html (-> params (compile-html) (output-html!))
      :css  (-> params (output-css!)))
    params))

(defn generate-site!
  "Output the result of `render` to directory specified by `target`"
  [{:keys [debug] :or {debug false} :as params}]
  (try
    (->> params
         (preface)
         (make! :html)
         (make! :css))
    (log/info [:generate-site!->success])
    (catch Exception e
      (log/error [:generate-site!->failure e]))))

(comment
  (generate-site! {:target   "build"
                   :renderer 'joonas.soy.render/render-site
                   :env      {"NODE_ENV" "production"}}))
