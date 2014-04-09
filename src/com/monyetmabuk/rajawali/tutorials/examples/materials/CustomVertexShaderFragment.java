package com.monyetmabuk.rajawali.tutorials.examples.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.materials.Material;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Sphere;
import android.content.Context;
import android.opengl.GLES20;
import android.view.View;
import android.view.View.OnClickListener;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.materials.CustomVertexShaderMaterialPlugin;

public class CustomVertexShaderFragment extends AExampleFragment implements
		OnClickListener {

	@Override
	protected AExampleRenderer createRenderer() {
		return new VertexShaderRenderer(getActivity());
	}

	@Override
	public void onClick(View v) {
		((VertexShaderRenderer) mRenderer).toggleWireframe();
	}

	private final class VertexShaderRenderer extends AExampleRenderer {

		private int mFrameCount = 0;
		private Material mMaterial;
		private Object3D mSphere;

		public VertexShaderRenderer(Context context) {
			super(context);
		}

		@Override
		protected void initScene() {
			mMaterial = new Material();
			mMaterial.enableTime(true);
			mMaterial.addPlugin(new CustomVertexShaderMaterialPlugin());
			mSphere = new Sphere(2, 60, 60);
			mSphere.setMaterial(mMaterial);
			getCurrentScene().addChild(mSphere);

			Vector3 axis = new Vector3(2, 4, 1);
			axis.normalize();

			RotateOnAxisAnimation mAnim = new RotateOnAxisAnimation(axis, 360);
			mAnim.setRepeatMode(RepeatMode.INFINITE);
			mAnim.setDurationMilliseconds(12000);
			mAnim.setTransformable3D(mSphere);
			getCurrentScene().registerAnimation(mAnim);
			mAnim.play();

			getCurrentCamera().setPosition(0, 0, 10);
		}

		@Override
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mMaterial.setTime((float) mFrameCount++);
		}

		public void toggleWireframe() {
			mSphere.setDrawingMode(mSphere.getDrawingMode() == GLES20.GL_TRIANGLES ? GLES20.GL_LINES
					: GLES20.GL_TRIANGLES);
		}
	}
}
