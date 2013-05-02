package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import rajawali.Camera2D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.ColorAnimation3D;
import rajawali.materials.SimpleMaterial;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class RajawaliColorAnimationRenderer extends RajawaliRenderer {

	public RajawaliColorAnimationRenderer(Context context) {
		super(context);
		setFrameRate(60);
		getCurrentScene().switchCamera(new Camera2D());
	}
	
	@Override
	protected void initScene() {
		final SimpleMaterial material = new SimpleMaterial();
		material.setUseColor(true);
		
		final Plane plane = new Plane(1, 1, 1, 1);
		plane.setMaterial(material);
		plane.setTransparent(true);
		addChild(plane);
		
		final Animation3D anim = new ColorAnimation3D(0xaaff1111, 0xffffff11);
		anim.setTransformable3D(plane);
		anim.setDuration(2000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		registerAnimation(anim);
		anim.play();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
}
