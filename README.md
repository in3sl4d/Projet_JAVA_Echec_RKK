# Projet_JAVA_Echec_RKK
Ines LADJENEF 208, Elodie DAI 207, Yannis LEGROS 207, Tarik MOHAMED 208


Comme couche de la clean architecture nous avons : 
- entities qui contient : le package move, le package piece ainsi que la classe Board, IllegalMoveException et IPiece
- dans usercase : la classe Game, IBot et IGameLoader
- dans interFaceAdapters : le package forsyEdwards, les classes Bot, Evaluate, MoveSearch, PieceSquareTables et UCI 


Tous les tests passent (30/30)


comment connecte le moteur a cuteChess
  dans un premier temps il faut generer le jar du projet, avec intelijie cela n'est pas complique dans projet structure, Artifacts ajouter un jar puis build project
  dans un deuxieme temps il faut mettre le jar dans un dossier et ecrire un .bat avec sa cette ligne
  @echo off
  java -jar Projet_JAVA_Echec_RKK.jar
  puis dans cuteChess il faut enregistre un nouveau moteur en lui donnant le .bat puis on peut l'utiliser
