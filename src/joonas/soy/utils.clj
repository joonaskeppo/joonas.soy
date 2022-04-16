(ns joonas.soy.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import java.io.File))

(defn map-vals
  "Like `map`, but only for values of key-value pairs"
  [f m]
  (->> m
       (map (fn [[k v]] [k (f v)]))
       (into {})))

;; --- files and directories ---

(defn path-seq
  "Transform `path` string into seq"
  [path]
  (str/split path (re-pattern File/separator)))

(def last-in-path
  "Get last entry, such as filename, from a given `path` string"
  (comp last path-seq))

;; https://gist.github.com/ZucchiniZe/f24fdcb346ed070ec2f3
(defn list-files
  "List files under `path`"
  [path]
  (let [directory (io/file path)
        dir?      #(.isDirectory %)]
    (->> (tree-seq dir? #(.listFiles %) directory)
         (filter (comp not dir?))
         (map #(.getPath %)))))

(comment
  (list-files "/Users/joonaskeppo/org/roam/build/"))
