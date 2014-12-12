package nl.mprog.projects.npuzzle6072909;

/* Author: Riaan Zoetmulder   Studentnummer: 6072909
 * Project: Npuzzle			  Date: 12-12-2014
 * 
 * ***Description of classes and methods:***
 * Class: GamePlay
 * gets the difficulty and image from Imageselection
 * 
 * Methods:
 * onCreate(): Resizes the bitmap, cuts the bitmap and fills the board with bitmaps. Implements
 * OnItemClickListener which moves pictures, shuffles the pictures and checks if victory condition is met.
 * 
 * onPause(): saves variables in shared preferences, these variables are an ID array for the images, 
 * difficulty, and the picture. 
 * 
 * onResume(): Does thesame thing OnCreate does, however it only activates if there is existant data in shared
 * preferences. Gets data from shared preferences instead of intent bundle.
 */
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity {
	// some definitions of numbers
	final static int maxSize = 25;
	final static int screenCorrection = 170;

	// file to save state of board
	private final String saved_state = "MySavedState";

	// The imageview
	ImageView image;
	GridView gridView;

	// variables for the program to remember the correct difficulty
	int difficulty = 3;
	int storedDifficulty = 3;
	int savedDifficulty;

	// ID and Bitmaparray for pictures
	Bitmap[] pictureParts;
	int[] ID = new int[maxSize];
	int[] Identification = new int[maxSize];

	// global variables for pictures
	final int picture = 0;

	// helps the program remember the correct picture
	int savedPicture;

	// defining 2 integers for location tiles to swap
	int click, white_tile, temp;
	int i = 0;
	int m = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameplay_layout);

		// retrieve bundle of information from previous screens
		Bundle extras = getIntent().getExtras();

		// ensure that the package contains something
		if (extras != null) {

			difficulty = extras.getInt("difficulty");
			final int picture = extras.getInt("pictureclicked");

			// Make sure program remembers which picture was selected
			if (picture != 0) {
				savedPicture = picture;
			}

			// make sure program remembers which difficulty was selected
			if (difficulty != 3) {
				storedDifficulty = difficulty;
			}

			try {
				// get the size of the screen
				DisplayMetrics screensize = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(screensize);

				// load the correct bitmap, default darth vader
				Bitmap loadedPicture = BitmapFactory.decodeResource(
						this.getResources(), R.drawable.darthvader);

				// load other bitmaps, more can be added if necessary
				if (picture == 1) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.asthma);
				} else if (picture == 2) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.batman);
				} else if (picture == 3) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.darkholiday);
				} else if (picture == 4) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.fatdarth);
				} else if (picture == 5) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.kirkspock);
				} else if (picture == 6) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.lukelea);
				}

				// get dimensions of the picture
				int pictureWidth = loadedPicture.getWidth();
				int pictureHeight = loadedPicture.getHeight();

				// get screenheight and width and correct
				int screenHeight = screensize.heightPixels - screenCorrection;
				int screenWidth = screensize.widthPixels;

				// keep picture width and height ratios
				screenHeight = screenWidth * (pictureWidth / pictureHeight);

				// resize the bitmap
				Bitmap resizedPicture = Bitmap.createScaledBitmap(
						loadedPicture, screenWidth, screenHeight, true);

				// remove old picture from memory
				loadedPicture.recycle();

				// crop bitmap into pieces depending on the difficulty
				final Bitmap[] pictureParts = cutbitmap(resizedPicture,
						difficulty, true);

				// remove resizedPicture
				resizedPicture.recycle();

				// cropped picture sizes
				int croppedheight = pictureParts[1].getHeight();
				int croppedwidth = pictureParts[1].getHeight();

				// last bitmap must be white
				pictureParts[difficulty * difficulty - 1] = Bitmap
						.createBitmap(croppedwidth, croppedheight,
								Bitmap.Config.ARGB_8888);

				// Create gridview
				final GridView gridView = (GridView) findViewById(R.id.gridview);

				// set imageadapter to gridview
				final ImageAdapter theimageadapter = new ImageAdapter(this,
						pictureParts, ID, false);
				gridView.setAdapter(theimageadapter);

				// make number of columns of pictures adaptable
				gridView.setNumColumns(difficulty);

				// Check if picture is clicked
				gridView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {

						// on first click, shuffle the deck
						if (m == 0) {
							Shuffle();

							// check for solveability
							int solveability = 0;
							while (solveability == 0) {

								// if not solveable reshuffle
								Shuffle();
								solveability = CheckSolveable();
							}
						}
						m++;

						// find current position white tile
						int white_Tile_Position = 0;
						int whiteTag = difficulty * difficulty - 1;

						// iterate through grid to find location of white tile
						while (whiteTag != Integer.parseInt(gridView
								.getChildAt(white_Tile_Position).getTag()
								.toString())) {
							white_Tile_Position++;
						}

						// check if upward movement is legal
						if (position - difficulty == white_Tile_Position) {
							swap(position, white_Tile_Position);
						}
						// check if movement to the right is legal
						else if (((position + 1 == white_Tile_Position) && ((white_Tile_Position % difficulty) != 0))) {
							swap(position, white_Tile_Position);
						}
						// check if movement to the left is legal
						else if (((position - 1 == white_Tile_Position) && ((white_Tile_Position % difficulty) != difficulty - 1))) {
							swap(position, white_Tile_Position);
						}
						// check if downward movement is legal
						else if (position + difficulty == white_Tile_Position) {
							swap(position, white_Tile_Position);
						} else if (m == 1) {
							Toast.makeText(GamePlay.this, "Start",
									Toast.LENGTH_SHORT).show();
						} else if (m > 1) {
							Toast.makeText(GamePlay.this, "Invalid move",
									Toast.LENGTH_SHORT).show();
						}

						// implement change to view
						theimageadapter.notifyDataSetChanged();

						// check if victorycondition is met, if so click once
						// more.
						int check = victoryCondition();
						if ((check == difficulty * difficulty - 1) && (m > 2)) {

							// creates new intent
							Intent victory = new Intent(GamePlay.this,
									YouWin.class);

							// activates intent, returns to first screen
							startActivity(victory);
						}
					}

					private int CheckSolveable() {

						// number of inversions
						int inversions = 0;

						// double for loop to iterate through the entire array
						for (int x = 0; x < ID.length - 1; x++) {
							for (int y = x + 1; y < ID.length; y++) {
								if (ID[x] > ID[y])
									inversions++;

								else if (ID[x] == 8 && x % 2 == 1) {
									inversions++;
								}
							}
							if (difficulty % 2 == 1 && inversions % 2 == 0) {
								return 1;
							} else if (difficulty % 2 == 0
									&& inversions % 2 == 1) {
								return 1;
							} else {
								return 0;
							}
						}
						return 0;
					}

					private int victoryCondition() {
						// if correct_location equals difficulty squared -1
						// player wins
						int correct_location = 0;

						// see if all the tiles are in the right posiion
						for (int n = 0; n < (difficulty * difficulty) - 1; n++) {
							if (n == Integer.parseInt(gridView.getChildAt(n)
									.getTag().toString())) {
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
						// inspired by
						// http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
						for (int k = (difficulty * difficulty) - 1; k > 0; k--) {
							int index = rand.nextInt(k + 1);

							// calls the swap function
							swap(index, k);
						}
					}

					// implements the swap function
					private void swap(int click, int white_tile) {

						// swap bitmaps
						Bitmap temp = pictureParts[click];
						pictureParts[click] = pictureParts[white_tile];
						pictureParts[white_tile] = temp;

						// swap ID tags
						int tagTemp = ID[click];
						ID[click] = ID[white_tile];
						ID[white_tile] = tagTemp;

					}
				});
			}

			// catching out of memory errors
			catch (OutOfMemoryError e) {
				Toast.makeText(
						this,
						"There is a highly trained monkey working on the problem, don't worry",
						Toast.LENGTH_LONG).show();

			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gameplaymenu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (item.getItemId() == R.id.changedifficulty) {

			// create new dialog
			new AlertDialog.Builder(this)
					.setTitle("Select Difficulty")

					// set onClickListener to check which item is selected
					.setItems(R.array.myarray,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									// create new intent
									Intent intent = new Intent(GamePlay.this,
											GamePlay.class);

									// put current remembered picture in intent
									intent.putExtra("pictureclicked",
											savedPicture);

									// put extra correct level of difficulty
									if (which == 0) {
										intent.putExtra("difficulty", 3);
									} else if (which == 1) {
										intent.putExtra("difficulty", 4);
									} else {
										intent.putExtra("difficulty", 5);
									}

									// finish current activity
									finish();

									// start new activity
									startActivity(intent);
								}
							}).show();

		} else if (item.getItemId() == R.id.shuffle) {

			// reset entire game
			// create new intent
			Intent intent = new Intent(GamePlay.this, GamePlay.class);

			// put current picture as picture
			intent.putExtra("pictureclicked", savedPicture);
			intent.putExtra("difficulty", difficulty);

			// finish current activity
			finish();

			// start new activity
			startActivity(intent);

		} else if (item.getItemId() == R.id.back) {

			// creates new intent, links back to first screen
			Intent back = new Intent(this, ImageSelection.class);

			// let imageselection activity know that you are returning and it
			// should thus show list
			back.putExtra("stop", true);

			// activates intent, returns to first screen, and finishes
			startActivity(back);
			finish();

		} else {
			finish();
			// creates new intent
			Intent victory = new Intent(GamePlay.this, YouWin.class);

			// activates intent, returns to first screen
			startActivity(victory);

		}
		return true;
	}

	// source (mostly):
	// http://stackoverflow.com/questions/9412940/cutting-the-image-or-photo-into-pieces
	// changed to make adaptable to difficulty
	// Crop bitmap into pieces.
	public Bitmap[] cutbitmap(Bitmap loadedPicture, int givendifficulty,
			boolean createnewID) {

		// create bitmaparray to store pictures in
		Bitmap[] bitmaparray = new Bitmap[givendifficulty * givendifficulty];

		// variable for counting how many times both loops execute
		int counter = 0;

		// get measurements of pictures
		int bitmapwidth = loadedPicture.getWidth();
		int bitmapheight = loadedPicture.getHeight();

		// cut picture into bits and pieces
		// also make an array for the ID's
		for (int i = 0; i < givendifficulty; i++) {
			for (int j = 0; j < givendifficulty; j++) {
				bitmaparray[counter] = Bitmap.createBitmap(loadedPicture, j
						* (bitmapwidth / givendifficulty), i
						* (bitmapheight / givendifficulty), bitmapwidth
						/ givendifficulty, bitmapheight / givendifficulty);
				if (createnewID == true) {
					ID[counter] = counter;
				}
				counter++;
			}
		}
		return bitmaparray;
	}

	protected void onPause() {
		super.onPause();

		// Save state
		final SharedPreferences settings = getSharedPreferences(saved_state,
				MODE_PRIVATE);
		final SharedPreferences.Editor editor = settings.edit();

		// clear any previous state
		editor.clear();

		// save: difficulty, picture, and check check if previously paused
		editor.putInt("thedifficulty", difficulty);
		editor.putInt("thepicture", savedPicture);
		editor.putBoolean("reinitialize", true);

		// Save integer array as seperate primitives
		for (int z = 0; z < maxSize; z++) {
			// String ident = "ID" + z;
			editor.putInt("myID" + z, ID[z]);

		}

		// commit to memory
		editor.commit();

	}

	protected void onResume() {
		super.onResume();

		// Get sharedpreferences
		SharedPreferences prefs = getSharedPreferences(saved_state,
				MODE_PRIVATE);

		// get preferences from memory
		final int savedDifficulty = prefs.getInt("thedifficulty", 9);
		final int thesavedPicture = prefs.getInt("thepicture", picture);

		// refill array with ID tags
		for (int g = 0; g < savedDifficulty * savedDifficulty; g++) {
			Identification[g] = prefs.getInt("myID" + g, 0);
		}

		// does the board have to be reinitialized?
		boolean activate = prefs.getBoolean("reinitialize", false);

		// check if previous screen wasn't the Imageselection
		Bundle extras = getIntent().getExtras();

		// if game not previously paused, reinit board
		if (activate == true && extras == null) {
			try {
				// ensuring variables are kept saved
				difficulty = savedDifficulty;
				savedPicture = thesavedPicture;

				// get the size of the screen
				DisplayMetrics screensize = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(screensize);

				// load the correct bitmap, default darth vader
				Bitmap loadedPicture = BitmapFactory.decodeResource(
						this.getResources(), R.drawable.darthvader);

				// load other bitmaps, more can be added if necessary
				if (thesavedPicture == 1) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.asthma);
				} else if (thesavedPicture == 2) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.batman);
				} else if (thesavedPicture == 3) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.darkholiday);
				} else if (thesavedPicture == 4) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.fatdarth);
				} else if (thesavedPicture == 5) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.kirkspock);
				} else if (thesavedPicture == 6) {
					loadedPicture.recycle();
					loadedPicture = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.lukelea);
				}

				// get dimensions of the picture
				int pictureWidth = loadedPicture.getWidth();
				int pictureHeight = loadedPicture.getHeight();

				// get screenheight and width
				int screenHeight = screensize.heightPixels - screenCorrection;
				int screenWidth = screensize.widthPixels;

				// keep picture width and height ratios
				screenHeight = screenWidth * (pictureWidth / pictureHeight);

				// resize the bitmap
				Bitmap resizedPicture = Bitmap.createScaledBitmap(
						loadedPicture, screenWidth, screenHeight, false);

				// remove old picture from memory
				loadedPicture.recycle();

				// crop bitmap into pieces depending on the difficulty
				final Bitmap[] pictures = cutbitmap(resizedPicture,
						savedDifficulty, false);
				ID = Identification;

				// shuffle bitmaparray in correct order and find blank tile
				int blacktile = 0;
				final Bitmap[] orderedPics = new Bitmap[savedDifficulty
						* savedDifficulty];
				for (int u = 0; u < savedDifficulty * savedDifficulty; u++) {
					orderedPics[u] = pictures[Identification[u]];
					if (Identification[u] == (savedDifficulty * savedDifficulty - 1)) {
						blacktile = u;
					}
				}

				// cropped picture sizes
				int croppedHeight = pictures[1].getHeight();
				int croppedWidth = pictures[1].getHeight();

				// last bitmap must be white
				orderedPics[blacktile] = Bitmap.createBitmap(croppedWidth,
						croppedHeight, Bitmap.Config.ARGB_8888);

				// Create gridview
				final GridView gridView = (GridView) findViewById(R.id.gridview);
				gridView.setAdapter(new ImageAdapter(this, orderedPics,
						Identification, true));

				// set imageadapter to gridview
				final ImageAdapter theimageadapter = new ImageAdapter(this,
						orderedPics, Identification, true);
				gridView.setAdapter(theimageadapter);

				// make number of columns of pictures adaptable
				gridView.setNumColumns(savedDifficulty);

				// variable that counts how many moves have been made
				m = 0;

				// set a new OnItemClickListener
				gridView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// increment global variable
						m++;

						// find current position white tile
						int white_Tile_Position = 0;
						int whiteTag = savedDifficulty * savedDifficulty - 1;

						// iterate through grid to find location of white tile
						while (whiteTag != Integer.parseInt(gridView
								.getChildAt(white_Tile_Position).getTag()
								.toString())) {
							white_Tile_Position++;
						}
						// check if upward movement is legal
						if (position - savedDifficulty == white_Tile_Position) {
							swap(position, white_Tile_Position);
						}
						// check if movement to the right is legal
						else if (((position + 1 == white_Tile_Position) && ((white_Tile_Position % savedDifficulty) != 0))) {
							swap(position, white_Tile_Position);
						}
						// check if movement to the left is legal
						else if (((position - 1 == white_Tile_Position) && ((white_Tile_Position % savedDifficulty) != savedDifficulty - 1))) {
							swap(position, white_Tile_Position);
						}
						// check if downward movement is legal
						else if (position + savedDifficulty == white_Tile_Position) {
							swap(position, white_Tile_Position);
						}

						// implement change to view
						theimageadapter.notifyDataSetChanged();

						// check if victorycondition is met, if so click once
						// more.
						int check = victoryCondition();
						if ((check == savedDifficulty * savedDifficulty - 1)
								&& m > 1) {

							// creates new intent
							Intent victory = new Intent(GamePlay.this,
									YouWin.class);

							// finish activity
							finish();

							// activates intent, returns to first screen
							startActivity(victory);

							// clear last game and commit
							final SharedPreferences settings = getSharedPreferences(
									saved_state, MODE_PRIVATE);
							final SharedPreferences.Editor editor = settings
									.edit();
							editor.clear();
							editor.commit();

						}

					}

					private int victoryCondition() {
						// if correct_location equals difficulty squared -1
						// player wins
						int correct_location = 0;

						// see if all the tiles are in the right posiion
						for (int n = 0; n < (savedDifficulty * savedDifficulty) - 1; n++) {
							if (n == Integer.parseInt(gridView.getChildAt(n)
									.getTag().toString())) {
								correct_location++;
							}
						}
						return correct_location;
					}

					// implements the swap function
					private void swap(int click, int white_tile) {

						// swap bitmaps
						Bitmap temp = orderedPics[click];
						orderedPics[click] = orderedPics[white_tile];
						orderedPics[white_tile] = temp;

						// swap ID tags
						int tagTemp = Identification[click];
						Identification[click] = Identification[white_tile];
						Identification[white_tile] = tagTemp;

					}
				});
			}

			catch (OutOfMemoryError e) {
				Toast.makeText(
						this,
						"There is a highly trained monkey working on the problem, don't worry",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
