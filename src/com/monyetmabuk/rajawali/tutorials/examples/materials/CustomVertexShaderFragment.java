package com.monyetmabuk.rajawali.tutorials.examples.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.math.Vector3;
import rajawali.primitives.Sphere;
import android.content.Context;
import android.opengl.GLES20;
import android.view.View;
import android.view.View.OnClickListener;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.materials.CustomVertexShaderMaterial;

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

	public final class VertexShaderRenderer extends AExampleRenderer {

		private int mFrameCount = 0;
		private CustomVertexShaderMaterial mMaterial;
		private Animation3D mAnim;
		private BaseObject3D mSphere;

		public VertexShaderRenderer(Context context) {
			super(context);
		}

		@Override
		protected void initScene() {
			mMaterial = new CustomVertexShaderMaterial();
			mMaterial.setUseVertexColors(true);

			mSphere = new Sphere(2, 60, 60);
			mSphere.setMaterial(mMaterial);
			addChild(mSphere);

			Vector3 axis = new Vector3(2, 4, 1);
			axis.normalize();

			mAnim = new RotateAnimation3D(axis, 360);
			mAnim.setRepeatMode(RepeatMode.INFINITE);
			mAnim.setDuration(12000);
			mAnim.setTransformable3D(mSphere);
			registerAnimation(mAnim);
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
