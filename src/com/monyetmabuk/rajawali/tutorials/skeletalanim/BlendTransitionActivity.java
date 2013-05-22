package com.monyetmabuk.rajawali.tutorials.skeletalanim;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class BlendTransitionActivity extends RajawaliExampleActivity implements OnClickListener {
	private BlendTransitionRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		mRenderer = new BlendTransitionRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.BOTTOM);

        Button button1 = new Button(this);
        button1.setText("IDLE");
        button1.setOnClickListener(this);
        button1.setId(0);
        ll.addView(button1);
        
        Button button2 = new Button(this);
        button2.setText("WALK");
        button2.setOnClickListener(this);
        button2.setId(1);
        ll.addView(button2);
        
        Button button3 = new Button(this);
        button3.setText("ARM STRETCH");
        button3.setOnClickListener(this);
        button3.setId(2);
        ll.addView(button3);
        
        Button button4 = new Button(this);
        button4.setText("BEND");
        button4.setOnClickListener(this);
        button4.setId(3);
        ll.addView(button4);
        
        mLayout.addView(ll);
        
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.TOP);
        
        TextView creatorText = new TextView(this);
        creatorText.setText("Model by Tiziana Loni. Lame animations by MasDennis.");
        ll.addView(creatorText);
        
        mLayout.addView(ll);
        
        initLoader();
	}
	
	public void onClick(View v) {
		mRenderer.transitionAnimation(v.getId());
	}
}
