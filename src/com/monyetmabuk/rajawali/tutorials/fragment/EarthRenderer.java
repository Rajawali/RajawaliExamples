package com.monyetmabuk.rajawali.tutorials.fragment;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.ScaleAnimation3D;
import rajawali.animation.SlerpAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;

public class EarthRenderer extends RajawaliRenderer {
	private final float CAMERA_DISTANCE = 3;
	private BaseObject3D mMarker;
	private PointLight mLight;
	
	
	public EarthRenderer(Context context) {
		super(context);
	}

	public void initScene() {
		mLight = new PointLight();
		mLight.setPower(2);
		
		try {
			BaseObject3D earth = new Sphere(1, 32, 32);
			DiffuseMaterial material = new DiffuseMaterial();
			material.addTexture(new Texture(R.drawable.earth_diffuse_big));
			earth.addLight(mLight);
			earth.setMaterial(material);
			getCurrentScene().addChild(earth);
			
			mMarker = new Sphere(.03f, 16, 16);
			DiffuseMaterial markerMaterial = new DiffuseMaterial();
			markerMaterial.setUseSingleColor(true);
			mMarker.setColor(0xffff00);
			mMarker.setPosition(0, 0, 1);
			mMarker.addLight(mLight);
			mMarker.setMaterial(markerMaterial);
			getCurrentScene().addChild(mMarker);
			
			ScaleAnimation3D scaleAnim = new ScaleAnimation3D(.5f, 1);
			scaleAnim.setDuration(500);
			scaleAnim.setTransformable3D(mMarker);
			scaleAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			registerAnimation(scaleAnim);
			scaleAnim.play();
			
			getCurrentScene().setSkybox(R.drawable.space_skybox);
		} catch (TextureException e) {
			e.printStackTrace();
		}

		getCurrentCamera().setLookAt(0, 0, 0);
		getCurrentCamera().setPosition(0, 0, CAMERA_DISTANCE);
	}
	
	public void setLongitudeLatitudeAndElevation(float longitude, float latitude, float elevation)
	{
		float radiansMultiplier = (float)Math.PI / 180.f;
		// -- convert latitude and longitude to radians
		float latitudeRadians = latitude * radiansMultiplier;
		float longitudeRadians = longitude * radiansMultiplier;
		// -- earth's radius in kilometers + (elevation in meters / 1000 to convert it to kilometers)
		//final float EARTH_RADIUS = 6371;
		//float altitude = EARTH_RADIUS + (elevation / 1000); 
		// we can ignore altitude in this example
		float altitude = 1;
		
		// -- convert to cartesian (x, y, z) coordinates
		Vector3 v = new Vector3();	
		v.x = (float)Math.cos(latitudeRadians) * (float)Math.cos(longitudeRadians) * -altitude;
		v.y = (float)Math.sin(latitudeRadians) * altitude;
		v.z = (float)Math.cos(latitudeRadians) * (float)Math.sin(longitudeRadians) * altitude;

		// -- animate the marker sphere
		SlerpAnimation3D anim = new SlerpAnimation3D(mMarker.getPosition(), v);
		anim.setDuration(1000);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setTransformable3D(mMarker);
		registerAnimation(anim);
		anim.play();
		
		// -- animate the camera
		Vector3 from = new Vector3(getCurrentCamera().getPosition());
		anim = new SlerpAnimation3D(from, Vector3.multiply(v, CAMERA_DISTANCE));
		anim.setDuration(2000);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setTransformable3D(getCurrentCamera());
		registerAnimation(anim);
		anim.play();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mLight.setPosition(getCurrentCamera().getPosition());
	}
}
