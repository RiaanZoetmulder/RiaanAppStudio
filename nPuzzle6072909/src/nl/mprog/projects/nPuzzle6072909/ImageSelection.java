/*Author: Riaan Zoetmulder
 * Project: NPuzzle
 * 
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

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.list_container);
        
        CustomView adapter = new CustomView(ImageSelection.this, PICTURES, imageId);
        
        setListAdapter(adapter);
         
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	String itemText = PICTURES[position];
    	final int pictureclicked = position; 
    	
    	new AlertDialog.Builder(this)
        .setTitle("Select Difficulty")
        .setItems(R.array.myarray, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        	
        	// start new intent
        	// With the help of the following link: http://stackoverflow.com/questions/22645107/android-intent-in-dialog-box
        	Intent intent = new Intent(ImageSelection.this, GamePlay.class);
        	intent.putExtra("pictureclicked", pictureclicked);
        	if(which == 0){
        		intent.putExtra("difficulty",3);
        	}
        	else if(which ==1){
        		intent.putExtra("difficulty",4);
        	}
        	else{
        		intent.putExtra("difficulty",5);
        	}
        	ImageSelection.this.startActivity(intent);
            
        }
    }).show();
    	
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
    	//Intent gameon = new Intent(this, GamePlay.class);
    	
    	// check for three cases, easy, difficult and Hard
    	if (item.getItemId() == R.id.Easy) {
    		Intent easy = new Intent(this, GamePlay.class);
    		easy.putExtra("difficulty", 1);
    		//gameon.putExtra("difficulty", "3");
    		startActivity(easy);
    		
    	}
    	else if (item.getItemId() == R.id.Difficult) {
    		Intent difficult = new Intent(this, GamePlay.class);
    		difficult.putExtra("difficulty", 2);
    		//gameon.putExtra("difficulty", "4");
    		startActivity(difficult);
    		
    	}
    	else if (item.getItemId() == R.id.Hard){
    		Intent hard = new Intent(this, GamePlay.class);
    		hard.putExtra("difficulty", 3);
    		//gameon.putExtra("difficulty", "5");
    		startActivity(hard);
    	}
    	
    	
    	// show message via toast
    	Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    	toast.show();
    	return true;
    }
    
    // shows message with puzzle that was clicked
    private void showMessage(CharSequence text) {
    	Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();  
    }
    
    // define list of possible pictures 
    static final String[] PICTURES = new String[] {
    	"Darth Vader",
    	"Luke Skywalker"
    };
    static final Integer[] imageId = {
    	R.drawable.darthvader,
    	R.drawable.lukesky
    	
    };
    
    // for normal clicks
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView item = (TextView)view;
		showMessage(item.getText());
		
	}

}