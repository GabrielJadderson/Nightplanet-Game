package com.gabrieljadderson.nightplanetgame.serialization.build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gabrieljadderson.nightplanetgame.serialization.SerializationFilter;
import com.gabrieljadderson.nightplanetgame.serialization.TokenSerializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The serializer that will serialize and deserialize character files for
 * players.
 * <p>
 * <p>
 * Serialization of character files can and should be done on another thread
 * whenever possible to avoid doing disk I/O on the main game thread.
 *
 * @author Gabriel Jadderson
 */
public final class BuildSerialization
{
	
	/**
	 * the application directory in docs. TODO: fix this to be more compact and not create a file in documents.
	 */
	private static final String DIR = ("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/config/");
	
	
	/**
	 * The linked hash collection of tokens that will be serialized and
	 * deserialized. A linked hash set is used here to ensure that there is only
	 * one of each token, and to preserve order.
	 */
	private final Set<TokenSerializer> tokens = new LinkedHashSet<>();
	
	
	/**
	 * The player this serializer is dedicated to.
	 */
	private final BuildTokens bt;
	
	/**
	 * The character file that corresponds to this player.
	 */
	private final File bf;
	
	/**
	 * Creates a new {@link PlayerSerialization}.
	 *
	 * @param player the player this serializer is dedicated to.
	 */
	public BuildSerialization(BuildTokens bt)
	{
		this.bt = bt;
		this.bf = Paths.get(DIR + "GAMEBUILD" + ".json").toFile();
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
		BuildTokens u = bt;
		
		tokens.add(new TokenSerializer("title", u.getGameTitle(), n -> u.setGameTitle(n.getAsString())));
		tokens.add(new TokenSerializer("version", u.getVersion(), n -> u.setVersion(n.getAsString())));
		tokens.add(new TokenSerializer("build", u.getBuild(), n -> u.setBuild(n.getAsInt())));

//        tokens.add(new TokenSerializer("id", u.getID(), n -> u.setID(n.getAsString())));
//        tokens.add(new TokenSerializer("name", u.getName(), n -> u.setName(n.getAsString())));
//        tokens.add(new TokenSerializer("email", u.getEmail(), n -> u.setEmail(n.getAsString())));
//        tokens.add(new TokenSerializer("premium", u.isPremium(), n -> u.setPremium(n.getAsBoolean())));
//        tokens.add(new TokenSerializer("password", u.getPassword(), n -> u.setPassword(b.fromJson(n, byte[].class))));
//        
//        tokensSalt.add(new TokenSerializer("id", u.getID(), n -> u.setID(n.getAsString())));
//        tokensSalt.add(new TokenSerializer("salt", u.getSalt(), n -> u.setSalt(b.fromJson(n, byte[].class))));
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
	public void deserialize()
	{
		try
		{
			if (!bf.exists())
			{
//                Skills.create(player);
//                return LoginResponse.NORMAL;
				//new user...
//            	System.out.println("uf does not exist and is new?");
			} else
			{
				//existing user?
//            	System.out.println("uf exists");
			}
			
			bf.setReadable(true);
			try (FileReader in = new FileReader(bf))
			{
				JsonObject reader = (JsonObject) new JsonParser().parse(in);
				tokens.stream().filter(t -> reader.has(t.getName())).forEach(t -> t.getFromJson().accept(reader.get(t.getName())));
			}
//            if (!password.equals(player.getPassword()))
//                return LoginResponse.INVALID_CREDENTIALS;
//            if (player.isBanned())
//                return LoginResponse.ACCOUNT_DISABLED;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean doesBuildFileExist()
	{
		File buildFile = new File(DIR + "GAMEBUILD" + ".json");
		if (buildFile.isFile())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
}
