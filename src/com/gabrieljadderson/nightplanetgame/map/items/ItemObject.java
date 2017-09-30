package com.gabrieljadderson.nightplanetgame.map.items;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;
import com.gabrieljadderson.nightplanetgame.graphics.anim.ItemAnimator;
import com.gabrieljadderson.nightplanetgame.utils.Direction;
import com.gabrieljadderson.nightplanetgame.utils.LightBuilder;
import com.gabrieljadderson.nightplanetgame.utils.MapUtils;
import com.gabrieljadderson.nightplanetgame.utils.Position;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import box2dLight.ConeLight;
import box2dLight.Light;
import com.gabrieljadderson.nightplanetgame.spine.SkeletonRenderer;

public class ItemObject extends Item {
	
	private boolean isActive = false;
	
	private Body body;
	
    private ObjectMap<Animation.Type, Animation> animations;

	private Animation animation;
	private ItemAnimator itemAnimator;
	
	private float health;
	private float speed = .25f;
	
	
	
	/**
	 * the vector3 containing the touchPoint.
	 */
//	private Vector3 touchPoint;

	private Array<Light> lights;
	ConeLight f;
	
//	int counter;
	float elapsedTime;
	
	SkeletonRenderer skeletonRenderer = new SkeletonRenderer();
	
	
//	TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("objects/dragon/0.25/dragon_01.atlas"));
//	SkeletonJson json = new SkeletonJson(playerAtlas);
//	SkeletonData playerSkeletonData = json.readSkeletonData(Gdx.files.internal("objects/dragon/dragon.json"));
//	AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);
	
//	Skeleton skeleton = new Skeleton(playerSkeletonData);
//	AnimationState animationState = new AnimationState(playerAnimationData);

	@Override
	protected void init(ItemDefinition def, Body body, float pixelsPerMeter) {
		super.init(def, body, pixelsPerMeter);
		this.body = body;
		
		itemAnimator = new ItemAnimator(); //ia
		
		
		
//		json.setScale(0.2f);
//		animationState.setAnimation(0, "flying", false);
//		skeleton.setPosition(250, 10);
		
//		touchPoint = new Vector3(); //touchpoint used to check if item is clicked. 
		
//		speed = def.speed / pixelsPerMeter;
//		health = def.health;
//
//		Array<TextureRegion> d = new Array<TextureRegion>();
//		for (int i = 0; i < 50; i++) {
//			if (i > 9) {
//				d.add(GameConstants.ta.findRegion("00" + i));
//			} else {
//				d.add(GameConstants.ta.findRegion("000" + i));
//			}
//			
//		}
		
		
//		Array<TextureRegion> d = new Array<TextureRegion>();
//		for (int i = 0; i < 165; i++) {
//			if (i > 9) {
//				d.add(GameConstants.ta1.findRegion("000" + i));
//			} else if (i < 9) {
//				d.add(GameConstants.ta1.findRegion("0000" + i));
//			} else if (i > 99) {
//				d.add(GameConstants.ta1.findRegion("00" + i));
//			}
//			
//		}
		
		//TODO fix this. 
		
//		animation = new Animation(1f/30f, d);
//		TextureAtlas d = GameConstants.assetManager.get("graphics/items/chest/chest.atlas");
//		animation = new Animation(1f/30f, d.getRegions(), true);
//		NpcAnimation npcAnimation = new NpcAnimation(def.getId());
		
//		animations = npcAnimation.getAnimationArray();
//		animation = animations.get(Animation.Type.WALK_SOUTH);
		lights = new Array<Light>();
		
//		itemAnimtor.animateLight(7.3f, 0.2f, 0.1f, 0.15f, 0.19f, -1f, 0f, true);
//		itemAnimtor.animateLightCircle(1200f, 0.015f, true);
		
		f = LightBuilder.createConeLight(GameConstants.map.getRayHandler(), 800, Color.BLUE, GameConstants.map.toMeters(20f), -90f, 900f, 0f, 0f, 200f, true);
		manageItemQuality(ItemQuality.getByValue(def.getQuality()));
		
		itemAnimator.flickerLights(f); //animation
		detachLight(f);
		
		isActive = true;
	}

	@Override
	public void reset(World world) {
		super.reset(world);
		
		animation = null;
		
		f.remove();
		detachLight(f);
//		f.dispose();
		f = null;
		
		speed = -1f;
		health = -1f;
		System.out.println("item reset invoked.");
		
		isActive = false;
	}

	@Override
	public void update() {
		super.update();
		
		itemAnimator.update();
		
		for (int i = lights.size - 1; i >= 0; i--) {
			Light light = lights.get(i);
			float lightX = body.getPosition().x + (getWidth() / 2f);
			float lightY = body.getPosition().y + ((getHeight() / 2f) - 0.25f);
			itemAnimator.setLightX(lightX); itemAnimator.setLightY(lightY);
			light.setPosition(itemAnimator.getCircleX(), itemAnimator.getCircleY());
		}
		
		 if (Gdx.input.justTouched()) {
			 if (contains(GameConstants.mouseTouchPoint.x, GameConstants.mouseTouchPoint.y)) {
				 if (Gdx.input.isButtonPressed(Buttons.RIGHT)) { //right button
					 System.out.println("right was pressed");
				 }
				 if (Gdx.input.isButtonPressed(Buttons.LEFT)) { //left button
					 System.out.println("LEFT was pressed");
				 }
			 }
		 }

	}
	
	@Override
	public void render(Batch batch) {
		if (animation != null) {
			setSprite(animation.getKeyFrame());
			animation.update(Gdx.graphics.getDeltaTime());
		}
		
		
		
//		if (animation != null) {
//			setSprite(animation.getKeyFrame());
//		}
		
		super.render(batch);
	}


	public void move(Direction direction, float delta) {
		Vector2 motion = new Vector2();

		if (direction == null) {
			body.setLinearVelocity(0f, 0f);
			return;
		}
		else {
			switch (direction) {
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
		System.out.println(delta);
	}
	
//	//FIXME: removed FOR AI IMPLEMENTATION
//	public void moveRandom(float delta) {
////		Vector2 motion = new Vector2();
//		Vector2 motion = body.getPosition();
//		ObjectSet<Direction> directions = new ObjectSet<Direction>();
//		RandomGen rand = new RandomGen();
//		int d = rand.inclusive(3);
////		float c = rand.floatRandom(5f);
//		switch (d) {
//		case 0:
//			directions.add(Direction.WEST); 
//			break;
//		case 1:
//			directions.add(Direction.SOUTH); 
//			break;
//		case 2:
//			directions.add(Direction.NORTH); 
//			break;
//		case 3: 
//			directions.add(Direction.EAST); 
//			break;
//			default: break;
//		}
//		
//		Direction direction = MapUtils.directionSetToDirection(directions);
//		
////		int loopTimes = rand.inclusive(300);//find a better place for coutner and delete it btw.
//		
//		elapsedTime =+ delta;
//		
////		Timer.schedule(new Task() {
////			@Override
////			public void run() {
////				if (elapsedTime < animation.getAnimationDuration() && elapsedTime != -1f) {
////					animation = animations.get(MapUtils.directionToWalk(direction));
////					animation.update(delta);
////				} else {
////					cancel();
////					elapsedTime = 0f;
////				}
////			}
////		}, 1f, delta);
//		
////		Timer.schedule(new Task() {
////			@Override
////			public void run() {
////				if (elapsedTime < animation.getAnimationDuration()) {
////					if (directions.contains(Direction.WEST)) {
////						motion.x = getX() - 0.10f;
////					} else if (directions.contains(Direction.EAST)) {
////						motion.x = getX() + 0.10f;
////					} else if (directions.contains(Direction.NORTH)) {
////						motion.y = getY() + 0.10f;
////					} else if (directions.contains(Direction.SOUTH)) {
////						motion.y = getY() - 0.10f;
////					}
////					
////					if (animation.didFinish()) {
////						cancel();
////						elapsedTime = -1f;
////					}
////					if (GameConstants.mapObjects_collision.containsKey(body)) {
////						System.out.println("shitititititi");
////					}
////					for (Body bodyop : GameConstants.mapObjects_collision.values()) {
//////						if (bodyop.getPosition());
////						if (body == bodyop) {
////							System.out.println(bodyop.getPosition());
////						}
////					}
////					body.setTransform(motion, 0);
////				} else {
////					cancel();
////					elapsedTime = -1f;
////				}
////			}
////		}, 1f, 0.05f);
////		
//		
//	}

	public void attachLight(Light light) {
		if (!lights.contains(light, true)) {
			lights.add(light);
		}
	}

	public void detachLight(Light light) {
		lights.removeValue(light, true);
	}

	public float getSpeed() {
		return speed;
	}

	public float getHealth() {
		return health;
	}
	public Body getBody() {
		return this.body;
	}
	
	public void setHealth(float newHealth) {
		health = newHealth;
	}
	public void setSpeed(float newSpeed) {
		speed = newSpeed;
	}
	public void pickUpItem() {
		GameConstants.inventory.addItem(this, getID());
		setUserData("item:delete" + getID()); //item:delete:id
		destroy(this);
	}

	private void manageItemQuality(ItemQuality quality) {
		attachLight(f);
		f.setColor(quality.getColor());
	}
	
	/** 
	 * checks if the given float x and y contains within the x-pos and y-pos and width and height of this class. 
	 * 
	 * @param x point x coordinate
	 * @param y point y coordinate
	 * @return whether the point is contained in the rectangle */
	public boolean contains (float x, float y) {
		return this.getX() <= x && this.getX() + this.getWidth() >= x && this.getY() <= y && this.getY() + this.getHeight() >= y;
	}
	
	public Position getPosition() {
		return new Position(body.getPosition());
	}
	
	public boolean isActive() {
		return isActive;
	}
	
}
