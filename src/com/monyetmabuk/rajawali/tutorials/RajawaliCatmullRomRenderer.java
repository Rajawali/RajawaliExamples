package com.monyetmabuk.rajawali.tutorials;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.CatmullRomPath3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Number3D;
import rajawali.math.Number3D.Axis;
import rajawali.parser.ObjParser;
import rajawali.parser.AParser.ParsingException;
import rajawali.primitives.Line3D;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliCatmullRomRenderer extends RajawaliRenderer {
	private Stack<Animation3D> mAnims;
	
	public RajawaliCatmullRomRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mAnims = new Stack<Animation3D>();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		for (int i = 0; i < mAnims.size(); i++)
			mAnims.get(i).start();
	}
	
	protected void initScene() {
		super.initScene();
		ALight light1 = new DirectionalLight(0, 0, -1);
		light1.setPower(1);
		mCamera.setPosition(0, 0, 10);
		mCamera.setLookAt(0, 0, 0);

		SimpleMaterial material = new SimpleMaterial();
		material.setUseColor(true);
		
		// -- create a catmull-rom path. The first and the last point are control points.
		CatmullRomPath3D path = new CatmullRomPath3D();
		float r = 12;
		float rh = r * .5f;

		for (int i = 0; i < 16; i++) {
			path.addPoint(new Number3D(-rh + (Math.random() * r), -rh + (Math.random() * r), -rh + (Math.random() * r)));
		}

		try {
			ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.arrow);
			parser.parse();
			BaseObject3D arrow = parser.getParsedObject();
			arrow.setMaterial(material);
			arrow.setScale(.2f);
			arrow.setColor(0xffffff00);
			arrow.addLight(light1);
			addChild(arrow);

			TranslateAnimation3D mAnim = new TranslateAnimation3D(path);
			mAnim.setDuration(12000);
			mAnim.setRepeatCount(Animation3D.INFINITE);
			mAnim.setRepeatMode(Animation3D.REVERSE);
			// -- orient to path 
			mAnim.setOrientToPath(true);
			mAnim.setTransformable3D(arrow);
			mAnims.add(mAnim);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		
		int numPoints = path.getNumPoints();

		for (int i = 0; i < numPoints; i++) {
			Sphere s = new Sphere(.2f, 6, 6);
			s.addLight(light1);
			s.setMaterial(material);
			s.setPosition(path.getPoint(i));

			if (i == 0)
				s.setColor(0xffff0000);
			else if (i == numPoints - 1)
				s.setColor(0xff0066ff);
			else
				s.setColor(0xff999999);

			addChild(s);
		}

		// -- visualize the line
		Stack<Number3D> linePoints = new Stack<Number3D>();
		for (int i = 0; i < 100; i++) {
			linePoints.add(path.calculatePoint(i / 100f));
		}
		Line3D line = new Line3D(linePoints, 1, 0xffffffff);
		line.setMaterial(material);
		addChild(line);

		RotateAroundAnimation3D mCamAnim = new RotateAroundAnimation3D(new Number3D(), Axis.Y, 26);
		mCamAnim.setDuration(10000);
		mCamAnim.setRepeatCount(Animation3D.INFINITE);
		mCamAnim.setTransformable3D(mCamera);
		mAnims.add(mCamAnim);
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
