package com.monyetmabuk.rajawali.tutorials;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.Animation3D;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.bounds.IBoundingVolume;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Number3D;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import rajawali.scene.RajawaliScene;
import rajawali.scenegraph.IGraphNode.GRAPH_TYPE;
import rajawali.scenegraph.Octree;
import android.content.Context;
import android.opengl.Matrix;
import android.os.Handler;
import android.widget.TextView;

public class RajawaliSceneRenderer extends RajawaliRenderer {
	private DirectionalLight mLight, mLight2;
	private DiffuseMaterial mMaterial;
	private BaseObject3D mInitialTeapot;
	private EllipticalOrbitAnimation3D mCameraAnim;
	private Number3D mFocal;
	private Number3D mPeriapsis;
	
	private RajawaliScene mScene;
	
	private Handler mHandler;
	private TextView mObjectCount;
	private TextView mTriCount;
	
	private Camera mCam2;

	private Random mRandom = new Random();
	private ArrayList<BaseObject3D> teapots = new ArrayList<BaseObject3D>();

	public RajawaliSceneRenderer(Context context, Handler handler, TextView obj, TextView tri) {
		super(context);
		setFrameRate(60);
		mHandler = handler;
		mObjectCount = obj;
		mTriCount = tri;
	}

	protected void initScene() {
		mScene = new RajawaliScene(this, GRAPH_TYPE.OCTREE);
		mScene.displaySceneGraph(true);

		mCam2 = new Camera();
		mCam2.setPosition(5, 5, -5);
		mCam2.setLookAt(0.0f, 0.0f, 0.0f);
		mCam2.setFarPlane(50);
		mCam2.setFieldOfView(60);
		mCam2.updateFrustum(mPMatrix,mVMatrix); //update frustum plane
		
		mLight = new DirectionalLight(0, 1, -1);
		mLight2 = new DirectionalLight(0, -1, -1);
		getCurrentCamera().setPosition(0, 0, 20);
		getCurrentCamera().setFieldOfView(60);
		getCurrentCamera().setFarPlane(50);

		mMaterial = new DiffuseMaterial();
		mMaterial.setUseColor(true);

		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.teapot_obj);
		try {
			objParser.parse();
		} catch (ParsingException e) {
			e.printStackTrace();
		}
		//mInitialTeapot = objParser.getParsedObject();
		mInitialTeapot = new Cube(1);
		mInitialTeapot.setScale(0.250f);
		mInitialTeapot.setColor(0xFF00BFFF);
		mInitialTeapot.setMaterial(mMaterial);
		mInitialTeapot.addLight(mLight);
		mInitialTeapot.addLight(mLight2);
		mInitialTeapot.setPosition(0, 1, 0);
		mInitialTeapot.setRotation(45f, 45f, 45f);
		mInitialTeapot.setShowBoundingVolume(true);

		teapots.add(mInitialTeapot);
		mScene.addChild(mInitialTeapot);

		Animation3D anim = new EllipticalOrbitAnimation3D(new Number3D(0, 0, -5), new Number3D(0, 0, 5), 0.0,
				OrbitDirection.CLOCKWISE);
		mFocal = new Number3D(0, 0, 0);
		mPeriapsis = new Number3D(0, 0, 20);
		mCameraAnim = new EllipticalOrbitAnimation3D(mFocal, mPeriapsis, 0.0,
				OrbitDirection.CLOCKWISE);
		mCameraAnim.setDuration(10000);
		mCameraAnim.setRepeatMode(Animation3D.RepeatMode.INFINITE);
		mCameraAnim.setTransformable3D(mScene.getCamera());
		mCameraAnim.play();
		mScene.registerAnimation(mCameraAnim);
		anim.setDuration(10000);
		anim.setRepeatMode(Animation3D.RepeatMode.INFINITE);
		anim.setTransformable3D(mInitialTeapot);
		//anim.play();
		mScene.registerAnimation(anim);
		
		replaceAndSwitchScene(getCurrentScene(), mScene);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		Number3D tMin = getCurrentScene().getSceneMinBound();
		Number3D tMax = getCurrentScene().getSceneMaxBound();
		mFocal.x = tMin.x + (tMax.x - tMin.x) * .5f;
		mFocal.y = tMin.y + (tMax.y - tMin.y) * .5f;
		mFocal.z = tMin.z + (tMax.z - tMin.z) * .5f;
		mPeriapsis.y = mFocal.y;
		mPeriapsis.x = mFocal.x;
		//mPeriapsis.z = mFocal.z + Math.signum(mFocal.z)*20.0f;
		getCurrentCamera().setLookAt(mFocal);
		float[] model = new float[16];
		Matrix.setIdentityM(model, 0);
		mCam2.getTransformedBoundingVolume()
			.drawBoundingVolume(getCurrentCamera(), mPMatrix, mVMatrix, model);
		
		int length = teapots.size();
		for (int i = 0; i < length; ++i) {
			IBoundingVolume bcube = teapots.get(i).getGeometry().getBoundingBox();
			bcube.transform(teapots.get(i).getModelMatrix());
		}
		mHandler.post(new Runnable() {
			public void run() {
				mObjectCount.setText("Object Count: " + getCurrentScene().getNumChildren());
				mTriCount.setText("   Triangle Count: " + getCurrentScene().getNumTriangles());
			}
		});
	}

	public void addObject(float x, float y) {
		BaseObject3D obj = new Cube(1);
		//BaseObject3D obj = mInitialTeapot.clone();
		obj.addLight(mLight);
		obj.addLight(mLight2);
		obj.setMaterial(mMaterial);
		obj.setShowBoundingVolume(true);
		obj.setScale(mRandom.nextFloat()*0.5f+0.1f);
		obj.setColor(new Number3D(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255)));
		boolean positive = mRandom.nextBoolean();
		int sign1 = 1;
		int sign2 = 1;
		if (positive) {sign1 = 1;} else {sign1 = -1;}
		positive = mRandom.nextBoolean();
		if (positive) {sign2 = 1;} else {sign2 = -1;}
		obj.setPosition(sign1*mRandom.nextFloat()*4, sign2*mRandom.nextFloat()*2, -mRandom.nextFloat()*10);
		obj.setRotation(45f, 45f, 45f);
		teapots.add(obj);
		addChild(obj);
	}
	
	public void removeObject() {
		BaseObject3D child = teapots.get(0);
		removeChild(child);
		teapots.remove(child);
	}
}