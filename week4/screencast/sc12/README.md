# Spring

[Lien vers la vidéo](https://youtu.be/-IDPUBumnak)

## Approche classique

* Importez ce projet maven dans votre IDE
* Essayez de lancer fr.eservices.sample1.Application. 
* Que se passe-t-il ? Pourquoi ?
* Implémentez de manière simple le constructeur de cette classe pour y initialiser les propriétés.
* Relancez l'application et vérifiez que cela fonctionne.


## Vers l'inversion de contrôle

Nous aimerions éviter d'avoir à brancher les instances de Greeter, Printer et Welcome.  

* Supprimez le contenu du constructeur de Application. Les propriétés de l'object ne sont donc plus initialisées dans votre objet Application.
* Utilisons les annotations @Component et @Autowired pour définir les composants et leurs injections. 
* Dans le main de l'application, créons un "ApplicationContext" pour obtenir une instance de notre objet Application.
* Relancez l'application est vérifiez que cela fonctionne.
* Testez avec src/test/java/sample1/BasicContextTest

## Choisir l'assemblage de composants

Regardez le package _fr.eservices.sample2_ et implémenter le _fr.eservices.sample2.appl.Application_ à l'aide des objets console/english à disposition dans impl. Ils encapsulent simplement les objets de sample1...  
Il faudra évidemment placer toutes les annotations nécessaire sur ConsolePrinter, ConsoleWelcome, EnglishGreeter et Application.  
Vérifiez le fonctionnement.  
Le test hasBasicComponents de src/test/java/sample2/MultiComponentTest.java devrait passer.  


Ajouter les annotations @Component sur les autres implémentation.  
Le test hasManyComponents devrait passer. 

Relancez l'application.  
Que se passe-t-il et pourquoi ?  
Ajouter une annotation @Qualifier avec les noms "swing" et "french" sur les objets correspondant.  
Idem avec les qualifier "console" et "english".  
le test hasProperQualifier devrait passer. 

Vérifier qu'en plaçant les qualifier dans Application cela branche la bonne implémentation.
Lancer Application, essayer différents assemblages.

## Un peu de xml

Vous allez maintenant définir le contexte spring en XML.

Lorsque vous utilisez un ClassPathXmlApplicationContext comme c'est le cas dans le test XmlContextTest, par défaut, aucune annotation n'est lue/interprétée.

Pour définir le bean "Application" vous devez y injecter les références (leur id) des beans définis plus haut. Le ClassPathXmlApplicationContext injecte les références uniquement avec des setter. Ainsi, si vous souhaiter injecter une propriété "greeter", il faudra un "setGreeter(...)" dans votre objet.

* Complétez le fichier src/main/resources/application-context.xml
* Définisser l'assemblage à l'aide des objets SwingWelcome, FrenchGreeter et ConsolePrinter
* injecter ces beans dans un bean Application, également défini dans le xml
* ajoutez les setter nécessaire à l'injection des références
* vérifier avec le test testXmlContext


## Spring completed !

Félicitation, vous avez compris le fonctionnement de base des beans spring.  
tous les sous-projets de la communauté sont basées sur ces mécanismes, il est important de les comprendre.

