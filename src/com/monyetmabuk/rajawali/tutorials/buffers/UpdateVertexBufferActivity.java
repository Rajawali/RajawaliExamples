package com.monyetmabuk.rajawali.tutorials.buffers;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class UpdateVertexBufferActivity extends RajawaliExampleActivity {
	private UpdateVertexBufferRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new UpdateVertexBufferRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
