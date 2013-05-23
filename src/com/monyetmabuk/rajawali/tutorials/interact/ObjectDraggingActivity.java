package com.monyetmabuk.rajawali.tutorials.interact;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class ObjectDraggingActivity extends RajawaliExampleActivity implements OnTouchListener {
	private ObjectDraggingRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new ObjectDraggingRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		mSurfaceView.setOnTouchListener(this);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);

        TextView label = new TextView(this);
        label.setText("Touch & drag");
        label.setTextSize(20);
        label.setGravity(Gravity.CENTER_HORIZONTAL);
        label.setHeight(100);
        ll.addView(label);
        
        mLayout.addView(ll);
		
		initLoader();
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mRenderer.getObjectAt(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			mRenderer.moveSelectedObject(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_UP:
			mRenderer.stopMovingSelectedObject();
			break;
		}
		return true;
	}
}
