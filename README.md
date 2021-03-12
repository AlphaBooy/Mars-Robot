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
TODO
