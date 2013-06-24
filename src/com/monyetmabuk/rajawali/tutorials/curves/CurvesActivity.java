package com.monyetmabuk.rajawali.tutorials.curves;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class CurvesActivity extends RajawaliExampleActivity {
	private CurvesRenderer mRenderer;

    public void onCreate(Bundle savedInstanceState) {
    	mMultisamplingEnabled = true;
        super.onCreate(savedInstanceState);
        mRenderer = new CurvesRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.TOP);

        TextView label = new TextView(this);
        label.setText("Cubic Bezier, Linear Bezier, Quadratic Bezier, Catmull Rom, Compound Curve");
        label.setTextSize(14);
        label.setGravity(Gravity.CENTER);
        label.setHeight(100);
        ll.addView(label);
        
        mLayout.addView(ll);
        
        initLoader();
    }
}
