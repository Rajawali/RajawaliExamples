package ie.cathalcoffey.android.rajawali.tutorials;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.monyetmabuk.rajawali.tutorials.R;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.lights.PointLight;
import rajawali.math.Number3D;
import rajawali.math.Number3D.Axis;
import rajawali.parser.ObjParser;
import rajawali.parser.AParser.ParsingException;
import rajawali.renderer.RajawaliRenderer;
import android.annotation.SuppressLint;
import android.content.Context;

public class RajawaliLoadModelRenderer extends RajawaliRenderer {
	private PointLight mLight;
	BaseObject3D mObjectGroup;
	RotateAroundAnimation3D mLightAnim;
	
	public RajawaliLoadModelRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new PointLight();
		mLight.setPosition(0, 1, -6);
		mLight.setPower(3);
		mCamera.setLookAt(0, 0, 0);
		mCamera.setZ(-6);
		
		try {
			ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.camaro_obj);
			objParser.parse();
			mObjectGroup = objParser.getParsedObject();
			mObjectGroup.addLight(mLight);
			mObjectGroup.setY(-1);
			mObjectGroup.setRotation(90, 0, 0);
			
			addChild(mObjectGroup);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		
		mLightAnim = new RotateAroundAnimation3D(new Number3D(), Axis.Z, 3);
		mLightAnim.setDuration(5000);
		mLightAnim.setRepeatCount(Animation3D.INFINITE);
		mLightAnim.setTransformable3D(mLight);
	}
	
	@SuppressLint("NewApi")
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		FragmentsExampleActivity mainActivity = ((FragmentsExampleActivity) mContext);
		
		RajawaliLoadModel  rlmf = (RajawaliLoadModel) mainActivity.getFragmentManager().findFragmentByTag("rajawali");
		
		((RajawaliExampleFragment) rlmf).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleFragment) rlmf).hideLoader();
		
		mLightAnim.start();	
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
