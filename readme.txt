For this project, I decided to implement Space Invaders, developing on the Mushroom 
of Doom game that was initially given to us. For my game, I am using the keyboard 
control and checking for the left and right arrow buttons as well as the
space bar button (to shoot the enemy).  I stored my game objects in a ListArray
from the Java Collections Library, creating a new array for each row of the 
enemy ship as well as an array for bullets being fired.  In the game display, there is
a reset button for users to press after winning/losing the game.  I am also
keeping track of the number of lives and the score with the bottom JPanel.

Special features I implemented are creating 'automatic' levels, I say 'automatic'
in the sense that it the calculations for the spacing and the speed of my objects
are dependent on my game level as well.  Thus, it is actually possible to level up
as high as mathematically possible, although I cut this off at level 10, because after 
that the speed that the enemy ships travel down start getting a little too fast for 
the user to be able to shoot them all in time. 

I also implemented a UFO ship that shoots bullets at the player ship but cannot be
destroyed, however, when shot the player will gain 100 points.

At one point, I adapted a third-party code segment from Stack Overflow (URL below), this
was to create the pop-up image window at the beginning of the game.

All in all I truly enjoyed making this game!!