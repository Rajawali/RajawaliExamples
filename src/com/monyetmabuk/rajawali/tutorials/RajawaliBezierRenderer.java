package com.monyetmabuk.rajawali.tutorials;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.BezierPath3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.PhongMaterial;
import rajawali.math.Number3D;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class RajawaliBezierRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private Stack<Animation3D> mAnimations;

	public RajawaliBezierRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(0, 0, -6);
		mCamera.setPosition(0, 0, -14);

		BaseObject3D redSphere = new Sphere(1, 16, 16);
		redSphere.setLight(mLight);
		redSphere.setMaterial(new PhongMaterial());
		redSphere.setPosition(0, -4, 0);
		redSphere.setColor(0xffff0000);
		redSphere.getMaterial().setUseColor(true);
		addChild(redSphere);

		BaseObject3D yellowSphere = new Sphere(.6f, 16, 16);
		yellowSphere.setLight(mLight);
		yellowSphere.setPosition(2, 4, 0);
		yellowSphere.setColor(0xffffff00);
		yellowSphere.getMaterial().setUseColor(true);
		addChild(yellowSphere);

		BezierPath3D redBezierPath = new BezierPath3D();
		redBezierPath.addPoint(new Number3D(0, -4, 0), new Number3D(-2, -4, .2f), new Number3D(4, 4, 4), new Number3D(-2, 4, 4.5f));
		redBezierPath.addPoint(new Number3D(-2, 4, 4.5f), new Number3D(2, -2, -2), new Number3D(4, 4, 4), new Number3D(-2, 4, 4.5f));

		BezierPath3D yellowBezierPath = new BezierPath3D();
		yellowBezierPath.addPoint(new Number3D(2, 4, 0), new Number3D(-8, 3, 4), new Number3D(-4, 0, -2), new Number3D(4, -3, 30));
		yellowBezierPath.addPoint(new Number3D(4, -3, 30), new Number3D(6, 1, 2), new Number3D(4, 2, 3), new Number3D(-3, -3, -4.5f));

		mAnimations = new Stack<Animation3D>();
		
		Animation3D redAnim = new TranslateAnimation3D(redBezierPath);
		redAnim.setDuration(2000);
		redAnim.setRepeatCount(Animation3D.INFINITE);
		redAnim.setRepeatMode(Animation3D.REVERSE);
		redAnim.setTransformable3D(redSphere);
		mAnimations.add(redAnim);

		Animation3D yellowAnim = new TranslateAnimation3D(yellowBezierPath);
		yellowAnim.setDuration(3800);
		yellowAnim.setRepeatCount(Animation3D.INFINITE);
		yellowAnim.setRepeatMode(Animation3D.REVERSE);
		yellowAnim.setTransformable3D(yellowSphere);
		mAnimations.add(yellowAnim);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		for(int i=0; i<mAnimations.size(); i++)
			mAnimations.get(i).start();
	}
}
