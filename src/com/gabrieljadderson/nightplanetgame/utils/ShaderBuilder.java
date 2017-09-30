package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gabrieljadderson.nightplanetgame.GameConstants;

//TODO: DELETE

/**
 * @author Gabriel Jadderson
 */
public class ShaderBuilder
{
	
	public static final String V_TEXCOORD = "vTexCoord";
	public static final String V_COLOR = "vColor";
	
	
	public static ShaderProgram newShader(String vert, String frag)
	{
		ShaderProgram shader = new ShaderProgram(vert, frag);
		if (!shader.isCompiled())
			throw new GdxRuntimeException("could not compile shader" + shader.getLog());
		if (shader.getLog().length() != 0)
			Gdx.app.log("ShaderUtil", "Shader Log: " + shader.getLog());
		return shader;
	}
	
	public static ShaderProgram newTextureShader(int numTexCoords, String frag)
	{
		String vert = buildVertexShaderSource(numTexCoords);
		return newShader(vert, frag);
	}
	
	public static ShaderProgram newTextureShader(String frag)
	{
		return newTextureShader(1, frag);
	}
	
	public static ShaderProgram newSimpleShader(int numTexCoords, String fragMain, String header)
	{
		String str =
				"#ifdef GL_ES\n" //
						+ "#define LOWP lowp\n" //
						+ "precision mediump float;\n" //
						+ "#else\n" //
						+ "#define LOWP \n" //
						+ "#endif\n\n" //
						+ "varying vec4 " + V_COLOR + ";\n\n";
		for (int i = 0; i < numTexCoords; i++)
			str += "varying vec2 " + V_TEXCOORD + i + ";\n";
		str += "\n";
		for (int i = 0; i < numTexCoords; i++)
			str += "uniform sampler2D " + GenericBatch.U_TEXTURE + i + ";\n";
		str += "\n";
		if (header != null && header.length() != 0)
			str += header + "\n";
		str +=
				"void main(void)\n" +
						"{\n";
		for (int i = 0; i < numTexCoords; i++)
		{
			str += "   vec4 texColor" + i + " = texture2D(" + GenericBatch.U_TEXTURE + i + ", " + V_TEXCOORD + i + ");\n";
		}
		str += fragMain;
		str += "}";
		return newTextureShader(numTexCoords, str);
	}
	
	public static ShaderProgram newSimpleShader(int numTexCoords, String fragMain)
	{
		return newSimpleShader(numTexCoords, fragMain, null);
	}
	
	public static ShaderProgram newSimpleShader(String fragMain)
	{
		return newSimpleShader(1, fragMain);
	}
	
	public static String buildVertexShaderSource(int numTexCoords)
	{
		String str = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
				"attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n";
		for (int i = 0; i < numTexCoords; i++)
			str += "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + i + ";\n";
		str += "\n";
		str += "uniform mat4 " + GameConstants.camera.projection + ";\n";
		str += "varying vec4 " + V_COLOR + ";\n";
		for (int i = 0; i < numTexCoords; i++)
			str += "varying vec2 " + V_TEXCOORD + i + ";\n";
		str += "\nvoid main() {\n";
		str += "  " + V_COLOR + " = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n";
		for (int i = 0; i < numTexCoords; i++)
			str += "  " + V_TEXCOORD + i + " = " + ShaderProgram.TEXCOORD_ATTRIBUTE + i + ";\n";
		str += "  gl_Position = " + GameConstants.camera.projection + " * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n";
		str += "}";
		return str;
	}
	
}