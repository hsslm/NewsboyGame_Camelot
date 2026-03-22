# NewsboyGame - Camelot à vélo

Jeu en interface graphique développé en Java avec JavaFX. Vous incarnez un camelot à vélo qui doit livrer des journaux dans les boîtes aux lettres des maisons abonnées. Le jeu intègre un moteur physique complet avec gravité, vélocité, accélération et un système de champ électrique basé sur la loi de Coulomb qui affecte la trajectoire des journaux à partir du deuxième niveau.

---

## Gameplay

- Contrôlez le camelot au clavier à travers des niveaux générés aléatoirement
- Lancez des journaux dans les boîtes aux lettres des maisons abonnées pour gagner de l'argent
- Évitez de casser les fenêtres des maisons abonnées (pénalité de 2$)
- Cassez les fenêtres des maisons non-abonnées pour un bonus (récompense de 2$)
- À partir du niveau 2, des particules chargées électriquement apparaissent et dévient la trajectoire des journaux selon la loi de Coulomb

---

## Contrôles

- Flèche droite - accélérer
- Flèche gauche - ralentir
- Espace / Flèche haut - sauter
- Z - lancer un journal vers le haut
- X - lancer un journal vers l'avant
- Shift + Z / X - lancer avec plus de force
- Escape - quitter le jeu

---

## Physique et champ électrique

Le jeu implémente un moteur physique complet :

- Gravité constante appliquée au camelot et aux journaux (1500 px/s²)
- Vélocité et accélération calculées à chaque frame pour un mouvement fluide
- Masse aléatoire des journaux à chaque niveau, qui influence leur trajectoire
- Vitesse maximale du camelot limitée (200 à 600 px/s) avec retour progressif à la vitesse de base

À partir du niveau 2, des particules chargées électriquement sont placées dans le niveau :

- Chaque particule génère un champ électrique calculé selon la loi de Coulomb (E = kq / r²)
- Le champ électrique total est la somme vectorielle des champs générés par chaque particule
- Ce champ applique une force sur les journaux (qui ont eux-mêmes une charge électrique), déviant leur trajectoire en temps réel
- Le nombre de particules augmente avec les niveaux : N = min((niveau - 1) * 30, 400)
- La vitesse des journaux est limitée à 1500 px/s pour éviter les instabilités physiques

---

## Fonctionnalités

- Moteur physique complet - gravité, vélocité, accélération, quantité de mouvement
- Simulation du champ électrique de Coulomb en temps réel
- Génération aléatoire des niveaux - maisons, adresses, abonnés, positions des particules
- Plusieurs niveaux avec difficulté croissante
- Mode débogage (touche D) - affichage des rectangles de collision
- Visualisation du champ électrique avec des flèches (touche F)
- Touches de test : Q (+10 journaux), K (fin de partie), L (niveau suivant), I (particules de test)

---

## Technologies

- Langage : Java
- Interface graphique : JavaFX
- Outil de build : Gradle
- IDE : IntelliJ IDEA

---

## Lancer le projet

Via le menu Gradle dans IntelliJ :

- Tasks > Application > run

---

## Auteure

Selma Hajjami - Projet réalisé dans le cadre du cours SIM - Développement d'applications dans un environnement graphique - Collège de Bois-de-Boulogne, Automne 2025.

---
---

# NewsboyGame - Camelot à vélo

A JavaFX graphical game developed in Java. You play as a newspaper delivery boy on a bike throwing newspapers into mailboxes across randomly generated levels. The game features a full physics engine with gravity, velocity, acceleration, and a real-time electric field simulation based on Coulomb's law that deflects newspaper trajectories from level 2 onward.

---

## Gameplay

- Control the newsboy with your keyboard across randomly generated levels
- Throw newspapers into subscribed houses mailboxes to earn money
- Avoid breaking windows of subscribed houses (2$ penalty)
- Break windows of non-subscribed houses for a bonus (2$ reward)
- From level 2 onward, electrically charged particles appear and deflect newspaper trajectories according to Coulomb's law

---

## Controls

- Right arrow - speed up
- Left arrow - slow down
- Space / Up arrow - jump
- Z - throw newspaper upward
- X - throw newspaper forward
- Shift + Z / X - throw with more force
- Escape - quit game

---

## Physics and Electric Field

The game implements a full physics engine :

- Constant gravity applied to the newsboy and all newspapers (1500 px/s²)
- Velocity and acceleration computed every frame for smooth movement
- Random newspaper mass each level, influencing trajectory
- Newsboy speed clamped between 200 and 600 px/s with gradual return to base speed

From level 2 onward, electrically charged particles are placed in the level :

- Each particle generates an electric field computed using Coulomb's law (E = kq / r²)
- The total electric field is the vector sum of all individual particle fields
- This field applies a force on newspapers (which carry their own electric charge), deflecting their trajectory in real time
- Particle count increases with level : N = min((level - 1) * 30, 400)
- Newspaper speed is capped at 1500 px/s to prevent physics instability

---

## Features

- Full physics engine - gravity, velocity, acceleration, momentum
- Real-time Coulomb electric field simulation
- Randomly generated levels - houses, addresses, subscribers, particle positions
- Multiple levels with increasing difficulty
- Debug mode (D key) - collision rectangle overlay
- Electric field visualization with arrows (F key)
- Debug keys : Q (+10 newspapers), K (end game), L (next level), I (test particles)

---

## Technologies

- Language : Java
- GUI : JavaFX
- Build tool : Gradle
- IDE : IntelliJ IDEA

---

## Running the project

Via the Gradle menu in IntelliJ :

- Tasks > Application > run

---

## Author

Selma Hajjami - Project completed as part of the SIM course - Application Development in a Graphical Environment - Collège de Bois-de-Boulogne, Fall 2025.
