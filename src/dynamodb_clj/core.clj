(ns dynamodb-clj.core
  (:require [taoensso.faraday :as far]))

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

(far/put-item client-opts
              :movies
              {:year  2015
               :title "The Big New Movie"
               :info  {:plot   "Nothing happens at all."
                       :rating 0}
               })

(far/get-item client-opts :movies {:year  2015
                                   :title "The Big New Movie"})

(far/update-item client-opts :movies {:year  2015
                                      :title "The Big New Movie"}
                 {:update-expr    "SET info.rating = :r, info.plot=:p, info.actors=:a"
                  :expr-attr-vals {":r" 5.5
                                   ":p" "Everything happens all at once."
                                   ":a" ["Larry" "Moe" "Curly"]}
                  :return         :updated-new})
