package com.monyetmabuk.rajawali.tutorials.examples.general;

import java.util.Random;

import rajawali.Object3D;
import rajawali.OrthographicCamera;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import android.content.Context;
import android.view.animation.BounceInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class OrthographicFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new OrthographicRenderer(getActivity());
	}

	private final class OrthographicRenderer extends AExampleRenderer {

		public OrthographicRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			OrthographicCamera orthoCam;
			int[][] grid;

			orthoCam = new OrthographicCamera();
			getCurrentScene().switchCamera(orthoCam);

			DirectionalLight spotLight = new DirectionalLight(1f, -.1f, -.5f);
			spotLight.setPower(2);
			getCurrentScene().addLight(spotLight);

			grid = new int[10][];
			for (int i = 0; i < 10; i++)
				grid[i] = new int[10];

			Material material = new Material();
			try {
				material.addTexture(new Texture("checkerboard", R.drawable.checkerboard));
				material.setColorInfluence(0);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			Object3D group = new Object3D();
			group.setRotX(-45);
			group.setRotY(-45);
			group.setY(-.8f);

			Object3D innerGroup = new Object3D();
			group.addChild(innerGroup);

			Plane plane = new Plane(Axis.Y);
			plane.setMaterial(material);
			plane.setDoubleSided(true);
			plane.setColor(0xff0000ff);
			innerGroup.addChild(plane);

			orthoCam.setZoom(1.5f);

			Material cubeMaterial = new Material();
			cubeMaterial.enableLighting(true);
			cubeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());

			Random random = new Random();
			
			for (int i = 0; i < 40; i++) {
				Cube cube = new Cube(.1f);
				cube.setMaterial(cubeMaterial);
				cube.setY(100);
				cube.setColor(0x666666 + random.nextInt(0x999999));
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
				Vector3 fromPosition = new Vector3(toPosition.x, 7,
						toPosition.z);

				TranslateAnimation3D anim = new TranslateAnimation3D(
						fromPosition, toPosition);
				anim.setDurationMilliseconds(4000 + (int) (4000 * Math.random()));
				anim.setInterpolator(new BounceInterpolator());
				anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
				anim.setTransformable3D(cube);
				anim.setDelayMilliseconds((int) (10000 * Math.random()));
				getCurrentScene().registerAnimation(anim);
				anim.play();
			}

			RotateOnAxisAnimation anim = new RotateOnAxisAnimation(Axis.Y, 359);
			anim.setDurationMilliseconds(20000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setTransformable3D(innerGroup);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			getCurrentScene().addChild(group);
		}

	}

}
