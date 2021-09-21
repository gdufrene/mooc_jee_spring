# Exercice 2/01

Pour cet exercice, vous réutiliserez le contenu du répertoire webapp de la semaine précédente.  
Prenez soin d'avoir, au final, un contexte web nommé "exo201".

## Implémentation d'un DAO avec JDBC

* Implémentez [UserDaoSqlite](WEB-INF/classes/user/UserDaoSqlite.java)

exemple de commande de compilation depuis webapps/exo203 :

    javac -cp WEB-INF/classes -d WEB-INF/classes WEB-INF/classes/user/UserDaoSqlite.java

Pour tester le Dao, une classe de test unitaire est fournie. 
Vous devriez être en mesure de faire passer le "TestUserJDBC".  
Vous pouvez la lancer en bash avec le script `runTests.sh`

Sinon, il faut télécharger les librairies [junit](https://repo1.maven.org/maven2/junit/junit/4.12/junit-4.12.jar), [hamcrest](https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar) et le driver [JDBC SQLite Driver](https://oss.sonatype.org/content/repositories/releases/org/xerial/sqlite-jdbc/3.20.0/sqlite-jdbc-3.20.0.jar). Mettez le drivers dans WEB-INF/lib, il sera utile à tomcat ensuite. Puis, il faut compiler et lancer les tests à l'aide des commandes :

    javac -cp WEB-INF/classes:junit-4.12.jar:hamcrest-core-1.3.jar -d WEB-INF/classes WEB-INF/classes/TestUserJDBC.java WEB-INF/classes/auth/SigninCheck.java
	java -cp WEB-INF/classes:junit-4.12.jar:hamcrest-core-1.3.jar:WEB-INF/lib/sqlite-jdbc-3.20.0.jar org.junit.runner.JUnitCore TestUserJDBC

**ATTENTION** sous windows le séparateur de classpath est `;` et non `:`

* Téléchargez [JDBC SQLite Driver](https://oss.sonatype.org/content/repositories/releases/org/xerial/sqlite-jdbc/3.20.0/sqlite-jdbc-3.20.0.jar), et placez le dans le répertoire WEB-INF/lib


## Utilisation d'un DAO avec une servlet/jsp 

Nous allons utiliser le DAO pour l'enregistrement des utilisateurs.

* Complétez [RegisterServlet](WEB-INF/classes/user/RegisterServlet.java) et utilisez le DAO
* Complétez register.jsp, et notamment : 
  * pensez à traiter les différents cas d'erreur,
  * affichez des messages d'erreur en rapport avec le cas rencontré,
  * mettez en avant les champs du formulaire en erreur à l'aide de bootstrap.

A cette étape, vous devriez pouvoir faire passer le test "auth/SigninCheck"

* Si vous l'avez réalisé, reprenez votre solution de l'exercice 1/02 en utilisant ce DAO pour la servlet d'authentification

