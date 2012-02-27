package com.monyetmabuk.tutorials.rajawali.tutorials.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExamplesAdapter extends BaseAdapter {

	private List<String> objects;
	private final Context context;

	public ExamplesAdapter(Context context, List<String> objects) {
		this.context = context;
		this.objects = objects;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Object obj = objects.get(position);

		TextView tv = new TextView(context);
		tv.setPadding(10, 10, 10, 10);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setTextSize(20);
		tv.setText(obj.toString());
		tv.setTextColor(0xaaffffff);
		
		Typeface font=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
	    tv.setTypeface(font);
		
		return tv;
	}

}
