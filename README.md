# MazeGame
A Java application that generates mazes and lets the user solve them. 

![Screenshot](http://i.imgur.com/h1DTGD1.png)![Screenshot2](http://i.imgur.com/iVd7rEQ.png)
#####The program currently supports:
* Player paths - See your path through the maze, as well as where you had to backtrack so you don't make the same mistake twice
* Very large maze size - Mazes can be very large, up to 65535 by 65535 cells, though they won't easily fit on your screen
* Customizable start and finish points
* Multiple players - Up to two players are currently supported (with WASD and Arrow keys controlling Player 1 and Player 2 respectively), but a very large amount could be supported once I implement better input handling
* Multiple algorithms - Currently a depth-first search, Prim's algorithm, and Wilson's algorithm are implemented
* Limited view mode - Limits view to only a small circle around your player. Makes solving a maze much more difficult. The size of the circle can be adjusted.

#####Coming soon:
* More algorithms - I plan on implementing all the algorithms I can find
* More than 2 players
* Scrolling for mazes that are too big for the screen (though any maze that can't fit on the screen would take forever to complete)

#####Possible future features:
* Online play - host or join a server and play with your friends or compete with people around the world
* 3-dimensional mazes - take stairs up or down to different floors of the maze
* Better graphics - possibly 3D with an actual art aesthetic
