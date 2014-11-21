package nl.mprog.projects.nPuzzle6072909;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameplay_layout);
	}
	
	/*// creates method SendSecondMessage
	 public void SendSecondMessage(View view) {
		 
		 	// creates new intent, links back to first screen
	    	Intent intent = new Intent(this, ImageSelection.class);
	    	
	    	// activates intent, returns to first screen
	    	startActivity(intent);
	 }*/
	 
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.gameplaymenu, menu);
	        android.view.MenuInflater inflater = getMenuInflater();
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	    	String message = "";
	    	if (item.getItemId() == R.id.changedifficulty) {
	    		message = "altering difficulty";
	    		DialogFragment alterdifficulty = new TestDialog();
	    		alterdifficulty.show(getSupportFragmentManager(), "dialogbox");
	    		
	    		// **TODO: Actually alter the difficulty of the game and restart**
	    		
	    	}
	    	else if (item.getItemId() == R.id.shuffle) {
	    		// **TODO: initiate reshuffling*
	    		message = "reshuffling";
	   
	    	}
	    	else if (item.getItemId() == R.id.back){
	    		message = "returning to menu";
	    		
	    		// creates new intent, links back to first screen
		    	Intent back = new Intent(this, ImageSelection.class);
		    	
		    	// activates intent, returns to first screen
		    	startActivity(back);
		    	
		    	// ** save current puzzle or destroy it?  **
	    	}
	    	else if(item.getItemId() == R.id.mockvictory){
	    		
	    		Intent victory = new Intent(this, YouWin.class);
		    	
		    	// activates intent, returns to first screen
		    	startActivity(victory);
	    	}
	    	
	    	// show message via toast
	    	Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
	    	toast.show();
	    	
	        return true;
	        }
	
}
