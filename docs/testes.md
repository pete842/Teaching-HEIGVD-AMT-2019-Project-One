[Retour](../README.md)


# Testes

## Arquillian

Afin de tester les éléments du code interagissant directement avec les modèles (et donc la base de données), il est nécessaire d'utiliser un outil étant capable de garantir de pouvoir obtenir l'état de la BDD requis pour l'exécution d'un test, et ensuite de pouvoir rétablir le contexte avant son exécution.
Il va sans dire que cet environnement de test doit aussi être capable de résoudre les injections effectué par Java EE, autrement dit, il doit être capable de gérer les *resources* utilisées par le framework et de faire appel à ces dernières.

Arquillian répond à tout ces critères et plus encore, c'est pourquoi est utilisé dans ce projet afin de tester les *DAOs* (objets d'accès aux données, en français).

[Retour](../README.md)
