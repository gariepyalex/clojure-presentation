(ns clojure-presentation.demo.basic)

;; ===========================================================================
;; Exemple de code as data
(comment
  (+ 1 2 3 4) ;; Qu'est-ce qui ce passe ici?
  
  (def a (quote (+ 1 2 3 4))) ;; quote retire l'évaluation, sinon on aurait a = 10
  (type a)
  (type (quote a))
  (eval a)

  (eval (read-string "(+ 1 2 3 4)")))


;; ===========================================================================
;;Fonctions d'ordre supérieur
(comment
  (def v (range 11))

  (defn equals-ten?
    [y]
    (println y)
    (= 10 y))
  
  (defn plus-two
    [y]
    (+ 2 y))

  (map plus-two v)

  (reduce + 10 v)

  (filter equals-ten? (conj v 10)))

;; ===========================================================================
;; Laziness
(comment
  (defn sleep-and-return
    [x]
    (Thread/sleep x)
    x)

  (sleep-and-return 1000)

  (first (map sleep-and-return (repeat 10 1000)))) ;; sequence de 10 fois 1000


;; ===========================================================================
;; Exemple génération de nombres premiers

(defn divides?
  "True if a divides b"
  [a b]
  ;;true)
  (zero? (mod b a)))

(defn factors-to-verify
  [x]
  (if (= 2 x)
    nil
    (range 2 (inc (Math/sqrt x)))))

(defn prime?
  [x]
;;  false) 
  (nil?
   (first (filter #(divides? % x)
                  (factors-to-verify x)))))

(def prime-numbers
  (filter prime? (range 2 Integer/MAX_VALUE)))

(comment
  (first prime-numbers)
  (second prime-numbers)
  (take 10 prime-numbers)
  (take 10 (drop 10 prime-numbers)))
