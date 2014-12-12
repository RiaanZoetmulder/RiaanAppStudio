/* Author: Riaan Zoetmulder   Studentnummer: 6072909
 * Project: Npuzzle			  Date: 12-12-2014
 * 
 * Class:ImageSelection, implements a list of pictures with text from which the user can select.
 * 
 * **Methods Implemented**
 * onCreate: Creates the layout and checks whether a previous game has been played, also starts music
 * 
 * onPause: stops the music and erases it from memory
 * onResume: starts the music once again
 * 
 * Sources:
 * Listview with Images: http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 * 
 * 
*/
package nl.mprog.projects.npuzzle6072909;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

public class ImageSelection extends ListActivity implements OnItemClickListener {
	
	// create mediaplayer
	MediaPlayer startmusic;
	
	
	 // define list of possible pictures 
    static final String[] PICTURES = new String[] {
    	"Darth Vader",
    	"Asthma Vader",
    	"Bat Vader",
    	"Surf Vader",
    	"Fat Vader",
    	"Kirk Vader",
    	"Luke Vader"
    };
    
    // define which images have to be drawn
    static final Integer[] imageId = {
    	R.drawable.darthvader,
    	R.drawable.asthma,
    	R.drawable.batman,
    	R.drawable.darkholiday,
    	R.drawable.fatdarth,
    	R.drawable.kirkspock,
    	R.drawable.lukelea
    };
    
    // define which saved state to access
	public static final String saved_state = "MySavedState";
	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // setting layout
        setContentView(R.layout.list_container);
        
        // setting customview for listadapter
        CustomView adapter = new CustomView(ImageSelection.this, PICTURES, imageId);
        setListAdapter(adapter);
        
        // get shared preferences
        SharedPreferences mPrefs = getSharedPreferences(saved_state,0); 
        
        // set variable for checking if back is pressed in gameplay class
        boolean backoverride = false;
        
        // check if previousgames have been played
        boolean previousgames = mPrefs.getBoolean("reinitialize", false);
        
        // Start the music
        startmusic = MediaPlayer.create(getApplicationContext(), R.raw.firstscreen);
		startmusic.start();
		
        // check bundle to see if data from gameplay activity
        Bundle extras = getIntent().getExtras();
        if( extras != null){
        	backoverride = extras.getBoolean("stop");
        	
        	// if you came from game screen, stop music
        	startmusic.stop();
        }
        
        // if there have been previous games and back wasn't pressed
        if (previousgames == true && backoverride == false){
        	
        	// immediately restore previous game activity 
        	Intent previousgameintent = new Intent(this, GamePlay.class);
        	startActivity(previousgameintent);
        	
        	// stop the music player
        	startmusic.stop();
        	
        	// terminate current activity
        	finish();
        } 
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	// find position of listitem clicked
    	String itemText = PICTURES[position];
    	final int pictureclicked = position; 
    	
    	// create Dialog with easy, difficult, hard
    	new AlertDialog.Builder(this)
        .setTitle("Select Difficulty")
        .setItems(R.array.myarray, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        	
        	// create new intent
        	// With the help of the following link: http://stackoverflow.com/questions/22645107/android-intent-in-dialog-box
        	Intent intent = new Intent(ImageSelection.this, GamePlay.class);
        	intent.putExtra("pictureclicked", pictureclicked);
        	
        	// add extra information to the intent
        	if(which == 0){
        		intent.putExtra("difficulty",3);
        	}
        	else if(which ==1){
        		intent.putExtra("difficulty",4);
        	}
        	else{
        		intent.putExtra("difficulty",5);
        	}
        	
        	startmusic.stop();
        	// start new intent and finish current one
        	ImageSelection.this.startActivity(intent);
        	finish(); 
        	}
        }).show();
    }
    

    // empty on itemclick listener. because this has to be implemented.
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	protected void onStop(){
		super.onStop();
		
		// stop the music when stopped
		startmusic.stop();
		
		// releasing music from memory
		startmusic.release();
		startmusic = null;
	}
	protected void onStart(){
		super.onStart();
		
		// start music when restarted
		startmusic = MediaPlayer.create(getApplicationContext(), R.raw.firstscreen);
		startmusic.start();
	}
}