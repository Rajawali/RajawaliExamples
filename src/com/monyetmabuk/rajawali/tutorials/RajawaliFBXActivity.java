package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RajawaliFBXActivity extends RajawaliExampleActivity {
	private RajawaliFBXRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliFBXRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);

        TextView label = new TextView(this);
        label.setText("Model by Sampo Rask");
        label.setTextSize(20);
        label.setGravity(Gravity.CENTER);
        label.setHeight(100);
        ll.addView(label);

        mLayout.addView(ll);
		
		initLoader();
	}
}
