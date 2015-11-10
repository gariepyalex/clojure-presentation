(ns clojure-presentation.core
  (:require [clojure-presentation.slide :as slide]
            [clojure-presentation.web-demo :as web-demo]
            [clojure.string :as string]))

;; -------------------------
;; Views
(defn home-slide-example []
  (slide/home-slide "Clojure"
                    "Un langage fonctionnel pragmatique"
                    "by Alexandre Gariépy"
                    "November the 11th, 2015"))

(defn plan []
  (slide/default-slide "Plan de la présentation"
    (slide/bullet-point-content "Programmation fonctionnelle"
                                "Démo: nombres premiers"
                                "Avantages de Clojure"
                                "Démo: web"
                                "Quelques liens")))

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

(defn mutability []
  (slide/default-slide "Impossible à prédire!"
    (slide/bullet-point-content "Chaque objet doit encapsuler ses variables"
                                "Mène à des interactions complexes entre des objets"
                                "Très difficile de prédire l'état du système"
                                "Doit isoler les composantes pour les tester (mocks)")))

(defn immutability []
  (slide/default-slide "Immuabilité en clojure"
    (slide/bullet-point-content "Clojure impose une discipline stricte sur l'assignation"
                                "Les \"variable\" sont immuables"
                                "Les collections sont immuables")))
  
(defn immutability-code-example []
  (slide/two-column-slide
   "Immuabilité..."
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
  (slide/default-slide "Immuabilité - Fonctions pures"
    (slide/bullet-point-content "Facile à comprendre"
                                "Facile à tester (pas de mocks)"
                                "Multithreading sans locks!")))

(defn data-orientation []
  (slide/default-slide "Clojure est orienté data"
    (slide/bullet-point-content "Manipulation de structures de données immuables"
                                "Déclaratif vs Impératif"
                                "Code as data"
                                "Lisp = LISt Processing")))

(defn demo1 []
  (slide/default-slide "Démo"
    (slide/text-content "Générateur de nombres premiers")))

(defn demo1-recap []
  (slide/two-column-slide
   "Pourquoi Clojure?"
   "Pure"
   (slide/bullet-point-content "Laziness"
                               "Fonctions d'ordre supérieur"
                               "Code as data (Lisp)"
                               "Immuabilité stricte"
                               "Structures de données immuables")
   "Pragmatique"
   (slide/bullet-point-content "Pure, mais pas trop (Haskell...)"
                               "Interop Java/Javascript"
                               "Développement dynamique avec le REPL"
                               "Met l'accent sur la simplicité")))

(defn demo-web []
  (slide/default-slide "Démo web"
    (slide/custom-content (web-demo/web-demo-component))))

(defn to-know-more1 []
  (slide/default-slide
    "Pour en savoir plus"
    (slide/img-content "http://www.braveclojure.com/assets/images/home/book-cover.jpg"
                       "Livre gratuit sur le web")))

(defn to-know-more2 []
  (slide/default-slide
    "Pour en savoir plus"
    (slide/img-content "https://raw.githubusercontent.com/matthiasn/talk-transcripts/master/Hickey_Rich/SimpleMadeEasy/00.00.00.jpg"
                       "Et ClojureTV sur YouTube")))

(defn to-know-more3 []
  (slide/default-slide
    "Pour en savoir plus"
    (slide/text-content "Robert C Martin - Functional Programming; What? Why? When?"
                        "https://www.youtube.com/watch?v=7Zlp9rKHGD4")))
  

(defn end-slide-example []
  (slide/end-slide "Merci"
                   "Des questions?"))

(def slides [home-slide-example
             que-fait-ce-python?
             que-fait-ce-python?-2
             mutability
             immutability
             immutability-code-example
             immutability-is-good
             pure-fuction
             immutability-pure-function-recap
             data-orientation
             demo1
             demo1-recap
             demo-web
             to-know-more1
             to-know-more2
             to-know-more3
             end-slide-example])

(defn -main
  []
  (slide/start-presentation! slides))
