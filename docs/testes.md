[Retour](../README.md)


# Testes

## Servlet & Mokito

Nous utilisons `Mokito` pour effectuer les tests sur les servlets, car le fonctionnement d'un servlet est complexe et travail avec beaucoup de classes tierces.

`Mokito` est une framework de `Mocking` permettant de réduire le `scope` de test lorsqu'on s'attaque à de la logique `business` ou des fonctions utilisant beaucoup de classe externe.
Le but ici est de rendre un test vraiment, `unitaire` car il est nécessaire de tester uniquement le code (logique) et non pas l'ensemble des classes tiers, qui elles sont testées de leur côté.
Ceux-ci permet aussi de ne pas baser notre test sur une classe qui pourrait contenir une erreur et faire échouer le test pas une raison externe.

Pour ce faire, on crée un ensemble de `Mock` des différents objets utilisés et nous les installons dans notre classe testée.
Il est alors nécessaire de définir le comportement des appels pour qu'elle puisse renvoyer une information cohérente pour la bonne suite du déroulement du code.
Après l'ensemble de ces définitions, nous pouvons vérifier que des appels précis ont été correctement effectués pour savoir si la logique est correcte.
Par exemple, vérifier que si l'utilisation s'authentifie avec ces informations valides, le `servlet` redirige correctement l'utilisateur en ajoutant bien les informations nécessaires dans la `session`.

### Avis sur Mokito

Nous avons tenté d'atteindre une certaine perfection sur les testes unitaires en cherchant à `mock` l'ensemble des options, mais cela est complexe dû aux limitations de `Mokito` a ne pas pouvoir atteindre les méthodes `static`.
Nous nous sommes permis quelques simplifications sur l'utilisation des `builder` de `Lombok` pour ne pas complexifier le code en acceptant que la librairie ne soit pas un risque d'erreur.

Ensuite, il a été nécessaire de `mock` les appels de méthodes à la super class, pour ce faire, nous utilisons le système de `spy` permettant de réagir à un appel à une méthode :
```java
servlet = Mockito.spy(servlet);
doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
doReturn(false).when((BaseHttpServlet) servlet).doHTMLFormBetter(any(HttpServletRequest.class), any(HttpServletResponse.class));
```

`Mokito` Se révèle très agréable à utiliser et il est assez rapide à prendre en main sans avoir la nécessité de faire du copier/coller.
C'est clairement un framework de test que nous réutiliserons.


## Arquillian

Afin de tester les éléments du code interagissant directement avec les modèles (et donc la base de données), il est nécessaire d'utiliser un outil étant capable de garantir de pouvoir obtenir l'état de la BDD requis pour l'exécution d'un test, et ensuite de pouvoir rétablir le contexte avant son exécution.
Il va sans dire que cet environnement de test doit aussi être capable de résoudre les injections effectuées par Java EE, autrement dit, il doit être capable de gérer les *resources* utilisées par le framework et de faire appel à ces dernières.

Arquillian répond à tout ces critères et plus encore, c'est pourquoi est utilisé dans ce projet afin de tester les *DAOs* (objets d'accès aux données, en français).

Grâce à l'annotation `@Transactional(TransactionMode.ROLLBACK)`, il est possible d'indiquer à Arquillian de restituer la base dans l'état précédant le test en question.
Ainsi, un ajout sera supprimé, une modification inversée et un élément supprimé sera réajouté.

L'annotation `@EJB` quant à elle permet l'injection d'une `bean` et la résolution de ses `resources`.
Elle sera donc située au niveau de la déclaration d'un objet nécessitant ce comportement, tel que les `DAOs`.

Enfin, les annotations `@Before` et `@After` permette respectivement de mettre en place une fonction de *setup* et une fonction de *teardown*.

### Avis

Pour être parfaitement honnête, Arquillian nous a posé de grandes difficultés de mise en place.
En effet, il s'est avéré plus que critique de trouver l'ensemble des modules à importer de façon à pouvoir faire fonctionner correctement ce *framework* de test.

Cependant, une fois en possession d'un environnement stable et fonctionnel, force nous est de constater qu'Arquillian est un outil infiniment puissant, pour la simple et bonne raison qu'il représente plus qu'un système de mocking.
Il s'agit en effet d'un logiciel très (trop) puissant qui pourra être configuré afin de réaliser **n'importe quel test** d'intégration sur votre application web.

Le seul défaut majeur relatif à l'utilisation d'Arquillian résidera dans la lenteur du déploiement et d'exécution des tests.


[Retour](../README.md)
