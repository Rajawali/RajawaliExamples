package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.ColorAnimation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliColorAnimationRenderer extends RajawaliRenderer {

	public RajawaliColorAnimationRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	@Override
	protected void initScene() {
		//
		// -- First cube
		//
		
		SimpleMaterial material1 = new SimpleMaterial();
		material1.setUseSingleColor(true);
		
		Cube cube1 = new Cube(1);
		cube1.setMaterial(material1);
		cube1.setTransparent(true);
		cube1.setX(-1);
		getCurrentScene().addChild(cube1);
		
		Animation3D anim = new ColorAnimation3D(0xaaff1111, 0xffffff11);
		anim.setTransformable3D(cube1);
		anim.setDuration(2000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		registerAnimation(anim);
		anim.play();
		
		anim = new RotateAnimation3D(Axis.Y, 359);
		anim.setTransformable3D(cube1);
		anim.setDuration(6000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		registerAnimation(anim);
		anim.play();
		
		//
		// -- second cube
		//
		
		SimpleMaterial material2 = new SimpleMaterial();
		material2.setColorBlendFactor(.5f);
		try {
			material2.addTexture(new Texture(R.drawable.camden_town_alpha));
		} catch (TextureException e) {
			e.printStackTrace();
		}
		material2.setUseSingleColor(true);
		
		Cube cube2 = new Cube(1);
		cube2.setMaterial(material2);
		cube2.setX(1);
		getCurrentScene().addChild(cube2);
		
		anim = new ColorAnimation3D(0xaaff1111, 0xff0000ff);
		anim.setTransformable3D(cube2);
		anim.setDuration(2000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		registerAnimation(anim);
		anim.play();
		
		anim = new RotateAnimation3D(Axis.Y, -359);
		anim.setTransformable3D(cube2);
		anim.setDuration(6000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		registerAnimation(anim);
		anim.play();
		
		getCurrentCamera().setPosition(0, 4, 8);
		getCurrentCamera().setLookAt(0, 0, 0);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
}
