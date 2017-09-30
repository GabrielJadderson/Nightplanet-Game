package com.gabrieljadderson.nightplanetgame.map.npc;

import box2dLight.Light;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.gabrieljadderson.nightplanetgame.tests.ai.NPCAI;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.Position;
import com.gabrieljadderson.nightplanetgame.spine.*;

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
 * The container that represents an NPC definition.
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO: MAKE THIS NPC EXTEND NODE!!
 * @since 26/01/2016
 */
public class NpcDefinition
{
	
	/**
	 * The identification for this NPC.
	 */
	private int id;
	
	/**
	 * The name of this NPC.
	 */
	private String name;
	
	/**
	 * The combat level of this NPC.
	 */
	private int combatLevel;
	
	/**
	 * The size of this NPC. used to scale up and down.
	 */
	private float size;
	
	/**
	 * The Path to the textureAtlas
	 */
	private String skeletonAtlasPath;
	
	/**
	 * the path to the json of the skeleton
	 */
	private String skeletonJsonPath;
	
	/**
	 * the AI Controller of the NPC
	 */
	private NPCAI AI;
	
	/**
	 * independantFacing stops the AI from adjusting the angle of this NPC
	 */
	private boolean independentFacing;
	
	/**
	 * the Max Angular Acceleration
	 */
	private float maxAngularAcceleration;
	
	/**
	 * the Max Angular Speed
	 */
	private float maxAngularSpeed;
	
	/**
	 * the Max Linear Acceleration
	 */
	private float maxLinearAcceleration;
	
	/**
	 * the Max Linear Speed
	 */
	private float maxLinearSpeed;
	
	/**
	 * the Zero Linear Speed Threshold, e.g. minimum linear speed.
	 */
	private float zeroLinearSpeedThreshold;
	
	/**
	 * the Dimensions of the NPC, e.g. width and height.
	 */
	private Dimension dimension;
	/**
	 * the Position of the NPC.
	 */
	private Position position;
	
	/**
	 * NPC BodyType
	 */
	private BodyType bodyType;
	
	/**
	 * NPC Body
	 */
	private Body body;
	
	/**
	 * boolean active Default: true
	 */
	private boolean active = true;
	/**
	 * boolean Allow Sleep Default: true
	 */
	private boolean allowSleep = true;
	/**
	 * boolean awake Default: true
	 */
	private boolean awake = true;
	
	/**
	 * the density of the item.
	 */
	private float density;
	
	/**
	 * the friction of the item.
	 */
	private float friction;
	
	/*
	 * the restitution of the item.
	 */
	private float restitution;
	
	/**
	 * the fixedrotation of the item
	 */
	private boolean fixedRotation;
	
	/**
	 * the isSensor of the item
	 */
	private boolean isSensor;
	
	/**
	 * the bullet of the item
	 */
	private boolean bullet;
	
	/**
	 * the airborne of the item
	 */
	private boolean airborne;
	
	/**
	 * Determines if this NPC respawns.
	 */
	private boolean respawn;
	
	/**
	 * target is Player, if true this will enable the npc to target the player.
	 */
	private boolean targetIsPlayer;
	
	/**
	 * targetId the target is an NPC id that the npc will be able to target, to enable npc vs npc.
	 */
	private int targetId;
	
	/**
	 * Determines if this NPC can be attacked.
	 */
	private boolean attackable;
	
	/**
	 * Determines if this NPC is aggressive.
	 */
	private boolean aggressive;
	
	/**
	 * Determines if this NPC retreats.
	 */
	private boolean retreats;
	
	/**
	 * Determines if this NPC is poisonous.
	 */
	private boolean poisonous;
	
	/**
	 * The time it takes for this NPC to respawn.
	 */
	public float respawnTime;
	
	/**
	 * The max hit of this NPC.
	 */
	public float maxHit;
	
	/**
	 * The maximum amount of hitpoints this NPC has.
	 */
	public float maxHealth;
	
	/**
	 * The current health of this NPC.
	 */
	public float currentHealth;
	
	/**
	 * The attack speed of this NPC.
	 */
	private float attackSpeed;
	
	/**
	 * The Idle Animation of this NPC.
	 */
	private String idleAnimation;
	
	/**
	 * The attack animation of this NPC.
	 */
	private String attackAnimation;
	
	/**
	 * The defence animation of this NPC.
	 */
	private String defenceAnimation;
	
	/**
	 * The death animation of this NPC.
	 */
	private String deathAnimation;
	
	/**
	 * The attack bonus of this NPC.
	 */
	private float attackBonus;
	
	/**
	 * The melee defence bonus of this NPC.
	 */
	private float meleeDefence;
	
	/**
	 * The ranged defence of this NPC.
	 */
	private float rangedDefence;
	
	/**
	 * The magic defence of this NPC.
	 */
	private float magicDefence;
	
	
	//--------------------------------- com.gabrieljadderson.nightplanetgame.spine
	/**
	 * NPC ATLAS, MUST BE A SPINE ATLAS
	 */
	private TextureAtlas skeletonAtlas; //the atlas
	
	/**
	 * NPC SKELETON, MUST BE A SPINE SKELETON
	 */
	private Skeleton skeleton; //the skeleton
	
	/**
	 * NPC SKELETON RENDERER, used to the render the NPC skeleton
	 */
	private SkeletonRenderer skeletonRenderer;
	
	/**
	 * NPC ANIMATIONSTATE - SPINE
	 */
	private AnimationState animationState; //the animation state 
	
	/**
	 * NPC Animation
	 */
	private Animation animation;
	/**
	 * NPC ANIMATION TIME
	 */
	public float animationTime;
	/**
	 * NPC ANIMATION EVENTS
	 */
	private Array<Event> animationEvents;
	
	/**
	 * NPC Lights
	 */
	private Array<Light> lights;
	
	/**
	 * Registered whether this npc is registered or not.
	 */
	private boolean registered;
	
	/**
	 * The update Speed of the NPC,
	 */
	private float updateSpeed;
	
	/**
	 * Whether this Npc is a boss or not.
	 */
	private boolean isBoss;
	
	private String AttackSoundPath;
	private String DefenceSoundPath;
	private String DeathSoundPath;
	
	/**
	 * Creates a new {@link NpcDefintion}.
	 *
	 * @param id
	 *            the identification for this NPC.
	 * @param name
	 *            the name of this NPC.
	 * @param description
	 *            the description of this NPC.
	 * @param combatLevel
	 *            the combat level of this NPC.
	 * @param size
	 *            the size of this NPC.
	 * @param attackable
	 *            determines if this NPC can be attacked.
	 * @param aggressive
	 *            determines if this NPC is aggressive.
	 * @param retreats
	 *            determines if this NPC retreats.
	 * @param poisonous
	 *            determines if this NPC is poisonous.
	 * @param respawnTime
	 *            the time it takes for this NPC to respawn.
	 * @param maxHit
	 *            the max hit of this NPC.
	 * @param hitpoints
	 *            the maximum amount of hitpoints this NPC has.
	 * @param attackSpeed
	 *            the attack speed of this NPC.
	 * @param attackAnimation
	 *            the attack animation of this NPC.
	 * @param defenceAnimation
	 *            the defence animation of this NPC.
	 * @param deathAnimation
	 *            the death animation of this NPC.
	 * @param attackBonus
	 *            the attack bonus of this NPC.
	 * @param meleeDefence
	 *            the melee defence bonus of this NPC.
	 * @param rangedDefence
	 *            the ranged defence of this NPC.
	 * @param magicDefence
	 *            the magic defence of this NPC.
	 */
//    public NpcDefinition(int id, String name, int combatLevel, float size, boolean attackable, boolean aggressive,
//        boolean retreats, boolean poisonous, float respawnTime, float maxHit, float health, float attackSpeed, String attackAnimation,
//        String defenceAnimation, String deathAnimation, float attackBonus, float meleeDefence, float rangedDefence, float magicDefence) {
//        this.id = id;
//        this.name = name;
//        this.combatLevel = combatLevel;
//        this.size = size;
//        this.attackable = attackable;
//        this.aggressive = aggressive;
//        this.retreats = retreats;
//        this.poisonous = poisonous;
//        this.respawnTime = respawnTime;
//        this.maxHit = maxHit;
//        this.attackSpeed = attackSpeed;
//        this.attackAnimation = attackAnimation;
//        this.defenceAnimation = defenceAnimation;
//        this.deathAnimation = deathAnimation;
//        this.attackBonus = attackBonus;
//        this.meleeDefence = meleeDefence;
//        this.rangedDefence = rangedDefence;
//        this.magicDefence = magicDefence;
//    }
	
	
	/**
	 * empty contructor for the itemParser class
	 */
	public NpcDefinition()
	{
		
		//default values;
		bodyType = BodyType.DynamicBody;
		
		setPosition(new Position());
		setDimension(new Dimension());
		
		
		animationEvents = new Array<Event>();
		lights = new Array<Light>();
	}
	
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GETTERS AND SETTERS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	/**
	 * Gets the identification for this NPC.
	 *
	 * @return the identification.
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Gets the name of this NPC.
	 *
	 * @return the name.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the combat level of this npc.
	 *
	 * @return the combat level
	 */
	public int getCombatLevel()
	{
		return combatLevel;
	}
	
	/**
	 * Gets the size of this NPC.
	 *
	 * @return the size.
	 */
	public float getSize()
	{
		return size;
	}
	
	/**
	 * Determines if this NPC can be attacked.
	 *
	 * @return {@code true} if this NPC can be attacked, {@code false}
	 * otherwise.
	 */
	public boolean isAttackable()
	{
		return attackable;
	}
	
	/**
	 * Determines if this NPC is aggressive.
	 *
	 * @return {@code true} if this NPC is aggressive, {@code false} otherwise.
	 */
	public boolean isAggressive()
	{
		return aggressive;
	}
	
	/**
	 * Determines if this NPC retreats.
	 *
	 * @return {@code true} if this NPC can retreat, {@code false} otherwise.
	 */
	public boolean Retreats()
	{
		return retreats;
	}
	
	/**
	 * Determines if this NPC is poisonous.
	 *
	 * @return {@code true} if this NPC is poisonous, {@code false} otherwise.
	 */
	public boolean isPoisonous()
	{
		return poisonous;
	}
	
	/**
	 * Gets the time it takes for this NPC to respawn.
	 *
	 * @return the respawn time.
	 */
	public float getRespawnTime()
	{
		return ((respawnTime - 1) <= 0 ? 1 : (respawnTime - 1));
	}
	
	/**
	 * Gets the max hit of this NPC.
	 *
	 * @return the max hit.
	 */
	public float getMaxHit()
	{
		return maxHit;
	}
	
	/**
	 * Gets the maximum amount of hitpoints this NPC has.
	 *
	 * @return the maximum amount of hitpoints.
	 */
	public float getCurrentHealth()
	{
		return currentHealth;
	}
	
	/**
	 * Gets the maximum amount of hitpoints this NPC has.
	 *
	 * @return the attack speed.
	 */
	public float getAttackSpeed()
	{
		return attackSpeed;
	}
	
	/**
	 * Gets the attack animation of this NPC.
	 *
	 * @return the attack animation.
	 */
	public String getAttackAnimation()
	{
		return attackAnimation;
	}
	
	/**
	 * Gets the defence animation of this NPC.
	 *
	 * @return the defence animation.
	 */
	public String getDefenceAnimation()
	{
		return defenceAnimation;
	}
	
	/**
	 * Gets the death animation of this NPC.
	 *
	 * @return the death animation.
	 */
	public String getDeathAnimation()
	{
		return deathAnimation;
	}
	
	/**
	 * Gets the attack bonus of this NPC.
	 *
	 * @return the attack bonus.
	 */
	public float getAttackBonus()
	{
		return attackBonus;
	}
	
	/**
	 * Gets the melee defence bonus of this NPC.
	 *
	 * @return the melee defence bonus.
	 */
	public float getMeleeDefence()
	{
		return meleeDefence;
	}
	
	/**
	 * Gets the ranged defence of this NPC.
	 *
	 * @return the ranged defence bonus.
	 */
	public float getRangedDefence()
	{
		return rangedDefence;
	}
	
	/**
	 * Gets the magic defence of this NPC.
	 *
	 * @return the magic defence bonus.
	 */
	public float getMagicDefence()
	{
		return magicDefence;
	}
	
	/**
	 * @return the skeletonAtlasPath
	 */
	public String getSkeletonAtlasPath()
	{
		return skeletonAtlasPath;
	}
	
	/**
	 * @param skeletonAtlasPath the skeletonAtlasPath to set
	 */
	public void setSkeletonAtlasPath(String skeletonAtlasPath)
	{
		this.skeletonAtlasPath = skeletonAtlasPath;
	}
	
	/**
	 * @return the skeletonJsonPath
	 */
	public String getSkeletonJsonPath()
	{
		return skeletonJsonPath;
	}
	
	/**
	 * @param skeletonJsonPath the skeletonJsonPath to set
	 */
	public void setSkeletonJsonPath(String skeletonJsonPath)
	{
		this.skeletonJsonPath = skeletonJsonPath;
	}
	
	/**
	 * @return the targetIsPlayer
	 */
	public boolean TargetIsPlayer()
	{
		return targetIsPlayer;
	}
	
	/**
	 * @param targetIsPlayer the targetIsPlayer to set
	 */
	public void setTargetIsPlayer(boolean targetIsPlayer)
	{
		this.targetIsPlayer = targetIsPlayer;
	}
	
	/**
	 * @return the targetId
	 */
	public int getTargetId()
	{
		return targetId;
	}
	
	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(int targetId)
	{
		this.targetId = targetId;
	}
	
	/**
	 * @return the idleAnimation
	 */
	public String getIdleAnimation()
	{
		return idleAnimation;
	}
	
	/**
	 * @param idleAnimation the idleAnimation to set
	 */
	public void setIdleAnimation(String idleAnimation)
	{
		this.idleAnimation = idleAnimation;
	}
	
	/**
	 * sets the id of the npc
	 *
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * sets the name of the npc
	 *
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * sets the Combat Level of the NPC
	 *
	 * @param combatLevel
	 */
	public void setCombatLevel(int combatLevel)
	{
		this.combatLevel = combatLevel;
	}
	
	/**
	 * sets the scale of the NPC
	 *
	 * @param size
	 */
	public void setSize(float size)
	{
		this.size = size;
	}
	
	/**
	 * sets whether this npc is attackable or not
	 *
	 * @param attackable
	 */
	public void setAttackable(boolean attackable)
	{
		this.attackable = attackable;
	}
	
	/**
	 * sets whether this npc is agressive or not
	 *
	 * @param aggressive
	 */
	public void setAggressive(boolean aggressive)
	{
		this.aggressive = aggressive;
	}
	
	
	/**
	 * sets whether this npc flee's from the target when low health or not.
	 *
	 * @param retreats
	 */
	public void setRetreats(boolean retreats)
	{
		this.retreats = retreats;
	}
	
	/**
	 * sets whether this npc is poisonous or not
	 *
	 * @param poisonous
	 */
	public void setPoisonous(boolean poisonous)
	{
		this.poisonous = poisonous;
	}
	
	
	/**
	 * sets the respawnTime
	 *
	 * @param respawnTime
	 */
	public void setRespawnTime(float respawnTime)
	{
		this.respawnTime = respawnTime;
	}
	
	/**
	 * sets the maxHit
	 *
	 * @param maxHit
	 */
	public void setMaxHit(float maxHit)
	{
		this.maxHit = maxHit;
	}
	
	/**
	 * sets the health
	 *
	 * @param health
	 */
	public void setCurrentHealth(float health)
	{
		this.currentHealth = health;
	}
	
	/**
	 * sets the attack speed
	 *
	 * @param attackSpeed
	 */
	public void setAttackSpeed(float attackSpeed)
	{
		this.attackSpeed = attackSpeed;
	}
	
	/**
	 * sets the attack Animation
	 *
	 * @param attackAnimation
	 */
	public void setAttackAnimation(String attackAnimation)
	{
		this.attackAnimation = attackAnimation;
	}
	
	/**
	 * sets the defence animation
	 *
	 * @param defenceAnimation
	 */
	public void setDefenceAnimation(String defenceAnimation)
	{
		this.defenceAnimation = defenceAnimation;
	}
	
	/**
	 * sets the death Animation
	 *
	 * @param deathAnimation
	 */
	public void setDeathAnimation(String deathAnimation)
	{
		this.deathAnimation = deathAnimation;
	}
	
	/**
	 * sets the attackBonus
	 *
	 * @param attackBonus
	 */
	public void setAttackBonus(float attackBonus)
	{
		this.attackBonus = attackBonus;
	}
	
	/**
	 * sets the melee defence
	 *
	 * @param meleeDefence
	 */
	public void setMeleeDefence(float meleeDefence)
	{
		this.meleeDefence = meleeDefence;
	}
	
	/**
	 * sets the ranged defence
	 *
	 * @param rangedDefence
	 */
	public void setRangedDefence(float rangedDefence)
	{
		this.rangedDefence = rangedDefence;
	}
	
	/**
	 * sets the magic defence
	 *
	 * @param magicDefence
	 */
	public void setMagicDefence(float magicDefence)
	{
		this.magicDefence = magicDefence;
	}
	
	public NPCAI getAI()
	{
		return AI;
	}
	
	public void setAI(NPCAI aI)
	{
		AI = aI;
	}
	
	public boolean hasIndependentFacing()
	{
		return independentFacing;
	}
	
	public void setIndependentFacing(boolean independentFacing)
	{
		this.independentFacing = independentFacing;
	}
	
	public float getMaxAngularAcceleration()
	{
		return maxAngularAcceleration;
	}
	
	public void setMaxAngularAcceleration(float maxAngularAcceleration)
	{
		this.maxAngularAcceleration = maxAngularAcceleration;
	}
	
	public float getMaxAngularSpeed()
	{
		return maxAngularSpeed;
	}
	
	public void setMaxAngularSpeed(float maxAngularSpeed)
	{
		this.maxAngularSpeed = maxAngularSpeed;
	}
	
	public float getMaxLinearAcceleration()
	{
		return maxLinearAcceleration;
	}
	
	public void setMaxLinearAcceleration(float maxLinearAcceleration)
	{
		this.maxLinearAcceleration = maxLinearAcceleration;
	}
	
	public float getMaxLinearSpeed()
	{
		return maxLinearSpeed;
	}
	
	public void setMaxLinearSpeed(float maxLinearSpeed)
	{
		this.maxLinearSpeed = maxLinearSpeed;
	}
	
	public float getZeroLinearSpeedThreshold()
	{
		return zeroLinearSpeedThreshold;
	}
	
	public void setZeroLinearSpeedThreshold(float zeroLinearSpeedThreshold)
	{
		this.zeroLinearSpeedThreshold = zeroLinearSpeedThreshold;
	}
	
	public Dimension getDimension()
	{
		return dimension;
	}
	
	public void setDimension(Dimension dimension2)
	{
		this.dimension = dimension2;
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	public BodyType getBodyType()
	{
		return bodyType;
	}
	
	public void setBodyType(BodyType bodyType)
	{
		this.bodyType = bodyType;
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public void setBody(Body body)
	{
		this.body = body;
	}
	
	public boolean Respawns()
	{
		return respawn;
	}
	
	public void setRespawns(boolean respawn)
	{
		this.respawn = respawn;
	}
	
	public float getMaxHealth()
	{
		return maxHealth;
	}
	
	public void setMaxHealth(float maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	public float getDensity()
	{
		return density;
	}
	
	public void setDensity(float density)
	{
		this.density = density;
	}
	
	public float getFriction()
	{
		return friction;
	}
	
	public void setFriction(float friction)
	{
		this.friction = friction;
	}
	
	
	public float getRestitution()
	{
		return restitution;
	}
	
	public void setRestitution(float restitution)
	{
		this.restitution = restitution;
	}
	
	public boolean isFixedRotation()
	{
		return fixedRotation;
	}
	
	public void setFixedRotation(boolean fixedRotation)
	{
		this.fixedRotation = fixedRotation;
	}
	
	public boolean isSensor()
	{
		return isSensor;
	}
	
	public void setSensor(boolean isSensor)
	{
		this.isSensor = isSensor;
	}
	
	public boolean isBullet()
	{
		return bullet;
	}
	
	public void setBullet(boolean bullet)
	{
		this.bullet = bullet;
	}
	
	public boolean isAirborne()
	{
		return airborne;
	}
	
	public void setAirborne(boolean airborne)
	{
		this.airborne = airborne;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public boolean canSleep()
	{
		return allowSleep;
	}
	
	public void setSleep(boolean allowSleep)
	{
		this.allowSleep = allowSleep;
	}
	
	public boolean isAwake()
	{
		return awake;
	}
	
	public void setAwake(boolean awake)
	{
		this.awake = awake;
	}
	
	public Array<Light> getLights()
	{
		return lights;
	}
	
	public void setLights(Array<Light> lights)
	{
		this.lights = lights;
	}
	
	public TextureAtlas getSkeletonAtlas()
	{
		return skeletonAtlas;
	}
	
	public void setSkeletonAtlas(TextureAtlas skeletonAtlas)
	{
		this.skeletonAtlas = skeletonAtlas;
	}
	
	public Skeleton getSkeleton()
	{
		return skeleton;
	}
	
	public void setSkeleton(Skeleton skeleton)
	{
		this.skeleton = skeleton;
	}
	
	public SkeletonRenderer getSkeletonRenderer()
	{
		return skeletonRenderer;
	}
	
	public void setSkeletonRenderer(SkeletonRenderer skeletonRenderer)
	{
		this.skeletonRenderer = skeletonRenderer;
	}
	
	public AnimationState getAnimationState()
	{
		return animationState;
	}
	
	public void setAnimationState(AnimationState animationState)
	{
		this.animationState = animationState;
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
	}
	
	public float getAnimationTime()
	{
		return animationTime;
	}
	
	public void setAnimationTime(float time)
	{
		this.animationTime = time;
	}
	
	public Array<Event> getAnimationEvents()
	{
		return animationEvents;
	}
	
	public void setAnimationEvents(Array<Event> events)
	{
		this.animationEvents = events;
	}
	
	public boolean isRegistered()
	{
		return registered;
	}
	
	public void setRegistered(boolean registered)
	{
		this.registered = registered;
	}
	
	public void setBoss(boolean boss)
	{
		this.isBoss = boss;
	}
	
	public boolean isBoss()
	{
		return isBoss;
	}
	
	public float getUpdateSpeed()
	{
		return updateSpeed;
	}
	
	public void setUpdateSpeed(float updateSpeed)
	{
		this.updateSpeed = updateSpeed;
	}
	
	public void setAttackSoundPath(String np)
	{
		this.AttackSoundPath = np;
	}
	
	public String getAttackSoundPath()
	{
		return AttackSoundPath;
	}
	
	public void setDefenceSoundPath(String np)
	{
		this.DefenceSoundPath = np;
	}
	
	public String getDefenceSoundPath()
	{
		return DefenceSoundPath;
	}
	
	public void setDeathSoundPath(String np)
	{
		this.DeathSoundPath = np;
	}
	
	public String getDeathSoundPath()
	{
		return DeathSoundPath;
	}
}
