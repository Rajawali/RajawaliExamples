package com.monyetmabuk.rajawali.tutorials.cameras;

import rajawali.BaseObject3D;
import rajawali.OrthographicCamera;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.BounceInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;

public class OrthographicCamRenderer extends RajawaliRenderer {
	public OrthographicCamRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		OrthographicCamera orthoCam;
		int[][] grid;

		orthoCam = new OrthographicCamera();
		getCurrentScene().switchCamera(orthoCam);

		DirectionalLight spotLight = new DirectionalLight(1f, -.1f, -.5f);
		spotLight.setPower(2);

		grid = new int[10][];
		for (int i = 0; i < 10; i++)
			grid[i] = new int[10];

		DiffuseMaterial material = new DiffuseMaterial();
		try {
			material.addTexture(new Texture(R.drawable.checkerboard));
		} catch (TextureException e) {
			e.printStackTrace();
		}

		BaseObject3D group = new BaseObject3D();
		group.setRotX(-45);
		group.setRotY(-45);
		group.setY(-.8f);

		BaseObject3D innerGroup = new BaseObject3D();
		group.addChild(innerGroup);

		Plane plane = new Plane(Axis.Y);
		plane.setMaterial(material);
		plane.setColor(0xff0000ff);
		plane.addLight(spotLight);
		innerGroup.addChild(plane);

		orthoCam.setZoom(1.5f);

		DiffuseMaterial cubeMaterial = new DiffuseMaterial();
		cubeMaterial.setUseSingleColor(true);

		for (int i = 0; i < 40; i++) {
			Cube cube = new Cube(.1f);
			cube.setMaterial(cubeMaterial);
			cube.addLight(spotLight);
			cube.setY(100);
			innerGroup.addChild(cube);

			// find grid available grid cell
			boolean foundCell = false;
			int row = 0, column = 0;
			while (!foundCell) {
				int cell = (int) Math.floor(Math.random() * 100);
				row = (int) Math.floor(cell / 10.f);
				column = cell % 10;
				if (grid[row][column] == 0) {
					grid[row][column] = 1;
					foundCell = true;
				}
			}

			Vector3 toPosition = new Vector3(-.45f + (column * .1f), .05f,
					-.45f + (row * .1f));
			Vector3 fromPosition = new Vector3(toPosition.x, 7, toPosition.z);

			TranslateAnimation3D anim = new TranslateAnimation3D(fromPosition,
					toPosition);
			anim.setDuration(4000 + (int) (4000 * Math.random()));
			anim.setInterpolator(new BounceInterpolator());
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			anim.setTransformable3D(cube);
			anim.setDelay((int) (10000 * Math.random()));
			registerAnimation(anim);
			anim.play();
		}

		RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, 359);
		anim.setDuration(20000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		anim.setTransformable3D(innerGroup);
		registerAnimation(anim);
		anim.play();

		addChild(group);
	}
}
