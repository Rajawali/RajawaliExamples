package com.monyetmabuk.rajawali.tutorials.fragment;

import rajawali.RajawaliFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.monyetmabuk.rajawali.tutorials.R;

public class EarthFragment extends RajawaliFragment {
	private EarthRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new EarthRenderer(getActivity());
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	mLayout = (FrameLayout)inflater.inflate(R.layout.fragment_rajawali,  container, false);
    	mLayout.addView(mSurfaceView);
    	
    	return mLayout;
    }
    
    public void setLongitudeLatitudeAndElevation(float longitude, float latitude, float elevation)
    {
    	mRenderer.setLongitudeLatitudeAndElevation(longitude, latitude, elevation);
    }
}
