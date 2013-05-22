package com.monyetmabuk.rajawali.tutorials.skeletalanim;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.mesh.SkeletalAnimationObject3D;
import rajawali.animation.mesh.SkeletalAnimationSequence;
import rajawali.lights.DirectionalLight;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.md5.MD5AnimParser;
import rajawali.parser.md5.MD5MeshParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class BlendTransitionRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private SkeletalAnimationObject3D mObject;
	private SkeletalAnimationSequence mSequenceWalk;
	private SkeletalAnimationSequence mSequenceIdle;
	private SkeletalAnimationSequence mSequenceArmStretch;
	private SkeletalAnimationSequence mSequenceBend;
	
	public BlendTransitionRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(1f, -0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, .8f);
		mLight.setPower(1);

		getCurrentCamera().setZ(8);
		
		try {
			MD5MeshParser meshParser = new MD5MeshParser(this, R.raw.ingrid_mesh);
			meshParser.parse();

			MD5AnimParser animParser = new MD5AnimParser("idle", this, R.raw.ingrid_idle);
			animParser.parse();

			mSequenceIdle = (SkeletalAnimationSequence)animParser.getParsedAnimationSequence();
			
			animParser = new MD5AnimParser("walk", this, R.raw.ingrid_walk);
			animParser.parse();
			
			mSequenceWalk = (SkeletalAnimationSequence)animParser.getParsedAnimationSequence();
			
			animParser = new MD5AnimParser("armstretch", this, R.raw.ingrid_arm_stretch);
			animParser.parse();
			
			mSequenceArmStretch = (SkeletalAnimationSequence)animParser.getParsedAnimationSequence();
			
			animParser = new MD5AnimParser("bend", this, R.raw.ingrid_bend);
			animParser.parse();
			
			mSequenceBend = (SkeletalAnimationSequence)animParser.getParsedAnimationSequence();

			mObject = (SkeletalAnimationObject3D)meshParser.getParsedAnimationObject();
			mObject.setAnimationSequence(mSequenceIdle);
			mObject.setFps(24);
			mObject.addLight(mLight);
			mObject.setScale(.8f);
			mObject.setRotY(180);
			mObject.play();

			addChild(mObject);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
	}
	
	public void transitionAnimation(int id)
	{
		switch(id)
		{
		case 0:
			mObject.transitionToAnimationSequence(mSequenceIdle, 1000);
			break;
		case 1:
			mObject.transitionToAnimationSequence(mSequenceWalk, 1000);
			break;
		case 2:
			mObject.transitionToAnimationSequence(mSequenceArmStretch, 1000);
			break;
		case 3:
			mObject.transitionToAnimationSequence(mSequenceBend, 1000);
			break;
		}
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
}
