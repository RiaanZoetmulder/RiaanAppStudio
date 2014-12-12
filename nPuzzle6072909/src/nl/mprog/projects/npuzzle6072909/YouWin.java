/* Author: Riaan Zoetmulder   Studentnummer: 6072909
 * Project: Npuzzle			  Date: 12-12-2014
 * 
 * ***Description of classes and methods:***
 * Class: YouWin
 * prints a congratulatory message and starts music, has a return button to the imageselection screen.
 * 
 * Methods: onCreate: sets the image, starts the music and implements the return button
 * 
 */
package nl.mprog.projects.npuzzle6072909;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class YouWin extends ActionBarActivity {

	// create mediaplayer
	MediaPlayer mp;

	// access to sharedpreferences in order to wipe them
	private final String saved_state = "MySavedState";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_win);

		// set image
		final ImageView image = (ImageView) findViewById(R.id.victoryscreen);

		// start sound
		mp = MediaPlayer.create(getApplicationContext(), R.raw.victorymusic);
		mp.start();

		// wipe the memory from saved data
		final SharedPreferences settings = getSharedPreferences(saved_state,
				MODE_PRIVATE);
		final SharedPreferences.Editor editor = settings.edit();

		editor.clear();
	}

	public void SendSecondMessage(View view) {

		// stop sound
		mp.reset();

		// creates new intent, links back to first screen
		Intent intent = new Intent(this, ImageSelection.class);

		// sets stop to false, re enters menu
		intent.putExtra("stop", true);

		// activates intent, returns to first screen
		startActivity(intent);
		
		// destroy current activity
		finish();
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