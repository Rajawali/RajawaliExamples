package com.monyetmabuk.rajawali.tutorials;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.filters.TouchRippleFilter;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliRipplesRenderer extends RajawaliRenderer {
	private final int NUM_CUBES_H = 2;
	private final int NUM_CUBES_V = 2;
	private final int NUM_CUBES = NUM_CUBES_H * NUM_CUBES_V;
	private Animation3D[] mAnims;
	private TouchRippleFilter mFilter;
	private long frameCount;
	private final int QUAD_SEGMENTS = 40;
	
	public RajawaliRipplesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mAnims = new Animation3D[NUM_CUBES];
		
		getCurrentCamera().setPosition(0, 0, 10);
		
		DirectionalLight light = new DirectionalLight(0, 0, 1);
		light.setPower(1f);
				
		BaseObject3D group = new BaseObject3D();
		DiffuseMaterial material = new DiffuseMaterial();
		material.setUseSingleColor(true);
		
		Random rnd = new Random();		
		
		for(int y=0; y<NUM_CUBES_V; ++y) {
			for(int x=0; x<NUM_CUBES_H; ++x) {
				Cube cube = new Cube(.7f);
				cube.setPosition(x * 2, y * 2, 0);
				cube.addLight(light);
				cube.setMaterial(material);
				cube.setColor((int)(0xffffff * rnd.nextFloat()));
				group.addChild(cube);
				Vector3 axis = new Vector3(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
				Animation3D anim = new RotateAnimation3D(axis, 360);
				anim.setRepeatMode(RepeatMode.INFINITE);
				anim.setDuration(8000);
				anim.setTransformable3D(cube);
				
				int index = x + (NUM_CUBES_H * y);
				mAnims[index] = anim;
			}
		}
		
		group.setX(((NUM_CUBES_H-1) * 2) * -.5f);
		group.setY(((NUM_CUBES_V-1) * 2) * -.5f);
		group.setZ(-1f);
		
		addChild(group);
		
		SimpleMaterial planeMat = new SimpleMaterial();
		try {
			planeMat.addTexture(new Texture(R.drawable.utrecht));
		} catch (TextureException e) {
			e.printStackTrace();
		}
		Plane plane = new Plane(4, 4, 1, 1);
		plane.setRotZ(-90);
		plane.setScale(3.7f);
		plane.setMaterial(planeMat);
		addChild(plane);
		
		mFilter = new TouchRippleFilter();
		mFilter.setRippleSize(62);
		//mPostProcessingRenderer.setQuadSegments(QUAD_SEGMENTS);
		//mPostProcessingRenderer.setQuality(PostProcessingQuality.LOW);
		//addPostProcessingFilter(mFilter);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		for(int i=0; i<NUM_CUBES; ++i) {
			registerAnimation(mAnims[i]);
			mAnims[i].play();
		}
		mFilter.setScreenSize(mViewportWidth, mViewportHeight);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mFilter.setTime((float) frameCount++ *.05f);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);
		mFilter.setScreenSize(width, height);
	}
	
	public void setTouch(float x, float y) {
		mFilter.addTouch(x, y, frameCount *.05f);
	}
}
