(ns clojure-presentation.core
    (:require [reagent.core :as reagent]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [clojure.string :as str])
    (:import goog.History))

(enable-console-print!)

(defn go-to-url!
  ([url] (go-to-url! (.-location js/window) url))
  ([loc url] (aset loc "hash" url)))


(defn current-slide-number [location]
  (-> (aget location "hash")
      (str/split #"/")
      last
      (js/parseInt)))

(defn next-slide! []
  (let [current-slide (current-slide-number (.-location js/window))]
    (go-to-url! (str "#/slide/" (inc current-slide)))))

(defn previous-slide! []
  (let [current-slide (current-slide-number (.-location js/window))]
    (when (not (< 0 current-slide-number))
        (go-to-url! (str "#/slide/" (dec current-slide))))))


;; -------------------------
;; Slide types
(defn default-slide [title & content]
  [:div.slide
   [:h1.slide_title title]
   [:div.slide_content content]
   [:div.slide_switch_buttons
    [:span.previous_slide_button {:on-click previous-slide!}
     "PREVIOUS"]
    [:span.next_slide_button {:on-click next-slide!}
     "NEXT"]]])

(defn text-slide [title & paragraphs]
  (default-slide
    title
    (for [p paragraphs] [:p p])))

(defn bullet-point-slides [title & bullets]
  (default-slide
    title
    [:ul
     (for [b bullets]
       [:li b])]))

(defn img-slide [title img-url img-description]
  (default-slide
    title
    [:img.slide_img {:src img-url}]
    [:p.slide_img_description img-description]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to clojure-presentation"]
   [:div [:a {:href "#/slide/1"} "Start presentation"]]])

(defn text-slide-example []
  (text-slide "Text slide" "this slide contains some text."
              "This is the most basic type of slide."
              "Each string passed is a <p> tag."))

(defn bullet-point-slide-example []
  (bullet-point-slides "Bullet point slide"
                       "this slide contains bullet points"
                       "some next text"
                       "so on."))

(defn image-slide-example []
  (img-slide "Image slide"
             "http://verse.aasemoon.com/images/5/51/Clojure-Logo.png"
             "this is the clojure logo"))

(defn current-page []
  [:div [(session/get :current-page)]])


;; -------------------------
;; Routes

(def slides [home-page
             text-slide-example
             bullet-point-slide-example
             image-slide-example])

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page home-page))

(secretary/defroute #"/slide/(\d+)" [id]
  (->> id
       js/parseInt
       (get slides)
       (session/put! :current-page)))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
