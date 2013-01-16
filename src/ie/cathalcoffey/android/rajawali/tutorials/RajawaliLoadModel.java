package ie.cathalcoffey.android.rajawali.tutorials;

import com.monyetmabuk.rajawali.tutorials.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class RajawaliLoadModel extends RajawaliExampleFragment{
	RajawaliLoadModelRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliLoadModelRenderer(getActivity());
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mLayout = (FrameLayout)inflater.inflate(R.layout.fragment_rajawali, container, false);
    	mLayout.addView(mSurfaceView);
    	
    	mLoaderGraphic = new ImageView(getActivity());
    	mLayout.addView(mLoaderGraphic);
    	
        return mLayout;
    }
}