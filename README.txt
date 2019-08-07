HW4: How to Run

Controls:
left: left
right: right
space: jump
A: pause
S: play
D: start recording
F: end recording
1: 0.5x speed
2: 1.0x speed
3: 2.0x speed

First, ensure that the core.jar is in the build path of the project.

Next, run src/server/GameServer.java as a Java Application.
The window will now display the game world without any characters.
When clients connect, their box is shown on the screen.
The GameServer accepts no user input.

Next, run src/client/ClientWorld.java as a Java Application.
Move the character around on the screen with left, right, and space.
If another client is to be created, simply repeat this step.

In order to demonstrate scripted game object behavior, go to the src/scripting/move_platform.js file.
To change the range of motion for the moving platform, change the upper_bound and lower_bound values.
This will change the upper and lower limit of movement for the moving platforms.

In order to demonstrate scripted event handling, go to the src/scripting/spawn_handler.js file.
By default, spawn_x and spawn_y are given by a random spawn point.
To determine a single, constant spawn point, change the spawn_x and spawn_y values.
To always spawn in the top left corner, uncomment lines 7 and 8.
This will change the event handling for respawns.