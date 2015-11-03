(ns clojure-presentation.core
  (:require [clojure-presentation.slide :as slide]))

;; -------------------------
;; Views
(defn home-slide-example []
  (slide/home-slide "Clojure"
                    "a pragmatic functional language"
                    "by Alexandre Gariépy"
                    "November the 13th, 2015"))

(defn text-slide-example []
  (slide/text-slide "Text slide" "this slide contains some text."
                    "This is the most basic type of slide."
                    "Each string passed is a <p> tag."))

(defn bullet-point-slide-example []
  (slide/bullet-point-slides "Bullet point slide"
                             "this slide contains bullet points"
                             "some next text"
                             "so on."))

(defn image-slide-example []
  (slide/img-slide "Image slide"
                   "http://verse.aasemoon.com/images/5/51/Clojure-Logo.png"
                   "this is the clojure logo"))

(defn code-slide-example []
  (slide/code-slide "Code slide"
                    (str "(let [a 2]\n"
                         "  (+ 3 a))")
                    "field for a short code descripion"))

(defn end-slide-example []
  (slide/end-slide "Thank you"
                   "Any questions?"))

;; -------------------------
;; Routes
(def slides [home-slide-example
             text-slide-example
             bullet-point-slide-example
             image-slide-example
             code-slide-example
             end-slide-example])

(defn -main
  []
  (slide/start-presentation! slides))
