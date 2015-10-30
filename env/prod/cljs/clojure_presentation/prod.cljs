(ns clojure-presentation.prod
  (:require [clojure-presentation.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
