(ns joonas.soy.pages.common
  (:require [joonas.soy.components.icons :refer [icon]]
            [clojure.string :as str]))

;; --- links ---

;; TODO: -> config.edn
(def nav-links
  [{:label "Projects" :href "/"}
   {:label "Résumé" :href "/resume"}
   {:label "Notes" :href "/notes"}])

(defn make-href
  "Convert `path` to a relative href"
  [path]
  (cond
    (vector? path)              (str "/" (str/join "/" path))
    (str/starts-with? path "/") path
    (string? path)              (str "/" path)))

;; --- page components ---

(defn header
  [{current-href :href}]
  [:div {:class "space-y-4"}
   [:div {:class "flex items-center justify-between print:hidden"}
     ;; main nav links
    [:nav {:class "flex items-center space-x-4"}
     (for [{:keys [label href]} nav-links]
       [:a {:href  href
            :class (if (= href current-href)
                     "border-b-2 border-orange-800 text-orange-900"
                     "border-b-2 border-transparent hover:border-orange-800 text-orange-800 hover:text-orange-900 transition duration-100")}
        [:span {:class "text-lg"}
          label]])]
     ;; social links
    (let [class "h-6 text-orange-800 hover:text-orange-900 transition duration-100"]
      [:div {:class "flex items-center space-x-4"}
       [:a {:href "https://www.linkedin.com/in/joonaskeppo/"}
        (icon :remixicon/github {:class class})]
       [:a {:href "https://twitter.com/joonaskeppo"}
        (icon :remixicon/linkedin {:class class})]
       [:a {:href "https://github.com/joonaskeppo/"}
        (icon :remixicon/twitter {:class class})]])]])

;; --- pages ---

(defrecord Page [id title href hiccup])

(defn make-page
  "Generate page data with opts:
  - `:id` should be an id value matching a nav link"
  [{:keys [id title] :as _opts} children]
  (let [href (make-href id)]
    (map->Page
     {:id     id
      :title  title
      :href   href
      :hiccup [:body {:class "antialiased bg-orange-50 min-h-screen"}
               [:div
                [:div {:class "p-4 md:py-16 max-w-5xl mx-auto space-y-6"}
                 (header {:href href})
                 children]]]})))

(defn page? [x]
  (instance? Page x))

(defn pages? [xs]
  (and (sequential? xs) (every? page? xs)))

(defn combine-pages
  "Combine pages.
  Takes as params individual Page records or seqs of Pages."
  [& xs-or-m]
  {:pre [(every? (some-fn page? pages?) xs-or-m)]}
  (reduce (fn [acc val]
            (if (vector? val)
              (apply conj acc val)
              (conj acc val)))
          [] xs-or-m))
