package com.monyetmabuk.rajawali.tutorials.materials;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class AnimatedGIFTextureActivity extends RajawaliExampleActivity implements OnTouchListener {
	private AnimatedGIFTextureRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new AnimatedGIFTextureRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		mSurfaceView.setOnTouchListener(this);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);

        TextView label = new TextView(this);
        label.setText("Touch the screen to load the next GIF");
        label.setTextSize(20);
        label.setGravity(Gravity.CENTER);
        label.setHeight(100);
        ll.addView(label);

        mLayout.addView(ll);
		
		initLoader();
	}

	public boolean onTouch(View view, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			mRenderer.nextGIF();
		}
		return true;
	}
}
