package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.math.Number3D.Axis;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.fbx.FBXParser;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.content.Context;

public class RajawaliFBXRenderer extends RajawaliRenderer {
	private Animation3D mAnim;

	public RajawaliFBXRenderer(Context context) {
		super(context);
		RajLog.enableDebug(false);
		setFrameRate(60);
	}

	protected void initScene() {
		mAnim = new RotateAnimation3D(Axis.Y, 360);
		mAnim.setDuration(16000);
		mAnim.setRepeatMode(RepeatMode.INFINITE);
		registerAnimation(mAnim);
		
		try {
			// -- Model by Sampo Rask (http://www.blendswap.com/blends/characters/low-poly-rocks-character/)
			FBXParser parser = new FBXParser(this, R.raw.lowpolyrocks_character_blendswap);
			parser.parse();
			BaseObject3D o = parser.getParsedObject();
			addChild(o);
			
			mAnim.setTransformable3D(o);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mAnim.play();	
	}
}
