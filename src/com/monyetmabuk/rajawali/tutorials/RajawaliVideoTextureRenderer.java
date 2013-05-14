package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.VideoMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.VideoTexture;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.media.MediaPlayer;

public class RajawaliVideoTextureRenderer extends RajawaliRenderer {
	private BaseObject3D mCube;
	private MediaPlayer mMediaPlayer;
	private VideoTexture mVideoTexture;
	
	public RajawaliVideoTextureRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(1f, 0.2f, -1.0f);
		light.setColor(1.0f, 1.0f, 1.0f);
		light.setPower(2);
		
		mMediaPlayer = MediaPlayer.create(getContext(), R.raw.sintel_trailer_480p);
		mMediaPlayer.setLooping(true);
		
		mVideoTexture = new VideoTexture("sintelTrailer", mMediaPlayer);
		VideoMaterial material = new VideoMaterial();		
		try {
			material.addTexture(mVideoTexture);
		} catch (TextureException e) {
			e.printStackTrace();
		}		
		
		mCube = new Cube(1);
		mCube.setMaterial(material);
		addChild(mCube);
		 
		getCurrentCamera().setZ(2.2f);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mMediaPlayer.start();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		if (mCube != null) {
			mCube.setRotX(mCube.getRotX() + .5f);
			mCube.setRotZ(mCube.getRotZ() + .8f);
		}
		mVideoTexture.update();
	}
	
	public void onVisibilityChanged(boolean visible) {
		super.onVisibilityChanged(visible);
		if(!visible)
			if(mMediaPlayer != null) mMediaPlayer.pause();
		else
			if(mMediaPlayer != null) mMediaPlayer.start();
	}
	
	public void onSurfaceDestroyed() {
		super.onSurfaceDestroyed();
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}
}
