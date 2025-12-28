# Projet_JAVA_Echec_RKK
Ines LADJENEF 208, Elodie DAI 207, Yannis LEGROS 207, Tarik MOHAMED 208


Comme couche de la clean architecture nous avons : 
- entities qui contient : le package move, le package piece ainsi que la classe Board, IllegalMoveException et IPiece
- dans usercase : la classe Game, IBot et IGameLoader
- dans interFaceAdapters : le package forsyEdwards, les classes Bot, Evaluate, MoveSearch, PieceSquareTables et UCI 


Tous les tests passent (30/30)


Comment connecter le moteur de jeu à cuteChess ?
  Dans un premier temps il faut generer le jar du projet, avec intelliJ. Pour cela, il faut aller dans File puis dans projet structure, Artifacts, ajouter un jar puis build project
  Ensuite il faut mettre le jar dans un dossier et ecrire dans un fichier .bat les lignes suivantes:
  
  @echo off
  java -jar Projet_JAVA_Echec_RKK.jar
  
  Enfin, dans cuteChess il faut enregistree un nouveau moteur en lui donnant le .bat afin de pouvoir l'utiliser.
