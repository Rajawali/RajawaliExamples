package com.monyetmabuk.rajawali.tutorials.curves;

import java.util.List;
import java.util.Stack;

import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.curves.CompoundCurve3D;
import rajawali.curves.ICurve3D;
import rajawali.curves.SVGPath;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Vector3;
import rajawali.primitives.Line3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.BounceInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;

public class SVGPathRenderer extends RajawaliRenderer {
	public SVGPathRenderer(Context context)
	{
		super(context);
	}
	
	public void initScene()
	{	
		String path = "M22.395-127.223c-4.492,11.344-4.688,33.75,0,44.883" +
				"c-11.328-4.492-33.656-4.579-44.789,0.109c4.491-11.354,4.688-33.75,0-44.892" +
				"C-11.066-122.63,11.262-122.536,22.395-127.223z";		
		SVGPath svgPath = new SVGPath();
		
		List<CompoundCurve3D> paths = svgPath.parseResourceString(mContext, R.raw.lavatories_svg_path);
		paths.addAll(svgPath.parseString(path));
		Stack<Stack<Vector3>> pathPoints = new Stack<Stack<Vector3>>();

		for(int i=0; i<paths.size(); i++)
		{
			ICurve3D subPath = paths.get(i);
			Stack<Vector3> points = new Stack<Vector3>();
			int subdiv = 1000;
			for(int j=0; j<=subdiv; j++)
			{
				points.add(subPath.calculatePoint((float)j / (float)subdiv));
			}
			
			pathPoints.add(points);
			Line3D line = new Line3D(points, 1);
			SimpleMaterial material = new SimpleMaterial();
			material.setUseSingleColor(true);
			line.setMaterial(material);
			getCurrentScene().addChild(line);
			
			TranslateAnimation3D anim = new TranslateAnimation3D(new Vector3(line.getPosition().x, line.getPosition().y, line.getPosition().z - 80));
			anim.setInterpolator(new BounceInterpolator());
			anim.setDuration(1 + Math.random() * 5);
			anim.setDelay(.5f + Math.random());
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			anim.setTransformable3D(line);
			registerAnimation(anim);
			anim.play();
		}		
		getCurrentCamera().setFarPlane(2000);
		getCurrentCamera().setY(50);
		getCurrentCamera().setZ(520);
	}
}
