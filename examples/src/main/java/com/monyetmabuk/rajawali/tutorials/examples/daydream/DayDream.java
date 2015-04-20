package com.monyetmabuk.rajawali.tutorials.examples.daydream;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;

import com.monyetmabuk.rajawali.tutorials.R;

import org.rajawali3d.Object3D;
import org.rajawali3d.RajawaliDaydream;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

/**
 * @author Jared Woolston (jwoolston@idealcorp.com)
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class DayDream extends RajawaliDaydream {

    @Override
    public RajawaliRenderer createRenderer() {
        return new BasicRenderer(this);
    }

    public final class BasicRenderer extends RajawaliRenderer {

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

        @Override
        public void onOffsetsChanged(float v, float v1, float v2, float v3, int i, int i1) {

        }

        @Override
        public void onTouchEvent(MotionEvent motionEvent) {

        }
    }
}