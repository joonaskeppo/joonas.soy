(ns joonas.soy.render
  (:require [clojure.algo.generic.functor :refer [fmap]]
            [hiccup.page :refer [html5]]
            ;; --- pages ---
            [joonas.soy.pages.home :as home]
            [joonas.soy.pages.resume :as resume]))

(def pages
  {:site/root   home/page
   :site/resume resume/page})

(def head
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "description" :content "Answering the age-old question \"who am I?\" for the specific case of me, Joonas Keppo, and without all too much philosophizing."}]
   [:title "Joonas Keppo"]
   [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
   [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin true}]
   [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css2?family=Space+Mono:ital,wght@0,400;0,700;1,400&family=Work+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap"}]
   [:link {:href "/styles.css" :rel "stylesheet"}]])

(defn render-page
  [page]
  (html5 {:lang "en"} head page))

(defn render-site []
  (fmap render-page pages))

(comment
  (render-site))
