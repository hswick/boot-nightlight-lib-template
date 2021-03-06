(ns boot.new.boot-nightlight-lib-template
  (:require [boot.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "boot-nightlight-lib-template"))

(defn boot-nightlight-lib-template
  "Input project name to create nightlight boot template"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (println (str "Generating fresh boot-nightlight-lib-template project: " name "."))
    (->files data
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["build.boot" (render "build.boot" data)]
             [".gitignore" (render ".gitignore" data)]
             ["README.MD" (render "README.MD" data)]
             ["test/{{sanitized}}/test.clj" (render "test.clj" data)]
             ["LICENSE" (render "LICENSE" data)])))
