(ns joonas.soy.pages.resume
  (:require [joonas.soy.pages.common :refer [->page]]
            [joonas.soy.components.icons :as icons]))

;; --- data ---

(def experience-data
  [{:title       "Co-Founder, CTO"
    :place       "Aatos Health"
    :timeline    ["2020" "present"]
    :tags        ["Clojure" "ClojureScript" "Reagent" "Re-frame" "PostgreSQL"]
    :description "Developing dynamic, personalized well-being programs that augment traditional coaching sessions, and leveraging the ACT psychological framework."}

   {:title       "Software Consultant"
    :place       "Aatos Health"
    :timeline    ["2019" "present"]
    :tags        ["Clojure" "ClojureScript" "JavaScript" "TypeScript" "React" "Reagent" "Re-frame" "PostgreSQL"]
    :description "Worked on various health-tech-related web development projects, in the context of pre-existing, larger codebases, and of designing and building from scratch."}

   {:title       "Team Lead, \"AI Mental Health Applications\" Project"
    :place       "University of Jyväskylä"
    :timeline    ["2019" "2020"]
    :description "Lead a multidisciplinary team in development of a consortium of mental health service providers, headed by the University of Jyväskylä's Computer Science department."}

   {:title       "Research Assistant, \"IBM Watson Health Cloud Finland\" Project"
    :place       "University of Jyväskylä"
    :timeline    ["2018" "2019"]
    :description "Researched mental health applications of artificial intelligence, while developing prototypes of various concepts. These prototypes would later inspire the creation of Aatos Health."
    :tags        ["React Native" "JavaScript" "React" "Redux"]}

   {:title       "Summer Intern"
    :place       "SEB"
    :timeline    "2017"
    :tags        ["Python" "SAS"]
    :description "Credit Risk department in Arenastaden. Various tasks ranging from simulating economic behavior with Excel and Python, working with machine learning models using SAS, to inserting database records through a web-based frontend. Involved weekly intern meetings with insights about the structure and primary functions of the various risk departments at the bank."}

   {:title       "Summer Trainee"
    :place       "University of Jyväskylä"
    :timeline    "2016"
    :tags        ["JavaScript" "Angular.js" "Python" "Flask"]
    :description " Web development, both frontend and some backend work, on the University of Jyväskylä's electronic learning environment, TIM. Frontend work with Angular.js and plain JavaScript, with occasional Python, Flask backend programming. "}])

(def education-data
  [{:title    "Computer Science"
    :degree   "M.Sc."
    :place    "University of Jyväskylä"
    :timeline ["2016" "present"]}

   {:title    "Mathematics"
    :degree   "B.Sc."
    :place    "University of Jyväskylä"
    :timeline "2016"}])

(def skills-data
  (->> [{:title "JavaScript" :skill 0.9}
        {:title "Clojure(Script)" :skill 0.7}
        {:title "React, Redux" :skill 0.7}
        {:title "Reagent, Re-frame" :skill 0.8}
        {:title "TypeScript" :skill 0.7}
        {:title "PostgreSQL, SQLite" :skill 0.6}
        {:title "Linux/Unix" :skill 0.6}
        {:title "Python" :skill 0.6}
        {:title "Ruby" :skill 0.4}
        {:title "C" :skill 0.4}]
      (sort-by :skill)
      (reverse)))

;; --- components ---

(def basic-info
  [:div {:class "flex"}
   [:div {:class "mr-4 flex-shrink-0"}
    [:img {:class "rounded-lg w-28 h-auto border border-orange-100"
           :src "/img/joonas.png"}]]
   [:div {:class "space-y-2"}
    [:h1 {:class "text-2xl font-mono font-bold text-coolGray-800"} "Joonas Keppo"]
    [:div {:class "flex items-center space-x-2"}
     (icons/github {:class "h-5 text-orange-900"})
     [:a {:href "https://github.com/joonaskeppo"}
      [:span {:class "text-coolGray-500"} "github.com/"]
      [:span {:class "text-coolGray-900"} "joonaskeppo"]]]
    [:div {:class "flex items-center space-x-2"}
     (icons/linkedin {:class "h-5 text-orange-900"})
     [:a {:href "https://linkedin.com/in/joonaskeppo"}
      [:span {:class "text-coolGray-500"} "linkedin.com/in/"]
      [:span {:class "text-coolGray-900"} "joonaskeppo"]]]]])

(def who-am-i
  [:div {:class "space-y-2"}
   [:h2 {:class "font-mono font-bold text-sm text-coolGray-700 uppercase tracking-widest"}
    "Who Am I?"]
   [:p {:class "text-coolGray-900"}
    [:span "A full-stack web developer with a fondness for functional programming, mathematics, and occasionally, design. I've spent years working on various mental health projects, both technical and non-technical. My current primary focus is developing better mental health promotion for knowledge workers via "]
    [:a {:class "font-bold text-orange-800 hover:text-orange-900"
         :href "https://aatoshealth.com"} "Aatos"]
    [:span {:class "text-coolGray-900"} "."]]
   #_[:p {:class "text-coolGray-900"}
    [:span "I'm currently on a deep-dive into the world of Clojure, while working through some classic Lisp-oriented textbooks like Peter Norvig's "]
    [:span {:class "italic"} "Paradigms of Artificial Intelligence Programming"]
    [:span "."]]])

(def experience
  [:div {:class "space-y-2"}
   [:h2 {:class "font-mono font-bold text-sm text-coolGray-700 uppercase tracking-widest"}
    "Work Experience"]
   [:ul {:class "space-y-4"}
    (for [{:keys [title place tags description] [start-time end-time :as time] :timeline} experience-data]
      [:li {:class "flex flex-col space-y-2"}
       [:div {:class "flex justify-between space-x-4"}
        [:h3 {:class "font-mono font-bold block text-coolGray-900"} title]
        (if (vector? time)
          [:div {:class "flex items-center space-x-1"}
           [:span {:class "text-sm text-coolGray-700"} start-time]
           [:span {:class "text-sm text-coolGray-400"} "-"]
           [:span {:class "text-sm text-coolGray-700"} end-time]]
          [:span {:class "text-sm text-coolGray-700"} time])]
       [:h4 {:class "italic text-coolGray-900"} place]
       [:p {:class "text-base text-coolGray-800"} description]
       [:div {:class "pt-1 flex flex-wrap items-center"}
        (for [tag tags]
          [:span {:class "px-2 py-0.5 rounded text-sm font-medium bg-orange-100 text-orange-700 mr-1.5 mb-1.5"}
           tag])]])]])

(def education
  [:div {:class "space-y-2"}
   [:h2 {:class "font-mono font-bold text-sm text-coolGray-700 uppercase tracking-widest"}
    "Education"]
   [:ul {:class "space-y-4"}
    (for [{:keys [title place degree] [start-time end-time :as time] :timeline} education-data]
      [:li {:class "flex flex-col space-y-2"}
       [:div {:class "flex justify-between space-x-4"}
        [:h4 {:class "font-mono font-bold text-coolGray-900"}
         [:span title]
         [:span ", "]
         [:span degree]]
        (if (vector? time)
          [:div {:class "flex items-center space-x-1"}
           [:span {:class "text-sm text-coolGray-700"} start-time]
           [:span {:class "text-sm text-coolGray-400"} "-"]
           [:span {:class "text-sm text-coolGray-700"} end-time]]
          [:span {:class "text-sm text-coolGray-700"} time])]
       [:h3 {:class "italic text-coolGray-900"} place]])]])

(def skills
  [:div {:class "space-y-2"}
   [:h2 {:class "font-mono font-bold text-sm text-coolGray-700 uppercase tracking-widest"}
    "Skills"]
   [:ul {:class "grid grid-cols-2 gap-5"}
    (for [{:keys [title skill]} skills-data]
      [:li {:class "mb-1 space-y-1"}
       [:p {:class "font-mono font-bold text-sm text-coolGray-800"} title]
       [:div {:class "w-full h-3 bg-orange-100 rounded-lg"}
        [:div {:class "rounded-lg bg-orange-500 h-full"
               :style (str "width: " (int (* 100 skill)) "%")}]]])]])

(def page
  (->page
   {:page :resume}
   [:div
    [:div {:class "grid grid-cols-1 md:grid-cols-2 gap-12"}
     [:div {:class "flex flex-col space-y-12"}
      basic-info
      who-am-i
      skills
      education]
     [:div {:class "flex-col space-y-12"}
      experience]]]))
