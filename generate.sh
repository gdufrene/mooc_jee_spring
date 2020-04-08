#!/bin/bash

function gen() {
  files=""
  outfile="$1.html"
  test $# -gt 1 && shift
  for file in $@; do
    files="$files tpl/$file.html"
  done
  cat tpl/header.html $files tpl/footer.html > $outfile
}

gen index

gen java
gen maven
gen maven-lille1
gen tomcat
#gen devops_tools
gen git
gen jdbc
gen jpa
gen servlet
gen spring
gen spring-mvc
gen jstl

gen javaee-intro
gen javaee-servlet-jsp

#gen sitemap
