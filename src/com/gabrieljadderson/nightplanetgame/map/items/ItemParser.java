package com.gabrieljadderson.nightplanetgame.map.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.gabrieljadderson.nightplanetgame.ResourceLoader;
import com.gabrieljadderson.nightplanetgame.serialization.SerializationFilter;
import com.gabrieljadderson.nightplanetgame.serialization.TokenSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * * Neither the name of 'Shaven Puppy' nor the names of its contributors
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
 * @author Gabriel Howard Jadderson
 * @version 0.1
 * TODO:
 * @since 25/01/2016
 */
public class ItemParser
{
	
	
	/**
	 * the application directory in docs. TODO: fix this to be more compact and not create a file in documents.
	 */
	public static String DIR = new ResourceLoader().getAbsoluteResourcePath("data/items/");
	
	
	/**
	 * The linked hash collection of tokens that will be serialized and
	 * deserialized. A linked hash set is used here to ensure that there is only
	 * one of each token, and to preserve order.
	 */
	private final Set<TokenSerializer> tokens = new LinkedHashSet<>();
	
	
	/**
	 * The player this serializer is dedicated to.
	 */
	private final ItemDefinition bt;
	
	/**
	 * The character file that corresponds to this player.
	 */
	private final File bf;
	
	/**
	 * Creates a new {@link PlayerSerialization}.
	 *
	 * @param player the player this serializer is dedicated to.
	 */
	public ItemParser(int id)
	{
		this.bt = new ItemDefinition();
		this.bf = Paths.get(DIR + "/" + id + ".json").toFile();
		createTokens();
	}
	
	/**
	 * The function where all of the tokens are added to the linked hash
	 * collection. Add as many tokens here as needed but keep in mind tokens
	 * cannot have the same name.
	 * <p>
	 * The token serialization format is as follows:
	 * <p>
	 * <p>
	 * <p>
	 * <pre>
	 * tokens.add(new TokenSerializer(NAME_OF_TOKEN, SERIALIZATION, DESERIALIZATION));
	 * </pre>
	 * <p>
	 * For those who are still confused, here is an example. Lets say we want
	 * "deathCount" to be saved to and loaded from the character file:
	 * <p>
	 * <p>
	 * <p>
	 * <pre>
	 * private int deathCount;
	 *
	 * public void setDeathCount(int deathCount) {
	 *     this.deathCount = deathCount;
	 * }
	 *
	 * public int getDeathCount() {
	 *     return deathCount;
	 * }
	 * </pre>
	 * <p>
	 * We would be able to do it like this:
	 * <p>
	 * <p>
	 * <p>
	 * <pre>
	 * tokens.add(new TokenSerializer(&quot;death-count&quot;, player.getDeathCount(), n -&gt; player.setDeathCount(n.getAsInt())));
	 * </pre>
	 */
	private void createTokens()
	{
		Gson b = new GsonBuilder().create();
		ItemDefinition u = bt;

//		tokens.add(new TokenSerializer("title", u.getGameTitle(), n -> u.setGameTitle(n.getAsString())));
//		tokens.add(new TokenSerializer("version", u.getVersion(), n -> u.setVersion(n.getAsString())));
//		tokens.add(new TokenSerializer("build", u.getBuild(), n -> u.setBuild(n.getAsInt())));
		
		tokens.add(new TokenSerializer("id", u.getId(), n -> u.setId(n.getAsInt())));
		tokens.add(new TokenSerializer("name", u.getName(), n -> u.setName(n.getAsString())));
		tokens.add(new TokenSerializer("description", u.getDescription(), n -> u.setDescription(n.getAsString())));
		tokens.add(new TokenSerializer("quality", u.getQuality(), n -> u.setQuality(n.getAsInt())));
		tokens.add(new TokenSerializer("position X", u.getPositionX(), n -> u.setPositionX(n.getAsInt())));
		tokens.add(new TokenSerializer("position Y", u.getPositionY(), n -> u.setPositionY(n.getAsInt())));
		tokens.add(new TokenSerializer("bodyType", u.getBodyType(), n -> u.setBodyType(b.fromJson(n, BodyType.class))));
		tokens.add(new TokenSerializer("sprite", u.getSprite(), n -> u.setSprite(b.fromJson(n, TextureRegion.class))));
		tokens.add(new TokenSerializer("lightDistance", u.getLightDistance(), n -> u.setLightDistance(n.getAsFloat())));
		tokens.add(new TokenSerializer("spritePath", u.getSpritePath(), n -> u.setSpritePath(n.getAsString())));
		tokens.add(new TokenSerializer("spriteRevision", u.getSpriteRevision(), n -> u.setSpriteRevision(n.getAsInt())));
		
		
		//	        tokens.add(new TokenSerializer("id", u.getID(), n -> u.setID(n.getAsString())));
		//	        tokens.add(new TokenSerializer("name", u.getName(), n -> u.setName(n.getAsString())));
		//	        tokens.add(new TokenSerializer("email", u.getEmail(), n -> u.setEmail(n.getAsString())));
		//	        tokens.add(new TokenSerializer("premium", u.isPremium(), n -> u.setPremium(n.getAsBoolean())));
		//	        tokens.add(new TokenSerializer("password", u.getPassword(), n -> u.setPassword(b.fromJson(n, byte[].class))));
		//	        
		//	        tokensSalt.add(new TokenSerializer("id", u.getID(), n -> u.setID(n.getAsString())));
		//	        tokensSalt.add(new TokenSerializer("salt", u.getSalt(), n -> u.setSalt(b.fromJson(n, byte[].class))));
	}
	
	/**
	 * Serializes the dedicated player into a {@code JSON} file.
	 */
	public void serialize()
	{
		try
		{
			bf.getParentFile().setWritable(true);
			if (!bf.getParentFile().exists())
			{
				try
				{
					bf.getParentFile().mkdirs();
				} catch (SecurityException e)
				{
					throw new IllegalStateException("Unable to create " + "directory for users!");
				}
			}
			try (FileWriter outuser = new FileWriter(bf))
			{
				Gson gson = new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(new SerializationFilter()).create();
				JsonObject obj = new JsonObject();
				tokens.stream().forEach(t -> obj.add(t.getName(), gson.toJsonTree(t.getToJson())));
				outuser.write(gson.toJson(obj));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Deserializes the dedicated player from a {@code JSON} file.
	 *
	 * @param password the password that will be used to validate if the player has
	 *                 the right credentials.
	 * @return the login response determined by what happened before, during,
	 * and after deserialization.
	 */
	public ItemDefinition deserialize()
	{
		try
		{
			bf.setReadable(true);
			try (FileReader in = new FileReader(bf))
			{
				JsonObject reader = (JsonObject) new JsonParser().parse(in);
				tokens.stream().filter(t -> reader.has(t.getName())).forEach(t -> t.getFromJson().accept(reader.get(t.getName())));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bt;
	}
	
	public static boolean doesBuildFileExist(int id)
	{
		File buildFile = new File(DIR + id + ".json");
		if (buildFile.isFile())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	
}
