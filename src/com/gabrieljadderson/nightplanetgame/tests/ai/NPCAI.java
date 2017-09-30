package com.gabrieljadderson.nightplanetgame.tests.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.map.GameCollisionBits;
import com.gabrieljadderson.nightplanetgame.map.npc.NpcDefinition;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.MapUtils;
import com.gabrieljadderson.nightplanetgame.utils.Position;

/**
 * @author Gabriel Jadderson
 */
public class NPCAI
{
	
	BodyDef characterBodyDef;
	FixtureDef charFixtureDef;
	NpcDefinition npcdef;
	
	/**
	 * entities
	 */
	Box2dSteeringEntity character;
	Box2dSteeringEntity target;
	
	/**
	 * misc
	 */
	public Position originPos;
	Position position;
	Dimension size;
	
	int radiusInPixels;
	
	public NPCAI(NpcDefinition def, Body targetBody)
	{
		this.npcdef = def; //important
		this.position = npcdef.getPosition();
		this.size = new Dimension(MapUtils.pixelsToMeters((int) npcdef.getSkeleton().getData().getWidth()), MapUtils.pixelsToMeters((int) npcdef.getSkeleton().getData().getHeight()));
		
		CircleShape circleChape = new CircleShape();
		circleChape.setPosition(new Vector2());
		radiusInPixels = (int) ((size.w + size.h) + 12);
		circleChape.setRadius(MapUtils.pixelsToMeters(radiusInPixels));
//
		characterBodyDef = new BodyDef();
		characterBodyDef.position.set(npcdef.getPosition().getX(), npcdef.getPosition().getY());
		characterBodyDef.type = npcdef.getBodyType();
		characterBodyDef.bullet = npcdef.isBullet();
		characterBodyDef.active = npcdef.isActive();
		characterBodyDef.allowSleep = npcdef.canSleep();
		characterBodyDef.awake = npcdef.isAwake();
		characterBodyDef.fixedRotation = npcdef.isFixedRotation();
//		characterBodyDef.angularDamping = 0f;
		characterBodyDef.type = npcdef.getBodyType();
//		characterBodyDef.linearDamping = 0f;
//		characterBodyDef.angularVelocity = 0f;
		npcdef.setBody(GameConstants.world.createBody(characterBodyDef));
		npcdef.getBody().setUserData("NPC::ID::" + npcdef.getId());
		npcdef.getBody().getPosition().set(position.getAsVector2());
		npcdef.setRegistered(true);
		
		charFixtureDef = new FixtureDef();
		charFixtureDef.density = npcdef.getDensity();
		charFixtureDef.friction = npcdef.getFriction();
		charFixtureDef.restitution = npcdef.getRestitution();
		charFixtureDef.isSensor = npcdef.isSensor();
		charFixtureDef.shape = circleChape;
		charFixtureDef.filter.groupIndex = GameCollisionBits.GROUP_INDEX_NPC;
		charFixtureDef.filter.categoryBits = GameCollisionBits.NPC_COLLISION_BIT;
		charFixtureDef.filter.maskBits = GameCollisionBits.NPC_MASK_BIT;
		npcdef.getBody().createFixture(charFixtureDef);
		
		circleChape.dispose();


//		target = new Box2dSteeringEntity(targetBody, false, MapUtils.pixelsToMeters(radiusInPixels), null);
		
		
		character = new Box2dSteeringEntity(npcdef.getBody(), true, MapUtils.pixelsToMeters(radiusInPixels), null);

//		character.setMaxLinearSpeed(5);
//		character.setMaxLinearAcceleration(10);
		
		//create character
//		markAsSensor(target);
		
		
		/* slow boss values: AngAcc=0.5f, AngSpeed=0.6f, LinAcc=0.5f, LinSpeed=0.6f; */
		
		//angular
		character.setMaxAngularAcceleration(0.5f);
		character.setMaxAngularSpeed(0.6f);
		
		//linear
		character.setMaxLinearAcceleration(0.5f); //a higher value effects the speed 
		character.setMaxLinearSpeed(0.6f); //a higher number makes the object turn slower a lower value makes the object turn faster.
//		character.setZeroLinearSpeedThreshold(0.05f); //not supported?? dont for seek, i guess.
//		
		character.setIndependentFacing(false); //if set to true, will not rotate the object, false otherwise. 
//		character.setTagged(true); //can be used as colision detection???
//		character.isTagged();// yes it can be used as colision detection, e.g. freeze the character if it has tagged the player. 
		
		// Create character's steering behavior
//		seekSB = new Seek<Vector2>(character, target);
//		character.setSteeringBehavior(seekSB);
		character.setBehavior(character.IDLE_BEHAVIOR);
//		character.setTarget(target);
		
		this.originPos = new Position(character.getBody().getPosition());
	}
	
	public void setTarget(Body body)
	{
		target = new Box2dSteeringEntity(body, false, MapUtils.pixelsToMeters(radiusInPixels), null);
		character.setTarget(target);
	}
	
	public void update()
	{
		character.update(Gdx.graphics.getDeltaTime());
	}
	
	public void dispose()
	{
		if (character.getBody() != null)
		{
			setTarget(null);
			GameConstants.world.destroyBody(character.getBody());
			character.body = null;
			npcdef.setRegistered(false);
			System.err.println("disposed");
		}
	}
	
	
	public Position getPosition()
	{
		if (character != null)
		{
			position.setVector2Position(character.getPosition());
		}
		return position;
	}
	
	public Dimension getSize()
	{
		return size;
	}
	
	
	public Box2dSteeringEntity getTarget()
	{
		return target;
	}
	
	public Box2dSteeringEntity getCharacter()
	{
		return character;
	}
	
	public BodyDef getCharacterBodyDef()
	{
		return characterBodyDef;
	}
	
	public FixtureDef getCharacterFixtureDef()
	{
		return charFixtureDef;
	}

//	public void markAsSensor (Box2dSteeringEntity character) {
//		Array<Fixture> fixtures = character.getBody().getFixtureList();
//		for (int i = 0; i < fixtures.size; i++) {
//			fixtures.get(i).setSensor(true);
//		}
//	}
	
	/**
	 * recreates the body, only works if the body is null.
	 */
	public void reCreate()
	{
		if (character.body == null)
		{
//			character = null;
			System.err.println("0");
			getCharacterBodyDef().position.set(originPos.getAsVector2());
			
			CircleShape circleChape = new CircleShape();
			circleChape.setPosition(new Vector2());
			radiusInPixels = (int) ((size.w + size.h) + 12);
			circleChape.setRadius(MapUtils.pixelsToMeters(radiusInPixels));
			getCharacterFixtureDef().shape = circleChape;
			
			
			npcdef.setBody(GameConstants.world.createBody(characterBodyDef));
			npcdef.getBody().setUserData("NPC::ID::" + npcdef.getId());
			npcdef.getBody().createFixture(getCharacterFixtureDef());
			System.err.println("3");
			npcdef.setRegistered(true);
			System.err.println("4");
			
			character = new Box2dSteeringEntity(npcdef.getBody(), true, MapUtils.pixelsToMeters(radiusInPixels), null);
			
			character.setBody(npcdef.getBody());
			System.err.println("5");
			setTarget(GameConstants.player.getBody());
			System.err.println("6");
			character.setMaxAngularAcceleration(0.5f);
			System.err.println("7");
			character.setMaxAngularSpeed(0.6f);
			System.err.println("8");
			character.setMaxLinearAcceleration(0.5f); //a higher value effects the speed 
			System.err.println("9");
			character.setMaxLinearSpeed(0.6f); //a higher number makes the object turn slower a lower value makes the object turn faster.
			System.err.println("10");
			character.setIndependentFacing(false); //if set to true, will not rotate the object, false otherwise. 
			System.err.println("11");
			character.setBehavior(character.IDLE_BEHAVIOR);
			System.err.println("12");
			character.getBody().setTransform(originPos.getAsVector2(), 0f);
			System.err.println("created new NPC");
			
			circleChape.dispose();
			
		}
	}
	
	
}
