package nl.mprog.projects.npuzzle6072909;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomView extends ArrayAdapter<String>{
	
	private final Activity context;
	private final String[] PICTURES;
	private final Integer[] imageId;
	public CustomView(Activity context, String[] PICTURES, Integer[] imageId) {
		super(context, R.layout.listobjects, PICTURES);
		
		this.context = context;
		this.PICTURES = PICTURES;
		this.imageId = imageId;
	}
	
	// creates listview
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		// create layout, and inflate.
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.listobjects, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(PICTURES[position]);
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
}