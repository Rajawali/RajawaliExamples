package com.monyetmabuk.rajawali.tutorials;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RajawaliRipplesActivity extends RajawaliExampleActivity implements OnTouchListener {
	private RajawaliRipplesRenderer mRenderer;
	private Point mScreenSize;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliRipplesRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		mSurfaceView.setOnTouchListener(this);
		
		Display display = getWindowManager().getDefaultDisplay();
		mScreenSize = new Point();
		mScreenSize.x = display.getWidth();
		mScreenSize.y = display.getHeight();
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);
		
        TextView tv = new TextView(this);
        tv.setText("Touch Me.");
        tv.setTextColor(0xffffffff);
        ll.addView(tv);
        
		initLoader();
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			mRenderer.setTouch(event.getX() / mScreenSize.x, 1.0f - (event.getY() / mScreenSize.y));
		}
		return super.onTouchEvent(event);
	}
}
