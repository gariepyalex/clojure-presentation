(ns clojure-presentation.slide
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [goog.events.EventType :as EventType]
            [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.async :refer [put! chan <!]]
            [clojure.string :as str]
            cljsjs.highlight

            cljsjs.highlight.langs.clojure)
  (:import goog.History))

(defonce state (reagent/atom {}))

(defn go-to-url!
  ([url] (go-to-url! (.-location js/window) url))
  ([loc url] (aset loc "hash" url)))

(defn current-slide-number [location]
  (-> (aget location "hash")
      (str/split #"/")
      last
      (js/parseInt)))

(defn go-to-slide! [number]
    (go-to-url! (str "#/slide/" number)))

(defn next-slide! []
  (let [current-slide (current-slide-number (.-location js/window))]
    (when (> (count (get @state :slides)) (inc current-slide))
      (go-to-slide! (inc current-slide)))))

(defn previous-slide! []
  (let [current-slide (current-slide-number (.-location js/window))]
    (when (< 0 current-slide)
        (go-to-slide! (dec current-slide)))))

;; -------------------------
;; Util components
(def previous-slide-arrow
    [:span.previous_slide_button.slide_button {:on-click previous-slide!}
     "\u2b05"])

(def next-slide-arrow
    [:span.next_slide_button.slide_button {:on-click next-slide!}
        "\u27a1"])

;; -------------------------
;; Slide content

(defn text-content [& paragraphs]
  [:div
   (for [p paragraphs] [:p p])])

(defn bullet-point-content [& bullets]
  [:ul
   (for [b bullets]
     [:li b])])

(defn img-content [img-url img-description]
  [:div
   [:img.slide_img {:src img-url}]
   [:p.slide_img_description img-description]])

(defn- code-component []
  (fn [code] [:pre [:code.clojure code]]))

(defn- highlight-code [html-node]
  (let [nodes (.querySelectorAll html-node "pre code")]
    (loop [i (.-length nodes)]
      (when-not (neg? i)
        (when-let [item (.item nodes i)]
          (.highlightBlock js/hljs item))
        (recur (dec i))))))

(def syntax-highlight-wrapper
  (with-meta code-component
    {:component-did-mount
     (fn [this] (let [node (reagent/dom-node this)]
                  (highlight-code node)))}))

(defn code-content
  ([code description]
   [:div
    [syntax-highlight-wrapper code]
    [:p.code_description description]])
  ([code]
   (code-content code "")))

;; -------------------------
;; Slide types
(defn home-slide [title subtitle author date]
  [:div.slide
   [:h1#home_title title]
   [:h3#home_subtitle subtitle]
   [:div#home_other_infos
    [:h4#home_author author]
    [:h5#home_date date]]
   [:div.slide_switch_buttons
    next-slide-arrow]])

(defn end-slide [text subtext]
  [:div#end_slide.slide
   [:h1#end_text text]
   [:h3#end_subtext subtext]
   [:div.slide_switch_buttons previous-slide-arrow]])

(defn default-slide [title & content]
  [:div.slide
   [:h1.slide_title title]
   (into [:div.slide_content] content) 
   [:div.slide_switch_buttons
    previous-slide-arrow next-slide-arrow]])

(defn two-column-slide [title title-left content-left title-right content-right]
  [:div.slide
   [:h1.slide_title title]
   [:div.slide_column.slide_content_left [:h3.column_title title-left]
    content-left]
   [:div.slide_column.slide_content_right [:h3.column_title title-right]
    content-right]
   [:div.slide_switch_buttons
    previous-slide-arrow next-slide-arrow]])

;; -------------------------
;; Routing
(defn current-page []
  [:div [(session/get :current-page)]])

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (go-to-slide! 0)
  (session/put! :current-page (first (get @state :slides))))

(secretary/defroute #"/slide/(\d+)" [id]
  (->> id
       js/parseInt
       (#(get-in @state [:slides %]))
       (session/put! :current-page)))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     HistoryEventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(enable-console-print!)

;; -------------------------
;; Key bindings
(def key-codes {:left-arrow 37
                :right-arrow 39})

(defn handle-key-event! [keycode]
  (condp = keycode
      (:left-arrow key-codes) (previous-slide!)
      (:right-arrow key-codes) (next-slide!)
      nil))


(defn listen-events [element event-type]
  (let [out (chan)]
    (events/listen element event-type
                   (fn [event] (put! out event)))
    out))

(defn hook-key-events! []
  (let [keypresses (listen-events (.-body js/document) "keydown")]
    (go (while true
          (let [key-event (<! keypresses)
                char-code (.-keyCode key-event)]
            (handle-key-event! char-code))))))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn start-presentation! [slides]
  (reset! state {:slides slides})
  (hook-browser-navigation!)
  (hook-key-events!)
  (mount-root))
