# Teaching-HEIGVD-AMT-2019-Project-One

1er projet d'AMT ayant pour but d'acquérir de l'expérience sur Java EE Stock dans le thème de notre choix.

## Auteurs

- Pierre Kohler <pierre.kohler@heig-vd.ch>
- Jonathan Zaehringer <jonathan.zaehringer@heig-vd.ch>

## Comment ...

### Déployer

Afin de déployer ce projet, il suffit de créer les deux conténaires nécessaires au bon fonctionnement de ce dernier.
Il s'agit du conteneur de l'application (nommé `app`) ainsi que celui de la base de donnée MariaDB (nommé `db`).

Ces deux conteneurs ainsi que leur contexte d'exécution est orchestré par un fichier `Docker Compose`.
Il se charge de créer les conteneurs à partir de leur image d'origine, de les connecter entre eux et d'exposer les ports qu'il convient.

Afin de l'exécuter, il faut se trouver à la racine du projet, et effectuer la commande `docker-compose up`.
Pour les stopper, il conviendra d'effectuer le raccourci clavier `CTRL` + `c`.

Si l'on souhaite détruire les conteneurs, la commande `docker-compose down` permet d'arrêter les conteneurs si nécessaire ainsi que de les supprimer.

Ensuite, afin de créer une nouvelle fois ces conteneurs à l'aide de la commande `docker-compose up` susmentionnée, il conviendra de supprimer le volume correspondant aux données de la base de donnée (à savoir le dossier `/db/data` du projet).

NB1: L'application est exposée sur les ports `8080` (site web), `9990` (wildfly) et `3306` (db).
Si vous avez une quelconque application utilisant ces ports, il sera nécessaire de les libérer afin de pouvoir construire et démarrer les conteneurs

NB2: Sur certaine machine, le premier `docker-compose up` ne permet pas d'obtenir un état fonctionnel du système.
Cela est dû à l'incapacité de Wildfly à se connecter à la base de donnée en amont de sa première exécution.
Il suffit donc d'arrêter le `Compose` (avec `CTRL` + `c`), et de le relancer (`docker-compose up`).

#### Accès et utilisation

- Site web : http://localhost:8080/projectone/
- Interface d'administration de Wildfly : http://localhost:9990/

### Tester

Afin de mettre en place le contexte d'exécution des testes, vous aurez besoin d'effectuer la même mise en place que pour le déploiement ci-dessus.
En effet, pour plus de simplicité dans le cadre de ce projet, un seul `Compose` permet à la fois de déployer l'application et de la tester.

Une fois l'application déployée et en fonctionnement, veuillez vous rendre sur l'interface d'administration de Wildfly (http://localhost:9990).
Les identifiants demandés sont par défault `admin:admin`.
Rendez vous ensuite sur la page *Deployments* (http://localhost:9990/console/index.html#deployments) afin de supprimer (*Remove*) le déploiement de paquet `projectone.war`.

Une fois cela fait, il sera possible d'effectuer tous les tests décrits [ici](docs/testes.md).

De plus, vous disposez du compte de test `pete842`, associé au mot de passe `totem`, qui permettra de bénéficier d'un compte disposant d'un contenu fictif.
Ceci vous permettra notamment d'observer la pagination.

## Documentation

- [Contexte](docs/context.md)
- [Implémentation](docs/implement.md)
- [Testes](docs/testes.md)
- [Performances](docs/performance.md)
- [Bugs connus](docs/bugs.md)

