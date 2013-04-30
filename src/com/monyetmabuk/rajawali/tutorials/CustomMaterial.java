package com.monyetmabuk.rajawali.tutorials;

import android.opengl.GLES20;
import rajawali.materials.SimpleMaterial;

public class CustomMaterial extends SimpleMaterial {
	
	protected int muTimeHandle;
	
	public CustomMaterial() {
		super(com.monyetmabuk.livewallpapers.photosdof.R.raw.simple_material_vertex, R.raw.custom_material_fragment);
		setShaders();
	}
	
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muTimeHandle = GLES20.glGetUniformLocation(mProgram, "uTime");
		if(muTimeHandle == -1) {
			throw new RuntimeException("Could not get uniform location for uTime");
		}
	}
	
	public void setTime(float time) {
		GLES20.glUniform1f(muTimeHandle, time);
	}
}
