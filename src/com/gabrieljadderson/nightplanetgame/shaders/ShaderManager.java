package com.gabrieljadderson.nightplanetgame.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.HashMap;

/**
 * @author Gabriel Jadderson
 */
public class ShaderManager
{
	
	public static HashMap<String, ShaderProgram> shaders = new HashMap<>();//holds all shaderprograms, a shaderprogram consists of a vertex shader and a fragment shader.
	public static HashMap<String, String> shaders_vert = new HashMap<>();//holds ALL loaded vertex shader files.
	public static HashMap<String, String> shaders_frag = new HashMap<>();//holds ALL loaded fragment shader files.
	
	public void createShaderProgram(ShaderDescriptor shaderDescriptor)
	{
		assert shaderDescriptor != null && shaderDescriptor.ShaderProgramName != null && shaderDescriptor.Shader_Fragment_Name != null && shaderDescriptor.Shader_Vertex_Name != null;
		shaders.put(shaderDescriptor.ShaderProgramName, new ShaderProgram(shaders_vert.get(shaderDescriptor.Shader_Vertex_Name), shaders_frag.get(shaderDescriptor.Shader_Fragment_Name)));
	}
	
	
	public static ShaderProgram get(String shadername)
	{
		return shaders.get(shadername);
	}
}
