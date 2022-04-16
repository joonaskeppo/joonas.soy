(ns joonas.soy.pages.notes
  (:require [joonas.soy.pages.common :refer [make-page]]
            [joonas.soy.utils :refer [path-seq list-files]]
            [hickory.core]
            [hickory.select]
            [hickory.convert]
            [clojure.string :as str]))

;; TODO: -> config.edn
(def notes-dir
  "/Users/joonaskeppo/org/roam/build/")

(def notes-path-seq
  (path-seq notes-dir))

(defn html-file?
  [s]
  (str/ends-with? s ".html"))

(defn list-html-files
  [path]
  (->> path
       (list-files)
       (filter html-file?)))

(comment
  (list-html-files notes-dir))

(defn parse-note
  [h]
  (let [hickory (->> h (hickory.core/parse) (hickory.core/as-hickory))]
    {:title  (->> hickory
                  (hickory.select/select (hickory.select/tag :head))
                  (first)
                  (hickory.select/select (hickory.select/tag :title))
                  (first) :content (first))
     :hiccup (->> hickory
                  (hickory.select/select (hickory.select/tag :body))
                  (first) :content
                  (map hickory.convert/hickory-to-hiccup))}))

(defn get-note-data
  "Heuristically infer note data"
  [path]
  (letfn [(path-seq->note-name [ps]
            (first (str/split (last ps) #"\.html")))
          (path-seq->note-id [ps]
            (let [subdir    (loop [idx       0
                                   [p & ps*] ps
                                   [n & ns*] notes-path-seq]
                              (cond
                                (or (nil? p) (html-file? p)) nil
                                (not= p n)                   p
                                :else                        (recur (inc idx) ps* ns*)))
                  note-name (path-seq->note-name ps)]
              (if subdir
                [subdir note-name]
                [note-name])))]
    (let [ps      (path-seq path)
          note-id (path-seq->note-id ps)]
      (assoc (parse-note (slurp path))
             :id note-id
             :type (when (= 2 (count note-id)) (first note-id))))))

(def notes-data
  (->> (list-html-files notes-dir)
       (map (comp (juxt :id identity) get-note-data))
       (into {})))

(def front-page
  (make-page
   {:id "notes"}
   [:div "Front page of notes (TODO)"]))

(def notes-pages
  (->> notes-data
       (mapv (fn [[_ {:keys [id hiccup] :as page}]]
               (let [id     (apply conj ["notes"] id)
                     hiccup (apply conj [:div] hiccup)]
                 (make-page (assoc page :id id) hiccup))))))
