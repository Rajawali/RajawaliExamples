package com.monyetmabuk.rajawali.tutorials.examples.general;

import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.textures.ATexture.TextureException;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class SkyboxFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new SkyboxRenderer(getActivity());
	}

	private final class SkyboxRenderer extends AExampleRenderer {

		public SkyboxRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			getCurrentCamera().setFarPlane(1000);
			/*
			 * Skybox images by Emil Persson, aka Humus. http://www.humus.name humus@comhem.se
			 */
			try {
				getCurrentScene().setSkybox(R.drawable.posx, R.drawable.negx,
						R.drawable.posy, R.drawable.negy, R.drawable.posz,
						R.drawable.negz);
			} catch (TextureException e) {
				e.printStackTrace();
			}
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			getCurrentCamera().setRotY(getCurrentCamera().getRotY() - .2f);
		}

	}

}
