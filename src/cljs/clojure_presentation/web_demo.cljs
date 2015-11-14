(ns clojure-presentation.web-demo
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [ajax.core :as ajax :refer [GET]]))

(defonce page-data (reagent/atom {}))

(defn update-data-handler
  [response]
  (reset! page-data response)
  (println @page-data))

(defn update-data! 
  []
  (GET "/data" {:handler update-data-handler}))

(defn vote-member!
  [index]
  (swap! page-data update-in [:members index :popularity] inc))

(defn member-view
  [index member]
  [:div.web_demo_member
   [:h3 (:name member)]
   [:h2 "toto"]
   [:p (str "Born in " (:year-of-birth member))]
   [:h4 "Qualities:"]
   [:ul (for [q (:qualities member)]
          [:li q])]
   [:p "Popularity: " (:popularity member) ; ]])
    [:button {:on-click #(vote-member! index)} "vote"]]])

(defn top-member
  []
  (->> @page-data
       :members
       (sort-by :popularity)
       reverse
       first
       :name))

(defn web-demo-component
  []
  (update-data!)
  (fn []
    [:div#web_demo
     ;;(str @page-data)]))
     [:p#top_member (str "Top member: " (top-member))]
     [:div#web_demo_members
      (map-indexed member-view (:members @page-data))]]))
