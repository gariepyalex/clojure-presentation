(ns clojure-presentation.core
  (:require [clojure-presentation.slide :as slide]
            [clojure.string :as string]))

;; -------------------------
;; Views
(defn home-slide-example []
  (slide/home-slide "Clojure"
                    "a pragmatic functional language"
                    "by Alexandre Gariépy"
                    "November the 11th, 2015"))

(defn que-fait-ce-python? []
  (slide/default-slide "Que fait ce code?"
    (slide/code-content (str "def print_number():\n"
                             "    a = 2\n"
                             "    print(a)")
                        :language "python")))

(defn que-fait-ce-python?-2 []
  (slide/default-slide "Que fait ce code?"
    (slide/code-content (str "def print_number():\n"
                             "    a = 2\n"
                             "    some_function(a)\n"
                             "    print(a)")
                        :language "python")))

(defn immutability []
  (slide/default-slide "Immutabilité en clojure"
    (slide/bullet-point-content "Clojure impose une discipline stricte sur l'assignation"
                                "Les \"variable\" sont immuables"
                                "Les collections sont immuables")))
  
(defn immutability-code-example []
  (slide/two-column-slide
   "Immutabilité..."
   "des variables"
   (slide/code-content (string/join "\n"
                                    ["(def a 2)"
                                     "(+ a 3)"
                                     ";; => 5"
                                     "(eval a)"
                                     ";; => 2"]))
    "des collections"
    (slide/code-content (string/join "\n"
                                     ["(def a [0 1 2])"
                                      "(conj a 5)"
                                      ";; => [0 1 2 3]"
                                      "(eval a)"
                                      ";; => [0 1 2]"])
                        :description "conj comme conjonction, ajoute un élément")))

(defn immutability-is-good []
  (slide/default-slide "C'est une bonne chose!"
   (slide/code-content (string/join "\n"
                                    ["(def a [1 2 3])"
                                     "(function1 a)"
                                     "(function2 a)"
                                     "(function3 a)"
                                     "(eval a)"
                                     ";; => [1 2 3]"]))
    (slide/text-content "Pas d'effets de bord, pas de surprise")))

(defn pure-fuction []
  (slide/default-slide "Fonction pure"
    (slide/bullet-point-content "f(x, y) = 3x + 6y + 5"
                                "Input/output"
                                "Pas d'effet de bord"
                                "Complexité provient de la composition de fonction simples")))

(defn immutability-pure-function-recap []
  (slide/default-slide "Immutabilité - Fonctions pure"
    (slide/bullet-point-content "Facile à comprendre"
                                "Facile à tester (pas de mocks)"
                                "Multithreading sans locks!")))

(defn multi-lang-code-example []
  (slide/default-slide "Regardez ce code"
    (slide/code-content "def toto(a, b, c):" :language "python")
    (slide/code-content "public static void main(String[] args){}" :language "java")
    (slide/code-content "(defn -main [x] (println x))" :language "clojure")))

(defn text-slide-example []
  (slide/default-slide "Text slide" 
    (slide/text-content
     "This slide contains some text."
     "This is the most basic type of slide."
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
             que-fait-ce-python?
             que-fait-ce-python?-2
             immutability
             immutability-code-example
             immutability-is-good
             pure-fuction
             immutability-pure-function-recap
             multi-lang-code-example
             text-slide-example
             bullet-point-slide-example
             image-slide-example
             code-slide-example
             column-slide-example
             end-slide-example])

(defn -main
  []
  (slide/start-presentation! slides))
