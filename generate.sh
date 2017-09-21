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
gen tomcat
gen devops_tools
gen git
gen jdbc
gen jpa
gen servlet
gen spring
gen jstl

gen week1
gen week2
gen week3
gen week4

gen sitemap
