package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RajawaliSceneActivity extends RajawaliExampleActivity implements OnClickListener, OnTouchListener {

	private RajawaliSceneRenderer mRenderer;
	
	private Button mAddObject;
	private Button mRemoveObject;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        mAddObject = new Button(getApplicationContext());
        mAddObject.setGravity(Gravity.CENTER);
        mAddObject.setHeight(50);
        mAddObject.setWidth(200);
        mAddObject.setOnClickListener(this);
        mAddObject.setText("Add Object");
        ll.addView(mAddObject);
        
        mRemoveObject = new Button(getApplicationContext());
        mRemoveObject.setGravity(Gravity.CENTER);
        mRemoveObject.setHeight(50);
        mRemoveObject.setWidth(200);
        mRemoveObject.setOnClickListener(this);
        mRemoveObject.setText("Remove Object");
        ll.addView(mRemoveObject);
        
        TextView objectCount = new TextView(getApplicationContext());
        objectCount.setGravity(Gravity.CENTER);
        objectCount.setText("Object Count: 0");
        ll.addView(objectCount);
        
        TextView triCount = new TextView(getApplicationContext());
        triCount.setGravity(Gravity.CENTER);
        triCount.setText("   Triangle Count: 0");
        ll.addView(triCount);
        
        mLayout.addView(ll);
        
        mRenderer = new RajawaliSceneRenderer(this, new Handler(), objectCount, triCount);
        mRenderer.setSurfaceView(mSurfaceView);
        mSurfaceView.setOnTouchListener(this);
        super.setRenderer(mRenderer);
        
        initLoader();
    }
    
    public boolean onTouch(View v, MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.equals(mAddObject)) {
			//Add an object
			mRenderer.addObject(0, 0);
		} else if (arg0.equals(mRemoveObject)) {
			mRenderer.removeObject();
		}
	}
}
