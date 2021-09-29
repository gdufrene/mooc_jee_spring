# Spring ORM + MVC

[Lien vers la vidéo](https://youtu.be/wqS-x_2LiBQ)

L'objectif est de rassembler les notions vues sur JPA et Spring MVC.  
Nous utiliserons la librairie spring-orm afin de configurer et d'injecter l'EntityManagerFactory.  
Cette librairie peut aussi être utile pour se passer du fichier META-INF/persistence.xml et pour paramétrer dynamiquement le contexte de persistance.

## Configuration des contrôleurs

Commencez par implémenter le contrôleur et la vue d'édition de d'une compagnie aérienne.

* Exposez le contrôleur et le DAO dans le contexte
* Configurez le ComponentScan pour découvrir ces composants
* Testez l'accès au formulaire

## Configuration du contexte de persistance

* Ajoutez une dépendance maven vers "spring-orm"
* Exposez un EntityManagerFactory à l'aide de l'objet utilitaire spring "LocalContainerEntityManagerFactoryBean"
* Injectez le "contexte de persistance" dans votre DAO
* Redémarrez tomcat pour vérifier le lancement d'hibernate pour le contexte de persistance

## Utiliser les DAO pour alimenter des vues

* modifiez le contrôleur pour utiliser le dao
* ajouter les entitées issues du dao dans le model
* utilisez les entitées dans la vue pour afficher les informations

## Lien avec le RESTController

Implémentez le web service REST manipulant du JSON pour lister les routes aériennes.

* injectez le DAO dans le contrôleur REST
* injectez le contexte de persistance dans le DAO
* complétez la requête JPA du DAO pour retourner les routes
* utilisez le DAO dans le contrôleur et retourner directement la liste des entitées
* redémarrez tomcat et exécutez une requête pour vérifier le contenu de la réponse JSON

