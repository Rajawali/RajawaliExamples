package com.monyetmabuk.rajawali.tutorials.wear_example_face;

import android.content.Context;
import android.support.wearable.watchface.Gles2WatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.wear.RajawaliWatchFaceService;
import org.rajawali3d.wear.RajawaliWatchRenderer;

public class WatchService extends RajawaliWatchFaceService {

    @Override
    protected Gles2WatchFaceService.Engine getRajawaliWatchEngine() {
        return new Engine();
    }

    private final class Engine extends RajawaliWatchEngine {

        @Override
        protected WatchFaceStyle getWatchFaceStyle() {
            return new WatchFaceStyle.Builder(WatchService.this)
                .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                .setShowSystemUiTime(false)
                .build();
        }

        @Override
        protected RajawaliWatchRenderer getRenderer() {
            return new Renderer(WatchService.this);
        }

    }

    private final class Renderer extends RajawaliWatchRenderer {

        private DirectionalLight mLight;
        private Object3D mSphere;

        public Renderer(Context context) {
            super(context);
        }

        @Override
        public void initScene() {
            mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
            mLight.setColor(1.0f, 1.0f, 1.0f);
            mLight.setPower(2);

            getCurrentScene().addLight(mLight);

            try {
                Material material = new Material();
                material.addTexture(new Texture("earthColors", R.mipmap.earthtruecolor_nasa_big));
                material.setColorInfluence(0);
                mSphere = new Sphere(1, 24, 24);
                mSphere.setMaterial(material);
                getCurrentScene().addChild(mSphere);
            } catch (ATexture.TextureException e) {
                e.printStackTrace();
            }

            getCurrentCamera().setZ(6);
        }

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            super.onRender(ellapsedRealtime, deltaTime);
            mSphere.setRotY(mSphere.getRotY() + 1);
        }

        @Override
        public void onOffsetsChanged(float v, float v1, float v2, float v3, int i, int i1) {

        }

        @Override
        public void onTouchEvent(MotionEvent motionEvent) {

        }
    }

}
