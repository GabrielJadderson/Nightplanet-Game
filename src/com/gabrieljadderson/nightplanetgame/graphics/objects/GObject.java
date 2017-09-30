package com.gabrieljadderson.nightplanetgame.graphics.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;
import com.gabrieljadderson.nightplanetgame.map.GameCollisionBits;
import com.gabrieljadderson.nightplanetgame.map.npc.NodeType;
import com.gabrieljadderson.nightplanetgame.utils.Position;

/**
 * @author Gabriel Jadderson
 */
public class GObject
{
	
	boolean isActive = false;
	boolean assetsLoaded = false;
	
	ObjectDefinition objectDef;
	
	Sound SFX = null;
	long CONT_SFX = 0L;
	Sound SFX2 = null;
	long CONT_SFX2 = 0L;
	Sound SFX3 = null;
	long CONT_SFX3 = 0L;
	
	/**
	 * The type of node we are working with.
	 */
	NodeType type;
	
	/**
	 * the spineObject of the class
	 */
	SpineObject so;
	
	/**
	 * the body only used if the nodeobject requires it.
	 */
	Body body;
	FixtureDef fixtureDef;
	BodyDef bodyDef;
	
	public GObject(ObjectDefinition od)
	{
		loadAssets(od);
		this.type = NodeType.OBJECT;
		this.objectDef = od;
		
		setSpine(
				od.getPathAtlas(),
				od.getPathJson(),
				od.getScale(),
				od.getAnimationSpeed(),
				od.hasPremultipliedAlpha(),
				od.getPosition(),
				od.getAnimationName(),
				od.isAnimationLooped()
		);
		
		if (od.getPathSFX().length() > 5)
		{
			SFX = GameConstants.assetManager.getCrawled(od.getPathSFX(), Sound.class);
		}
		if (od.getPathSFX2().length() > 5)
		{
			SFX2 = GameConstants.assetManager.getCrawled(od.getPathSFX2(), Sound.class);
		}
		if (od.getPathSFX3().length() > 5)
		{
			SFX3 = GameConstants.assetManager.getCrawled(od.getPathSFX3(), Sound.class);
		}
		
		
		if (od.isBodyObject())
		{
			bodyDef = new BodyDef();
			bodyDef.bullet = true;
			bodyDef.fixedRotation = false;
			bodyDef.position.set(od.getPosition().getX(), od.getPosition().getY());
			bodyDef.type = BodyType.DynamicBody;
			
			PolygonShape shape = new PolygonShape();
			float width = od.getSize().w;
			float height = od.getSize().h;
			float x = ((1 / 64) - width) / 2f;
			float y = ((1 / 64) - height) / 2f;
			shape.setAsBox(width / 2f, height / 2f, new Vector2(x + width / 2f, y + height / 2f), 0f);
			
			fixtureDef = new FixtureDef();
			fixtureDef.density = 0f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.groupIndex = GameCollisionBits.GROUP_INDEX_OBJECT; //its own group.
			fixtureDef.filter.categoryBits = GameCollisionBits.OBJECT_BIT; //its own stuff.
			fixtureDef.filter.maskBits = GameCollisionBits.OBJECT_MASK_BIT; //stuff to collide with
			fixtureDef.shape = shape;
			
			body = GameConstants.world.createBody(bodyDef);
			body.setUserData("NodeObject"); // fix this so we can trace the bullet back to the player with his own unique id
			body.createFixture(fixtureDef);
			shape.dispose();
		} else
		{
			body = null;
			fixtureDef = null;
			bodyDef = null;
		}
		
		isActive = true;
	}
	
	private void loadAssets(ObjectDefinition od)
	{
		if (assetsLoaded == false)
		{
			GameConstants.assetManager.loadFromCrawler(od.getPathAtlas(), TextureAtlas.class);
			GameConstants.assetManager.loadFromCrawler(od.getPathSFX(), Sound.class);
			GameConstants.assetManager.loadFromCrawler(od.getPathSFX2(), Sound.class);
			GameConstants.assetManager.loadFromCrawler(od.getPathSFX3(), Sound.class);
			GameConstants.assetManager.update();
			GameConstants.assetManager.finishLoading();
			assetsLoaded = true;
		}
	}
	
	
	public void update(float delta)
	{
		so.update(delta);
		
		if (CONT_SFX == 0L) playSFX();
		if (CONT_SFX2 == 0L) playSFX2();
		if (CONT_SFX3 == 0L) playSFX3();
	}
	
	public void render(Batch batch)
	{
		if (isActive)
		{
			
			so.render(batch);
			
			//TODO: rework the update loop
			float delta = Gdx.graphics.getDeltaTime();
			float remaining = delta;
			while (remaining > 0)
			{
				float d = Math.min(0.040f, remaining);
				update(delta);
				remaining -= d;
			}
			
			
		}
	}
	
	public void dispose()
	{
		isActive = false;
		if (body != null) GameConstants.world.destroyBody(body);
		so.dispose();
		if (SFX != null) SFX.dispose();
		if (SFX2 != null) SFX2.dispose();
		if (SFX3 != null) SFX3.dispose();
		GameConstants.GameObjects.remove(objectDef.getId(), this);
	}
	
	
	public void show()
	{
		isActive = true;
	}
	
	public void hide()
	{
		isActive = false;
	}
	
	public void setSpine(String atlas, String json, float scale, float animationSpeed, boolean alphaPremultiplied, Position position, String animationName, boolean loopAnimation)
	{
		so = new SpineObject(
				atlas,
				json,
				scale,
				animationSpeed,
				alphaPremultiplied);
		
		so.getSkeleton().setPosition(position.getX(), position.getY());
		so.setAnimation(0, animationName, loopAnimation);
	}
	
	public void setSpine(SpineObject spineObject)
	{
		so = spineObject;
	}
	
	public NodeType getType()
	{
		return type;
	}
	
	public SpineObject getSpineObject()
	{
		return so;
	}
	
	public ObjectDefinition getObjectDef()
	{
		return objectDef;
	}
	
	public boolean playSFX()
	{
		if (SFX != null)
		{
			CONT_SFX = SFX.play(objectDef.getVolumeSFX());
			return true;
		}
		return false;
	}
	
	public boolean playSFX2()
	{
		if (SFX2 != null)
		{
			CONT_SFX2 = SFX2.play(objectDef.getVolumeSFX2());
			return true;
		}
		return false;
	}
	
	public boolean playSFX3()
	{
		if (SFX3 != null)
		{
			CONT_SFX3 = SFX3.play(objectDef.getVolumeSFX3());
			return true;
		}
		return false;
	}
	
	public boolean stopSFX()
	{
		if (SFX != null)
		{
			SFX.stop();
			return true;
		}
		return false;
	}
	
	public boolean stopSFX2()
	{
		if (SFX != null)
		{
			SFX2.stop();
			return true;
		}
		return false;
	}
	
	public boolean stopSFX3()
	{
		if (SFX != null)
		{
			SFX3.stop();
			return true;
		}
		return false;
	}
	
	
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	
}
