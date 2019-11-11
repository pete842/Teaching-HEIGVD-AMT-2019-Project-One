[Retour](../README.md)


# Testes

## Servlet & Mokito

Nous utilisons `Mokito` pour effectuer les testes sur les servlets car le fonctionnement d'un servlet est complexe et travail avec beaucoup de classes tierses.

`Mokito` est une framework de `Mocking` permettant de réduire le `scope` de test lorsqu'on s'attaque à de la logique `business` ou des fonctions utilisant beaucoup de classe externe.
Le but ici est de rendre un test vraiment `unitaire` car il est nécessaire de tester uniquement le code (logique) et non pas l'ensemble des classes tiers, qui elles sont testé de leur coté.
Ceux-ci permet aussi de ne pas baser notre teste sur une classe qui pourrait contenir une erreur et faire échouer le test pas une raison externe.

Pour ce faire, on crée un ensemble de `Mock` des différents objets utilisés et nous les installons dans notre classe testée.
Il est alors nécessaire de définir le comportement des appels pour qu'elle puisse renvoyé une information cohérente pour la bonne suite du déroulement du code.
Après l'ensemble de ces definitions, nous pouvons verifié que des appels précis ont été correctement effecuter pour savoir si la logique est correct.
Par exemple, vérifé que si l'utilisation s'authentifie avec ces informations valides, le `servlet` redirige correctement l'utilisateur en ajoutant bien les informations nécessaires dans la `session`.

### Avis sur Mokito

Nous avons tenté d'atteindre une certaine perfection sur les testes unitaires en cherchant à `mock` l'ensemble des options mais cela est complexe dû au limitation de `Mokito` a ne pas pouvoir atteindre les méthodes `static`.
Nous nous sommes permit quelques simplifications sur l'utilisation des `builder` de `Lombok` pour ne pas complexifier le code en acceptant que la librairie ne sera pas un risque d'erreur.

Ensuite, il a été nécessaire de `mock` les appels de méthodes à la super class, pour ce faire, nous utilisons le système de `spy` permettant de réagir à un appel à une méthode :
```java
servlet = Mockito.spy(servlet);
doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
doReturn(false).when((BaseHttpServlet) servlet).doHTMLFormBetter(any(HttpServletRequest.class), any(HttpServletResponse.class));
```

`Mokito` se revèle très agreable à utiliser et il est assez rapide à prendre en main sans avoir la nécessité de faire du copier/coller.
C'est clairement un framework de test que nous réutiliserons.


## Arquillian

Afin de tester les éléments du code interagissant directement avec les modèles (et donc la base de données), il est nécessaire d'utiliser un outil étant capable de garantir de pouvoir obtenir l'état de la BDD requis pour l'exécution d'un test, et ensuite de pouvoir rétablir le contexte avant son exécution.
Il va sans dire que cet environnement de test doit aussi être capable de résoudre les injections effectué par Java EE, autrement dit, il doit être capable de gérer les *resources* utilisées par le framework et de faire appel à ces dernières.

Arquillian répond à tout ces critères et plus encore, c'est pourquoi est utilisé dans ce projet afin de tester les *DAOs* (objets d'accès aux données, en français).

[Retour](../README.md)
