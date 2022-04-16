(ns joonas.soy.render
  (:require [hiccup.page :refer [html5]]
            [joonas.soy.pages.common :refer [combine-pages]]
            ;; --- pages ---
            [joonas.soy.pages.home :as home]
            [joonas.soy.pages.resume :as resume]
            [joonas.soy.pages.notes :as notes]))


;; TODO: page refs to config.edn, combine-pages call under core ns?
(def pages
  (combine-pages home/page
                 resume/page
                 notes/front-page
                 notes/notes-pages))

(defn head
  [{:keys [title]}]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "description" :content "Answering the age-old question \"who am I?\" for the specific case of me, Joonas Keppo, and without all too much philosophizing."}]
   [:title (if title (str "Joonas Keppo | " title) "Joonas Keppo")]
   [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
   [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin true}]
   [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css2?family=Space+Mono:ital,wght@0,400;0,700;1,400&family=Work+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap"}]
   [:link {:href "/styles.css" :rel "stylesheet"}]])

(defn render-page
  [{:keys [hiccup] :as page}]
  (html5 {:lang "en"} (head page) hiccup))
