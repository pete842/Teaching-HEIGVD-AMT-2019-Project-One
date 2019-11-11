[Retour](../README.md)


# Implementation

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
