# Exercice 4/02

Cet exercice vous permettra d'utiliser spring-webmvc.  
Il est réalisable sans utiliser les éléments des exercices précédants.

Le pom définit un mode de packaging "war", ce qui permettra de construire une archive web s'exécutant dans un container web java compatible, tel que tomcat.  
Vous remarquerez qu'il y a peu de dépendances :

* spring-webmvc : pour définir les contrôleurs et les liens avec les vues.
* jackson-dataformat-csv : utiliser pour une implémentation mémoire du DAO Catalog.
* javax.servlet-api : fourni par tomcat au runtime, défini le modèle de programmation "servlet".
* junit : pour nos tests unitaires

Evidemment, chacune de ces librairies dépendent elle-même d'autres librairies, ce qui nous impose de produire un war contenant une bonne dizaine de jar.

## Configurer un contexte web pour spring

Il faut commencer par enregistrer la DispatcherServlet de spring-webmvc.  
Cela se fait au moyen d'un fichier "web.xml", ou en étendant WebApplicationInitializer et sa méthode "onStartup".  
Nous opterons pour cette 2e solution.

Ouvrez la classe AppConfig et ajoutez les annotations nécessaires pour activer le module Spring Web MVC.  
Ajoutez une autre annotation pour rechercher les composants et contrôleurs dans les paquetages pertinents.

## Configurer un contrôleur

Ouvrez la classe CatalogController.

* exposez cette classe dans le contexte comme un contrôleur
* injectez une référence de CatalogMockDao
* configurez convenablement le mapping des url pour que les opérations "list" et "categoryContent" soient respectivement appelées lors de l'utilisation des url /catalog/categories.html et /catalog/category/ID.html (ID étant l'identifiant de la catégorie à afficher).
* Implémentez les deux opérations pour mettre à disposition du model les objets issus du DAO.
* Ces opérations retourneront le nom logique des vues à afficher.
* Ajoutez un ViewResolver dans le contexte de spring pour permettre de placer toutes les vues dans /WEB-INF/views et d'ajouter automatiquement l'extension "jsp" à ces vues.

## Implémenter les vues

Complétez la vue "categories.jsp" pour afficher la liste des catégories.

Pour chaque catégorie, on affichera :

* Un lien vers le contenu de la catégorie
* Une image représentant la catégorie
* Le nom de la catégorie

Complétez la vue "category.jsp" pour afficher les produits d'une catégorie.

Pour chaque produit, on affichera :

* son image
* son prix au format X,XX € (les prix sont en centimes dans la base fournie)
* son libellé (nom du produit)

## Implémenter un webservice manipulant des données JSON

Pour permettre à spring de manipuler des objets en entrée ou sortie au format JSON, il est nécessaire d'avoir une librairie tierse dans le classpath au runtime.  
Notre application possède déjà une dépendance vers une telle librairie. En effet, le mock du DAO Catalog utilise une partie de la librairie "jackson" qui permet de transformer du JSON en objet java et inversement.  
Si ce n'était pas le cas, il faudrait ajouter la dépendance vers l'artefact "jackson-databind" du groupe "com.fasterxml.jackson.core". Cette dépendance étant uniquement nécessaire par spring à l'éxécution, il serait possible de mettre cette dépendance en scope "runtime".

Un contrôleur REST est un contrôleur permettant de manipuler en entrée des objets java construit à partir du corps de requêtes HTTP, et produisant des objet java traduit directement dans un format, autre que html.  
Ces contrôleurs sont généralement appelés par une librairie ou un programme tiers, qui devra pouvoir interpréter les réponses HTTP.

Ce type de Contrôleur est annoté "RestControler" en spring.

Complétez la classe "RestHistoryController" pour exposer les deux opérations "getHistory" et "addStatus".  
Les deux opérations seront exposées sur la même URL "/history/ID.json" (ID étant l'identifiant de la commande concernée).  
La première opération sera exposée sur les opération HTTP GET, la seconde uniquement sur du "POST".

le paramètre StatusHistory de l'opération "addStatus" sera créé à partir du corps de la requête HTTP, sous la forme d'un objet JSON transformé automatiquement en java par la librairie "jackson".

L'opération "addStatus" renverra "Ok" si le status est correctement ajouté, "Error" si une exception est générée (exemple : commande introuvable).

Le mock "HistorySourceMock" renvoit les même status pour chaque commande, excepté pour la commande numéro 666 pour laquelle une exception est levée.

## Pour aller plus loin

* Essayez de brancher les DAO JPA de l'exercice 3/01.  
Vous devriez être en mesure d'ajouter une dépendance vers votre librairie "drive-model" si vous l'avez installé dans votre repository maven local.  
Pensez à supprimer les packages "fr.eservices.drive.dao" et "fr.eservices.drive.model" du projet "exo401" si vous utilisez cette librairie.
* Implémentez l'interface HistorySource à l'aide d'un DAO JPA.


