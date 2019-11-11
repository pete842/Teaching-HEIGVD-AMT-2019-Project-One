[Retour](../README.md)

# Perfomances

Les performances de charge de l'application, ou sa capacité à résister à un grand nombre de requêtes distinctes, ont été évaluées par nos soins à l'aide de l'application JMeter.

## Ronde de test

Une ronde de test correspond à une itération de test pour un thread.

Cela correspond, en d'autres termes, à une suite d'actions qu'un utilisateur pourrait être amené à faire lors d'une session.

Une ronde sera ensuite effectuée 50 fois afin d'encenser davantage la moyenne produite.

### Choix

Pour ce compte rendu, nous avons choisi d'utiliser une ronde de tests simpliste,
se contentant de se connecter à l'application (login), d'accéder (dans un ordre choisi aléatoirement à chaque ronde) à trois ressources.

À noter que le `login` n'est effectué qu'une seule fois, à la première itération, et que le `logout` n'est jamais effectué.
La raison à cela est que nous avons également effectué des tests disposant de caractéristiques plus réaliste à ce sujet, mais les résultats relatifs à ces requêtes étant absolument insignifiants, nous avons pris le parti de simplifier la ronde.

## 50 threads

Le premier test de charge que nous vous présentons représentes 50 utilisateurs fictifs, effectuant chancun 50 rondes décrites ci-dessus, tout en attandant un délai aléatoire de l'ordre de la seconde avant de passer à la ronde suivante.

Le graphique ci-dessous 

![Response times over time (100pts)](assets/graphs/big_session_1sec/reps_times_over_time_100pts.png)

## 20 threads
![Response times over time (100pts)](assets/graphs/big_session_1sec_20thr/reps_times_over_time_100pts.png)



[Retour](../README.md)
