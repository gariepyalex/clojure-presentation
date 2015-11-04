(ns clojure-presentation.core
  (:require [clojure-presentation.slide :as slide]))

;; -------------------------
;; Views
(defn home-slide-example []
  (slide/home-slide "Clojure"
                    "a pragmatic functional language"
                    "by Alexandre Gariépy"
                    "November the 13th, 2015"))

(defn simili-python []
  (slide/default-slide "Regardez ce python"
    (slide/code-content "myFunc(1, 2, 3, 4)")))

(defn simili-clojure []
  (slide/default-slide "OMG!"
    (slide/code-content "(myFunc 1, 2, 3, 4)")
    (slide/bullet-point-content "also" "some" "bullets")))

(defn text-slide-example []
  (slide/default-slide "Text slide" "this slide contains some text."
    (slide/text-content "This is the most basic type of slide."
                        "Each string passed is a <p> tag.")))

(defn bullet-point-slide-example []
  (slide/default-slide "Bullet point slide"
    (slide/bullet-point-content "this slide contains bullet points"
                                "some next text"
                                "so on.")))

(defn image-slide-example []
  (slide/default-slide "Image slide"
    (slide/img-content "http://verse.aasemoon.com/images/5/51/Clojure-Logo.png"
                       "this is the clojure logo")))

(defn code-slide-example []
  (slide/default-slide "Code slide"
    (slide/code-content (str "(let [a 2]\n"
                             "  (+ 3 a))")
                        "field for a short code descripion")))

(defn column-slide-example []
  (slide/two-column-slide "Two columns!"
                          "Some code"
                          (slide/code-content "(+ 3 4 5)"
                                              "Really cool addition")
                          "Some comments"
                          (slide/bullet-point-content "much precision"
                                                      "on the very nice"
                                                      "clojure language")))


(defn end-slide-example []
  (slide/end-slide "Thank you"
                   "Any questions?"))

(def slides [home-slide-example
             simili-python
             simili-clojure
             text-slide-example
             bullet-point-slide-example
             image-slide-example
             code-slide-example
             column-slide-example
             end-slide-example])

(defn -main
  []
  (slide/start-presentation! slides))
