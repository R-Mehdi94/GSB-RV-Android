## GSB-RV <img src="https://user-images.githubusercontent.com/102319778/230720966-cb56e373-2646-4edc-bde0-08bfc8e55956.png" alt="Logo GSB-RV-Android" style="float: right; width: 100px;">
   ![Python version](https://img.shields.io/badge/Python-3.10.6-blue) ![Java version](https://img.shields.io/badge/Java-11.0.13-navy) ![XML version](https://img.shields.io/badge/XML-1.0-orange) ![Flask version](https://img.shields.io/badge/Flask-2.2.3-pink) ![MariaDB version](https://img.shields.io/badge/MariaDB-15.1-red) ![SDK version](https://img.shields.io/badge/SDK-32-Gold) ![Gradle version](https://img.shields.io/badge/Gradle-7.4-yellow) 
Application de saisie des rapports de visite. 

## Contexte

- [GSB - Fiche descriptive](https://github.com/R-Mehdi94/GSB-RV/blob/master/doc/01-GSB-AppliRV-FicheDescriptive.pdf)
- [GSB - Cas d'utilisation](https://github.com/R-Mehdi94/GSB-RV/blob/master/doc/02-GSB-AppliRV-Visiteur-UC.pdf)
- [GSB - Modèle Entité-Association](https://github.com/R-Mehdi94/GSB-RV/blob/master/doc/03-GSB-AppliRV-MEA.pdf)
- [GSB - Diagramme de navigation](https://github.com/R-Mehdi94/GSB-RV/blob/master/doc/04-GSB-AppliRV-Navigation.pdf)
- [GSB - Documentation API / Arborescence](https://github.com/R-Mehdi94/GSB-RV/blob/master/doc/05-GSB-AppliRV-Documentation-API.pdf)

## Installation

Avant de pouvoir utiliser l'application, vous devez installer les composants suivants :

1. ### SGBDR - MariaDB

  ``` bash 
  sudo apt install mariadb-server
  ```

2. ### Python3

  ``` bash 
  sudo apt install python3
  ```

3. ### pip3

  ``` bash 
  sudo apt install python3-pip
  ```

4. ### Flask

  ``` bash 
  pip install flask
  ```

5. ### mysql-connector

  ``` bash 
  pip install mysql-connector
  ```

Assurez-vous que tous les composants sont installés avant de poursuivre.

## Utilisation

Pour utiliser l'application, veuillez suivre les étapes suivantes :

1. Clonez le dépôt GitHub sur votre machine locale.
2. Ouvrez le dossier du projet dans un terminal.
3. Exécutez la commande suivante pour lancer l'application Flask : 
``` bash 
python3 appRV-Visiteur.py
```
