package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class RajawaliVertexShaderActivity extends RajawaliExampleActivity implements OnClickListener {
	private RajawaliVertexShaderRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliVertexShaderRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);
        ll.setOnClickListener(this);

        Button button = new Button(this);
        button.setText("Touch To Toggle Wireframe Mode");
        button.setBackgroundColor(0xff000000);
        button.setTextColor(0xffffffff);
        ll.addView(button);
        mLayout.addView(ll);
		
		initLoader();
	}

	public void onClick(View v) {
		mRenderer.toggleWireframe();
	}
}
