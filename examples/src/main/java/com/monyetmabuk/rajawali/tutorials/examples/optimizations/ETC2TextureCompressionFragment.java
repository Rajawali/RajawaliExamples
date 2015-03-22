package com.monyetmabuk.rajawali.tutorials.examples.optimizations;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.about.dialogs.ExceptionDialog;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Capabilities;
import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Etc1Texture;
import rajawali.materials.textures.Etc2Texture;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public class ETC2TextureCompressionFragment extends AExampleFragment {

    @Override
    public AExampleRenderer createRenderer() {
		return new ETC2TextureCompression(getActivity());
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inflater.inflate(R.layout.etc2_overlay, mLayout, true);
        mLayout.findViewById(R.id.etc2_overlay).bringToFront();
        return mLayout;
    }

    private void showExceptionDialog(String title, String message) {
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        Fragment existingDialog = fragmentManager
            .findFragmentByTag(ExceptionDialog.TAG);

        if (existingDialog != null)
            fragmentManager.beginTransaction().remove(existingDialog).commit();

        Bundle bundle = new Bundle();
        bundle.putString(ExceptionDialog.BUNDLE_KEY_TITLE, title);
        bundle.putString(ExceptionDialog.BUNDLE_KEY_MESSAGE, message);

        ExceptionDialog exceptionDialog = new ExceptionDialog();
        exceptionDialog.setArguments(bundle);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(exceptionDialog, ExceptionDialog.TAG);
        ft.commit();
    }

	private final class ETC2TextureCompression extends AExampleRenderer {
		private Object3D mPNGPlane;
        private Object3D mETC1Plane;
        private Object3D mETC2Plane;

		public ETC2TextureCompression(Context context) {
			super(context);
		}

		protected void initScene() {
            if (Capabilities.getGLESMajorVersion() < 3) {
                showExceptionDialog("ETC2 Not Supported", "This device does not support OpenGL ES 3.0 and cannot use ETC2 textures.");
                return;
            }

			getCurrentCamera().setPosition(0, 0, 7);

            try {
                // This is a raw PNG image
                Texture texture0 = new Texture("png", R.drawable.rectangles);
                Material material0 = new Material();
                material0.addTexture(texture0);
                material0.setColorInfluence(0);
                mPNGPlane = new Plane(1.5f, 1.5f, 1, 1);
                mPNGPlane.setMaterial(material0);
                mPNGPlane.setPosition(0, -1.75f, 0);
                mPNGPlane.setDoubleSided(true);
                getCurrentScene().addChild(mPNGPlane);
            } catch (TextureException e) {
                e.printStackTrace();
            }

			try {
                // This is an ETC1 image
				Texture texture1 = new Texture("etc1", new Etc1Texture("etc1Tex", R.raw.rectangles_etc1, null));
				Material material1 = new Material();
				material1.addTexture(texture1);
				material1.setColorInfluence(0);
				mETC1Plane = new Plane(1.5f, 1.5f, 1, 1);
				mETC1Plane.setMaterial(material1);
				mETC1Plane.setPosition(0, 0, 0);
				mETC1Plane.setDoubleSided(true);
				getCurrentScene().addChild(mETC1Plane);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			try {
                // This is an ETC2 image
				Texture texture2 = new Texture("etc2", new Etc2Texture("etc2Tex", R.raw.rectangles_etc2, null));

				Material material2 = new Material();
				material2.addTexture(texture2);
				material2.setColorInfluence(0);

				mETC2Plane = new Plane(1.5f, 1.5f, 1, 1);
				mETC2Plane.setMaterial(material2);
				mETC2Plane.setPosition(0, 1.75f, 0);
				mETC2Plane.setDoubleSided(true);
				getCurrentScene().addChild(mETC2Plane);
			} catch (TextureException e) {
				e.printStackTrace();
			}
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			// Rotate the plane to showcase difference between a mipmapped
			// texture and non-mipmapped texture.
			/*if (mETC2Plane != null) {
				mETC2Plane.setRotX(mETC2Plane.getRotX() - 0.1f);
				mETC2Plane.setRotY(mETC2Plane.getRotY() - 0.1f);
			}
			if (mETC1Plane != null) {
				mETC1Plane.setRotX(mETC1Plane.getRotX() + 0.1f);
				mETC1Plane.setRotY(mETC1Plane.getRotY() + 0.1f);
			}*/
		}

	}

}
