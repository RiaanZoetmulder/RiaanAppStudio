package nl.mprog.projects.nPuzzle6072909;



import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


public class ImageSelection extends ListActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // add a listview that is defined in the layoutfile
        setListAdapter(new ArrayAdapter<String>(this,R.layout.list_container, PICTURES));
        
        // acquire listview
        ListView myList = getListView();
        
        // allow identification of pushed button in list
        myList.setTextFilterEnabled(true);
        
        // implement an onClick listener for when a user taps a color
        myList.setOnItemClickListener(this);
        
        // registering for context menu
        registerForContextMenu(getListView());
        
    }
    
    //  creating a context menu. long select a puzzle to open it 
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_selection, menu);
    }
    
    // when menu item selected use following messages
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	String message = "";
    	
    	// check for three cases, easy, difficult and Hard
    	if (item.getItemId() == R.id.Easy) {
    		Intent easy = new Intent(this, GamePlay.class);
    		easy.putExtra("difficulty", 1);
    		startActivity(easy);
    	}
    	else if (item.getItemId() == R.id.Difficult) {
    		Intent difficult = new Intent(this, GamePlay.class);
    		difficult.putExtra("difficulty", 2);
    		startActivity(difficult);
    	}
    	else if (item.getItemId() == R.id.Hard){
    		Intent hard = new Intent(this, GamePlay.class);
    		hard.putExtra("difficulty", 3);
    		startActivity(hard);
    	}
    	
    	// show message via toast
    	Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    	toast.show();
    	return true;
    }
    
   /* // left here for future implementations of options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_selection, menu);
        android.view.MenuInflater inflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	String message = "";
    	if (item.getItemId() == R.id.Easy) {
    		message = "You selected option 1!";
    	}
    	else if (item.getItemId() == R.id.Difficult) {
    		message = "You selected option 2!";
    	}
    	else if (item.getItemId() == R.id.Hard){
    		message = "Hard Mode";
    	}
    	
    	// show message via toast
    	Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    	toast.show();
    	
            return true;
        }*/
        
    
    // shows message with puzzle that was clicked
    private void showMessage(CharSequence text) {
    	Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();  
    }
    
    // define list of possible pictures (nothing fancy for beta version)
    static final String[] PICTURES = new String[] {
    	"Puzzle 1",
    	"Puzzle 2",
    	"Puzzle 3",
    	"Puzzle 4",
    	"Puzzle 5",
    	"Puzzle 6",
    	"Puzzle 7"
    };
    
    // for normal clicks
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView item = (TextView)view;
		showMessage(item.getText());
		
	}

}



