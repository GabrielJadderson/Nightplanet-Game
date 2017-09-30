package com.gabrieljadderson.nightplanetgame.graphics;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.map.npc.Hit;
import com.gabrieljadderson.nightplanetgame.map.npc.HitType;
import com.gabrieljadderson.nightplanetgame.spine.AnimationState.AnimationStateListener;
import com.gabrieljadderson.nightplanetgame.spine.Event;
import com.gabrieljadderson.nightplanetgame.utils.LightBuilder;
import com.gabrieljadderson.nightplanetgame.utils.MaxSizeHashMap;
import com.gabrieljadderson.nightplanetgame.utils.RandomGen;

/**
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO:
 * @since 24/01/2015
 */
public class SplashDamage
{
	
	String baseImageName = "IMAGE_HIT_SPLASH_";
	String AtlasName = "splash2.atlas";
	String JsonName = "splash2.json";
	
	/**
	 * the scale of the splash object
	 */
	float splashScale = 0.0018f;
	/**
	 * the animation speed of the com.gabrieljadderson.nightplanetgame.spine object
	 */
	float animationSpeed = 1.2f;
	
	/**
	 * max 100 damage ATM!! TODO: increase me.
	 */
	final int MAX_DAMAGE_TEXTURE_SIZE = 100;
	
	/**
	 * the HashMap that will hold all of the damage images.
	 */
	MaxSizeHashMap<Integer, TextureRegion> splashTextures = new MaxSizeHashMap<Integer, TextureRegion>(MAX_DAMAGE_TEXTURE_SIZE);
	
	/**
	 * the current Damage Texture to draw, this is retrieved dynamically from the stored hashmap
	 */
	TextureRegion currentTex;
	
	/**
	 * the Spine Animation
	 */
	SpineObject splashDamage;
	
	/**
	 * true if the animation has finished and we no longer need to render the textures.
	 */
	boolean didFinishAnimation = false;
	
	/**
	 * true if did crit, false otherwise
	 */
	boolean crit = false;
	
	/**
	 * max Hit
	 */
	public float maxHit = 40f;
	
	/**
	 * used to stop rendering
	 */
	boolean canRender = false;
	
	/**
	 * The Light used for the splash
	 */
	PointLight light;
	/**
	 * INTERNAL light rendering
	 */
	boolean renderLight = true;
	/**
	 * EXTERNAL enable ? Disable lights
	 */
	boolean enableLights = true;
	
	public SplashDamage()
	{


//		splashTextures = new MaxSizeHashMap<Integer, TextureRegion>(MAX_DAMAGE_TEXTURE_SIZE);
		
		splashDamage = new SpineObject(AtlasName, JsonName, splashScale, animationSpeed, false);
		
		splashDamage.getState().addListener(new AnimationStateListener()
		{
			@Override
			public void start(int trackIndex)
			{
			}
			
			@Override
			public void event(int trackIndex, Event event)
			{
			}
			
			@Override
			public void end(int trackIndex)
			{
				
			}
			
			@Override
			public void complete(int trackIndex, int loopCount)
			{
				didFinishAnimation = true;
				canRender = false;
				renderLight = false;
			}
		});
		
		for (int i = 0; i < splashTextures.getMaxSize(); i++)
		{
			splashTextures.put(i, SpriteSheet.loadPNG_tex_reg(ResourceCrawler.getRes(baseImageName + i + ".png")));
		}
		
		light = LightBuilder.createPointLight(GameConstants.rayHandler, 4, new Color(255, 255, 255, 1), 2f, 0f, 0f, 0f, true);
	}
	
	/* update */
	public void update(float delta, Body body, boolean dead)
	{
		if (canRender)
		{
			splashDamage.update(delta);
			
			light.update();

			/* light */
			if (renderLight && !dead)
			{
				if (light.getBody() != body)
				{
					light.attachToBody(body);
				}
				light.setActive(true);
			} else if (!renderLight || dead)
			{
				light.setActive(false);
				splashDamage.getState().clearTracks();
				didFinishAnimation = true;
				canRender = false;
				renderLight = false;
			}
		}
	}
	
	
	/* render */
	public void render(Batch batch, Body body)
	{
		
		if (canRender)
		{
			if (body.getWorldCenter().x > 1f || body.getWorldCenter().y > 1f)
			{
				if (!didFinishAnimation)
				{
					float x, y;
					if (crit)
					{
						x = body.getWorldCenter().x - new RandomGen().floatRandom(0.07f);
						y = body.getWorldCenter().y - new RandomGen().floatRandom(0.07f);
					} else
					{
						x = body.getWorldCenter().x - new RandomGen().floatRandom(0.01f);
						y = body.getWorldCenter().y - new RandomGen().floatRandom(0.01f);
					}
					
					splashDamage.getSkeleton().setX(x);
					splashDamage.getSkeleton().setY(y);
					splashDamage.render(batch);
					
					if (currentTex != null)
					{
						batch.draw(currentTex, x - 0.3f, y - 0.25f, 0.5f, 0.5f);
					}
					
				}
			}
			
		}
		
		
	}
	
	
	/**
	 * creates a new splash
	 * TODO: more modular and cleaner implementation.
	 *
	 * @param hit the hit intance
	 */
	public void newSplash(Hit hit)
	{
		if (enableLights) renderLight = true;
		else renderLight = false;
		canRender = true;
		if (hit.getDamage() >= 0.0f && hit.getDamage() <= (float) MAX_DAMAGE_TEXTURE_SIZE)
		{
			int dmg = Math.round(hit.getDamage());
			didFinishAnimation = false;
			if (dmg > (int) maxHit)
			{
				currentTex = splashTextures.get(dmg);
				
				if (hit.getType() == HitType.POISON)
				{
					currentTex = splashTextures.get(dmg);
					setSkinAndAnimate("poison");
					crit = true;
				} else if (hit.getType() == HitType.NORMAL)
				{
					RandomGen randomGen = new RandomGen(); //create a new one each instance, to eliminate conflicting threads locking the unique seed
					int r = randomGen.inclusive(4);
					
					switch (r)
					{
						case 0:
							crit = true;
							setSkinAndAnimate("normal");
							break;
						case 1:
							crit = true;
							setSkinAndAnimate("normal");
							break; // more chance of critting damage than triggering the below animations.
						case 2:
							crit = true;
							setSkinAndAnimate("normal");
							break;
						case 3:
							crit = true;
							setSkinAndAnimate("normal");
							break;
						case 4:
							currentTex = null;
							setSkinAndAnimate("crit_0");
							break; //TODO: skins crit_1 && crit_3 are broken.
						case 5:
							crit = true;
							setSkinAndAnimate("normal");
							break;
						default:
							break;
					}
				}
			} else
			{
				if (hit.getType() == HitType.NORMAL)
				{
					setSkinAndAnimate("normal");
					currentTex = splashTextures.get(dmg);
					crit = false;
				} else if (hit.getType() == HitType.POISON)
				{
					setSkinAndAnimate("poison");
					currentTex = splashTextures.get(dmg);
					crit = false;
				}
			}
		}
		
	}
	
	
	/**
	 * Enables ? Disablers the lights for the splash
	 *
	 * @param enableLights
	 */
	public void setEnableLights(boolean enableLights)
	{
		this.enableLights = enableLights;
	}
	
	/**
	 * Gets the spineObject used for displaying the animation of the splash
	 *
	 * @return splashDamage Spine
	 */
	public SpineObject getSpineObject()
	{
		return splashDamage;
	}
	
	/**
	 * Gets the maximum amount of images the renderer can draw for the splash object
	 *
	 * @return Max amount of textures
	 */
	public int getMaxSize()
	{
		return MAX_DAMAGE_TEXTURE_SIZE;
	}
	
	
	private void setSkinAndAnimate(String skin)
	{
		splashDamage.getSkeleton().setSkin(skin);
		splashDamage.setAnimation(0, "animation", false);
	}
	
}


