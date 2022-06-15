Checkers
=====================================================
## About

Checkers is an old board game, which is traditionally played on the dark squares of a chess board. 
It's made for two players who oppose each other across the board. 
Each player has 12 pieces in the beginning and is trying to reach the other end of the board to gain more power.
The objective of the game is to capture or block all of your opponents' pieces.

---

## Some Hints

The game was originally implemented in Scala 2 in the lecture Software Engineering and transferred to Scala 3 as part of the lecture Software Architectures.

Because the original DesktopUI, written in ScalaFX is not compatible to Scala 3 the WebUI developed in the lecture Web Technologien
was integrated in this project as a replacement.

The game is available for Download via a bunch of Docker Containers. For Details and available Tags see [Github Container Registry](https://github.com/ro99bre?tab=packages&repo_name=Checkers-SA).

After each successful Build a Demo System of the Game is automatically deployed.
It's currently accessible under http://192.52.45.214/gameBoard.html