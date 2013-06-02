package com.monyetmabuk.rajawali.tutorials.lights;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.IAnimation3DListener;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.lights.SpotLight;
import rajawali.materials.PhongMaterial;
import rajawali.math.MathUtil;
import rajawali.math.Vector3;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class SpotLightRenderer extends RajawaliRenderer {
	public SpotLightRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		super.initScene();
		
		final SpotLight spotLight = new SpotLight();
		spotLight.setPower(1.5f);
		
		getCurrentCamera().setPosition(0, 2, 6);
		getCurrentCamera().setLookAt(0, 0, 0);
		
		PhongMaterial sphereMaterial = new PhongMaterial();
		sphereMaterial.setShininess(180);
		sphereMaterial.setUseSingleColor(true);
		
		Sphere rootSphere = new Sphere(.2f, 12, 12);
		rootSphere.setMaterial(sphereMaterial);
		rootSphere.setRenderChildrenAsBatch(true);
		rootSphere.addLight(spotLight);
		rootSphere.setVisible(false);
		addChild(rootSphere);
		
		// -- inner ring
		
		float radius = .8f;
		int count = 0;
		
		for(int i=0; i<360; i+=36)
		{
			float radians = MathUtil.degreesToRadians(i);
			int color = 0xfed14f;
			if(count % 3 == 0) color = 0x10a962;
			else if(count % 3 == 1) color = 0x4184fa;
			count++;

			BaseObject3D sphere = rootSphere.clone(false);
			sphere.setPosition((float)Math.sin(radians) * radius, 0, (float)Math.cos(radians) * radius);
			sphere.setMaterial(sphereMaterial);
			sphere.setColor(color);
			sphere.addLight(spotLight);
			addChild(sphere);			
		}
		
		// -- outer ring
		
		radius = 2.4f;
		count = 0;	
		
		for(int i=0; i<360; i+=12)
		{
			float radians = MathUtil.degreesToRadians(i);
			int color = 0xfed14f;
			if(count % 3 == 0) color = 0x10a962;
			else if(count % 3 == 1) color = 0x4184fa;
			count++;

			BaseObject3D sphere = rootSphere.clone(false);
			sphere.setPosition((float)Math.sin(radians) * radius, 0, (float)Math.cos(radians) * radius);
			sphere.setMaterial(sphereMaterial);
			sphere.setColor(color);
			sphere.addLight(spotLight);
			addChild(sphere);			
		}

		final BaseObject3D target = new BaseObject3D();
		
		EllipticalOrbitAnimation3D anim = new EllipticalOrbitAnimation3D(new Vector3(0, .2f, 0), new Vector3(1, .2f, 1), 0, 359);
		anim.setRepeatMode(RepeatMode.INFINITE);
		anim.setDuration(6000);
		anim.setTransformable3D(target);
		anim.registerListener(new IAnimation3DListener() {
			public void onAnimationUpdate(Animation3D animation, double interpolatedTime) {
				spotLight.setLookAt(target.getPosition());
			}
			
			public void onAnimationStart(Animation3D animation) {
			}
			
			public void onAnimationRepeat(Animation3D animation) {
			}
			
			public void onAnimationEnd(Animation3D animation) {
			}
		});
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
