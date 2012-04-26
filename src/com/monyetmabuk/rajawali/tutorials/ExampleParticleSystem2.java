package com.monyetmabuk.rajawali.tutorials;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import rajawali.Camera;
import rajawali.Geometry3D;
import rajawali.materials.ParticleMaterial;
import rajawali.math.Number3D;
import rajawali.primitives.Particle;
import rajawali.util.ObjectColorPicker.ColorPickerInfo;
import android.opengl.GLES20;

public class ExampleParticleSystem2 extends Particle {
	protected Number3D mFriction;
	protected FloatBuffer mVelocityBuffer;
	protected FloatBuffer mAnimOffsetBuffer;
	protected float mTime;
	protected int mCurrentFrame;
	
	public ExampleParticleSystem2() {
		super();
	}
	
	protected void init() {
		mMaterial = new ParticleMaterial(true);
		mParticleShader = (ParticleMaterial)mMaterial;
		setDrawingMode(GLES20.GL_POINTS);
		setTransparent(true);
		
		final int numParticles = 200;
		
		float[] vertices = new float[numParticles * 3];
		float[] velocity = new float[numParticles * 3];
		float[] textureCoords = new float[numParticles * 2];
		float[] normals = new float[numParticles * 3];
		float[] colors = new float[numParticles * 4];
		short[] indices = new short[numParticles];
		float[] animOffsets = new float[numParticles];
		
		int index = 0;
		
		for(int i=0; i<numParticles; ++i) {
			index = i * 3;
			vertices[index] = 0;
			vertices[index + 1] = 0;
			vertices[index + 2] = 0;
			
			velocity[index] = -.2f + ((float)Math.random() * .4f);
			velocity[index + 1] = -.2f + ((float)Math.random() * .4f);
			velocity[index + 2] = -.2f + ((float)Math.random() * .4f);
			
			normals[index] = 0;
			normals[index + 1] = 0;
			normals[index + 2] = 1;
			
			index = i * 2;
			textureCoords[i] = 0;
			textureCoords[i + 1] = 0;
			
			index = i * 4;
			colors[i] = 1;
			colors[i + 1] = i;
			colors[i + 2] = i;
			colors[i + 3] = i;
			
			indices[i] = (short)i;
			
			animOffsets[i] = (float)Math.floor(Math.random() * 64);
		}
		
		mVelocityBuffer = ByteBuffer
				.allocateDirect(velocity.length * Geometry3D.FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mVelocityBuffer.put(velocity).position(0);
		
		mAnimOffsetBuffer = ByteBuffer
				.allocateDirect(animOffsets.length * Geometry3D.FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mAnimOffsetBuffer.put(animOffsets).position(0);
		
		mFriction = new Number3D(.95f, .95f, .95f);
		
		setData(vertices, normals, textureCoords, colors, indices);
	}
	
	public void setTime(float time) {
		mTime = time;
	}
	
	public float getTime() {
		return mTime;
	}
	
	public void setCurrentFrame(int currentFrame) {
		mCurrentFrame = currentFrame;
	}
	
	protected void setShaderParams(Camera camera) {
		super.setShaderParams(camera);
		mParticleShader.setCurrentFrame(mCurrentFrame);
		mParticleShader.setTileSize(1 / 8f);
		mParticleShader.setNumTileRows(8);
		mParticleShader.setAnimOffsets(mAnimOffsetBuffer);
		mParticleShader.setFriction(mFriction);
		mParticleShader.setVelocity(mVelocityBuffer);
		mParticleShader.setMultiParticlesEnabled(true);
		mParticleShader.setTime(mTime);
	}
	
	public void render(Camera camera, float[] projMatrix, float[] vMatrix,
			final float[] parentMatrix, ColorPickerInfo pickerInfo) {
		super.render(camera, projMatrix, vMatrix, parentMatrix, pickerInfo);
	}
}
