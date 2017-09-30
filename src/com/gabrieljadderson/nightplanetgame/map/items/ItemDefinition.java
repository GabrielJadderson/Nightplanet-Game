package com.gabrieljadderson.nightplanetgame.map.items;

import java.util.concurrent.ConcurrentHashMap;

import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * The container that represents an item definition.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class ItemDefinition {

    /**
     * The array that contains all of the item definitions.
     */
//    public static final ItemDefinition[] DEFINITIONS = new ItemDefinition[15]; //7956
    public static final ConcurrentHashMap<Integer, ItemDefinition> DEFINITIONS = new ConcurrentHashMap<Integer, ItemDefinition>();


    /**
     * The identifier for the item.
     */
    private int id;

    /**
     * The proper name of the item.
     */
    private String name;

    /**
     * The description of the item.
     */
    private String description;
    
    /**
     * The description of the item.
     */
    private String spritePath;

    /**
     * The flag that determines if the item is stackable.
     */
    private boolean stackable;

    /**
     * The general store price of this item.
     */
    private int generalPrice;

    /**
     * The weight value of this item.
     */
    private double weight;

    /**
     * The array of bonuses for this item.
     */
    private int[] bonus;

    /**
     * The flag that determines if this item is tradeable.
     */
    private boolean tradeable;
    
    /**
     * the quality of the item.
     */
	public int quality;
	
	
	/**
	 * the bodyType for collision handling.
	 */
	public BodyType bodyType;
	
	
	/**
	 * the lightdistance of the light that is used for the item.
	 */
	public float lightDistance;
	
	/**
	 * the x position of the item in the world in a float representation.
	 */
	public float PositionX;
	
	/**
	 * the y position of the item in the world in a float representation.
	 */
	public float PositionY;
	
	/**
	 * the density of the item.
	 */
	public float density;
	
	/**
	 * the friction of the item.
	 */
	public float friction;
	
	/*
	 * the restitution of the item.
	 */
	public float restitution;

	/**
	 * the position vector2 holding 2 values for the x and y.
	 */
	public Vector2 v_position;
	
	/**
	 * the size vector2 holding 2 values width and height.
	 */
	public Vector2 v_size; 

	/**
	 * the textureregion used to paint the animation or texture of the item.
	 */
	public TextureRegion sprite;
	
	/**
	 * the bounds of the item in a rectangle object.
	 */
	public Rectangle bounds;
	
	/**
	 * the fixedrotation of the item
	 */
	public boolean fixedRotation;
	
	/**
	 * the isSensor of the item
	 */
	public boolean isSensor;
	
	/**
	 * the bullet of the item
	 */
	public boolean bullet;
	
	/**
	 * the airborne of the item
	 */
	public boolean airborne;
	
	/**
	 * the spriteRevision of the sprite sheet.
	 */
	public int spriteRevision;
	
	/**
	 * the animations for this item.
	 */
	public ObjectMap<Animation.Type, Animation> animations;
	
	
    /**
     * Creates a new {@link ItemDefinition}.
     *
     * @param id
     *            the identifier for the item.
     * @param name
     *            the proper name of the item.
     * @param description
     *            the description of the item.
     * @param stackable
     *            the flag that determines if the item is stackable.
     * @param generalPrice
     *            the general store price of this item.
     * @param weight
     *            the weight value of this item.
     * @param bonus
     *            the array of bonuses for this item.
     * @param tradeable
     *            the flag that determines if this item is tradeable.
     */
    public ItemDefinition(int id, String name, String description, String spritePath, BodyType bodyType, int quality, 
    		boolean stackable, int generalPrice, double weight, int[] bonus, boolean tradeable, float lightDistance, 
    		float PositionX, float PositionY, Vector2 v_position, Vector2 v_size, TextureRegion sprite, 
    		Rectangle bounds, boolean fixedRotation, int spriteRevision) 
    {
      
    	this.id = id;
        this.name = name;
        this.description = description;
        this.stackable = stackable;
        this.generalPrice = generalPrice;
        this.weight = weight;
        this.bonus = bonus;
        this.tradeable = tradeable;
        this.spritePath = spritePath;
        this.quality = quality;
        this.bodyType = bodyType;
        this.lightDistance = lightDistance;
        this.PositionX = PositionX;
        this.PositionY = PositionY;
        this.v_position = v_position;
        this.v_size = v_size;
        this.sprite = sprite;
        this.bounds = bounds;
        this.fixedRotation = fixedRotation;
        this.spriteRevision = spriteRevision;
        
        
//		bodyType = BodyType.StaticBody;
//		v_position = new Vector2(PositionX, PositionY);
//		v_size = new Vector2(-1f, -1f);
//		bounds = new Rectangle(-1f, -1f, -1f, -1f);
//		fixedRotation = true;
//		quality = 0;
		
        
    }
    
    /**
     * empty contructor for the itemParser class
     */
    public ItemDefinition() {
    	
    	//default values; 
		bodyType = BodyType.DynamicBody;
//		bodyType = BodyType.KinematicBody;
//		bodyType = BodyType.StaticBody;
		v_position = new Vector2(PositionX, PositionY);
		v_size = new Vector2(-1f, -1f);
		bounds = new Rectangle(-1f, -1f, -1f, -1f);
		fixedRotation = true;
		quality = 0;
	}
    
    /**
     * gets the density.
     * 
     * @return the density.
     */
	public float getDensity() {
		return density;
	}

	/**
	 * sets the density of the item.
	 * 
	 * @param density
	 */
	public void setDensity(float density) {
		this.density = density;
	}

	/**
	 * gets the friction. 
	 * 
	 * @return the friction. 
	 */
	public float getFriction() {
		return friction;
	}
	
	/**
	 * sets the friction. 
	 * 
	 * @param friction
	 */
	public void setFriction(float friction) {
		this.friction = friction;
	}
	
	/**
	 * gets the resitution of the item. 
	 * 
	 * @return the restitution
	 */
	public float getRestitution() {
		return restitution;
	}

	/**
	 * sets the restitution of the item.
	 * 
	 * @param restitution
	 */
	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}
    
    
    /**
     * gets the sensor of the item.
     * 
     * @return true if the item is a sensor false otherwise.
     */
    public boolean isSensor() {
    	return isSensor;
    }
    
    /**
     * sets the item sensor
     * 
     * @param nv the new value
     */
    public void setSensor(boolean nv) {
    	isSensor = nv;
    }
    
    /**
     * gets the airborne of the item.
     * 
     * @return true if the item is a airborne false otherwise.
     */
    public boolean isAirborne() {
    	return airborne;
    }
    
    /**
     * sets the item airborne
     * 
     * @param nv the new value
     */
    public void setAirborne(boolean nv) {
    	airborne = nv;
    }
    
    /**
     * gets the bullet of the item.
     * 
     * @return true if the item is a bullet false otherwise.
     */
    public boolean isBullet() {
    	return bullet;
    }
    
    /**
     * sets the item bullet
     * 
     * @param nv the new value
     */
    public void setBullet(boolean nv) {
    	bullet = nv;
    }
    	
    /**
     * gets the item animation map
     * 
     * @return the objetmap containing the animations. 
     */
	public ObjectMap<Animation.Type, Animation> getAnimations() {
		return animations;
	}

	/**
	 * sets the animations of the item.
	 * 
	 * @param animations
	 */
	public void setAnimations(ObjectMap<Animation.Type, Animation> animations) {
		this.animations = animations;
	}
    
    /**
     * sets the spriterevision to the given int.
     * 
     * @param nv
     */
    public void setSpriteRevision(int nv) {
    	spriteRevision = nv;
    }
    
    /**
     * gets the spriterevision as an int.
     * 
     * @return an int representing the spriteRevision.
     */
    public int getSpriteRevision() {
    	return spriteRevision;
    }
	/**
     * gets the X position of the item in the world.
     * 
     * @return the X position in floats.
     */
    public float getPositionX() {
		return PositionX;
	}

    /**
     * sets the X position of the item in the world.
     * 
     * @param positionX
     */
	public void setPositionX(float positionX) {
		PositionX = positionX;
	}

	/**
	 * gets the Y position of the item in the world. 
	 * 
	 * @return the Y position in floats.
	 */
	public float getPositionY() {
		return PositionY;
	}

	/**
	 * sets the Y position of the item in the world. 
	 * 
	 * @param positionY 
	 */
	public void setPositionY(float positionY) {
		PositionY = positionY;
	}

	/**
	 * gets the position of the item in a vector2 representation.
	 * 
	 * @return a vector2 of the item position.
	 */
	public Vector2 getV_position() {
		return v_position;
	}

	/**
	 * sets the position of the item in a vector2.
	 * 
	 * @param v_position
	 */
	public void setV_position(Vector2 v_position) {
		this.v_position = v_position;
	}

	
	/**
	 * gets the size of the item in a vector 2
	 * 
	 * @return a vector2 representation of the item size. 
	 */
	public Vector2 getV_size() {
		return v_size;
	}

	/**
	 * sets the size of the item in a vector2
	 * 
	 * @param v_size
	 */
	public void setV_size(Vector2 v_size) {
		this.v_size = v_size;
	}

	/**
	 * gets the sprite of the item.
	 * 
	 * @return the item sprite.
	 */
	public TextureRegion getSprite() {
		return sprite;
	}

	/**
	 * sets the sprite of the item.
	 * 
	 * @param sprite
	 */
	public void setSprite(TextureRegion sprite) {
		this.sprite = sprite;
	}


	/**
	 * gets the bounds of the item as a rectangle.
	 * 
	 * @return the item bounds.
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * sets the bounds of the item in a rectangle.
	 * 
	 * @param bounds
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	
	/**
	 * gets the fixed rotation of the item.
	 * 
	 * @return true if item has a fixedRotation.
	 */
	public boolean isFixedRotation() {
		return fixedRotation;
	}

	/**
	 * sets whether this item has a fixedRotation.
	 * @param fixedRotation
	 */
	public void setFixedRotation(boolean fixedRotation) {
		this.fixedRotation = fixedRotation;
	}


	/**
	 * sets the item id.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * sets the name of the item.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * sets the description of the item.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * sets the spritepath of the item.
	 * 
	 * @param spritePath
	 */
	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	
	/**
	 * sets whether this item is stackeable or not.
	 * 
	 * @param stackable
	 */
	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}

	
	/**
	 * sets the genetral price of the item. 
	 * 
	 * @param generalPrice
	 */
	public void setGeneralPrice(int generalPrice) {
		this.generalPrice = generalPrice;
	}

	
	/**
	 * sets the weight of the item. 
	 * 
	 * @param weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}


	/**
	 * sets the bonus' for this item. 
	 * 
	 * @param bonus
	 */
	public void setBonus(int[] bonus) {
		this.bonus = bonus;
	}

	
	/**
	 * sets whether this item is tradeable or not.
	 * 
	 * @param tradeable
	 */
	public void setTradeable(boolean tradeable) {
		this.tradeable = tradeable;
	}

	
	/**
	 * sets the quality of the item 
	 * 
	 * @param quality int
	 */
	public void setQuality(int quality) {
		this.quality = quality;
	}


	/**
	 * sets the bodyType of the item.
	 * 
	 * @param the bodyType
	 */
	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}

	/**
	 * sets the lightDistance of the item.
	 * 
	 * @param lightDistance the lightDistance in floats.
	 */
	public void setLightDistance(float lightDistance) {
		this.lightDistance = lightDistance;
	}


	/**
     * Gets the identifier for the item.
     *
     * @return the identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the proper name of the item.
     *
     * @return the proper name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the item.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * gets the spritepath of the items.
     * 
     * @return the spritePath
     */
    public String getSpritePath() {
    	return spritePath;
    }
    
    /**
     * gets the quality as an int for this item.
     * 
     * @return the quality as int
     */
    public int getQuality() {
    	return quality;
    }
    
    /**
     * gets the bodytype for the item.
     * 
     * @return the bodytype
     */
    public BodyType getBodyType() {
    	return bodyType;
    }

    /**
     * Determines if the item is stackable or not.
     *
     * @return {@code true} if the item is stackable, {@code false} otherwise.
     */
    public boolean isStackable() {
        return stackable;
    }
    
    /**
     * gets the lightdistance for the item light.
     * 
     * @return the lightdistance in a float value.
     */
    public float getLightDistance() {
    	return lightDistance;
    }

    /**
     * Gets the general store price of this item.
     *
     * @return the general price.
     */
    public int getGeneralPrice() {
        return generalPrice;
    }
  
    /**
     * Gets the weight value of this item.
     *
     * @return the weight value.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the array of bonuses for this item.
     *
     * @return the array of bonuses.
     */
    public int[] getBonus() {
        return bonus;
    }

    /**
     * Determines if this item is tradeable.
     * 
     * @return {@code true} if this item is tradeable, {@code false} otherwise.
     */
    public boolean isTradeable() {
        return tradeable;
    }
}