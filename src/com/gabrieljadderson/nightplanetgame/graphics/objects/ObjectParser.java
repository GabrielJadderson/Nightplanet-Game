package com.gabrieljadderson.nightplanetgame.graphics.objects;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.serialization.SerializationFilter;
import com.gabrieljadderson.nightplanetgame.serialization.TokenSerializer;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.Position;
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
 * @author Gabriel Jadderson
 */
public class ObjectParser
{
	
	
	/**
	 * the application directory in docs. TODO: fix this to be more compact and not create a file in documents.
	 */
	public static final String DIR = GameConstants.resourceLoader.getAbsoluteResourcePath("data/objects/");
	
	
	/**
	 * The linked hash collection of tokens that will be serialized and
	 * deserialized. A linked hash set is used here to ensure that there is only
	 * one of each token, and to preserve order.
	 */
	private final Set<TokenSerializer> tokens = new LinkedHashSet<>();
	
	
	/**
	 * The player this serializer is dedicated to.
	 */
	public final ObjectDefinition bt;
	
	/**
	 * The character file that corresponds to this player.
	 */
	private final File bf;
	
	/**
	 * Creates a new {@link PlayerSerialization}.
	 *
	 * @param player the player this serializer is dedicated to.
	 */
	public ObjectParser(int id)
	{
		this.bt = new ObjectDefinition();
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
		ObjectDefinition u = bt;
		
		tokens.add(new TokenSerializer("id", u.getId(), n -> u.setId(n.getAsInt())));
		tokens.add(new TokenSerializer("name", u.getName(), n -> u.setName(n.getAsString())));
		tokens.add(new TokenSerializer("position", u.getPosition(), n -> u.setPosition(b.fromJson(n, Position.class))));
		tokens.add(new TokenSerializer("size", u.getSize(), n -> u.setSize(b.fromJson(n, Dimension.class))));
		tokens.add(new TokenSerializer("isBodyObject", u.isBodyObject(), n -> u.setBodyObject(n.getAsBoolean())));
		tokens.add(new TokenSerializer("hasPremultipliedAlpha", u.hasPremultipliedAlpha(), n -> u.setPremultipliedAlpha(n.getAsBoolean())));
		
		tokens.add(new TokenSerializer("scale", u.getScale(), n -> u.setScale(n.getAsFloat())));
		tokens.add(new TokenSerializer("animationSpeed", u.getAnimationSpeed(), n -> u.setAnimationSpeed(n.getAsFloat())));
		
		tokens.add(new TokenSerializer("animationName", u.getAnimationName(), n -> u.setAnimationName(n.getAsString())));
		tokens.add(new TokenSerializer("loopTheAnimation", u.isAnimationLooped(), n -> u.setAnimationLooped(n.getAsBoolean())));
		
		tokens.add(new TokenSerializer("pathAtlas", u.getPathAtlas(), n -> u.setPathAtlas(n.getAsString())));
		tokens.add(new TokenSerializer("pathJson", u.getPathJson(), n -> u.setPathJson(n.getAsString())));
		tokens.add(new TokenSerializer("pathSFX", u.getPathSFX(), n -> u.setPathSFX(n.getAsString())));
		tokens.add(new TokenSerializer("volumeSFX", u.getVolumeSFX(), n -> u.setVolumeSFX(n.getAsFloat())));
		tokens.add(new TokenSerializer("pathSFX2", u.getPathSFX2(), n -> u.setPathSFX2(n.getAsString())));
		tokens.add(new TokenSerializer("volumeSFX2", u.getVolumeSFX2(), n -> u.setVolumeSFX2(n.getAsFloat())));
		tokens.add(new TokenSerializer("pathSFX3", u.getPathSFX3(), n -> u.setPathSFX3(n.getAsString())));
		tokens.add(new TokenSerializer("volumeSFX3", u.getVolumeSFX3(), n -> u.setVolumeSFX3(n.getAsFloat())));
		
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
	public ObjectDefinition deserialize()
	{
		try
		{
			if (!bf.exists())
			{
				//	                Skills.create(player);
				//	                return LoginResponse.NORMAL;
				//new user...
				//	            	System.out.println("uf does not exist and is new?");
			} else
			{
				//existing user?
				//	            	System.out.println("uf exists");
			}
			
			bf.setReadable(true);
			try (FileReader in = new FileReader(bf))
			{
				JsonObject reader = (JsonObject) new JsonParser().parse(in);
				tokens.stream().filter(t -> reader.has(t.getName())).forEach(t -> t.getFromJson().accept(reader.get(t.getName())));
			}
			//	            if (!password.equals(player.getPassword()))
			//	                return LoginResponse.INVALID_CREDENTIALS;
			//	            if (player.isBanned())
			//	                return LoginResponse.ACCOUNT_DISABLED;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bt;
	}
	
	
}
