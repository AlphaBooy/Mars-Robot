# Projet objectif Mars - Equipe élan
## Première partie : L'exploration de Mars
### Contraintes
- Le robot commence toujours sur la base sur laquelle il est posé. La base est indiquée par un caractère @ sur le relevé de la zone.
- Il est possible de faire pointer le robot vers une direction (Nord, Est, Sud, Ouest). Exécuter une telle instruction prend temps rotation secondes, et coûte cout rotation points de batterie.
-  Attention, votre robot de peut transporter que charge maximale. Une fois cette valeur dépassée, il s’arrête ! Vous pouvez toujours choisir de revenir à la base pour décharger les objets de valeur que vous avez accumulés.
-  Vous pouvez faire avancer votre robot un certain nombre d’unités, et le temps mis pour cela d´epend de la dureté des minéraux et objets sur votre chemin, ainsi que de l’efficacité de votre laser de minage. Déplacer votre robot d’une unité coûte cout deplacement point de batterie
-  Installer un équipement depuis la base où le robot a été posé prend temps installation secondes.
- Le robot commence avec un laser d’une efficacité de laser defaut et batterie defaut. Se d´eplacer et se tourner consomme une petite quantité d’énergie. Creuser en consome bien plus, et dépend de la dureté des matériaux et de l’efficacité du laser de minage. Plus vous creusez, plus le laser s’use, en fonction du temps passé à creuser et des minéraux rencontrés
-  Si votre robot a une cargaison intéressante, il vous est possible de l’échanger en repartant à la base, pour acheter une nouvelle batterie ou un nouveau laser. Il est ainsi possible d’acheter du matériel d’une efficacité supérieure à 100%. Une batterie commence toujours par batterie , et un laser par laser .
- Le temps de minage (en secondes) suit la formule (durete×100)/efficacite où durete est la valeur de la case à miner et efficacite l’efficacité du laser. Le laser s’use et perd emoussage laser point pour chaque seconde passée à miner, mais ne peut descendre en dessous de limite emoussage.

### Fonctionnalités
Le but du projet est de réaliser un simulateur du parcours du robot qui comprenne :
- De charger une configuration de robot
- De charger trois fichiers d'environement
- De lancer la mission (en affichant les résultats en temps réels)
- De sauvegarder les résultats obtenus dans des fichiers. Le fichier obtenu comprendra une liste des instructions exécutées par le robot, ainsi que le score final obtenu.

## Deuxième partie : Les combats !
### L'environement
L'environement est composé de :
- Une grille de n x m pouvant contenir les éléments suivants :
    - Un mur '#'
    - Rien ' '
    - Un robot '@'
    - Une batterie de rechange '%'
- Une pile publique partagée entre tous les robots
- x robots répartis sur la grille

### Les robots
Chaque robot a une énergie MAX (=10). Un robot ayant une énergie nulle est détruit.
Chaque robot possède deux registres :
- D : stockage de données
- C : exécution du code
Ces deux registres sont initialisés à 0.

Le robot contient un programme qui peut utiliser plusieurs instructions :
- P : pousse la valeur ASCII du caractère en mémoire se trouvant à la position d (où d est la valeur du registre D) sur la pile publique (tous les robots partagent la même pile)
- G : récupère 2 valeurs de la pile, réalise un modulo de leurs valeurs par 2, effectue un NAND sur ces deux valeurs puis pousse le résultat.
- D : définit le registre D (et non la valeur en mémoire résidant en D) à la valeur dépilée de la pile publique.
- M : définit le caractère en mémoire situé à la position d sur la valeur ASCII de la valeur dépilée.
- K : met le registre C sur d et D sur 0
- Y : suivant le code ASCII (noté V) du caractère en mémoire à la position d, effectue une des actions suivantes :
    - V = 32 se déplace à droite sur la grille sauf si bloqué par un mur ;
    - V = 33 se déplace en haut sur la grille sauf si bloqué par un mur ;
    - V = 34 se déplace à gauche sur la grille à moins d’être bloqué par un mur ;
    - V = 35 se déplace en bas sur la grille à moins d’être bloqué par un mur ;
    - V = 36 inflige un point d’énergie à tous les robots situés à proximité (horizontalement, verticalement, en diagonale ou sur sa position), y compris le robot ayant lancé cette commande ;
    - V = 37 rend 1 point d’énergie pour chaque robot à proximité (horizontalement, verticalement, en diagonale ou sur sa position), mais leur donne 2 points d’énergie chacun ;
    - V = 38 détruit les murs à proximité, coûtant 1 point d’énergie pour chaque mur.
- I : d´epile 2 valeurs, x et y, de la pile, puis empile le caract`ere qui se trouve sur la grille aux coordonnées x et y.
- Toute autre commande fait exploser le robot.

A chaque tour, l’instruction dans le programme du robot à la position C est exécutée, puis le registre C est incrémenté de 1.

### Fonctionnalités

La plateforme à réaliser doit pouvoir :
- Paramétrer l’environnement où évolue les robots. On ne s’intéressera qu’à des environnements définis (dans le dossier ”cartes”, par exemple, sous forme de fichiers textes). La plateforme devra donc dans un premier temps de choisir l’environnement à charger parmi ceux présents dans le dossier.
- Charger les robots du dossier bots. Un robot est composé d’un nom et d’une chaîne texte représentant son programme, séparés par un espace.
- Lancer la simulation : il faut réaliser un module de visualisation des résultats de la simulation en cours ;
- Sauvegarder la liste des robots qui ont surv´ecu.
