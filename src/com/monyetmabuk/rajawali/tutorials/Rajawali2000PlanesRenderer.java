package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.CatmullRomPath3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.planes.PlanesGalore;
import com.monyetmabuk.rajawali.tutorials.planes.PlanesGaloreMaterial;

/**
 * This example shows how you can create a large number of textured planes efficiently.
 * The slow way is creating 2000 Plane objects and 16 separate textures. The optimized way
 * is to create one BaseObject3D with the vertex data for 2000 planes in one buffer (and
 * the same for tex coord data, normal data, etc). Each single plane is given the same position
 * at (0, 0, 0). Extra buffers are created for each plane's position and rotation.
 * 
 * Only one texture is used. It's a 1024*1024 bitmap containing 16 256*256 images. This is
 * called a 'texture atlas'. Each plane is assigned a specific portion of this texture.
 * 
 * This is much faster than creating separate object and textures because the shader program 
 * needs to be created once, only one texture has to be uploaded, matrix transformations need
 * to be done only once on the cpu, etc.
 * 
 * @author dennis.ippel
 *
 */
public class Rajawali2000PlanesRenderer extends RajawaliRenderer {
	private PlanesGalore mPlanes;
	private PlanesGaloreMaterial mMaterial; 
	private long mStartTime;
	private TranslateAnimation3D mCamAnim;
	
	public Rajawali2000PlanesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(0, 0, 1);
		mCamera.setPosition(0, 0, -16);

		mPlanes = new PlanesGalore();
		mMaterial = (PlanesGaloreMaterial)mPlanes.getMaterial();
		mPlanes.addLight(light);
		mPlanes.setDoubleSided(true);
		mPlanes.setZ(4);
		
		Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.flickrpics);
		mPlanes.addTexture(mTextureManager.addTexture(b));
		addChild(mPlanes);
		
		BaseObject3D empty = new BaseObject3D();
		addChild(empty);
		
		CatmullRomPath3D path = new CatmullRomPath3D();
		path.addPoint(new Number3D(-4, 0, -20));
		path.addPoint(new Number3D(2, 1, -10));
		path.addPoint(new Number3D(-2, 0, 10));
		path.addPoint(new Number3D(0, -4, 20));
		path.addPoint(new Number3D(5, 10, 30));
		path.addPoint(new Number3D(-2, 5, 40));
		path.addPoint(new Number3D(3, -1, 60));
		path.addPoint(new Number3D(5, -1, 70));
		
		mCamAnim = new TranslateAnimation3D(path);
		mCamAnim.setDuration(20000);
		mCamAnim.setRepeatCount(Animation3D.INFINITE);
		mCamAnim.setRepeatMode(Animation3D.REVERSE);
		mCamAnim.setTransformable3D(mCamera);
		mCamAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		
		mCamera.setLookAt(new Number3D(0,0,30));		
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mStartTime = System.currentTimeMillis();
		mCamAnim.start();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMaterial.setTime((System.currentTimeMillis() - mStartTime) / 1000f);
	}
}
