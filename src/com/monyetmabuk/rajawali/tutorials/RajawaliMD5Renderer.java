package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.mesh.AnimationSkeleton;
import rajawali.animation.mesh.BoneAnimationSequence;
import rajawali.lights.DirectionalLight;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.md5.MD5AnimParser;
import rajawali.parser.md5.MD5MeshParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliMD5Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private AnimationSkeleton mObject;
	
	public RajawaliMD5Renderer(Context context)
	{
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene()
	{
		mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);

		mCamera.setZ(20);
		
		try {
			MD5MeshParser meshParser = new MD5MeshParser(this, R.raw.boblampclean_mesh);
			meshParser.parse();
			
			MD5AnimParser animParser = new MD5AnimParser("attack2", this, R.raw.boblampclean_anim);
			animParser.parse();
			
			BoneAnimationSequence sequence = (BoneAnimationSequence)animParser.getParsedAnimationSequence();
			
			mObject = (AnimationSkeleton)meshParser.getParsedAnimationObject();
			mObject.setAnimationSequence(sequence);
			mObject.addLight(mLight);
			mObject.setScale(.1f);
			mObject.setRotY(180);
			mObject.play();
			
			addChild(mObject);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
