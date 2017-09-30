package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * The container class that represents a coordinate anywhere in the world.
 *
 * @author Gabriel Jadderson
 */
public class Position
{
	
	/**
	 * The {@code X} coordinate.
	 */
	private float x;
	
	/**
	 * The {@code Y} coordinate.
	 */
	private float y;
	
	/**
	 * The {@code Z} coordinate.
	 */
	private float z;
	
	/**
	 * position vector 2 on a 2 axis plane.
	 */
	private Vector2 positionVector2;
	
	/**
	 * a vector representing the position on an 3 axis plane.
	 */
	private Vector3 positionVector3;
	
	/**
	 * Creates a new {@link Position}.
	 *
	 * @param x the {@code X} coordinate.
	 * @param y the {@code Y} coordinate.
	 * @param z the {@code Z} coordinate.
	 */
	public Position(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates a new {@link Position} with the {@code Z} coordinate value as
	 * {@code 0}.
	 *
	 * @param x the {@code X} coordinate.
	 * @param y the {@code Y} coordinate.
	 */
	public Position(float x, float y)
	{
		this(x, y, 0);
	}
	
	public Position(Vector2 vec2)
	{
		this(vec2.x, vec2.y, 0);
	}
	
	public Position(Vector3 vec3)
	{
		this(vec3.x, vec3.y, vec3.z);
	}
	
	/**
	 * creates a 0, 0, 0, position.
	 */
	public Position()
	{
		this(0f, 0f, 0f);
	}
	
	@Override
	public String toString()
	{
		return "POSITION[x= " + x + ", y= " + y + ", z= " + z + "]";
	}
	
	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) x;
		result = prime * result + (int) y;
		result = prime * result + (int) z;
		return result;
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	
	/**
	 * Returns the delta coordinates. Note that the returned position is not an
	 * actual position, instead it's values represent the delta values between
	 * the two arguments.
	 *
	 * @param a the first position.
	 * @param b the second position.
	 * @return the delta coordinates contained within a position.
	 */
	public static Position delta(Position a, Position b)
	{
		return new Position(b.x - a.x, b.y - a.y);
	}
	
	/**
	 * A substitute for {@link Object#clone()} that creates another 'copy' of
	 * this instance. The created copy <i>safe</i> meaning it does not hold
	 * <b>any</b> references to the original instance.
	 *
	 * @return the copy of this instance that does not hold any references.
	 */
	public Position copy()
	{
		return new Position(x, y, z);
	}
	
	
	/**
	 * Determines if this position is viewable from {@code other}.
	 *
	 * @param other the other position to determine if viewable from.
	 * @return {@code true} if this position is viewable, {@code false}
	 * otherwise.
	 */
	public final boolean isViewableFrom(Position other)
	{
		if (this.getZ() != other.getZ())
			return false;
		Position p = Position.delta(this, other);
		return p.x <= 14 && p.x >= -15 && p.y <= 14 && p.y >= -15; //the constant numbers here are only an approximation.
	}
	
	/**
	 * Determines if this position is within {@code amount} distance of
	 * {@code other}.
	 *
	 * @param other  the position to check the distance for.
	 * @param amount the distance to check.
	 * @return {@code true} if this position is within the distance,
	 * {@code false} otherwise.
	 */
	public final boolean withinDistance(Position other, float amount)
	{
		if (this.z != other.z) //only 2D
			return false;
		return Math.abs(other.x - this.x) <= amount && Math.abs(other.y - this.y) <= amount;
	}
	
	/**
	 * Increments the {@code X}, {@code Y}, and {@code Z} coordinate values
	 * within this container by {@code amountX}, {@code amountY}, and
	 * {@code amountZ}.
	 *
	 * @param amountX the amount to increment the {@code X} coordinate by.
	 * @param amountY the amount to increment the {@code Y} coordinate by.
	 * @param amountZ the amount to increment the {@code Z} coordinate by.
	 * @return an instance of this position.
	 */
	public final Position move(float amountX, float amountY, float amountZ)
	{
		this.x += amountX;
		this.y += amountY;
		this.z += amountZ;
		return this;
	}
	
	/**
	 * Increments the {@code X} and {@code Y} coordinate values within this
	 * container by {@code amountX} and {@code amountY}.
	 *
	 * @param amountX the amount to increment the {@code X} coordinate by.
	 * @param amountY the amount to increment the {@code Y} coordinate by.
	 * @return an instance of this position.
	 */
	public final Position move(float amountX, float amountY)
	{
		return move(amountX, amountY, 0);
	}
	
	/**
	 * Increments the {@code X} and {@code Y} coordinate values within this
	 * container by random amounts positive and negative of {@code amount}.
	 *
	 * @return an instance of this position.
	 */
	public final Position random(int amount)
	{
		RandomGen random = new RandomGen();
		int x = random.inclusive(amount);
		int y = random.inclusive(amount);
		switch (random.inclusive(3))
		{
			case 1:
				return move(-x, -y);
			case 2:
				return move(-x, y);
			case 3:
				return move(x, -y);
			default:
				return move(x, y);
		}
	}
	
	/**
	 * Gets the {@code X} coordinate of the region containing this position.
	 *
	 * @return the {@code X} coordinate of the region.
	 */
	public final int getRegionX()
	{
		return ((int) x >> 3) - 6;
	}
	
	/**
	 * Gets the {@code Y} coordinate of the region containing this position.
	 *
	 * @return the {@code Y} coordinate of the region
	 */
	public final int getRegionY()
	{
		return ((int) y >> 3) - 6;
	}
	
	
	/**
	 * Gets the {@code X} region chunk relative to this position.
	 * <p>
	 * A "Chunk" is a whole block, if the map has e.g. 64bit tiles, then it will return the location at witch that tile has its middle point.
	 * <p>
	 * <dt>Example:</dt>
	 * <p>
	 * <blockquote>
	 * <pre>
	 * 	'-------------------------------'
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'               <b>X</b>               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'-------------------------------'
	 * </pre>
	 * Where <b>X</b> is the Tile Center Position.
	 * </blockquote>
	 *
	 * @return the {@code X} region chunk.
	 */
	public final float getChunkX()
	{
		return (int) x;
	}
	
	/**
	 * Gets the {@code Y} region chunk relative to this position.
	 * <p>
	 * A "Chunk" is a whole block, if the map has e.g. 64bit tiles, then it will return the location at witch that tile has its middle point.
	 * <p>
	 * <dt>Example:</dt>
	 * <p>
	 * <blockquote>
	 * <pre>
	 * 	'-------------------------------'
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'               <b>X</b>               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'                               '
	 * 	'-------------------------------'
	 * </pre>
	 * Where <b>X</b> is the Tile Center Position.
	 * </blockquote>
	 *
	 * @return the {@code Y} region chunk.
	 */
	public final float getChunkY()
	{
		return (int) y;
	}
	
	/**
	 * Sets the position from the given Vector2
	 *
	 * @param nv Vector2
	 */
	public void setVector2Position(Vector2 nv)
	{
		this.positionVector2 = nv;
		this.x = nv.x;
		this.y = nv.y;
	}
	
	/**
	 * Gets the position as Vector2
	 *
	 * @return A Vector2 containing {@code X,Y}
	 */
	public final Vector2 getAsVector2()
	{
		return new Vector2(getX(), getY());
	}
	
	/**
	 * Sets the position from a Vector3 Object.
	 *
	 * @param nv Vector3
	 */
	public void setVector3Position(Vector3 nv)
	{
		this.positionVector3 = nv;
		this.x = nv.x;
		this.y = nv.y;
		this.z = nv.z;
	}
	
	/**
	 * gets the {@code X, Y, Z} coordinates in a vector3
	 *
	 * @return A Vector3 containing the {@code X,Y,Z} coordinates.
	 */
	public final Vector3 getAsVector3()
	{
		return new Vector3(getX(), getY(), getZ());
	}
	
	public final void setPosition(Position position)
	{
		x = position.x;
		y = position.y;
		z = position.z;
	}
	
	/**
	 * Gets the {@code X} coordinate.
	 *
	 * @return the {@code X} coordinate.
	 */
	public final float getX()
	{
		return x;
	}
	
	/**
	 * Sets the {@code X} coordinate.
	 *
	 * @param x the new {@code X} coordinate.
	 */
	public final void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * Gets the {@code Y} coordinate.
	 *
	 * @return the {@code Y} coordinate.
	 */
	public final float getY()
	{
		return y;
	}
	
	/**
	 * Sets the {@code Y} coordinate.
	 *
	 * @param y the new {@code Y} coordinate.
	 */
	public final void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * Gets the {@code Z} coordinate.
	 *
	 * @return the {@code Z} coordinate.
	 */
	public final float getZ()
	{
		return z;
	}
	
	/**
	 * Sets the {@code Z} coordinate.
	 *
	 * @param z the new {@code Z} coordinate.
	 */
	public final void setZ(float z)
	{
		this.z = z;
	}
}
