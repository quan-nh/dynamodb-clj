(defproject dynamodb-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.taoensso/faraday "1.10.1"]
                 [org.clojure/data.json "1.0.0"]]
  :plugins [[clj-dynamodb-local "0.1.2"]]
  :repl-options {:init-ns dynamodb-clj.core})
