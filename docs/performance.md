[Retour](../README.md)

# Perfomances

Les performances de charge de l'application, ou sa capacité à résister à un grand nombre de requêtes distinctes, ont été évaluées par nos soins à l'aide de l'application JMeter.

## Ronde de test

Une ronde de test correspond à une itération de test pour un thread.

Cela correspond, en d'autres termes, à une suite d'actions qu'un utilisateur pourrait être amené à faire lors d'une session.

Une ronde sera ensuite effectuée 50 fois afin d'encenser d'avantage la moyenne produite.

### Choix

Pour ce compte rendu, nous avons choisi d'utiliser une ronde de tests simpliste,
se contentant de se connecter à l'application (login), d'accéder (dans un ordre choisi aléatoirement à chaque ronde) à trois ressources.

À noter que le `login` n'est effectué qu'une seule fois, à la première itération, et que le `logout` n'est jamais effectué.
La raison à cela est que nous avons également effectué des tests disposant de caractéristiques plus réaliste à ce sujet, mais les résultats relatifs à ces requêtes étant absolument insignifiants, nous avons pris le parti de simplifier la ronde.

## 50 threads

Le premier test de charge que nous vous présentons représentes 50 utilisateurs fictifs, effectuant chancun 50 rondes décrites ci-dessus, tout en attandant un délai aléatoire de l'ordre de la seconde avant de passer à la ronde suivante.

Le graphique ci-dessous 

![Response times over time (100pts)](assets/graphs/big_session_1sec/resp_times_over_time_100pts.png)

Ces graphes montre que le serveur d'application n'arrive clairement pas à suivre la demande avec des temps de réponse très élevé atteigant la dizaine de seconde parfois.
Ces temps ne sont pas étonnant car l'infrastructure tourne en docker sur un laptop, il ne faut pas prendre à la lettre ces testes.
Nous pouvons tous de même voir que la page `get movies` est clairement deux fois plus longs à calculer que les pages `get watched` et `get toWatch`.
Cela ne nous semble peu étonnant car nous travaillons avec un ensemble de données bien plus grands dans ce cas.
Ensuite, nous pouvons constaté que les pages `get watched` et `get toWatch` sont confondu ce qui est logique car ces deux pages sont très similaires.

Sur le graphe suivant nous pouvons plus facilement voir la répartition des temps de calcule des différentes pages :

![Response times over time (100pts)](assets/graphs/big_session_1sec/resp_times_distr.png)

## 20 threads

![Response times over time (100pts)](assets/graphs/big_session_1sec_20thr/resp_times_over_time_100pts.png)



[Retour](../README.md)
