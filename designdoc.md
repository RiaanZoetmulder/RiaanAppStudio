**Riaan Zoetmulder**


**Methods in the first screen**

![Alt text](https://github.com/RiaanZoetmulder/RiaanAppStudio/blob/master/screenshot/openingsscherm.png "First Screen")

*onCreate()*
> whenever the app is first launched this method is used. It refers to listview to create the list of playable pictures.

*listview()* 
> creates the list of playable scenarios refers to:

*onCreateOptionsMenu()*
> creates options for 3 difficulty levels, easy medium and hard. Easy medium and hard are going to be stored as a list with 
different values to make sure that in the future if a different difficulty level has to be added, it is easier to do so.

*onOptionsItemSelected() *
> whenever an option is selected this class fires and returns the id of that item. 

**Methods in the game screen**

![Alt text](https://github.com/RiaanZoetmulder/RiaanAppStudio/blob/master/screenshot/speelscherm.png "Game Screen")

*onPause()*
> whenever the app is exited while the game is being played this function saves all the data of the current game.

*onResume()*
> retrieve data and continue game. returns id's in their last location

*resizeBitmap()*
> resizes bitmaps to fit the screen. returns images

*gridView()* 
> creates a grid with the bitmaps.

*scrambleTiles()*
> scrambles the grid with the bitmaps. 

*checkTiles()* 
> checks whether a given move is legal or not. Boolean.

*gameVictory()*
> checks whether the game is won, returns true if all tiles are in the proper order. Boolean, if true links to victory screen.

![Alt text](https://github.com/RiaanZoetmulder/RiaanAppStudio/blob/master/screenshot/winscherm.png "Victory screen")

*swapTiles()* 
> swaps tiles if checktiles returns true. returns new grid.

*cropBitmap()*
> crop bitmaps and give them an ID.
