package ie.cathalcoffey.android.rajawali.tutorials;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.lights.PointLight;
import rajawali.math.Number3D;
import rajawali.math.Number3D.Axis;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.annotation.SuppressLint;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;

public class RajawaliLoadModelRenderer extends RajawaliRenderer {
	private PointLight mLight;
	BaseObject3D mObjectGroup;
	EllipticalOrbitAnimation3D mLightAnim;
	
	public RajawaliLoadModelRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new PointLight();
		mLight.setPosition(0, 1, -6);
		mLight.setPower(3);
		getCurrentCamera().setLookAt(0, 0, 0);
		getCurrentCamera().setZ(-6);
		
		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.camaro_obj);
		try {
			objParser.parse();
			mObjectGroup = objParser.getParsedObject();
			mObjectGroup.addLight(mLight);
			mObjectGroup.setY(-1);
			mObjectGroup.setRotation(90, 0, 0);
			
			addChild(mObjectGroup);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		mObjectGroup = objParser.getParsedObject();
		mObjectGroup.addLight(mLight);
		mObjectGroup.setY(-1);
		mObjectGroup.setRotation(90, 0, 0);
		
		mLightAnim = new EllipticalOrbitAnimation3D(new Number3D(), new Number3D(0, 3, 0), Number3D.getAxisVector(Axis.Z), 0, 0, OrbitDirection.CLOCKWISE);
		mLightAnim.setDuration(5000);
		mLightAnim.setRepeatMode(RepeatMode.INFINITE);
		mLightAnim.setTransformable3D(mLight);
	}
	
	@SuppressLint("NewApi")
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		FragmentsExampleActivity mainActivity = ((FragmentsExampleActivity) mContext);
		
		RajawaliLoadModel  rlmf = (RajawaliLoadModel) mainActivity.getFragmentManager().findFragmentByTag("rajawali");
		
		((RajawaliExampleFragment) rlmf).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleFragment) rlmf).hideLoader();
		
		registerAnimation(mLightAnim);
		mLightAnim.play();	
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
