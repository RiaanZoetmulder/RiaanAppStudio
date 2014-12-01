package nl.mprog.projects.npuzzle6072909;

import java.util.ArrayList;
import java.util.Random;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity {
	ImageView image;
	
	// variables for the program to remember the correct difficulty
	int difficulty;
	int storeddifficulty = 3;
	
	// ID and Bitmaparray for pictures
	Bitmap[] pictureparts;
	int[] ID = new int[25];
	
	// global variables for pictures
	final int picture = 0;
	
	// helps the program remember the correct picture
	int savedpicture;
	
	
	// defining 2 integers for location tiles to swap
	int click, white_tile, temp;
	int i= 0;
	int m= 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameplay_layout);
		
		// retrieve bundle of information from previous screens
		Bundle extras = getIntent().getExtras();
		
		// ensure that the package containts something
		if (extras != null) {
			
			// retrieve information from the first screen stored in extras
		    final int difficulty = extras.getInt("difficulty");
		    final int picture = extras.getInt("pictureclicked");
		    
		    // Make sure program remembers which picture was selected
		    if (picture != 0){
		    	savedpicture = picture;
		    }
		    
		    if (difficulty != 3){
		    	storeddifficulty = difficulty;
		    }
		    
		    // Resize the Bitmap
		    try{ 
		    	// get the size of the screen
		    	DisplayMetrics screensize = new DisplayMetrics();
		    	getWindowManager().getDefaultDisplay().getMetrics(screensize);
		    	
		    	//System.out.println("picture loaded in instance: " + picture); ** TODO: erase later
		    	// load the correct bitmap, default darth vader
		    	Bitmap loadedpicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.darthvader);
		    
		    	// load other bitmaps, more can be added if necessary
		    	if(picture == 1){
		    		loadedpicture.recycle();
		    		loadedpicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.lukesky);
		    	}
		    	
		    	// get screenheight and width
		    	int screenheight =  screensize.heightPixels- 170;
		    	int screenwidth = screensize.widthPixels;
		    
		    	// resize the bitmap
		    	Bitmap ResizedPicture = Bitmap.createScaledBitmap(loadedpicture,screenwidth, screenheight, true);
		    	
		    	// remove old picture from memory
		    	loadedpicture.recycle();
		    	
		    	// crop bitmap into pieces depending on the difficulty
		    	final Bitmap[] pictureparts = cutbitmap(ResizedPicture, difficulty);
		    	
		    	// cropped picture sizes
		    	int croppedheight = pictureparts[1].getHeight();
		    	int croppedwidth = pictureparts[1].getHeight();
		    	
		    	// last bitmap must be white
		    	pictureparts[difficulty*difficulty - 1] = Bitmap.createBitmap(croppedwidth, croppedheight, Bitmap.Config.ARGB_8888);
		    
		    	// Create gridview 
		    	final GridView gridView = (GridView) findViewById(R.id.gridview);
		        gridView.setAdapter( new ImageAdapter(this, pictureparts, ID));
		        
		        // set imageadapter to gridview
		        final ImageAdapter theimageadapter = new ImageAdapter(this,pictureparts, ID); // correct this!
		        gridView.setAdapter(theimageadapter);
		        
		        // make number of columns of pictures adaptable
		        gridView.setNumColumns(difficulty);
		        System.out.println("upon creating the puzzle " + picture);
		       
		        // Check if picture is clicked
		        gridView.setOnItemClickListener(new OnItemClickListener(){
		        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        		
		        		// on first click, shuffle the deck
		        		if(m==0){
		        			Shuffle();
		        		}
		        		m++;
		        		
		        		// find current position white tile
		        		int white_tile_position = 0;
		        		int whitetag = difficulty * difficulty-1;
		        		
		        		// iterate through grid to find location of white tile
		        		while(whitetag !=  Integer.parseInt(gridView.getChildAt(white_tile_position).getTag().toString())){
		        			white_tile_position++;
		        		}
		        		
		        		// check if upward movement is legal
		        		if (position - difficulty == white_tile_position ){
		        			swap(position, white_tile_position);
		        		}
		        		// check if movement to the right is legal
		        		else if(((position + 1 == white_tile_position) && ((white_tile_position %  difficulty )!= 0))){
		        			swap(position, white_tile_position);
		        		}
		        		// check if movement to the left is legal
		        		else if(((position - 1 == white_tile_position) &&((white_tile_position % difficulty) != difficulty - 1))){
		        			swap(position, white_tile_position);
		        		}
		        		// check if downward movement is legal
		        		else if( position + difficulty == white_tile_position){
		        			swap(position, white_tile_position);
		        		}
		        		else{
		        			 Toast.makeText(GamePlay.this, "Invalid move", Toast.LENGTH_SHORT).show();
		        		}
		        		
		        		// implement change to view
		        		theimageadapter.notifyDataSetChanged();
		        		
		        		// check if victorycondition is met, if so click once more.
		        		int check = victoryCondition();
		        		if((check == difficulty * difficulty - 1) && ( m > 2 )){
		        			
		        			// creates new intent
		        			Intent victory = new Intent(GamePlay.this, YouWin.class);
		    		    	
		    		    	// activates intent, returns to first screen
		    		    	startActivity(victory);
		        		}       		
		            }
		        	private int victoryCondition() {
		        		// if correct_location equals difficulty squared -1 player wins
						int correct_location = 0;
						
						// see if all the tiles are in the right posiion
						for( int n = 0; n < (difficulty * difficulty) - 1; n++){
							if( n == Integer.parseInt(gridView.getChildAt(n).getTag().toString())) {
								correct_location++;
							}
						}
								
						return correct_location;
					}
		        	
		        	// implements the shuffle function
					private void Shuffle() {
		        			// create new random variable
		        	    	Random rand = new Random();
		        	    	
		        	    	// Fisher- Yates shuffle 
		        	    	// inspired by http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
		        	    	for( int k = (difficulty * difficulty)-1 ; k > 0; k--){
		        	    		int index = rand.nextInt(k+1);
		        	    		
		        	    		// calls the swap function
		        	    		swap(index, k);
		        	    	}
		        	}
		        	
					// implements the swap function
		        	private void swap(int click, int white_tile) {
		        		
		        		// swap bitmaps
						Bitmap temp = pictureparts[click];
	        			pictureparts[click] = pictureparts[white_tile];
	        			pictureparts[white_tile] = temp;
	        			
	        			// swap ID tags
	        			int tagtemp = ID[click];
	        			ID[click] = ID[white_tile];
	        			ID[white_tile] = tagtemp;
	        			
					}
		        });
		    
		    }
		    catch(OutOfMemoryError e){
		    	Toast.makeText(this, "There is a highly trained monkey working on the problem, don't worry", Toast.LENGTH_LONG).show();
		    	
		    }
		}	
	}

		public boolean onCreateOptionsMenu(Menu menu) {
	    	
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.gameplaymenu, menu);
	        android.view.MenuInflater inflater = getMenuInflater();
	        return true;
	    }

	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	    	String message = "";
	    	if (item.getItemId() == R.id.changedifficulty) {
	    	
	    		// create new dialog
	    		new AlertDialog.Builder(this)
	            .setTitle("Select Difficulty")
	            
	            // set onClickListener to check which item is selected
	            .setItems(R.array.myarray, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
							
							// create new intent
							Intent intent = new Intent(GamePlay.this, GamePlay.class);
							
							// put current remembered picture in intent
				        	intent.putExtra("pictureclicked", savedpicture);
				        	
				        	// put extra correct level of difficulty
				        	if(which == 0){
				        		intent.putExtra("difficulty",3);
				        	}
				        	else if(which ==1){
				        		intent.putExtra("difficulty",4);
				        	}
				        	else{
				        		intent.putExtra("difficulty",5);
				        	}
				        	
				        	// finish current activity
				        	finish();
				        	
				        	// start new activity
				        	startActivity(intent);
					}
	            }).show();
	    		
	    	}
	    	else if (item.getItemId() == R.id.shuffle) {
	    		
	    		// reset entire game
	    		// create new intent
	    		Intent intent = new Intent(GamePlay.this, GamePlay.class);
	    		
	    		// put current picture as picture
	    		intent.putExtra("pictureclicked", savedpicture);
	    		intent.putExtra("difficulty", storeddifficulty);
	    		
	    		// finish current activity
	        	finish();
	        	
	        	// start new activity
	        	startActivity(intent);
	    		
	    	}
	    	else if (item.getItemId() == R.id.back){
	    		
	    		// creates new intent, links back to first screen
		    	Intent back = new Intent(this, ImageSelection.class);
		    	
		    	// activates intent, returns to first screen
		    	startActivity(back);
		    	
		    	// **TODO save current puzzle or destroy it?  **
	    	}
	        return true;
	        }
	    
	    // source (mostly): http://stackoverflow.com/questions/9412940/cutting-the-image-or-photo-into-pieces
	    // changed to make adaptable to difficulty
	    // Crop bitmap into pieces.
	    public Bitmap[] cutbitmap(Bitmap loadedpicture, int difficulty){
	    	
	    	// create bitmaparray to store pictures in
	    	Bitmap[] bitmaparray = new Bitmap[difficulty*difficulty];
	    	
	    	// variable for counting how many times both loops execute
	    	int counter = 0;
	    	
	    	// get measurements of pictures
	    	int bitmapwidth = loadedpicture.getWidth();
	    	int bitmapheight = loadedpicture.getHeight();
	    	
	    	// cut picture into bits and pieces
	    	// also make an array for the ID's
	    	for(int i = 0; i < difficulty; i++){
	    		for(int j = 0; j < difficulty; j ++){
	    			bitmaparray[counter]=Bitmap.createBitmap(loadedpicture, j*(bitmapwidth/difficulty), i*(bitmapheight/difficulty), bitmapwidth/difficulty, bitmapheight/difficulty);
	    			ID[counter] = counter;
	    			counter++;
	    			
	    		}
	    	}
	    	return bitmaparray;
	    }
	    
	  
	
}
