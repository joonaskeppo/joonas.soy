(ns joonas.soy.pages.home
  (:require [joonas.soy.components.icons :as icons]))

(def nav-links
  [{:label   "Projects"
    :href    "#"
    :current true}
   {:label "Resume"
    :href  "#"}
   {:label "Blog"
    :href  "#"}])

(def page
  [:body {:class "bg-orange-100 min-h-screen"}
   [:div
    [:div {:class "p-4 md:py-16 max-w-5xl mx-auto space-y-6"}
     [:div {:class "flex items-center justify-between"}
      ;; main nav links
      [:nav {:class "flex items-center space-x-4"}
       (for [{:keys [label href current]} nav-links]
         [:a {:href href
              :class (if current
                       "border-b-2 border-orange-800 text-orange-900"
                       "border-b-2 border-transparent hover:border-orange-800 text-orange-800 hover:text-orange-900 transition duration-100")}
          [:span {:class "text-lg"}
           label]])]

      ;; social links
      (let [class "h-6 text-orange-800 hover:text-orange-900 transition duration-100"]
        [:div {:class "flex items-center space-x-4"}
         [:a {:href "#"}
          (icons/linkedin {:class class})]
         [:a {:href "#"}
          (icons/twitter {:class class})]
         [:a {:href "#"}
          (icons/github {:class class})]])]
     [:p "Home page"]]]])
