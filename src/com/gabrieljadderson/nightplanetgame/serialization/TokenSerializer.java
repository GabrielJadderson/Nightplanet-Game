package com.gabrieljadderson.nightplanetgame.serialization;

import com.google.gson.JsonElement;

import java.util.function.Consumer;

/**
 * The container that represents a token that can be both serialized and
 * deserialized.
 *
 * @author Gabriel Jadderson
 */
public final class TokenSerializer
{
	
	/**
	 * The name of this serializable token.
	 */
	private final String name;
	
	/**
	 * The {@code Object} being serialized by this token.
	 */
	private final Object toJson;
	
	/**
	 * The deserialization consumer for this token.
	 */
	private final Consumer<JsonElement> fromJson;
	
	/**
	 * Creates a new {@link TokenSerializer}.
	 *
	 * @param name     the name of this serializable token.
	 * @param toJson   the {@code Object} being serialized by this token.
	 * @param fromJson the deserialization consumer for this token.
	 */
	public TokenSerializer(String name, Object toJson, Consumer<JsonElement> fromJson)
	{
		this.name = name;
		this.toJson = toJson;
		this.fromJson = fromJson;
	}
	
	@Override
	public String toString()
	{
		return "TOKEN[name= " + name + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TokenSerializer))
			return false;
		TokenSerializer other = (TokenSerializer) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/**
	 * Gets the name of this serializable token.
	 *
	 * @return the name of this token.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the {@code Object} being serialized by this token.
	 *
	 * @return the serializable object.
	 */
	public Object getToJson()
	{
		return toJson;
	}
	
	/**
	 * Gets the deserialization consumer for this token.
	 *
	 * @return the deserialization consumer.
	 */
	public Consumer<JsonElement> getFromJson()
	{
		return fromJson;
	}
}