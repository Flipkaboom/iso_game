I have made an Isometric platformer/puzzle game. While the rendering of the 3d world is made up of fully 2d sprites
the game still implements collision mechanics fully in 3d. It has support for very easily adding more blocks and items
by extending existing classes. There is also a map editor which can place blocks in the 3d space so you don't have to
manually type everything into a file, it also provides a little bit of help for the things it can't do (like placing
blocks that need special constructor). I just wish I had more time to implement all the fun and easy stuff instead
of debugging clipping into a corner for 10 hours

How to run:
Run "gradlew run" in a terminal or press run on the IsoGame object in IsoGame.scala
run with --args"mapName" to run a specific map

Controls:
WASD: movement
Spacebar: Jump
E: interact with something you're standing on top of

The screen automatically resizes the playing area to whatever fits the window so resize it any way you want :)


How to run map editor:
Run "gradlew levelEditor" in you console or run the object in IsoEditor.scala
A new empty map will automatically be made.
A name and size for a new map can be specified with '--args="mapName width otherWidth Height"'
To open an existing map use '--args="mapname"'

Controls:
WASD: controls for going sideways(it takes some getting used to)
E: go up
Shift: Go down
Space: place block
I: pick block at current position as block to paint with
Left and right arrows: cycle left and right through all the blocktype, they are printed in the console while you do it
P: Print line of code to console that can be used to add this object manually (e.g. teleporters are too complicated for the level editor)
F: toggle fill mode. When enable the first spacebar press will set a corner and the second will fill all points between with selected block type
ENTER: save to file (in levels folder, saves is for keeping track of rooms while playing)
Backspace: Load from said file
