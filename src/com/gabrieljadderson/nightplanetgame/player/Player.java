package com.gabrieljadderson.nightplanetgame.player;

import box2dLight.Light;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.SplashDamage;
import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;
import com.gabrieljadderson.nightplanetgame.map.GameCollisionBits;
import com.gabrieljadderson.nightplanetgame.map.npc.Hit;

/**
 * @author Gabriel Jadderson
 */
public class Player extends PlayerDefinition
{
	
	SplashDamage splash;
	
	
	public Player()
	{
		
	}
	
	public Player createPlayer(Object ID, World world, float pixelsPerMeter)
	{
		
		setAnimations(Animation.extract(GameConstants.SPRITE_SHEET, GameConstants.SPRITE_SHEET_ID, GameConstants.SPRITE_SHEET_GENDER_MALE));
//		setPosition(new Position(800, 1000));
		getPosition().setVector2Position(new Vector2(2500, 400));
		setSpeed(150f);
		
		if (getSize().x == -1f && getSize().y == -1f)
		{
			getSize().set(pixelsPerMeter, pixelsPerMeter);
		}
		
		if (getBounds().x == -1f && getBounds().y == -1f && getBounds().width == -1f && getBounds().height == -1f)
		{
			getBounds().set(0f, 0f, pixelsPerMeter - 2.5f, pixelsPerMeter - 2.5f);
		}
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.bullet = isBullet();
		bodyDef.fixedRotation = isFixedRotation();
		bodyDef.position.set(getPosition().getAsVector2().x / pixelsPerMeter, getPosition().getAsVector2().y / pixelsPerMeter);
		bodyDef.type = getBodyType();
		
		PolygonShape shape = new PolygonShape();
		float width = getBounds().width / pixelsPerMeter;
		float height = getBounds().height / pixelsPerMeter;
		float x = ((getSize().x / pixelsPerMeter) - width) / 2f;
		float y = ((getSize().y / pixelsPerMeter) - height) / 2f;
		shape.setAsBox(width / 2f, height / 2f, new Vector2(x + width / 2f, y + height / 2f), 0f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = getDensity();
		fixtureDef.friction = getFriction();
		fixtureDef.restitution = getRestitution();
		fixtureDef.filter.groupIndex = GameCollisionBits.GROUP_INDEX_PLAYER; //its own group.
		fixtureDef.filter.categoryBits = GameCollisionBits.PLAYER_COLLISION_BIT; //its own stuff. 
		fixtureDef.filter.maskBits = GameCollisionBits.PLAYER_MASK_BIT; //stuff to collide with
		fixtureDef.shape = shape;
		
		Body body = world.createBody(bodyDef);
		body.setUserData(ID);
		body.createFixture(fixtureDef);
		setRegistered(true);
		
		super.createDef(ID, body, pixelsPerMeter);
		
		
		splash = new SplashDamage();
		
		return this;
	}
	
	@Override
	public void reset(World world)
	{
		super.reset(world);

//		getAnimations().clear();
//		setAnimations(null); 
//		setAnimation(null);
		
		setSpeed(-1f);
		setHealth(-1f);
		
	}
	
	@Override
	public void update()
	{
		super.update();
		
		//invoked if the player moves
		if ((int) getBodyX() != (int) getPosition().getX() || (int) getBodyY() != (int) getPosition().getY())
		{
			setMoving(true);
			
			//update light position
			for (int i = getLightsArray().size - 1; i >= 0; i--)
			{
				Light light = getLightsArray().get(i);
				float lightX = getBody().getPosition().x + (getWidth() / 2f);
				float lightY = getBody().getPosition().y + (getHeight() / 2f);
				light.setPosition(lightX, lightY);
			}
		} else
		{
			if (isMoving()) setMoving(false);
		}


//		System.out.println("x: " + getX() + " y: " + getY());
		
		splash.update(Gdx.graphics.getDeltaTime(), getBody(), isDead());
	}
	
	
	@Override
	public void render(Batch batch)
	{
		if (getAnimation() != null)
		{
			setSprite(getAnimation().getKeyFrame());
		}
		
		super.render(batch);
		
		splash.render(batch, getBody());
	}
	
	
	/**
	 * takes in a hit, and decrements the health based on that hit and returns the hit instance.
	 *
	 * @param hit
	 * @return the hit instance itself again. to be reused or whatever
	 */
	public Hit decrementHealth(Hit hit)
	{
		splash.newSplash(hit);
		if (hit.getDamage() > getHealth())
			hit = new Hit(getHealth(), hit.getType());
		health -= hit.getDamage();
		return hit;
	}
	
}
