(ns clojure-presentation.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [environ.core :refer [env]]))

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(def home-page
  (html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (include-css (if (env :dev) "css/site.css" "css/site.min.css"))
     (include-css "css/highlightjs.default.css")
     (include-css "css/web_demo.css")]
    [:body
     mount-target
     (include-js "js/app.js")]]))

(def data {:members [{:name "John Lennon"
                      :year-of-birth 1940
                      :qualities ["the best one" "real mvp"]
                      :popularity 12}
                     {:name "Paul McCartney"
                      :year-of-birth 1942
                      :qualities ["not dead" "went to Quebec city" "sings ok I guess"]
                      :popularity 11}
                     {:name "George Harrison"
                      :year-of-birth 1943
                      :qualities ["his guitar gentley weeps"]
                      :popularity 10}
                     {:name "Ringo Starr"
                      :year-of-birth 1940
                      :qualities ["can do that one beat right"]
                      :popularity 6}]})

(defroutes routes
  (GET "/" [] home-page)
  (GET "/data" [] (response data))
  (resources "/")
  (not-found "Not Found"))

(def app
  (let [handler (-> routes
                    wrap-json-response
                    (wrap-defaults site-defaults))]
    (if (env :dev) (-> handler wrap-exceptions wrap-reload) handler)))
