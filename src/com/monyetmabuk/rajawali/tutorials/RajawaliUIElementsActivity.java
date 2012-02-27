package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RajawaliUIElementsActivity extends RajawaliExampleActivity {
	private RajawaliUIElementsRenderer mRenderer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliUIElementsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);

        TextView label = new TextView(this);
        label.setText("Halo Dunia!");
        label.setTextSize(20);
        label.setGravity(Gravity.CENTER);
        label.setHeight(100);
        ll.addView(label);
        
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.rajawali_outline);
        ll.addView(image);
        
        mLayout.addView(ll);
		
		initLoader();
	}
}
