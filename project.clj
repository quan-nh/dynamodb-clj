(defproject dynamodb-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.taoensso/faraday "1.11.1"]
                 [org.clojure/data.json "2.2.1"]]
  :plugins [[clj-dynamodb-local "0.1.2"]]
  :repl-options {:init-ns dynamodb-clj.core})
