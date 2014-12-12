package nl.mprog.projects.npuzzle6072909;
/*
 * 


*/
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
// create class Imageadapter
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private Bitmap[] mCutpictures;
	private int[] mIDs;
	boolean mPlayed;
	
	// Create method Imageadapter
	public ImageAdapter(Context c, Bitmap[] cutpictures, int[] ID, boolean played){
		mContext = c;
		mCutpictures = cutpictures;
		mIDs = ID;
		mPlayed = played;
		
	}
	
	// get the count of images
	@Override
	public int getCount() {
		return mCutpictures.length;
	}
	
	//return position of each image
	@Override
	public Object getItem(int position) {
		return position;
	}

	// get the ID of the Item
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	// fill the view with images
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// set bitmaps in the context with the right position in the gridview
		ImageView myimageview = new ImageView(mContext);
		myimageview.setImageBitmap(mCutpictures[position]);
		
		// set the Tags, when updated ID's will change accordingly
		myimageview.setTag(mIDs[position]);
		return myimageview;
	}



}
