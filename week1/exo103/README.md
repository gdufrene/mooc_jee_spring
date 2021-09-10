# Exercice 1/03

Cet exercice met en oeuvre l'ensemble des notions vues cette semaine.  

Nous allons créer un sac permettant de ranger une liste d'objets.  
Ce sac pourrait être vu comme un sac d'achat sur internet, ou le sac magique d'items d'un jeu.

Dans un premier temps, ce sac aura une forme très simple, associant une référence d'objet à la quantité associée.  
Nous vous fournissons un Objet `Bag` permettant d'ajouter une référence et une quantité souhaitée. Cet objet possède une méthode print qui affiche le contenu du sac dans une liste html.

## Transmettre des paramètres

1. Compléter la méthode doGet pour afficher un formulaire contenant deux champs nommés "ref" et "qty". Il faudra également un bouton pour valider le contenu. Ce formulaire devra être configurer pour générer une requête POST vers cette même servlet.  

1. Votre servlet doit indiquer le type de contenu "text/html" au moyen de l'objet HttpServletResponse.  

1. Compléter la méthode doPost pour afficher un message d'erreur si l'un des deux paramètres n'est pas présent ou vide, vous pouvez aussi mettre le status de réponse à 400 qui correspond à une erreur de requête. Affichez les deux paramètres saisis si ils sont présents afin de vous assurer qu'ils sont correctement transmis.

## Propriété de servlet

1. Modifiez doPost pour utiliser le sac via l'attribut de classe "myBag". La méthode "setItem" permet d'ajouter le produit demandé.  

1. Pour le moment, affichez le contenu du sac après l'ajout du produit en appelant la méthode print.  

1. Testez l'enchainement doGet / doPost et l'utilisation du sac.  

1. Pour Faciliter l'utilisation du sac, effectuez une redirection après l'ajout d'un produit dans la méthode doPost, affichez le contenu du sac avant le formulaire de saisi dans doGet.  

Si vous utilisez deux navigateurs différents vers la page du sac (ce qui simule deux utilisateurs différents), vous devriez arriver à la conclusion que le sac affiché est le même pour les deux utilisateurs. Ce n'est évidemment pas souhaitable.

## Utiliser une session

La servlet n'est instanciée qu'une fois pour l'ensemble des requêtes effectuées sur le serveur et nous aimerions que chaque utilisateur (navigateur), possède son propre sac.

1. Dans la méthode doPost, utilisez la session pour récupérer une instance de sac propre à chaque utilisateur.  

1. Pensez à ajouter un nouveau sac dans la session si il n'est pas encore créé.  

1. Modifiez doGet pour utiliser le sac présent dans la session.  

Essayez de nouveau de manipuler le sac dans deux navigateurs différents. Chaque sac devrait maintenant être indépendant et contenir des objets différents.


## Utiliser une JSP

1. Créez un fichier "bag.jsp" dans le répertoire de votre application web.  
1. Importer l'objet "web.Bag" au moyen d'une directive.  
1. Récupérer l'instance de sac depuis la session  
1. Ajouter un titre H1 "sac". Si il existe, affichez la liste des éléments du sac en utilisant sa méthode print  
1. Afficher un formulaire pour ajouter un article au sac. L'action sera l'url "bag" votre servlet. Les données seront transmises en POST.  
1. Vous pouvez modifier la redirection de votre servlet pour revenir sur la jsp après avoir ajouter quelquechose dans le sac.  

Exécutez votre jsp en allant sur http://localhost:8080/exo103/bag.jsp

Le fonctionnement est tout à fait similaire à ce que l'on ferait dans une servlet. Il serait possible de développer toutes les fonctions d'un site uniquement à l'aide de jsp.  
On préfère généralement développer les "traitements" contenant beaucoup de java dans des servlets, et la partie graphique contenant beaucoup de html dans des jsp.


## Déléguer l'affichage à une JSP

Il est possible de recevoir une requête dans une servlet, commencer un traitement, et déléguer la suite de la requête à une JSP.  
Nous allons considérer la récupération du sac depuis la session comme le traitement à effectuer dans la servlet.

1. Dans le doGet de la servlet, Utilisez la méthode setAttribute de HttpServletRequest pour affecter le sac à un attribut nommé "bag".  
1. Supprimez ou mettez en commentaire l'ensemble du code affichant du html.  
1. à la fin de votre méthode, utilisez getRequestDispatcher et forward pour déléguer l'affichage à la JSP.  
1. Dans la JSP, utilisez getAttribute sur l'instance request pour récupérer le sac plutôt que de le récupérer de la session.  

Notre jsp est maintenant indépendante de la notion de session, son rôle est d'afficher un sac présent sur l'objet request.  
La servlet ne contient plus de code concernant la présentation html du sac.

La jsp reste accessible directement via une url car elle est présente à la racine de notre application web. Ce n'est pas souhaitable car cette vue nécessite un traitement préalable: la récupération du sac.

1. Placez la jsp dans le répertoire "WEB-INF" dont les fichiers ne sont pas exposés comme des ressources web.  
1. Modifiez le forward pour indiquer le chemin vers la jsp à partir de la racine de l'application web. Ce chemin commencera donc par "/WEB-INF/".  
1. Vous devriez mettre ce chemin dans une propriété public/static nommée "jspView". Cela nous permettra de pouvoir changer le fichier facilement ultérieurement.  
1. Pensez à re-faire pointer votre redirection de doPost vers la servlet, soit vers l'url "bag".  


## Packaging

Il est possible de préparer une archive web java (war) de notre application pour faciliter la distribution et le déploiement.    
Un "war" est un fichier compressé au format zip contenant une arborescence particulière pour être utilisée par un serveur d'application java.

* Utilisez la commande "jar" pour créer une archive nommée "app.war" dans webapps, elle contiendra les fichiers de votre application web. Inutile d'y inclure les fichiers source java, ils ne sont pas utilisés par tomcat.

Le contenu du war devrait donc être :
```
WEB-INF/
 |_ bag.jsp
 |_ classes/
    |_ webcart/
       |_ Bag.class
       |_ BagServlet.class 
```


