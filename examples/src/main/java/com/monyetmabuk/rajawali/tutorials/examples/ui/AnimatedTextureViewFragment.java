package com.monyetmabuk.rajawali.tutorials.examples.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.surface.RajawaliTextureView;

/**
 * This example shows the addition of a {@link RajawaliTextureView} with properties
 * set in XML.
 */
public class AnimatedTextureViewFragment extends AExampleFragment {

    private RajawaliTextureView mRajawaliTextureView;

	@Override
    public AExampleRenderer createRenderer() {
		return new BasicRenderer(getActivity());
	}

    @Override
    public int getLayoutID() {
        return R.layout.rajawali_textureview_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((View) mRajawaliSurface).animate().rotation(360.0f).setDuration(20000).setInterpolator(new BounceInterpolator());
    }

	private final class BasicRenderer extends AExampleRenderer {

		private Object3D mSphere;

		public BasicRenderer(Context context) {
			super(context);
		}

        @Override
		protected void initScene() {
			try {
				Material material = new Material();
				material.addTexture(new Texture("earthColors",
						R.drawable.earthtruecolor_nasa_big));
				material.setColorInfluence(0);
				mSphere = new Sphere(1, 24, 24);
				mSphere.setMaterial(material);
				getCurrentScene().addChild(mSphere);
			} catch (ATexture.TextureException e) {
				e.printStackTrace();
			}

            getCurrentCamera().enableLookAt();
            getCurrentCamera().setLookAt(0, 0, 0);
            getCurrentCamera().setZ(6);
        }

        @Override
        public void onRender(final long elapsedTime, final double deltaTime) {
			super.onRender(elapsedTime, deltaTime);
			mSphere.rotate(Vector3.Axis.Y, 1.0);
		}
	}
}
