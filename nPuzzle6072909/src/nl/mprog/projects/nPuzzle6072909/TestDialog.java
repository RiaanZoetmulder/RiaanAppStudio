package nl.mprog.projects.npuzzle6072909;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TestDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Change difficulty")
	           .setItems(R.array.myarray, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	           }
	    });
	    return builder.create();
	}
	

}
