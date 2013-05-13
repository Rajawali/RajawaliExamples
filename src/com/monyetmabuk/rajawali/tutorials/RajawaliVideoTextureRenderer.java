package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Number3D.Axis;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;

public class RajawaliVideoTextureRenderer extends RajawaliRenderer {
	private BaseObject3D mCube;
	private SurfaceTexture mSurfaceTexture;
	private MediaPlayer mMediaPlayer;

	public RajawaliVideoTextureRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(1f, 0.2f, -1.0f);
		light.setColor(1.0f, 1.0f, 1.0f);
		light.setPower(2);
		
		/*
		 * 		VideoMaterial material = new VideoMaterial();
		 * 		TextureInfo tInfo = mTextureManager.addVideoTexture();
		 * 		
		 * 		mTexture = new SurfaceTexture(tInfo.getTextureId());
		 * 		
		 * 		mMediaPlayer = MediaPlayer.create(getContext(), R.raw.nemo);
		 * 		mMediaPlayer.setSurface(new Surface(mTexture));
		 * 		mMediaPlayer.start();
		 * 		
		 * 		BaseObject3D cube = new Plane(2, 2, 1, 1);
		 * 		cube.setMaterial(material);
		 * 		cube.addTexture(tInfo);
		 * 		cube.addLight(mLight);
		 * 		addChild(cube);
		 * 	}
		 * 
		 * 	public void onDrawFrame(GL10 glUnused) {
		 * 		mTexture.updateTexImage();


		try {
			DiffuseMaterial material = new DiffuseMaterial();
			material.addTexture(new Texture(R.drawable.earthtruecolor_nasa_big));
			mCube = new Cube(1);
			mCube.setMaterial(material);
			mCube.addLight(light);
			addChild(mCube);

		} catch (TextureException e) {
			e.printStackTrace();
		}
*/
		getCurrentCamera().setZ(4.2f);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		if (mCube != null) {
			mCube.setRotX(mCube.getRotX() + .1f);
			mCube.setRotZ(mCube.getRotZ() + .2f);
		}
		if(mSurfaceTexture != null) {
			mSurfaceTexture.updateTexImage();
		}
	}
}
