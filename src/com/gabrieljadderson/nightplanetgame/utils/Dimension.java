package com.gabrieljadderson.nightplanetgame.utils;

/**
 * @author Gabriel Jadderson
 */
public class Dimension
{
	
	public float w;
	public float h;
	
	public Dimension(float w, float h)
	{
		this.w = w;
		this.h = h;
	}
	
	public Dimension()
	{
		this.w = -1f;
		this.h = -1f;
	}
	
	public float getWidth()
	{
		return w;
	}
	
	public float getHeight()
	{
		return h;
	}
	
}
