package com.gabrieljadderson.nightplanetgame.map.projectiles;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;
import com.gabrieljadderson.nightplanetgame.map.GameCollisionBits;
import com.gabrieljadderson.nightplanetgame.tests.ai.Box2dSteeringEntity;
import com.gabrieljadderson.nightplanetgame.utils.LightBuilder;
import com.gabrieljadderson.nightplanetgame.utils.Position;
import com.gabrieljadderson.nightplanetgame.utils.RandomGen;
import com.gabrieljadderson.nightplanetgame.utils.Stopwatch;

import java.util.concurrent.TimeUnit;

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
 * the arrow class
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO:
 * @since 03/02/2016
 */
public class Projectile
{
	
	boolean cleanUp = false;
	
	Body body;
	BodyDef bodyDef;
	PolygonShape shape;
	FixtureDef fixtureDef;
	
	Body bodyTarget;
	BodyDef bodyDefTarget;
	PolygonShape shapeTarget;
	FixtureDef fixtureDefTarget;
	
	float velocity = 0.75f;
	float distanceToTarget = 0f;
	
	boolean registered = false;
	boolean shooting = false;
	
	SpineObject arrow;
	
	Box2dSteeringEntity arrowEntity;
	Box2dSteeringEntity targetEntity;
	
	PointLight light;
	
	Sound arrowSFX_1;
	long arrowSFX_1_ID;
	Sound arrowSFX_2;
	long arrowSFX_2_ID;
	
	Stopwatch shootTime;
	float shootDelay = 1200f; //milliseconds
	
	public Projectile(Position position)
	{
		
		arrow = new SpineObject(
				"arrow_025.atlas",
				"arrow.json",
				0.003f,
				1.0f,
				false);
		
		
		bodyDef = new BodyDef();
		bodyDef.bullet = true;
		bodyDef.fixedRotation = false;
		bodyDef.position.set(-10, -10);
		bodyDef.type = BodyType.DynamicBody;
		
		shape = new PolygonShape();
		float width = 0.5f;
		float height = 0.3f;
		float x = ((1 / 64) - width) / 2f;
		float y = ((1 / 64) - height) / 2f;
		shape.setAsBox(width / 2f, height / 2f, new Vector2(x + width / 2f, y + height / 2f), 0f);
		
		fixtureDef = new FixtureDef();
		fixtureDef.density = 0f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		fixtureDef.filter.groupIndex = GameCollisionBits.GROUP_INDEX_BULLET; //its own group.
		fixtureDef.filter.categoryBits = GameCollisionBits.BULLET_BIT; //its own stuff. 
		fixtureDef.filter.maskBits = GameCollisionBits.BULLET_MASK_BIT; //stuff to collide with
		fixtureDef.shape = shape;
		
		
		bodyDefTarget = new BodyDef();
		bodyDefTarget.bullet = true;
		bodyDefTarget.fixedRotation = false;
		bodyDefTarget.position.set(-10, -10);
		bodyDefTarget.type = BodyType.DynamicBody;
		
		shapeTarget = new PolygonShape();
		float widtht = 0.2f;
		float heightt = 0.2f;
		float xt = ((1 / 64) - widtht) / 2f;
		float yt = ((1 / 64) - heightt) / 2f;
		shapeTarget.setAsBox(widtht / 2f, heightt / 2f, new Vector2(xt + widtht / 2f, yt + heightt / 2f), 0f);
		
		fixtureDefTarget = new FixtureDef();
		fixtureDefTarget.isSensor = false;
		fixtureDefTarget.filter.groupIndex = GameCollisionBits.GROUP_INDEX_BULLET;
		fixtureDefTarget.filter.categoryBits = GameCollisionBits.BULLET_BIT;
		fixtureDefTarget.filter.maskBits = GameCollisionBits.NOTHING_BIT;
		fixtureDefTarget.shape = shapeTarget;
		
		targetEntity = new Box2dSteeringEntity(null, false, 0.2f, null);
		
		arrowEntity = new Box2dSteeringEntity(null, false, 1, null);
		
		light = LightBuilder.createPointLight(GameConstants.rayHandler, 50, Color.WHITE, 5f, 0f, 0f, 0f, false);
		light.setSoft(false);
		light.setXray(true);
		
		GameConstants.assetManager.loadFromCrawler("SFXGame.2.ogg", Sound.class);
		GameConstants.assetManager.loadFromCrawler("SFXGame.3.ogg", Sound.class);
		GameConstants.assetManager.finishLoading();
		arrowSFX_1 = GameConstants.assetManager.getCrawled("SFXGame.2.ogg", Sound.class);
		arrowSFX_2 = GameConstants.assetManager.getCrawled("SFXGame.3.ogg", Sound.class);
		
		shootTime = new Stopwatch();
		
		/* important to dispose of this. but how? */
//		shape.dispose();
//		shapeTarget.dispose();
	}
	
	public void shoot(Vector2 targetPosition)
	{
		if (!shooting && !GameConstants.isPaused)
		{
			if (shootTime.elapsed(shootDelay, TimeUnit.MILLISECONDS))
			{
				createBody(targetPosition);
//				this.targetPosition = targetPosition;
				shooting = true;
				shootTime.reset();
			}
		}

//		Vector3 vec3 = GameConstants.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
	}
	
	
	public void update()
	{
		if (registered)
		{
			float dt = Gdx.graphics.getDeltaTime();
			arrow.update(dt);
			arrowEntity.update(dt);
			targetEntity.update(dt);
			light.update();
//			if (calculateTrajectoryDistance(arrowEntity.getBody().getWorldCenter(), targetEntity.getBody().getWorldCenter()) > 0.3f) {
//				body.setUserData(0);
//			}
			
			
			distanceToTarget = calculateTrajectoryDistance(body.getWorldCenter(), targetEntity.getBody().getWorldCenter());
			if (distanceToTarget >= 0f && distanceToTarget <= 0.3f)
			{
				body.setUserData(0);
			}
			destroyBody();
		}
	}
	
	public void render(Batch batch)
	{
		
		if (registered)
		{
			arrow.getSkeleton().getBones().get(0).setRotation(arrowEntity.getBody().getAngle() * MathUtils.radDeg);
//			arrow.getSkeleton().setX(body.getPosition().x);
//			arrow.getSkeleton().setY(body.getPosition().y);
//			arrow.render(batch);
			
			if (distanceToTarget >= 1.5)
			{ //WIGGLE EFFECT
				//TODO wiggle!!!
				arrow.getSkeleton().setX(body.getPosition().x - new RandomGen().floatRandom(0.1f));
				arrow.getSkeleton().setY(body.getPosition().y - new RandomGen().floatRandom(0.1f));
			} else
			{
				arrow.getSkeleton().setX(body.getPosition().x);
				arrow.getSkeleton().setY(body.getPosition().y);
			}
			
			arrow.render(batch);

//			light.setPosition(arrow.getSkeleton().getBones().get(0).getX(), arrow.getSkeleton().getBones().get(0).getY());
//			Vector2 motion = new Vector2();

//			Vector2 vec = body.getWorldCenter();
//			
//			Vector3 vec3 = GameConstants.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//			Vector2 vec3 = targetEntity.getBody().getWorldCenter();

//			float distance = calculateTrajectoryDistance(body.getWorldCenter(), targetEntity.getBody().getWorldCenter());
//			if (distance >= 0f && distance <= 0.3f) {
//				body.setUserData(0);
//			}
//			
//			if ((vec3.x - vec.x) > 1 || (vec3.x - vec.x) < 1) {
//				System.err.println("pos CHANEG");

//			if (vec3.x - vec.x > 1 || (vec3.y >= vec.y)) {

//			} else {
//				if (vec3.x >= vec.x) {
//					motion.x = speed * velocity;
//				} else if (vec3.x <= vec.x) {
//					motion.x = -speed * velocity;
//				} 
//				
//				if (vec3.y >= vec.y) {
//					motion.y = speed * velocity;
//				} else if (vec3.y <= vec.y) {
//					motion.y = -speed * velocity;
//				}
//
			//				body.setLinearVelocity(motion);
			
		}

//		}
		
	
	}
	
	/**
	 * cheat??
	 *
	 * @param TargetBody
	 */
	private void hitTarget(Body TargetBody)
	{
		//TODO: implement
	}
	
	
	/**
	 * gets the distance between the origin and the target
	 * ie. sqrt(   (x2 - x1)^2     +     (y2 - y1)^2      )
	 * linear distance calculation, distance between two points.
	 *
	 * @param origin
	 * @param target
	 * @return the distance
	 */
	private float calculateTrajectoryDistance(Vector2 origin, Vector2 target)
	{
		return (float) Math.hypot(target.x - origin.x, target.y - origin.y);
	}
	
	/**
	 * CLOSED LOOP
	 * TODO: DO NOT ALLOW THIS TO DELETE THE BODY; MARK THE BODY FOR DELETION AND WAIT FOR WORLD STEP THEN DELETE IT, OR WHAT?  I AM NOT SURE
	 */
	private void destroyBody()
	{
		if (body.getUserData() != null)
		{
			if (body.getUserData() instanceof Integer)
			{
				if ((int) body.getUserData() == 0)
				{
					arrowEntity.setBody(null);
					targetEntity.setBody(null);
					GameConstants.world.destroyBody(body);
					GameConstants.world.destroyBody(bodyTarget);
					light.attachToBody(null);
					light.setActive(false);
					registered = false;
					shooting = false;
//					arrowSFX_2_ID = arrowSFX_2.play();
//					arrowSFX.stop(sfxID);
				}
			}
		}
	}
	
	/**
	 * CLOSED LOOP
	 *
	 * @param pos
	 */
	private void createBody(Vector2 pos)
	{
		if (!registered)
		{
			bodyDef.position.set(GameConstants.player.getBody().getWorldCenter());
			bodyDefTarget.position.set(pos);
			
			body = GameConstants.world.createBody(bodyDef);
			body.setUserData("BULLET"); // fix this so we can trace the bullet back to the player with his own unique id
			body.createFixture(fixtureDef);
			
			bodyTarget = GameConstants.world.createBody(bodyDefTarget);
			bodyTarget.setUserData("BULLET::TARGET");
			bodyTarget.createFixture(fixtureDefTarget);
			
			targetEntity.setBody(bodyTarget);
			
			arrowEntity.setBody(body);
			arrowEntity.setTarget(targetEntity);
			
			arrowEntity.setMaxAngularAcceleration(0.5f);
			arrowEntity.setMaxAngularSpeed(0.6f);
			arrowEntity.setMaxLinearAcceleration(80.5f);
			arrowEntity.setMaxLinearSpeed(40.6f);
			arrowEntity.setIndependentFacing(false);
			
			arrowEntity.setBehavior(arrowEntity.ARRIVE_BEHAVIOR);
			light.attachToBody(body);
			light.setActive(true);
			arrowSFX_1_ID = arrowSFX_1.play(0.2f);
			//TODO play arrow sound
			registered = true;
		}
	}
	
}
