package com.monyetmabuk.rajawali.tutorials.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AnimatedGIFTexture;
import rajawali.math.Vector3;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;

public class AnimatedGIFTextureRenderer extends RajawaliRenderer {
	private AnimatedGIFTexture mGIFTexture;
	private int[] mGifs;
	private int mCurrentGIF;
	private Plane mPlane;
	private boolean mUpdatePlaneSize;

	public AnimatedGIFTextureRenderer(Context context) {
		super(context);
		mGifs = new int[] {
				R.drawable.animated_gif_01,
				R.drawable.animated_gif_02,
				R.drawable.animated_gif_03,
				R.drawable.animated_gif_04,
				R.drawable.animated_gif_05
		};
		setFrameRate(60);
	}
	
	protected void initScene() {
		mPlane = new Plane(Axis.Z);
		SimpleMaterial material = new SimpleMaterial();
		try {
			mGIFTexture = new AnimatedGIFTexture(mGifs[0]);
			material.addTexture(mGIFTexture);
			mGIFTexture.rewind();
			mPlane.setScaleX((float)mGIFTexture.getWidth() / (float)mGIFTexture.getHeight());
			mCurrentGIF++;
		} catch (TextureException e) {
			e.printStackTrace();
		}
		
		mPlane.setMaterial(material);
		getCurrentScene().addChild(mPlane);
		getCurrentCamera().setLookAt(0, 0, 0);
		
		EllipticalOrbitAnimation3D anim = new EllipticalOrbitAnimation3D(new Vector3(0, 0, 3), new Vector3(2, 1, 3), 0, 359);
		anim.setDuration(12000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		anim.setTransformable3D(getCurrentCamera());
		registerAnimation(anim);
		anim.play();
	}
	
	public void nextGIF()
	{
		if(mCurrentGIF == mGifs.length) mCurrentGIF = 0;
		mGIFTexture.setResourceId(mGifs[mCurrentGIF++]);
		mUpdatePlaneSize = true;
		mPlane.setScaleX((float)mGIFTexture.getWidth() / (float)mGIFTexture.getHeight());
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		if(mGIFTexture != null)
		{
			try {
				mGIFTexture.update();
			} catch (TextureException e) {
				e.printStackTrace();
			}
			if(mUpdatePlaneSize)
			{
				mPlane.setScaleX((float)mGIFTexture.getWidth() / (float)mGIFTexture.getHeight());
				mUpdatePlaneSize = false;
			}
		}
	}
}
