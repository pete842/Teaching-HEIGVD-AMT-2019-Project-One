[Retour](../README.md)


# Implementation

## Tier de présentation : Servlet

Dans l'ensemble, la structure de `servlet` est assez classique, mais nous avons pris un choix au niveau de l'utilisation du `CRUD`.
Nous avons décider d'essayer de resortir une sorte "api" permettant le travail de la `MediaUser` dans un servlet spécifique `MediaUserServlet`.
Celle-ci se compose de `doPost, doPut & doDelete` pour faire les différentes opérations sur cette `entité`.
Le principe est que le servlet `forward` ensuite l'utilisation sur le servlet de départ pour ramener à la page originelle.

Malheureusement, cette manière de faire n'est pas parfaite car nous ne voulions par faire de `redirect` avec les informations d'erreurs ou de reussite dans l'url.
Cela implique donc que l'url ne correspond pas à la réalité de la page mais ce permet d'éviter une `URL` contenant un message d'erreur pouvant être forgé.

Pour finir, l'ensemble des `servlet` hérite de `BaseHttpServlet` qui est une super classe contenant plusieurs fonctions simplifiant les actions comme la vérification des paramètres ou la redirection en cas d'erreur.

## Filter

La partie des `Filters` est plus classique avec un `AuthentificationFilter` s'occupant de garantir qu'un utilisateur est bien authentifié pour accéder au site.
Nous avons toutes fois un filtre d'`encoding` permettant de forcer l'encodage des pages en `UTF-8`.

## Tier buisness : DAO

Dans l'ensemble, nous avons des DAO simples en accord avec nos entités.
Nous avons cependant des informations supplémentaires sur certaines entités pour fournir un ensemble d'information plus fourni pour l'affichage de l'utilisateur.
C'est le cas avec les `Media` qui contiennent l'information de état dans la table de jointure pour l'utilisateur authentifié permattant un affichage de multiple raccourci pour ajouter/supprimer/modifier un `MediaUser`.
Ces champs supplémentaires dans l'entité sont uniquement des champs calculés qui n'intervienne jamais dans leur utilisation dans la base de données ou dans les `servlets`.

## Template

### Technologies

Le template de l'interface web de ce projet a été réalisé en HTML / CSS3 / Javascript, sur la base du template Bootstrap [Now UI Kit](https://demos.creative-tim.com/now-ui-kit/index.html).

Le rendu dynamique du contenant est effectuée à l'aide de la technologie `JavaServer Pages`, ou `JSP`.

### Structure

La structure des `JavaServer Pages` commence dans le dossier `/src/main/webapp` du projet.

En voici un aperçu global :

- `index.jsp` : il s'agit du point d'entrée par défault de l'application web (ici localhost:8080/projectone)
- `/WEB-INF/` : `JSP` servit par le serveur
    - `components/` : divers composants utilisée par les pages
        - `footer.jsp`, `navbar.jsp`, ...
    - `contents/` : contenu / corps des pages de l'application
        - `home.jsp`, `login.jsp`, `movie.jsp`...
    - `pages/` : construction d'une page (appel d'un template et d'un contenu, choix d'un titre)
        - `home.jsp`, `login.jsp`, `movie.jsp`...
    - `templates/` : différentes structures de page pour l'application
        - `default.jsp` : template par défault (page de login, par exemple)
        - `intra.jsp` : affichage du contenu (pages utilisateurs)
- `/assets/` : assets de l'application (images, css, js, ...)

### Fonctionnalités

De la racine du projet, il sera possible de créer un compte utilisateur ou de se connecter à un compte existant.

Une fois connecté, on arrive sur la page principale de l'interface.
On pourra y voir et gérer deux listes.

Une première liste de films, nommée *To Watch*, correspond à une liste de films que l'utilisateur *souhaite* voir, dans un futur le concernant.

Une seconde liste de films, nommée *Watched*, correspond à une liste de films que l'utilisateur *a vu* par le passé, et pour lesquels il a attribué une note et une date de visionnage.

On pourra ensuite, en plus de gérer ces listes et de passer d'une à l'autre, accéder à la liste de tous les films présents dans la base.
L'accès se fera par le menu du coin supérieur gauche ou par l'ajout d'un élément au sein des deux listes précitées.

Finalement, l'utilisateur mettre fin à sa session à l'aide du bonton `Logout` du coin supérieur droit de l'application.


[Retour](../README.md)
