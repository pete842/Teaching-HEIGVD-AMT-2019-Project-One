[Retour](../README.md)


# Context

Nous avons choisi comme context de ce projet un site de librairie de média personnel permettant de gérer un suivi des médias vu ou à voir.
Nous avions eu l'idée d'utiliser une librairie externe pour avoir l'ensemble des données médias mais nous gardons cette idée pour le prochain projet.
Dans ce cas, nous avons simplifié la base de données et nous travaillons uniquement des films pour cette itération. 
Cependant, il serait très simple de pouvoir fournir un ensemble de médias différents en fournissant plus d'information au niveau de la base de données et des pages.

## Entities

Les trois types d'entités sont relativement simple :

- Users {id, username, firstname, lastname, email, member_since, password}
- MediaUser {id, user_id, media_id, rating, watched}
- Media {id, title, release, duration, main_genre, rating}

Les points importants sont la différence dans la table de jointure `MediaUser`, on fait la différence entre un média `toWatch` et `Watched` par la présence des champs `watched` et `rating`.
Ces champs sont dans l'ordre : une date de visionnage et une évaluation personnel du média.
Dans le cas de `Media` le champ `main_genre` contient un ensemble de genre (ex. Triller, Action) sous forme d'une String.

## Scoped

Nous avons décidé de `scoped` uniquement les informations concernants la jointure permettant à un utilisation de maintenir une librairie personnel de média.
Le `CRUD` se trouve sur cette table de jointure qui permet lecture, creation d'un `watched` ou d'un `toWatch`, mise à jour d'un `toWatch` à `watched` et la suppression d'un `watched`.
Le passage d'un `toWatch` à `watched` se fait par le renseignement des champs `watched` et `rating`.

[Retour](../README.md)
