(ns dynamodb-clj.core
  (:require [clojure.data.json :as json]
            [taoensso.faraday :as far]
            [clojure.java.io :as io]))

(def client-opts {:access-key "fakeMyKeyId"
                  :secret-key "fakeSecretAccessKey"
                  :endpoint   "http://localhost:8000"})

(far/create-table client-opts :movies
                  [:year :n]
                  {:range-keydef [:title :s]
                   :throughput   {:read 1 :write 1}
                   :block?       true})

(far/list-tables client-opts)
(far/describe-table client-opts :movies)

(doseq [movie (json/read-str (slurp (io/resource "moviedata.json"))
                             :key-fn keyword)]
  (far/put-item client-opts :movies movie))

(far/put-item client-opts
              :movies
              {:year  2015
               :title "The Big New Movie"
               :info  {:plot   "Nothing happens at all."
                       :rating 0}})

(far/get-item client-opts :movies {:year  2015
                                   :title "The Big New Movie"})

(far/update-item client-opts :movies {:year  2015
                                      :title "The Big New Movie"}
                 {:update-expr    "SET info.rating = :r, info.plot=:p, info.actors=:a"
                  :expr-attr-vals {":r" 5.5
                                   ":p" "Everything happens all at once."
                                   ":a" ["Larry" "Moe" "Curly"]}
                  :return         :updated-new})

(far/update-item client-opts :movies {:year  2015
                                      :title "The Big New Movie"}
                 {:update-expr    "SET info.rating = info.rating + :val"
                  :expr-attr-vals {":val" 1}
                  :return         :updated-new})

(far/update-item client-opts :movies {:year  2015
                                      :title "The Big New Movie"}
                 {:update-expr    "REMOVE info.actors[0]"
                  :cond-expr      "size(info.actors) >= :num"
                  :expr-attr-vals {":num" 3}
                  :return         :updated-new})

(far/delete-item client-opts :movies {:year  2015
                                      :title "The Big New Movie"}
                 #_{:cond-expr      "info.rating <= :val"
                    :expr-attr-vals {":val" 5.0}})
