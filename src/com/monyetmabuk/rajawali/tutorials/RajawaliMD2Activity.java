package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class RajawaliMD2Activity extends RajawaliExampleActivity implements OnClickListener {
	private RajawaliMD2Renderer mRenderer;
	private LinearLayout mLinearLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliMD2Renderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setGravity(Gravity.TOP);
        mLinearLayout.setVisibility(LinearLayout.INVISIBLE);
        
        Button button = new Button(this);
        button.setId(0);
        button.setOnClickListener(this);
        button.setText("salute");
        button.setTextSize(10);
        mLinearLayout.addView(button);
        
        button = new Button(this);
        button.setId(1);
        button.setOnClickListener(this);
        button.setText("death1");
        button.setTextSize(10);
        mLinearLayout.addView(button);
        
        button = new Button(this);
        button.setId(2);
        button.setOnClickListener(this);
        button.setText("crwalk");
        button.setTextSize(10);
        mLinearLayout.addView(button);
        
        button = new Button(this);
        button.setId(3);
        button.setOnClickListener(this);
        button.setText("wave");
        button.setTextSize(10);
        mLinearLayout.addView(button);

        button = new Button(this);
        button.setId(4);
        button.setOnClickListener(this);
        button.setText("loop all");
        button.setTextSize(10);
        mLinearLayout.addView(button);
        
        mLayout.addView(mLinearLayout);
		
		initLoader();
	}
	
	@Override
	public void hideLoader() {
		super.hideLoader();
		mLayout.post(new Runnable() {
			@Override
			public void run() {
				mLinearLayout.setVisibility(LinearLayout.VISIBLE);				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(((Button)v).getId()) {
		case 0:		mRenderer.playAnimation("salute");		break;
		case 1:		mRenderer.playAnimation("death1");		break;
		case 2:		mRenderer.playAnimation("crwalk");		break;
		case 3:		mRenderer.playAnimation("wave");		break;
		case 4:		mRenderer.playAnimation("loop all");	break;
		}
	}

}
