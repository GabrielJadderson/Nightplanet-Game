/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.gabrieljadderson.nightplanetgame.tests.ai;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gabrieljadderson.nightplanetgame.utils.GameMath;


/**
 * @author Gabriel Jadderson
 */
public class Box2dSteeringEntity implements Steerable<Vector2>
{
	
	Body body;
	
	Box2dSteeringEntity target = null;
	
	// behavior
	public final int WANDER_BEHAVIOR = 0; //needs revisiting, just wanders up nothing else happens.
	public final int ARRIVE_BEHAVIOR = 1;
	public final int SEEK_BEHAVIOR = 2;
	public final int FLEE_BEHAVIOR = 3;
	public final int IDLE_BEHAVIOR = 4;
	public final int FACE_BEHAVIOR = 5;
	
	
	float boundingRadius;
	boolean tagged;
	
	float maxLinearSpeed;
	float maxLinearAcceleration;
	float maxAngularSpeed;
	float maxAngularAcceleration;
	
	boolean independentFacing;
	
	protected SteeringBehavior<Vector2> steeringBehavior;
	
	private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
	
	private Wander<Vector2> wanderBehavior;
	private Arrive<Vector2> arriveBehavior;
	private Seek<Vector2> seekBehavior;
	private Flee<Vector2> fleeBehavior;
	private Face<Vector2> faceBehavior;
	
	public Box2dSteeringEntity(Body body, boolean independentFacing, float boundingRadius, Box2dSteeringEntity target)
	{
		this.independentFacing = independentFacing;
		this.boundingRadius = boundingRadius;
		this.tagged = false;
		
		if (target != null)
		{
			this.target = target;
		}
		
		if (body != null)
		{
			this.body = body;
		} else
		{
//			this.body = GameConstants.world.createBody(null);
		}
	}
	
	public void setBehavior(int behavior)
	{
		switch (behavior)
		{
			case ARRIVE_BEHAVIOR: //ARRIVE
				if (arriveBehavior == null)
				{
					if (target.getPosition() != null)
					{
						steeringBehavior = createArriveBehavior();
					}
				} else
				{
					steeringBehavior = arriveBehavior;
				}
				break;
			
			case WANDER_BEHAVIOR: //WANDER
				if (wanderBehavior == null)
				{
					steeringBehavior = createWanderBehavior();
				} else
				{
					steeringBehavior = wanderBehavior;
				}
				break;
			
			case SEEK_BEHAVIOR: //SEEK
				if (seekBehavior == null)
				{
					if (target.getPosition() != null)
					{
						steeringBehavior = createSeekBehavior();
					}
				} else
				{
					steeringBehavior = seekBehavior; //look at this deeper. not sure what it does TODO: chjeckl
				}
				break;
			
			case FLEE_BEHAVIOR: //FLEE
				if (fleeBehavior == null)
				{
					if (target.getPosition() != null)
					{ //add a check in here to flee witin a distance
						steeringBehavior = createFleeBehavior();
					}
				} else
				{
					steeringBehavior = fleeBehavior;
				}
				break;
			
			case FACE_BEHAVIOR:
				if (faceBehavior == null)
				{
					if (target.getPosition() != null)
					{
						steeringBehavior = createFaceBehavior();
					}
				} else
				{
					steeringBehavior = faceBehavior;
				}
				break;
			
			case IDLE_BEHAVIOR: //IDLE
				createIdleBehavior();
				break;
			default:
				break;
		}
	}
	
	public void update(float deltaTime)
	{
		
		if (body != null)
		{
			
			
			if (steeringBehavior != null)
			{
				// Calculate steering acceleration
				steeringBehavior.calculateSteering(steeringOutput);

			/*
			 * Here you might want to add a motor control layer filtering steering accelerations.
			 * 
			 * For instance, a car in a driving game has physical constraints on its movement: it cannot turn while stationary; the
			 * faster it moves, the slower it can turn (without going into a skid); it can brake much more quickly than it can
			 * accelerate; and it only moves in the direction it is facing (ignoring power slides).
			 */
				
				// Apply steering acceleration
				applySteering(steeringOutput, deltaTime);
			}
			
			//		wrapAround(pixelsToMeters(Gdx.graphics.getWidth()), pixelsToMeters(Gdx.graphics.getHeight()));//around screen
			//		wrapAround(MapUtils.pixelsToMeters(3200), MapUtils.pixelsToMeters(3200));//around map
		}
	}
	
	protected void applySteering(SteeringAcceleration<Vector2> steering, float deltaTime)
	{
		
		
		boolean anyAccelerations = false;
		
		// Update position and linear velocity.
		if (!steeringOutput.linear.isZero())
		{
			// this method internally scales the force by deltaTime
			body.applyForceToCenter(steeringOutput.linear, true);
			anyAccelerations = true;
		}
		
		// Update orientation and angular velocity
		if (isIndependentFacing())
		{
			if (steeringOutput.angular != 0)
			{
				// this method internally scales the torque by deltaTime
				body.applyTorque(steeringOutput.angular, true);
				anyAccelerations = true;
			}
		} else
		{
			// If we haven't got any velocity, then we can do nothing.
			Vector2 linVel = getLinearVelocity();
			if (!linVel.isZero(getZeroLinearSpeedThreshold()))
			{
				float newOrientation = vectorToAngle(linVel);
				body.setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime); // this is superfluous if independentFacing is always true
				body.setTransform(body.getPosition(), newOrientation);
			}
		}
		
		if (anyAccelerations)
		{
			// body.activate();
			
			// TODO:
			// Looks like truncating speeds here after applying forces doesn't work as expected.
			// We should likely cap speeds form inside an InternalTickCallback, see
			// http://www.bulletphysics.org/mediawiki-1.5.8/index.php/Simulation_Tick_Callbacks
			
			// Cap the linear speed
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			float maxLinearSpeed = getMaxLinearSpeed();
			if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed)
			{
				body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
			}
			
			// Cap the angular speed
			float maxAngVelocity = getMaxAngularSpeed();
			if (body.getAngularVelocity() > maxAngVelocity)
			{
				body.setAngularVelocity(maxAngVelocity);
			}
		}
	}
	
	// the display area is considered to wrap around from top to bottom
	// and from left to right
	protected void wrapAround(float maxX, float maxY)
	{
		float k = Float.POSITIVE_INFINITY;
		Vector2 pos = body.getPosition();
		
		if (pos.x > maxX)
		{
			k = pos.x = 0.0f;
			System.err.println("RESET POS X");
		}
		
		if (pos.x < 0) k = pos.x = maxX;
		
		if (pos.y < 0) k = pos.y = maxY;
		
		if (pos.y > maxY) k = pos.y = 0.0f;
		
		if (k != Float.POSITIVE_INFINITY) body.setTransform(pos, body.getAngle());
	}
	
	private Wander<Vector2> createWanderBehavior()
	{
		wanderBehavior = new Wander<>(this)
				.setEnabled(true)
				.setFaceEnabled(false) // We want to use Face internally (independent facing is on)
				.setAlignTolerance(1f) // Used by Face
				.setDecelerationRadius(3) // Used by Face
				.setTimeToTarget(1f) // Used by Face
				.setWanderOffset(0.2f) //
				.setWanderOrientation(0.05f) //
				.setWanderRadius(2.0f) //
//				.setWanderRate(MathUtils.PI2 * 4);
				.setWanderRate(24f);
		
		setMaxAngularSpeed(3.5f);
		setMaxAngularAcceleration(4f);
		
		setMaxLinearAcceleration(66f);
		setMaxLinearSpeed(1f);
		
		return wanderBehavior;
	}
	
	private Seek<Vector2> createSeekBehavior()
	{
		seekBehavior = new Seek<>(this, target)
				.setEnabled(true);
		return seekBehavior;
	}
	
	private Arrive<Vector2> createArriveBehavior()
	{
		arriveBehavior = new Arrive<>(this, target)
				.setEnabled(true)
				.setTimeToTarget(0.1f)
				.setArrivalTolerance(0.5f);
		return arriveBehavior;
	}
	
	private Flee<Vector2> createFleeBehavior()
	{
		fleeBehavior = new Flee<>(this, target)
				.setEnabled(true);
		return fleeBehavior;
	}
	
	private Face<Vector2> createFaceBehavior()
	{
		faceBehavior = new Face<>(this, target)
				.setEnabled(true);
		return faceBehavior;
	}
	
	private void createIdleBehavior()
	{
		steeringBehavior = null; //change this to setEnabled, i think its more efficient.
	}
	
	public Arrive<Vector2> getArriveBehavior()
	{
		if (arriveBehavior != null)
		{
			return arriveBehavior;
		} else
		{
			arriveBehavior = createArriveBehavior();
		}
		return arriveBehavior;
	}
	
	public Face<Vector2> getFaceBehavior()
	{
		if (faceBehavior != null)
		{
			return faceBehavior;
		} else
		{
			faceBehavior = createFaceBehavior();
		}
		return faceBehavior;
	}
	
	public Seek<Vector2> getSeekBehavior()
	{
		if (seekBehavior != null)
		{
			return seekBehavior;
		} else
		{
			seekBehavior = createSeekBehavior();
		}
		return seekBehavior;
	}
	
	public Flee<Vector2> getFleeBehavior()
	{
		if (fleeBehavior != null)
		{
			return fleeBehavior;
		} else
		{
			fleeBehavior = createFleeBehavior();
		}
		return fleeBehavior;
	}
	
	public Wander<Vector2> getWanderBehavior()
	{
		if (wanderBehavior != null)
		{
			return wanderBehavior;
		} else
		{
			wanderBehavior = createWanderBehavior();
		}
		return wanderBehavior;
	}
	
	//
	// Limiter implementation
	//
	
	@Override
	public float getMaxLinearSpeed()
	{
		return maxLinearSpeed;
	}
	
	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed)
	{
		this.maxLinearSpeed = maxLinearSpeed;
	}
	
	@Override
	public float getMaxLinearAcceleration()
	{
		return maxLinearAcceleration;
	}
	
	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration)
	{
		this.maxLinearAcceleration = maxLinearAcceleration;
	}
	
	@Override
	public float getMaxAngularSpeed()
	{
		return maxAngularSpeed;
	}
	
	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed)
	{
		this.maxAngularSpeed = maxAngularSpeed;
	}
	
	@Override
	public float getMaxAngularAcceleration()
	{
		return maxAngularAcceleration;
	}
	
	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration)
	{
		this.maxAngularAcceleration = maxAngularAcceleration;
	}
	
	@Override
	public float getZeroLinearSpeedThreshold()
	{
		return 0.001f;
	}
	
	@Override
	public void setZeroLinearSpeedThreshold(float value)
	{
		throw new UnsupportedOperationException();
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public void setBody(Body body)
	{
		this.body = body;
	}
	
	public boolean isIndependentFacing()
	{
		return independentFacing;
	}
	
	public void setIndependentFacing(boolean independentFacing)
	{
		this.independentFacing = independentFacing;
	}
	
	@Override
	public Vector2 getPosition()
	{
		if (body != null)
		{
			return body.getPosition();
		} else
		{
			return new Vector2(-1, -1);
		}
	}
	
	@Override
	public float getOrientation()
	{
		if (body != null)
		{
			return body.getAngle();
		} else
		{
			return 0f;
		}
	}
	
	@Override
	public void setOrientation(float orientation)
	{
		if (body != null)
		{
			body.setTransform(getPosition(), orientation);
		}
	}
	
	@Override
	public Vector2 getLinearVelocity()
	{
		if (body != null)
		{
			return body.getLinearVelocity();
		} else
		{
			return new Vector2(-1, -1);
		}
	}
	
	@Override
	public float getAngularVelocity()
	{
		if (body != null)
		{
			return body.getAngularVelocity();
		} else
		{
			return 0f;
		}
	}
	
	@Override
	public float getBoundingRadius()
	{
		if (body != null)
		{
			return boundingRadius;
		} else
		{
			return 0f;
		}
	}
	
	@Override
	public boolean isTagged()
	{
		if (body != null)
		{
			return tagged;
		} else
		{
			return false;
		}
	}
	
	@Override
	public void setTagged(boolean tagged)
	{
		if (body != null)
		{
			this.tagged = tagged;
		}
	}
	
	@Override
	public Location<Vector2> newLocation()
	{
		if (body != null)
		{
			return new Box2dLocation();
		} else
		{
			return (Location<Vector2>) new Vector2(-1, -1);
		}
	}
	
	@Override
	public float vectorToAngle(Vector2 vector)
	{
		return GameMath.vectorToAngle(vector);
	}
	
	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle)
	{
		return GameMath.angleToVector(outVector, angle);
	}
	
	
	public SteeringBehavior<Vector2> getSteeringBehavior()
	{
		return steeringBehavior;
	}
	
	public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior)
	{
		this.steeringBehavior = steeringBehavior;
	}
	
	public void setTarget(Box2dSteeringEntity target)
	{
		this.target = target;
	}
	
	public Box2dSteeringEntity getTarget()
	{
		return this.target;
	}
	
}
