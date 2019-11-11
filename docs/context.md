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



[Retour](../README.md)
