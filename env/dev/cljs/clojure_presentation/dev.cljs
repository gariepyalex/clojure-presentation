(ns ^:figwheel-no-load clojure-presentation.dev
  (:require [clojure-presentation.core :as core]
            [clojure-presentation.slide :as slide]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
 :websocket-url "ws://localhost:3449/figwheel-ws"
 :jsload-callback (fn [] (slide/mount-root)))

(core/-main)
