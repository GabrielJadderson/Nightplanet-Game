package com.gabrieljadderson.nightplanetgame.graphics.objects;

import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.Position;

/**
 * @author Gabriel Jadderson
 */
public class ObjectDefinition
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
	 * the texturescale of the object
	 */
	private float scale;
	
	/**
	 * the animationspeed
	 */
	private float animationSpeed;
	
	/**
	 * path to the animation atlas
	 */
	private String pathAtlas;
	
	/**
	 * path to the animation json
	 */
	private String pathJson;
	
	/**
	 * path to the sfx for this object
	 */
	private String pathSFX;
	
	/**
	 * the volume of the SFX
	 */
	private float volumeSFX;
	
	/**
	 * path to sfx 2 for this object.
	 */
	private String pathSFX2;
	
	/**
	 * the volume of the SFX2
	 */
	private float volumeSFX2;
	
	/**
	 * path to sfx 3 for this object
	 */
	private String pathSFX3;
	
	/**
	 * the volume of the SFX3
	 */
	private float volumeSFX3;
	
	/**
	 * the Position of this object
	 */
	private Position position;
	
	/**
	 * the dimensional size of this object
	 */
	private Dimension size;
	
	/**
	 * the name of the animation
	 */
	private String animationName;
	
	/**
	 * true if the animation has a loop, false otherwise.
	 */
	private boolean isAnimationLooped;
	
	/**
	 * whether this object uses an in-game body.
	 */
	private boolean isBodyObject;
	
	private boolean hasPremultipliedAlpha;
	
	public boolean hasPremultipliedAlpha()
	{
		return hasPremultipliedAlpha;
	}
	
	public void setPremultipliedAlpha(boolean new_val)
	{
		this.hasPremultipliedAlpha = new_val;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setAnimationName(String newVal)
	{
		this.animationName = newVal;
	}
	
	public String getAnimationName()
	{
		return animationName;
	}
	
	public void setAnimationLooped(boolean newVal)
	{
		this.isAnimationLooped = newVal;
	}
	
	public boolean isAnimationLooped()
	{
		return isAnimationLooped;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public float getScale()
	{
		return scale;
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	public float getAnimationSpeed()
	{
		return animationSpeed;
	}
	
	public void setAnimationSpeed(float animationSpeed)
	{
		this.animationSpeed = animationSpeed;
	}
	
	public String getPathAtlas()
	{
		return pathAtlas;
	}
	
	public void setPathAtlas(String pathAtlas)
	{
		this.pathAtlas = pathAtlas;
	}
	
	public String getPathJson()
	{
		return pathJson;
	}
	
	public void setPathJson(String pathJson)
	{
		this.pathJson = pathJson;
	}
	
	public String getPathSFX()
	{
		return pathSFX;
	}
	
	public void setPathSFX(String pathSFX)
	{
		this.pathSFX = pathSFX;
	}
	
	public String getPathSFX2()
	{
		return pathSFX2;
	}
	
	public void setPathSFX2(String pathSFX2)
	{
		this.pathSFX2 = pathSFX2;
	}
	
	public String getPathSFX3()
	{
		return pathSFX3;
	}
	
	public void setPathSFX3(String pathSFX3)
	{
		this.pathSFX3 = pathSFX3;
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	public Dimension getSize()
	{
		return size;
	}
	
	public void setSize(Dimension size)
	{
		this.size = size;
	}
	
	public boolean isBodyObject()
	{
		return isBodyObject;
	}
	
	public void setBodyObject(boolean isBodyObject)
	{
		this.isBodyObject = isBodyObject;
	}
	
	public void setVolumeSFX(float newVal)
	{
		volumeSFX = newVal;
	}
	
	public void setVolumeSFX2(float newVal)
	{
		volumeSFX2 = newVal;
	}
	
	public void setVolumeSFX3(float newVal)
	{
		volumeSFX3 = newVal;
	}
	
	public float getVolumeSFX()
	{
		return volumeSFX;
	}
	
	public float getVolumeSFX2()
	{
		return volumeSFX2;
	}
	
	public float getVolumeSFX3()
	{
		return volumeSFX3;
	}
	
	
}
