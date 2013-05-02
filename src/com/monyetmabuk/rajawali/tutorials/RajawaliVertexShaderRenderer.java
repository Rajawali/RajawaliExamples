package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.math.Number3D;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class RajawaliVertexShaderRenderer extends RajawaliRenderer {
	private int mFrameCount = 0;
	private CustomVertexShaderMaterial mMaterial;
	private Animation3D mAnim;
	private BaseObject3D mSphere;
	
	public RajawaliVertexShaderRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mMaterial = new CustomVertexShaderMaterial();
		mMaterial.setUseColor(true);
		
		mSphere = new Sphere(2, 60, 60);
		mSphere.setMaterial(mMaterial);
		addChild(mSphere);
		
		Number3D axis = new Number3D(2, 4, 1);
		axis.normalize();
		
		mAnim = new RotateAnimation3D(axis, 360);
		mAnim.setRepeatMode(RepeatMode.INFINITE);
		mAnim.setDuration(12000);
		mAnim.setTransformable3D(mSphere);
		
		getCurrentCamera().setPosition(0, 0, 10);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		registerAnimation(mAnim);
		mAnim.play();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMaterial.setTime((float)mFrameCount++);
	}
	
	public void toggleWireframe() {
		mSphere.setDrawingMode(mSphere.getDrawingMode() == GLES20.GL_TRIANGLES ? GLES20.GL_LINES : GLES20.GL_TRIANGLES);
	}
}
