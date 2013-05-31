package com.monyetmabuk.rajawali.tutorials.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.VideoMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.VideoTexture;
import rajawali.primitives.Cube;
import rajawali.primitives.ScreenQuad;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

public class CameraVideoTextureRenderer extends RajawaliRenderer implements SurfaceTexture.OnFrameAvailableListener {
	private Camera mHardwCamera;
	private VideoTexture mVideoTexture;
	private ScreenQuad mScreenQuad;
	private boolean mUpdateTex;

	public CameraVideoTextureRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		if(Camera.getNumberOfCameras() == 0)
			return;

		mHardwCamera = Camera.open(0);
		
		DirectionalLight light = new DirectionalLight(1f, 0.2f, -1.0f);
		light.setPower(2);
		
		VideoMaterial screenQuadMaterial = new VideoMaterial();
		try {
			mVideoTexture = new VideoTexture("camTexture", mHardwCamera, this);
			screenQuadMaterial.addTexture(mVideoTexture);
			mScreenQuad = new ScreenQuad();
			mScreenQuad.setMaterial(screenQuadMaterial);
			mScreenQuad.setScaleY(1.5f);
			getCurrentScene().addChild(mScreenQuad);
		} catch (TextureException e) {
			e.printStackTrace();
		}
		
		DiffuseMaterial material = new DiffuseMaterial();
		material.setUseSingleColor(true);
		
		Cube cube = new Cube(1);
		cube.setColor(0xff990000);
		cube.setMaterial(material);
		cube.addLight(light);
		addChild(cube);		

		if(mHardwCamera != null)
			mHardwCamera.startPreview();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		if(mUpdateTex)
		{
			mVideoTexture.update();
			mUpdateTex = false;
		}
	}
	
	public void onSurfaceDestroyed() {
		super.onSurfaceDestroyed();
		if(mHardwCamera != null)
		{
			mHardwCamera.stopPreview();
			mHardwCamera.release();
		}
	}
	
	@Override
	public void onFrameAvailable(SurfaceTexture surfaceTexture) {
		synchronized(this)
		{
			mUpdateTex = true;
		}
	}
}
