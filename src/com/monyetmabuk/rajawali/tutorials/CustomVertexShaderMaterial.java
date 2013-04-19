package com.monyetmabuk.rajawali.tutorials;

import rajawali.materials.SimpleMaterial;
import android.opengl.GLES20;

public class CustomVertexShaderMaterial extends SimpleMaterial {
	
	protected int muTimeHandle;
	protected float mTime;
	
	public CustomVertexShaderMaterial() {
		super(R.raw.custom_vertex_shader_material, R.raw.simple_material_fragment);
	}
	
	public CustomVertexShaderMaterial(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}
	
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muTimeHandle = getUniformLocation("uTime");
	}
	
	public void useProgram() {
		super.useProgram();
		// -- make sure that time updates every frame
		GLES20.glUniform1f(muTimeHandle, mTime);
	}
	
	public void setTime(float time) {
		mTime = time;
	}
}
