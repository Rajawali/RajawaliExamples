package com.monyetmabuk.rajawali.tutorials;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Number3D;
import rajawali.primitives.Line3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliLinesRenderer extends RajawaliRenderer {
	private Animation3D mAnim;
	
	public RajawaliLinesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		super.initScene();
		ALight light1 = new DirectionalLight(0, 0, -1);
		light1.setPower(.3f);
		mCamera.setPosition(0, 0, 27);
		
		Stack<Number3D> points = createWhirl(6, 6f, 0, 0, .05f);
		
		/**
		 * A Line3D takes a Stack of <Number3D>s, thickness and a color
		 */
		Line3D whirl = new Line3D(points, 1, 0xffffff00);
		SimpleMaterial material = new SimpleMaterial();
		material.setUseColor(true);
		whirl.setMaterial(material);
		addChild(whirl);
		
		Number3D axis = new Number3D(2, .4f, 1);
		axis.normalize();
		mAnim = new RotateAnimation3D(axis, 360);
		mAnim.setDuration(8000);
		mAnim.setRepeatCount(Animation3D.INFINITE);
		mAnim.setRepeatMode(Animation3D.RESTART);
		mAnim.setTransformable3D(whirl);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		mAnim.start();
	}
	
	private Stack<Number3D> createWhirl(int numSides, float scaleFactor, float centerX, float centerY, float rotAngle) {
		Stack<Number3D> points = new Stack<Number3D>();
		Number3D[] sidePoints = new Number3D[numSides];
		float rotAngleSin = (float)Math.sin(rotAngle);
		float rotAngleCos = (float)Math.cos(rotAngle);
		float a = (float)Math.PI * ( 1f - 2f / (float)numSides );
		float c = (float)Math.sin( a ) / ( rotAngleSin + (float)Math.sin( a + rotAngle ));
		
		for(int k=0; k<numSides; k++) {
			float t = (2f * (float)k + 1f) * (float)Math.PI / (float)numSides;
			sidePoints[k] = new Number3D(Math.sin(t), Math.cos(t), 0);
		}
		
		for(int n=0; n<64; n++) {
			for(int l=0; l<numSides; l++) {
				Number3D p = sidePoints[l];
				points.add(new Number3D((p.x * scaleFactor) + centerX, (p.y * scaleFactor) + centerY, 8 - (n * .25f)));
			}
			for(int m=0; m<numSides; m++) {
				Number3D p = sidePoints[m];
				float z = p.x;
				p.x = (p.x * rotAngleCos - p.y * rotAngleSin) * c;
				p.y = (z * rotAngleSin + p.y * rotAngleCos) * c;
			}
		}
		
		return points;
	}
}
