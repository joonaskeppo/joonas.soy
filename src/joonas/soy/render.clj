(ns joonas.soy.render
  (:require [hiccup.page :refer [html5]]))

(def head
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "description" :content "Answering the age-old question \"who am I?\" in a nutshell, albeit without too much philosophizing."}]
   [:title "Joonas Keppo"]
   [:link {:href "styles.css" :rel "stylesheet"}]])

(def body
  [:body
   [:div "Howdy"]])

(defn render []
  (html5 {:lang "en"} head body))
