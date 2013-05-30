package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Camera2D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.ColorAnimation3D;
import rajawali.materials.SimpleMaterial;
import rajawali.primitives.ScreenQuad;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliColorAnimationRenderer extends RajawaliRenderer {

	public RajawaliColorAnimationRenderer(Context context) {
		super(context);
		setFrameRate(60);
		getCurrentScene().switchCamera(new Camera2D());
	}
	
	@Override
	protected void initScene() {
		final SimpleMaterial material = new SimpleMaterial();
		material.setUseSingleColor(true);
		
		final ScreenQuad screenQuad = new ScreenQuad();
		screenQuad.setMaterial(material);
		screenQuad.setTransparent(true);
		addChild(screenQuad);
		
		final Animation3D anim = new ColorAnimation3D(0xaaff1111, 0xffffff11);
		anim.setTransformable3D(screenQuad);
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
