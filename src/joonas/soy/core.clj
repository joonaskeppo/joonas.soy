(ns joonas.soy.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hiccup.page :refer [html5]]))

;; just testing

(def head
  [:head
    [:title "joonas.soy"]])

(def body
  [:body
   [:div "Howdy"]])

(defn render []
  (html5 {:lang "en"} head body))

(defn output!
  "Output `html` to directory specified by `to`"
  [to html]
  (let [file-path (str/join "/" (-> (str/split to #"/") (conj "index.html")))]
    (io/make-parents file-path)
    (spit file-path html)))

(defn generate-site!
  [{:keys [target]}]
  (output! target (render)))
