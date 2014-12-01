package nl.mprog.projects.npuzzle6072909;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private Bitmap[] mCutpictures;
	private int[] mIDs;
	

	public ImageAdapter(Context c, Bitmap[] cutpictures, int[] ID){
		mContext = c;
		mCutpictures = cutpictures;
		mIDs = ID;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCutpictures.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView myimageview = new ImageView(mContext);
		myimageview.setImageBitmap(mCutpictures[position]);
		myimageview.setTag(mIDs[position]);
		//System.out.println("getView Method functions correctly");
		
		
		return myimageview;
	}



}
