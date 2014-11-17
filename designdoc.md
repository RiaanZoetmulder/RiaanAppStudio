**Riaan Zoetmulder**

**Classes**
> Activity

> Bitmapfactory

> Gameplay

**Methods in the first screen**

![Alt text](https://github.com/RiaanZoetmulder/RiaanAppStudio/blob/master/screenshot/openingsscherm.png "First Screen")

*onCreate()*
> class: activity

> whenever the app is first launched this method is used. It refers to listview to create the list of playable pictures.

*listview()* 
> class: Listview

> creates the list of playable scenarios. using ArrayAdapter  to convert the data.

*onCreateOptionsMenu()*
> class Activity

> creates options for 3 difficulty levels, easy medium and hard. Easy medium and hard are going to be stored as a list with 
different values to make sure that in the future if a different difficulty level has to be added, it is easier to do so.

*onOptionsItemSelected()*
> class Activity

> whenever an option is selected this class fires and returns the id of that item. it is used to see which option was selected or which picture was selected. In the first case it stores this value, in the latter case it creates an intent and starts the game.

**Methods in the game screen**

![Alt text](https://github.com/RiaanZoetmulder/RiaanAppStudio/blob/master/screenshot/speelscherm.png "Game Screen")

*onPause()*
> class: activity

> whenever the app is exited while the game is being played this function saves all the data of the current game.

*onResume()*
> class: activity

> retrieve data and continue game. returns id's in their last location

*resizeBitmap()*
> class: Bitmapfactory

> resizes bitmaps to fit the screen. returns images, will use Bitmap class.

*gridView()* 
> class: Gridview

> creates a grid with the bitmaps.

*scrambleTiles()*
> class: Gameplay

> scrambles the grid with the bitmaps. 

*checkTiles()* 
> class: Gameplay

> checks whether a given move is legal or not. Boolean.

*gameVictory()*
> class: Gameplay

> checks whether the game is won, returns true if all tiles are in the proper order. Boolean, if true links to victory screen.

![Alt text](https://github.com/RiaanZoetmulder/RiaanAppStudio/blob/master/screenshot/winscherm.png "Victory screen")

*swapTiles()* 
> class: Gameplay

> swaps tiles if checktiles returns true. returns new grid.

*cropBitmap()*
> class: Bitmapfactory

> crop bitmaps and give them an ID. using this API:  
> Bitmap.createBitmap(Bitmap bitmap, intx, int y, int width, int height); will be used

**API's Used**

> Bitmapfactory from Android.Graphics

> Activity form Android.App

> Listview, gridView from Android.Widget
