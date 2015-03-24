package com.monyetmabuk.rajawali.tutorials.examples.materials;

import android.content.Context;
import android.graphics.Color;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.SerializedObject3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.methods.SpecularMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.CubeMapTexture;

import java.io.ObjectInputStream;

import javax.microedition.khronos.opengles.GL10;

public class MaterialsFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new MaterialsRenderer(getActivity());
	}

	private final class MaterialsRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mMonkey1, mMonkey2, mMonkey3, mMonkey4;

		public MaterialsRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(.3f, -.3f, -1);
			mLight.setPower(.6f);
			
			getCurrentScene().addLight(mLight);
			getCurrentCamera().setPosition(0, 0, 9);

			try {
				ObjectInputStream ois = new ObjectInputStream(mContext
						.getResources().openRawResource(R.raw.monkey_ser));
				SerializedObject3D serializedMonkey = (SerializedObject3D) ois
						.readObject();
				ois.close();

				mMonkey1 = new Object3D(serializedMonkey);
				mMonkey1.setScale(.7f);
				mMonkey1.setPosition(-1, 1, 0);
				mMonkey1.setRotY(0);
				getCurrentScene().addChild(mMonkey1);

				mMonkey2 = mMonkey1.clone();
				mMonkey2.setScale(.7f);
				mMonkey2.setPosition(1, 1, 0);
				mMonkey2.setRotY(45);
				getCurrentScene().addChild(mMonkey2);

				mMonkey3 = mMonkey1.clone();
				mMonkey3.setScale(.7f);
				mMonkey3.setPosition(-1, -1, 0);
				mMonkey3.setRotY(90);
				getCurrentScene().addChild(mMonkey3);

				mMonkey4 = mMonkey1.clone();
				mMonkey4.setScale(.7f);
				mMonkey4.setPosition(1, -1, 0);
				mMonkey4.setRotY(135);
				getCurrentScene().addChild(mMonkey4);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Material diffuse = new Material();
			diffuse.enableLighting(true);
			diffuse.setDiffuseMethod(new DiffuseMethod.Lambert());
			mMonkey1.setMaterial(diffuse);
			mMonkey1.setColor(0xff00ff00);

			Material toon = new Material();
			toon.enableLighting(true);
			toon.setDiffuseMethod(new DiffuseMethod.Toon());
			mMonkey2.setMaterial(toon);
			mMonkey2.setColor(0xff999900);

			Material phong = new Material();
			phong.enableLighting(true);
			phong.setDiffuseMethod(new DiffuseMethod.Lambert());
			phong.setSpecularMethod(new SpecularMethod.Phong(Color.WHITE, 60));
			mMonkey3.setMaterial(phong);
			mMonkey3.setColor(0xff00ff00);

			int[] resourceIds = new int[] { R.drawable.posx, R.drawable.negx,
					R.drawable.posy, R.drawable.negy, R.drawable.posz,
					R.drawable.negz };

			Material cubeMapMaterial = new Material();
			cubeMapMaterial.enableLighting(true);
			cubeMapMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			try {
				CubeMapTexture envMap = new CubeMapTexture("monkeyCubeMap",
						resourceIds);
				envMap.isEnvironmentTexture(true);
				cubeMapMaterial.addTexture(envMap);
				cubeMapMaterial.setColorInfluence(0);
			} catch (ATexture.TextureException e) {
				e.printStackTrace();
			}
			mMonkey4.setMaterial(cubeMapMaterial);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mMonkey1.setRotY(mMonkey1.getRotY() - 1f);
			mMonkey2.setRotY(mMonkey2.getRotY() + 1f);
			mMonkey3.setRotY(mMonkey3.getRotY() - 1f);
			mMonkey4.setRotY(mMonkey4.getRotY() + 1f);
		}
	}
}
