package com.monyetmabuk.rajawali.tutorials.cameras;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class CameraFollowPathActivity extends RajawaliExampleActivity implements OnClickListener {
	private CameraFollowPathRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		// -- enable anti aliasing
		mMultisamplingEnabled = true;
		super.onCreate(savedInstanceState);
		mRenderer = new CameraFollowPathRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.BOTTOM);

		Button button = new Button(this);
		button.setText("Start Camera Path Animation");
		button.setOnClickListener(this);
		ll.addView(button);
		
		mLayout.addView(ll);
		
		initLoader();
	}
	
	public void onClick(View v) {
		v.setVisibility(View.GONE);
		mRenderer.startCameraPathAnimation();
	}
}
