package com.monyetmabuk.rajawali.tutorials.curves;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class UniformDistributionActivity extends RajawaliExampleActivity {
	private UniformDistributionRenderer mRenderer;

    public void onCreate(Bundle savedInstanceState) {
    	mMultisamplingEnabled = true;
        super.onCreate(savedInstanceState);
        mRenderer = new UniformDistributionRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.TOP);

        TextView label = new TextView(this);
        label.setText("How to distribute points uniformly along a curve. Useful for constant speed animations. White: original curve. Red: reparametrized curve.");
        label.setTextSize(16);
        label.setGravity(Gravity.CENTER);
        label.setHeight(140);
        ll.addView(label);

        mLayout.addView(ll);
        
        initLoader();
    }
}
