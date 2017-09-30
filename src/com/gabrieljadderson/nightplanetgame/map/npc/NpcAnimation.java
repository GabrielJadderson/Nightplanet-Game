package com.gabrieljadderson.nightplanetgame.map.npc;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;

/**
 * @author Gabriel Jadderson
 */
public class NpcAnimation
{
	
	private TextureAtlas front;
	private TextureAtlas back;
	
	private TextureAtlas right;
	private TextureAtlas left;
	
	private TextureAtlas idle;
	
	private Animation animation;
	
	private ObjectMap<Animation.Type, Animation> animations = new ObjectMap<Animation.Type, Animation>();
	
	/**
	 * TODO: get a list of all npc ids and their animations, a .json file containing npc ids and animation properties for those and fetch the data into here.
	 * TODO: that id param is used to get the data for the requested npc id.
	 *
	 * @param id
	 */
	public NpcAnimation(int id)
	{
		this.front = GameConstants.assetManager.get(ResourceCrawler.getResource("front.atlas"), TextureAtlas.class);
		this.back = GameConstants.assetManager.get(ResourceCrawler.getResource("back.atlas"), TextureAtlas.class);
		
		this.right = GameConstants.assetManager.get(ResourceCrawler.getResource("right.atlas"), TextureAtlas.class);
		this.left = GameConstants.assetManager.get(ResourceCrawler.getResource("left.atlas"), TextureAtlas.class);
		
		this.idle = null; //not implemented yet. 
		
		animations.put(Animation.Type.WALK_NORTH, new Animation(1f / (15 * 2), back.getRegions(), false));
		animations.put(Animation.Type.WALK_SOUTH, new Animation(1f / (15 * 2), front.getRegions(), false));
		animations.put(Animation.Type.WALK_WEST, new Animation(1f / (15 * 2), left.getRegions(), false));
		animations.put(Animation.Type.WALK_EAST, new Animation(1f / (15 * 2), right.getRegions(), false));


//		animation = new Animation(1/15f, front.getRegions(), true);
		animation = animations.get(Animation.Type.WALK_SOUTH); //always facing south when loaded. 
	}
	
	private Animation getAnimation()
	{
		return this.animation;
	}
	
	public ObjectMap<Animation.Type, Animation> getAnimationArray()
	{
		return animations;
	}
	
}
