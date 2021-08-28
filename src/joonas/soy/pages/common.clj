(ns joonas.soy.pages.common
  (:require [joonas.soy.components.icons :as icons]))

(def nav-links
  [{:label "Projects"
    :href  "/"
    :id    :projects}
   {:label "Résumé"
    :href  "/resume"
    :id    :resume}
   #_{:label "About"
    :href  "#"
    :id    :about}
   #_{:label "Blog"
    :href  "#"
    :id    :blog}])

(defn header
  [{:keys [page]}]
  [:div {:class "space-y-4"}
   [:div {:class "flex items-center justify-between print:hidden"}
    ;; main nav links
    [:nav {:class "flex items-center space-x-4"}
     (for [{:keys [label href id]} nav-links]
       [:a {:href href
            :class (if (= page id)
                     "border-b-2 border-orange-800 text-orange-900"
                     "border-b-2 border-transparent hover:border-orange-800 text-orange-800 hover:text-orange-900 transition duration-100")}
        [:span {:class "text-lg"}
         label]])]
    ;; social links
    (let [class "h-6 text-orange-800 hover:text-orange-900 transition duration-100"]
      [:div {:class "flex items-center space-x-4"}
       [:a {:href "https://www.linkedin.com/in/joonaskeppo/"}
        (icons/linkedin {:class class})]
       [:a {:href "https://twitter.com/joonaskeppo"}
        (icons/twitter {:class class})]
       [:a {:href "https://github.com/joonaskeppo/"}
        (icons/github {:class class})]])]])

(defn ->page
  "Generate page with opts:
  - `:page` should be an id value matching a nav link"
  [opts children]
  [:body {:class "antialiased bg-orange-50 min-h-screen"}
   [:div
    [:div {:class "p-4 md:py-16 max-w-5xl mx-auto space-y-6"}
     (header opts)
     children]]])
