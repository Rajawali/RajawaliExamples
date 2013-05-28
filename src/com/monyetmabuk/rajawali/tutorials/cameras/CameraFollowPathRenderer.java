package com.monyetmabuk.rajawali.tutorials.cameras;

import java.util.Stack;

import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.CatmullRomPath3D;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Vector3;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Line3D;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.opengl.GLES20;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;

public class CameraFollowPathRenderer extends RajawaliRenderer {
	private EllipticalOrbitAnimation3D mOrbitAnimation;
	private CatmullRomPath3D mPath;
	private Line3D mLine;

	public CameraFollowPathRenderer(Context context)
	{
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		Plane plane = new Plane(1, 1, 60, 60, Axis.Y, false, false);
		SimpleMaterial material = new SimpleMaterial(R.raw.sine_material_vertex, com.monyetmabuk.livewallpapers.photosdof.R.raw.simple_material_fragment);
		material.setUseSingleColor(true);
		
		plane.setMaterial(material);
		plane.setDrawingMode(GLES20.GL_LINES);
		plane.setColor(0xdddd11);
		plane.setScale(100);
		getCurrentScene().addChild(plane);
		
		getCurrentCamera().setLookAt(0, 0, 0);
		getCurrentCamera().setFarPlane(400);

		mPath = new CatmullRomPath3D();
		mPath.addPoint(new Vector3(-100, 100, 100));
		mPath.addPoint(new Vector3(-90, 80, 6));
		mPath.addPoint(new Vector3(-20, 60, 30));
		mPath.addPoint(new Vector3(130, 140, -10));
		mPath.addPoint(new Vector3(50, 0, -50));
		mPath.addPoint(new Vector3(0, 40, -20));
		mPath.addPoint(new Vector3(-120, 180, 130));
		mPath.addPoint(new Vector3(-50, 10, 50));
		mPath.addPoint(new Vector3(-100, 0, 120));
		
		Stack<Vector3> linePoints = new Stack<Vector3>();
		for (int i = 0; i < 100; i++) {
			linePoints.add(mPath.calculatePoint(i / 100f));
		}
		
		mLine = new Line3D(linePoints, 1, 0xffffffff);
		SimpleMaterial lineMaterial = new SimpleMaterial();
		lineMaterial.setUseSingleColor(true);
		mLine.setMaterial(lineMaterial);
		addChild(mLine);
		
		mOrbitAnimation = new EllipticalOrbitAnimation3D(new Vector3(0, 240, 0), new Vector3(150, 240, 150), 0, 359);
		mOrbitAnimation.setDuration(10000);
		mOrbitAnimation.setTransformable3D(getCurrentCamera());
		mOrbitAnimation.setRepeatMode(RepeatMode.INFINITE);
		registerAnimation(mOrbitAnimation);
		mOrbitAnimation.play();		
	}
	
	public void startCameraPathAnimation()
	{
		mOrbitAnimation.pause();
		unregisterAnimation(mOrbitAnimation);
		
		getCurrentScene().removeChild(mLine);
		
		TranslateAnimation3D camAnim = new TranslateAnimation3D(mPath);
		camAnim.setDuration(20000);
		camAnim.setTransformable3D(getCurrentCamera());
		camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		camAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		registerAnimation(camAnim);
		camAnim.play();
	}
}
