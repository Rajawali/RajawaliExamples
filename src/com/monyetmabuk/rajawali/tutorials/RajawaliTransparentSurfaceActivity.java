package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;
import android.widget.ImageView;

public class RajawaliTransparentSurfaceActivity extends RajawaliExampleActivity {
	private RajawaliTransparentSurfaceRenderer mRenderer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // -- set this before setRenderer()
        //    then use setBackgroundColor(0) in the renderer
        setGLBackgroundTransparent(true);
        
        mRenderer = new RajawaliTransparentSurfaceRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.flickrpics);

		mLayout.addView(iv, 0);

        initLoader();
    }
}
