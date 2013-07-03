package com.monyetmabuk.rajawali.tutorials.materials;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;

/**
 * This example shows how to use text drawn to a {@link Canvas} which gets rendered
 * to a {@link Bitmap} and is then used as an {@link AlphaMapTexture} on a rotating sphere.
 * 
 * The example also shows how to do this frequently and how to update textures.
 * 
 * @author dennis.ippel
 *
 */
public class CanvasTextRenderer extends RajawaliRenderer {
	private AlphaMapTexture mTimeTexture;
	private Bitmap mTimeBitmap;
	private Canvas mTimeCanvas;
	private Paint mTextPaint;
	private SimpleDateFormat mDateFormat;
	private int mFrameCount;
	private boolean mShouldUpdateTexture;
	
	public CanvasTextRenderer(Context context)
	{
		super(context);
	}
	
	public void initScene()
	{
		DirectionalLight light = new DirectionalLight(.1f, .1f, -1);
		
		DiffuseMaterial timeSphereMaterial = new DiffuseMaterial();
		timeSphereMaterial.setAlphaMaskingEnabled(true);
		mTimeBitmap = Bitmap.createBitmap(256, 256, Config.ARGB_8888);
		mTimeTexture = new AlphaMapTexture("timeTexture", mTimeBitmap);
		try {
			timeSphereMaterial.addTexture(mTimeTexture);
		} catch (TextureException e) {
			e.printStackTrace();
		}

		Sphere parentSphere = null;
		
		for(int i=0; i<20; i++)
		{
			Sphere timeSphere = new Sphere(.6f, 12,	12);
			timeSphere.setMaterial(timeSphereMaterial);
			timeSphere.addLight(light);
			timeSphere.setDoubleSided(true);
			
			if(parentSphere == null)
			{
				timeSphere.setPosition(0, 0, -3);
				timeSphere.setRenderChildrenAsBatch(true);
				getCurrentScene().addChild(timeSphere);
				parentSphere = timeSphere;
			}
			else
			{
				timeSphere.setX(-3 + (float)(Math.random() * 6));
				timeSphere.setY(-3 + (float)(Math.random() * 6));
				timeSphere.setZ(-3 + (float)(Math.random() * 6));
				parentSphere.addChild(timeSphere);
			}
			
			int direction = Math.random() < .5 ? 1 : -1;
			
			RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, 0, 360 * direction);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setDuration(i == 0 ? 12000 : 4000 + (int)(Math.random() * 4000));
			anim.setTransformable3D(timeSphere);
			registerAnimation(anim);
			anim.play();
		}
	}
	
	public void updateTimeBitmap()
	{
		new Thread(new Runnable() {
	        public void run() {
	    		if(mTimeCanvas == null)
	    		{
	    			
	    			mTimeCanvas = new Canvas(mTimeBitmap);
	    			mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    			mTextPaint.setColor(Color.WHITE);
	    			mTextPaint.setTextSize(35);
	    			mDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
	    		}
	    		//
	    		// -- Clear the canvas, transparent
	    		//
	    		mTimeCanvas.drawColor(0, Mode.CLEAR);
	    		//
	    		// -- Draw the time on the canvas
	    		//	    		
	    		mTimeCanvas.drawText(mDateFormat.format(new Date()), 75, 128, mTextPaint);
	    		//
	    		// -- Indicates that the texture should be updated on the OpenGL thread.
	    		//
	    		mShouldUpdateTexture = true;
	        }
	    }).start();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		//
		// -- not a really accurate way of doing things but you get the point :)
		//
		if(mFrameCount++ >= mFrameRate)
		{
			mFrameCount = 0;
			updateTimeBitmap();
		}
		//
		// -- update the texture because it is ready
		//
		if(mShouldUpdateTexture)
		{
			mTimeTexture.setBitmap(mTimeBitmap);
			mTextureManager.replaceTexture(mTimeTexture);
			mShouldUpdateTexture = false;
		}
		super.onDrawFrame(glUnused);
	}
}
