(ns joonas.soy.core
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.string :as str]
            [taoensso.timbre :as log]
            [joonas.soy.render :as render]))

(defrecord Site [env directives target renderer compiled output])

(defn preface
  "Transform params into Site record"
  [params]
  (-> params #_(update :renderer requiring-resolve) (map->Site)))

(comment
  (preface {:target     "build"
            ;; :renderer   #'joonas.soy.render/render-site
            :directives {:render-only [:html]}
            :env        {"NODE_ENV" "development"}}))

(defn compile-html
  "Update site data with compiled HTML"
  [params]
  (log/info [:compile-html params])
  (assoc params :pages (->> render/pages
                            (map #(assoc % :html (render/render-page %))))))

(defn ->target-path
  "Generate file path for a given `href`"
  [target href]
  (if (empty? href)
    (format "%s/index.html" target)
    (format "%s%s/index.html" target href)))

(defn output-html!
  "Output HTML to given target directory"
  [{:keys [target pages] :as params}]
  (let [pages (map #(assoc % :target-path (->target-path target (:href %))) pages)]
    (log/info [:output-html! target])
    (doseq [{:keys [target-path html]} pages]
      ;; create parent dir
      (io/make-parents target-path)
      ;; output HTML to given file path
      (spit target-path html))
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

(defn output-assets!
  "Copy assets from public directory to given target directory"
  [{:keys [target] :as params}]
  (log/info [:output-assets! target])
  (let [cmd (str/join " " ["cp" "-R" "public/*" target])]
    (shell/sh "bash" "-c" cmd))
  ;; report output handled ok
  (update params :output conj ::assets))

(defn make?
  "Should step be called?"
  [step {{:keys [output]} :directives}]
  (or (empty? output) (some #{step} output)))

(comment
  (make? :html {})
  (make? :html {:directives {:output [:html]}}))

(defn make!
  "Wrap conditional compilation and file writing"
  [step params]
  (if (make? step params)
    (case step
      :html   (-> params (compile-html) (output-html!))
      :css    (-> params (output-css!))
      :assets (-> params (output-assets!)))
    params))

(defn generate-site!
  "Output the result of `render` to directory specified by `target`"
  [params]
  (try
    (->> params
         (preface)
         (make! :html)
         (make! :assets)
         (make! :css))
    (log/info [:generate-site!->success])
    true
    (catch Exception e
      (log/error [:generate-site!->failure e]))))

(comment
  (generate-site! {:target   "build"
                   :renderer 'joonas.soy.render/render-site
                   :env      {"NODE_ENV" "production"}}))
