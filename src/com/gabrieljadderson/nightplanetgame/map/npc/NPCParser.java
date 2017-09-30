package com.gabrieljadderson.nightplanetgame.map.npc;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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
 * @version 0.1
 * TODO:
 * @since 25/01/2016
 */
public class NPCParser
{
	
	
	/**
	 * the application directory in docs. TODO: fix this to be more compact and not create a file in documents.
	 */
	public static final String DIR = GameConstants.resourceLoader.getAbsoluteResourcePath("data/npcs/");
	
	
	/**
	 * The linked hash collection of tokens that will be serialized and
	 * deserialized. A linked hash set is used here to ensure that there is only
	 * one of each token, and to preserve order.
	 */
	private final Set<TokenSerializer> tokens = new LinkedHashSet<>();
	
	
	/**
	 * The player this serializer is dedicated to.
	 */
	public final NpcDefinition bt;
	
	/**
	 * The character file that corresponds to this player.
	 */
	private final File bf;
	
	/**
	 * Creates a new {@link PlayerSerialization}.
	 *
	 * @param player the player this serializer is dedicated to.
	 */
	public NPCParser(int id)
	{
		this.bt = new NpcDefinition();
		this.bf = Paths.get(DIR + "/" + id + ".json").toFile();
		System.out.println("NPCParser: " + this.bf);
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
		NpcDefinition u = bt;
		
		
		//general 
		tokens.add(new TokenSerializer("id", u.getId(), n -> u.setId(n.getAsInt())));
		tokens.add(new TokenSerializer("name", u.getName(), n -> u.setName(n.getAsString())));
		tokens.add(new TokenSerializer("size", u.getSize(), n -> u.setSize(n.getAsFloat())));
		tokens.add(new TokenSerializer("position", u.getPosition(), n -> u.setPosition(b.fromJson(n, Position.class))));
		tokens.add(new TokenSerializer("dimension", u.getDimension(), n -> u.setDimension(b.fromJson(n, Dimension.class))));
		tokens.add(new TokenSerializer("updateSpeed", u.getUpdateSpeed(), n -> u.setUpdateSpeed(n.getAsFloat())));
		
		//npc constants
		tokens.add(new TokenSerializer("combatLevel", u.getCombatLevel(), n -> u.setCombatLevel(n.getAsInt())));
		tokens.add(new TokenSerializer("boss", u.isBoss(), n -> u.setBoss(n.getAsBoolean())));
		
		tokens.add(new TokenSerializer("attackable", u.isAttackable(), n -> u.setAttackable(n.getAsBoolean())));
		tokens.add(new TokenSerializer("agressive", u.isAggressive(), n -> u.setAggressive(n.getAsBoolean())));
		tokens.add(new TokenSerializer("poisonous", u.isPoisonous(), n -> u.setPoisonous(n.getAsBoolean())));
		tokens.add(new TokenSerializer("respawns", u.Respawns(), n -> u.setRespawns(n.getAsBoolean())));
		tokens.add(new TokenSerializer("retreats", u.Retreats(), n -> u.setRetreats(n.getAsBoolean())));
		
		tokens.add(new TokenSerializer("attackSpeed", u.getAttackSpeed(), n -> u.setAttackSpeed(n.getAsFloat())));
		tokens.add(new TokenSerializer("attackBonus", u.getAttackBonus(), n -> u.setAttackBonus(n.getAsFloat())));
		
		tokens.add(new TokenSerializer("magicDefence", u.getMagicDefence(), n -> u.setMagicDefence(n.getAsFloat())));
		tokens.add(new TokenSerializer("meleeDefence", u.getMeleeDefence(), n -> u.setMeleeDefence(n.getAsFloat())));
		tokens.add(new TokenSerializer("rangedDefence", u.getRangedDefence(), n -> u.setRangedDefence(n.getAsFloat())));
		
		tokens.add(new TokenSerializer("maxHealth", u.getMaxHealth(), n -> u.setMaxHealth(n.getAsFloat())));
		tokens.add(new TokenSerializer("maxHit", u.getMaxHit(), n -> u.setMaxHit(n.getAsFloat())));
		
		tokens.add(new TokenSerializer("respawnTime", u.getRespawnTime(), n -> u.setRespawnTime(n.getAsFloat())));
		
		
		//Spine
		tokens.add(new TokenSerializer("attackAnimation", u.getAttackAnimation(), n -> u.setAttackAnimation(n.getAsString())));
		tokens.add(new TokenSerializer("deathAnimation", u.getDeathAnimation(), n -> u.setDeathAnimation(n.getAsString())));
		tokens.add(new TokenSerializer("idleAnimation", u.getIdleAnimation(), n -> u.setIdleAnimation(n.getAsString())));
		tokens.add(new TokenSerializer("defenceAnimation", u.getDefenceAnimation(), n -> u.setDefenceAnimation(n.getAsString())));
		tokens.add(new TokenSerializer("skeletonAtlasPath", u.getSkeletonAtlasPath(), n -> u.setSkeletonAtlasPath(n.getAsString())));
		tokens.add(new TokenSerializer("skeletonJsonPath", u.getSkeletonJsonPath(), n -> u.setSkeletonJsonPath(n.getAsString())));
		
		
		//body
		tokens.add(new TokenSerializer("bodyType", u.getBodyType(), n -> u.setBodyType(b.fromJson(n, BodyType.class))));
		tokens.add(new TokenSerializer("density", u.getDensity(), n -> u.setDensity(n.getAsFloat())));
		tokens.add(new TokenSerializer("friction", u.getFriction(), n -> u.setFriction(n.getAsFloat())));
		tokens.add(new TokenSerializer("restitution", u.getRestitution(), n -> u.setRestitution(n.getAsFloat())));
		tokens.add(new TokenSerializer("fixedRotation", u.isFixedRotation(), n -> u.setFixedRotation(n.getAsBoolean())));
		tokens.add(new TokenSerializer("isSensor", u.isSensor(), n -> u.setSensor(n.getAsBoolean())));
		tokens.add(new TokenSerializer("bullet", u.isBullet(), n -> u.setBullet(n.getAsBoolean())));
		tokens.add(new TokenSerializer("airborne", u.isAirborne(), n -> u.setAirborne(n.getAsBoolean())));
		tokens.add(new TokenSerializer("active", u.isActive(), n -> u.setActive(n.getAsBoolean())));
		tokens.add(new TokenSerializer("allowsleep", u.canSleep(), n -> u.setSleep(n.getAsBoolean())));
		tokens.add(new TokenSerializer("awake", u.isAwake(), n -> u.setAwake(n.getAsBoolean())));
		tokens.add(new TokenSerializer("independentFacing", u.hasIndependentFacing(), n -> u.setIndependentFacing(n.getAsBoolean())));
		tokens.add(new TokenSerializer("targetIsPlayer", u.TargetIsPlayer(), n -> u.setTargetIsPlayer(n.getAsBoolean())));
		tokens.add(new TokenSerializer("targetID", u.getTargetId(), n -> u.setTargetId(n.getAsInt())));
		tokens.add(new TokenSerializer("zeroLinearSpeed", u.getZeroLinearSpeedThreshold(), n -> u.setZeroLinearSpeedThreshold(n.getAsFloat())));
		tokens.add(new TokenSerializer("maxAngularAcceleration", u.getMaxAngularAcceleration(), n -> u.setMaxAngularAcceleration(n.getAsFloat())));
		tokens.add(new TokenSerializer("maxAngularSpeed", u.getMaxAngularSpeed(), n -> u.setMaxAngularSpeed(n.getAsFloat())));
		tokens.add(new TokenSerializer("maxLinearAcceleration", u.getMaxLinearAcceleration(), n -> u.setMaxLinearAcceleration(n.getAsFloat())));
		tokens.add(new TokenSerializer("maxLinearSpeed", u.getMaxLinearSpeed(), n -> u.setMaxLinearSpeed(n.getAsFloat())));
		
		tokens.add(new TokenSerializer("AttackSFX_ID", u.getAttackSoundPath(), n -> u.setAttackSoundPath(n.getAsString())));
		tokens.add(new TokenSerializer("DefenceSFX_ID", u.getDefenceSoundPath(), n -> u.setDefenceSoundPath(n.getAsString())));
		tokens.add(new TokenSerializer("DeathSFX_ID", u.getDeathSoundPath(), n -> u.setDeathSoundPath(n.getAsString())));
		
		
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
	public NpcDefinition deserialize()
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


