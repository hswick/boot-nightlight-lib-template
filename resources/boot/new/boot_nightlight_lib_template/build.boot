(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.8.0"]
                  [nightlight "1.7.0" :scope "test"]
                  [adzerk/boot-test "1.2.0" :scope "test"]]
  :repositories (conj (get-env :repositories)
                  	  ["clojars" {:url "https://clojars.org/repo"
                              	  :username (System/getenv "CLOJARS_USER")
                              	  :password (System/getenv "CLOJARS_PASS")}]))

(task-options!
	jar {:main '{{name}}.core)
		 :manifest {"Description" "Clojure library meant to do..."}}
	pom {:version "0.0.1"
		 :project 'author/{{name}}
		 :description "{{name}} is meant to do..."
		 :url "https://github.com/author/{{name}}"}
	push {:repo "clojars"})

(deftask deploy []
  (comp
    (pom)
    (jar)
    (push)))

;;So nightlight can still open even if there is an error in the core file
(try
	(require '{{name}}.core)
	(catch Exception e (.getMessage e)))

(require
  '[nightlight.boot :refer [nightlight]]
  '[adzerk.boot-test :refer :all])

(deftask night []
  (comp
    (wait)
    (nightlight :port 4000)))

(deftask testing [] (merge-env! :source-paths #{"test"}) identity)

(deftask run-tests
	[]
	(comp
		(testing)
		(test)))