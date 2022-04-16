(ns joonas.soy.pages.home
  (:require [joonas.soy.pages.common :refer [make-page]]))

(def projects
  [{:heading     "Aatos"
    :image       [:local "aatos.png"]
    :timeline    ["2020" "present"]
    :tech        ["Clojure" "ClojureScript" "PostgreSQL" "Reagent" "Re-frame"]
    :description "Co-founder and token tech person at Aatos Health. Building better mental health promotion for knowledge workers, one Clojure form at a time."}

   {:heading     "HRS Academy"
    :image       [:local "hrs.png"]
    :timeline    ["2020" "present"]
    :tech        ["Clojure" "ClojureScript" "PostgreSQL" "Reagent" "Re-frame"]
    :description "A career coaching platform for HRS Advisors with a tailor-made content editor, customizable programs, multichannel chat, among others."}])

(defn ->src
  [[src-type file]]
  (case src-type
    :local (str "img/" file)
    :ext   file))

(def page
  (make-page
   {:id "projects"}
   [:div {:class "space-y-8"}
    [:p {:class "text-gray-900"}
     "Some projects I've been working on, mainly as a solo developer:"]
    [:div {:class "grid grid-cols-1 md:grid-cols-3 gap-6"}
     (for [{:keys [heading image tech description] [start-time end-time] :timeline} projects]
       [:div {:class "flex flex-col space-y-2"}
        [:img {:src (->src image)
               :class "rounded object-cover h-60 border border-orange-200 mb-4"}]
        [:div {:class "space-y-4"}
         [:div {:class "flex justify-between items-center"}
          [:h3 {:class "font-mono font-bold text-lg block text-gray-900"}
           heading]
          [:div {:class "flex items-center space-x-1"}
           [:span {:class "text-sm text-gray-700"} start-time]
           [:span {:class "text-sm text-gray-400"} "-"]
           [:span {:class "text-sm text-gray-700"} end-time]]]
         [:p {:class "text-base text-gray-800"}
          description]
         [:div {:class "pt-1 flex flex-wrap items-center"}
          (for [tech-item tech]
            [:span {:class "px-2 py-0.5 rounded text-sm font-medium bg-orange-100 text-orange-700 mr-1.5 mb-1.5"}
             tech-item])]]])]]))
