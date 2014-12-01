package nl.mprog.projects.npuzzle6072909;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class YouWin extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_win);
		ImageView image = (ImageView)findViewById(R.id.victoryscreen);
	}

	public void SendSecondMessage(View view) {
		 
	 	// creates new intent, links back to first screen
    	Intent intent = new Intent(this, ImageSelection.class);
    	
    	// activates intent, returns to first screen
    	startActivity(intent);
 }
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}