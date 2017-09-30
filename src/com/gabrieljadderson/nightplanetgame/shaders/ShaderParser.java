package com.gabrieljadderson.nightplanetgame.shaders;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceLoader;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

/**
 * Copyright (c) 2016-onwards NightPlanet /vGabriel Howard Jadderson
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * <p>
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p>
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * * Neither the name of 'NightPlanet' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO:
 * @since 26/03/2013
 */
public class ShaderParser
{
	
	public static final String SHADER_CONFIG_PATH = new ResourceLoader().getAbsoluteResourcePath("data/shaders/SHADER_CONFIG.json");
	
	
	public ShaderParser()
	{
		deserialize();
	}
	
	
	public void deserialize()
	{
		Gson gson = new Gson();
		ShaderDescriptor[] arr = null;
		try
		{
			JsonReader reader = new JsonReader(new FileReader(SHADER_CONFIG_PATH));
			arr = gson.fromJson(reader, ShaderDescriptor[].class);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		assert arr != null;
		for (ShaderDescriptor shaderDescriptor : arr)
		{
			GameConstants.shaderManager.createShaderProgram(shaderDescriptor);
		}
		
	}
	
	
}
