#!/bin/bash
test -f junit-4.12.jar || wget https://repo1.maven.org/maven2/junit/junit/4.12/junit-4.12.jar
test -f hamcrest-core-1.3.jar || wget https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
test -f WEB-INF/lib/sqlite-jdbc-3.20.0.jar || wget https://oss.sonatype.org/content/repositories/releases/org/xerial/sqlite-jdbc/3.20.0/sqlite-jdbc-3.20.0.jar

javac -cp WEB-INF/classes:junit-4.12.jar:hamcrest-core-1.3.jar -d WEB-INF/classes WEB-INF/classes/Test*.java
java -cp WEB-INF/classes:junit-4.12.jar:hamcrest-core-1.3.jar:WEB-INF/lib/sqlite-jdbc-3.20.0.jar org.junit.runner.JUnitCore TestUserJDBC
