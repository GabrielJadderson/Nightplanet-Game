package com.gabrieljadderson.nightplanetgame.player;

import box2dLight.ConeLight;
import box2dLight.Light;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;
import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;
import com.gabrieljadderson.nightplanetgame.map.npc.Node;
import com.gabrieljadderson.nightplanetgame.map.npc.NodeType;
import com.gabrieljadderson.nightplanetgame.map.projectiles.Projectile;
import com.gabrieljadderson.nightplanetgame.utils.Direction;
import com.gabrieljadderson.nightplanetgame.utils.MapUtils;
import com.gabrieljadderson.nightplanetgame.utils.Position;

/**
 * @author Gabriel Jadderson
 */
public class PlayerDefinition extends Node
{
	
	boolean isMoving = false;
	
	private ObjectMap<Animation.Type, Animation> animations;
	private Animation animation;
	
	private TextureRegion sprite;
	private BodyType bodyType = BodyType.DynamicBody;
	
	private boolean fixedRotation = true;
	private boolean isSensor = false;
	private boolean bullet = false;
	private boolean airborne = true;
	
	private float density = 1f;
	private float friction = 10f;
	private float restitution = 0f;
	
	private float speed = 100f;
	public float health = 100f; //health
	private float lightDistance = 300f;
	private float fogOfWarDistance = 200f;
	
	private boolean isDead = false;
	
	//=========================================================== TESST
	
	//shadow
	private final TextureRegion SHADOW_TEX = SpriteSheet.loadPNG_tex_reg(ResourceCrawler.getRes("player_shadow.png"));
	private Sprite SHADOW_SPRITE = new Sprite(SHADOW_TEX);
	//shadow
	
	
	//health
	private final TextureRegion HEART_TEX = SpriteSheet.loadPNG_tex_reg(ResourceCrawler.getRes("heart_med.png"));
	private Sprite HEART_SPRITE_0 = new Sprite(HEART_TEX);
	private Sprite HEART_SPRITE_1 = new Sprite(HEART_TEX);
	private Sprite HEART_SPRITE_2 = new Sprite(HEART_TEX);
	private Array<Sprite> HEART_SPRITES = new Array<Sprite>(2);
	//health
	
	
	//SPINE TEST
	/*
	private String skeletonAtlasPath = "objects/player/0.25/hero.atlas";
    private String skeletonJsonPath = "objects/player/hero.json";
	private SkeletonRenderer skeletonRenderer;
	private TextureAtlas skeletonAtlas;
	private Skeleton skeleton; //the skeleton
	private AnimationState animationState; //the animation state 
	private com.gabrieljadderson.nightplanetgame.spine.Animation animation;
	public float animationTime;
	private Array<Event> animationEvents;
    */
	//SPINE TEST========================================================
	public static volatile String PLAYER_NAME = "PLAYER_NAME";
	
	
	private Body body;
	private Array<Light> lights;
	private ConeLight cLight;
	
	public Projectile proj;
	
	public void createDef(Object ID, Body body, float pixelsPerMeter)
	{
		super.create(ID, NodeType.PLAYER, body.getPosition(), size);
		this.body = body;
		
		speed = speed / pixelsPerMeter; //regulating the speed.
		
		animation = animations.get(Animation.Type.WALK_SOUTH); //always facing south when loaded. 
		lights = new Array<Light>();
		
		
		//com.gabrieljadderson.nightplanetgame.spine TEST
		/*
		skeletonRenderer = new SkeletonRenderer();
		skeletonRenderer.setPremultipliedAlpha(false);
		skeletonAtlas = GameConstants.assetManager.get(skeletonAtlasPath);
		
		SkeletonJson json = new SkeletonJson(skeletonAtlas); // This loads skeleton JSON data, which is stateless.
		json.setScale(0.0038f); // Load the skeleton at 50% the size it was in Spine.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.classpath(skeletonJsonPath));
		animation = skeletonData.findAnimation("Idle");
		
		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		skeleton.setPosition(body.getPosition().x, body.getPosition().y); 
		skeleton.updateWorldTransform();
		*/
		
		
		HEART_SPRITE_0.setPosition(0.45f, -0.8f);
		HEART_SPRITE_0.setScale(1f - 0.55f, 1f - 0.7f);
		
		HEART_SPRITE_1.setPosition(-0.05f, -0.8f);
		HEART_SPRITE_1.setScale(1f - 0.55f, 1f - 0.7f);
		
		HEART_SPRITE_2.setPosition(-0.55f, -0.8f);
		HEART_SPRITE_2.setScale(1f - 0.55f, 1f - 0.7f);
		HEART_SPRITES.add(HEART_SPRITE_0);
		HEART_SPRITES.add(HEART_SPRITE_1);
		HEART_SPRITES.add(HEART_SPRITE_2);
		
		proj = new Projectile(new Position(body.getPosition()));
		
	}
	
	public void reset(World world)
	{
		setRegistered(false);
		sprite = null;
		size = null;
		
		world.destroyBody(body);
		body = null;
	}
	
	public void update()
	{
		proj.update();
	}
	
	public void render(Batch batch)
	{
		proj.render(batch);
		if (sprite != null)
		{
			float x = getBodyX();
			float y = getBodyY();
			float width = size.x;
			float height = size.y;
			float originX = width / 2f;
			float originY = height / 2f;
			float scaleX = 1f;
			float scaleY = 1f;
			float rotation = getAngle() * MathUtils.radDeg;
			
			batch.draw(SHADOW_SPRITE, x + 0.05f, y - 0.35f, originX, originY, width, height, scaleX + 0.8f, scaleY + 0.8f, rotation);
			
			batch.draw(sprite, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
	        
            /* com.gabrieljadderson.nightplanetgame.spine test
    		float delta = Gdx.graphics.getDeltaTime();
    		float remaining = delta;
    		while (remaining > 0) {
    			float d = Math.min(0.040f, remaining);
    			animationTime += d;
    			remaining -= d;
    		}
            
    		if (animation != null) {
    			animation.apply(skeleton, animationTime, animationTime, true, animationEvents);
    			skeleton.setX(body.getPosition().x+0.35f);
    			skeleton.setY(body.getPosition().y+0.08f);
    			skeleton.updateWorldTransform();
    			skeletonRenderer.draw(batch, skeleton);
    		}
            */
			
			for (Sprite sprite : HEART_SPRITES)
			{
				batch.draw(sprite, x - sprite.getX(), y - sprite.getY(), originX, originY, width, height, sprite.getScaleX(), sprite.getScaleY(), rotation);
			}
			
		}
	}
	
	public void move(Direction direction, float delta)
	{
		Vector2 motion = new Vector2();
		
		if (direction == null)
		{
			body.setLinearVelocity(0f, 0f);
			return;
		} else
		{
			switch (direction)
			{
				case NORTH:
					motion.y = speed;
					break;
				case NORTHEAST:
					motion.x = speed * 0.75f;
					motion.y = speed * 0.75f;
					break;
				case EAST:
					motion.x = speed;
					break;
				case SOUTHEAST:
					motion.x = speed * 0.75f;
					motion.y = -speed * 0.75f;
					break;
				case SOUTH:
					motion.y = -speed;
					break;
				case SOUTHWEST:
					motion.x = -speed * 0.75f;
					motion.y = -speed * 0.75f;
					break;
				case WEST:
					motion.x = -speed;
					break;
				case NORTHWEST:
					motion.x = -speed * 0.75f;
					motion.y = speed * 0.75f;
					break;
				default:
					break;
			}
		}
		
		body.setLinearVelocity(motion);
		animation = animations.get(MapUtils.directionToWalk(direction));
		animation.update(delta);
	}
	
	public Array<Light> getLightsArray()
	{
		return lights;
	}
	
	public void setAnimation(Animation nv)
	{
		animation = nv;
	}
	
	public void setAnimations(ObjectMap<Animation.Type, Animation> nv)
	{
		animations = nv;
	}
	
	public ObjectMap<Animation.Type, Animation> getAnimations()
	{
		return animations;
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public BodyType getBodyType()
	{
		return bodyType;
	}
	
	public void setBody(Body nv)
	{
		this.body = nv;
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public ConeLight getConeLight()
	{
		return cLight;
	}
	
	public void setSprite(TextureRegion nv)
	{
		this.sprite = nv;
	}
	
	public TextureRegion getSprite()
	{
		return sprite;
	}
	
	public boolean isBullet()
	{
		return bullet;
	}
	
	public boolean isAirborne()
	{
		return airborne;
	}
	
	public boolean isSensor()
	{
		return isSensor;
	}
	
	public boolean isFixedRotation()
	{
		return fixedRotation;
	}
	
	public float getRestitution()
	{
		return restitution;
	}
	
	public float getFriction()
	{
		return friction;
	}
	
	public float getDensity()
	{
		return density;
	}
	
	public float getFogOfWarDistance()
	{
		return fogOfWarDistance;
	}
	
	public float getLightDistance()
	{
		return lightDistance;
	}
	
	public float getHealth()
	{
		return health;
	}
	
	public void setHealth(float newHealth)
	{
		health = newHealth;
	}
	
	
	public void setSpeed(float newSpeed)
	{
		speed = newSpeed;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public void attachLight(Light light)
	{
		if (!lights.contains(light, true))
		{
			lights.add(light);
		}
	}
	
	public void detachLight(Light light)
	{
		lights.removeValue(light, true);
	}
	
	public float getBodyX()
	{
		return body.getPosition().x;
//        return getPosition().getX();
	}
	
	public float getBodyY()
	{
		return body.getPosition().y;
//    	return getPosition().getY();
	}
	
	public float getAngle()
	{
		return body.getAngle();
	}
	
	public float getWidth()
	{
		return size.x;
	}
	
	public float getHeight()
	{
		return size.y;
	}
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public void setDead(boolean newValue)
	{
		this.isDead = newValue;
	}
	
	public void setMoving(boolean newVal)
	{
		isMoving = newVal;
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
	
}
