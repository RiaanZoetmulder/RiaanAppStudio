package nl.mprog.projects.npuzzle6072909;

import android.app.AlertDialog;
import android.app.Dialog;
//import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TestDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		// creates new dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		// sets the title of the dialog and sets the selecteable items
	    builder.setTitle("Change difficulty")
	           .setItems(R.array.myarray, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	           }
	    });
	    return builder.create();
	}
	

}
