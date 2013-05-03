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
	private Button mSwitchCamera;
	private Button mSwitchScene;
	private Button mNextFrame; 
	
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
        
        mSwitchCamera = new Button(getApplicationContext());
        mSwitchCamera.setGravity(Gravity.CENTER);
        mSwitchCamera.setHeight(50);
        mSwitchCamera.setWidth(200);
        mSwitchCamera.setOnClickListener(this);
        mSwitchCamera.setText("Switch Camera");
        ll.addView(mSwitchCamera);
        
        mSwitchScene = new Button(getApplicationContext());
        mSwitchScene.setGravity(Gravity.CENTER);
        mSwitchScene.setHeight(50);
        mSwitchScene.setWidth(200);
        mSwitchScene.setOnClickListener(this);
        mSwitchScene.setText("Switch Scene");
        ll.addView(mSwitchScene);
        
        mNextFrame = new Button(getApplicationContext());
        mNextFrame.setGravity(Gravity.CENTER);
        mNextFrame.setHeight(50);
        mNextFrame.setWidth(200);
        mNextFrame.setOnClickListener(this);
        mNextFrame.setText("Next Frame");
        ll.addView(mNextFrame);
        
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
		} else if (arg0.equals(mSwitchCamera)) {
			mRenderer.nextCamera();
		} else if (arg0.equals(mSwitchScene)) {
			mRenderer.nextScene();
		} else if (arg0.equals(mNextFrame)) {
			mRenderer.nextFrame();
		}
	}
}
