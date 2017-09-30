package com.gabrieljadderson.nightplanetgame.tests.ai;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gabrieljadderson.nightplanetgame.utils.GameMath;

/**
 * @author Gabriel Jadderson
 */
public class PlayerAI implements Location<Vector2>
{
	
	private final Body body;
	
	public static Location<Vector2> playerLocation;
	
	public PlayerAI(Body body)
	{
		this.body = body;
	}
	
	@Override
	public Vector2 getPosition()
	{
		return body.getPosition();
	}
	
	@Override
	public float getOrientation()
	{
		return body.getAngle();
	}
	
	@Override
	public void setOrientation(float orientation)
	{
		body.setTransform(getPosition(), orientation);
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
	
	@Override
	public Location<Vector2> newLocation()
	{
		return new Box2dLocation();
	}
}
