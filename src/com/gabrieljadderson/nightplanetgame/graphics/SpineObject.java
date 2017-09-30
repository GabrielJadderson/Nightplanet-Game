package com.gabrieljadderson.nightplanetgame.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.spine.*;
import com.gabrieljadderson.nightplanetgame.utils.Position;

/**
 * Copyright (c) 2016-onwards NightPlanet /vGabriel Howard Jadderson
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * <p>
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p>
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * * Neither the name of 'Shaven Puppy' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * This Class loads a Spine Object and displays it on screen.
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO:
 * @since 24/01/2015
 */
public class SpineObject
{
	
	private SkeletonRenderer renderer;
	
	private TextureAtlas atlas; //loading_animation
	private Skeleton skeleton; //loading_animation
	private AnimationState state; //loading_animation
	
	private Position position;
	
	
	public SpineObject(String atlasName, String jsonPath, float scale, float timeScale, boolean setPremultipliedAlpha)
	{
		
		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(false);
		
		GameConstants.assetManager.loadFromCrawler(atlasName, TextureAtlas.class);
		GameConstants.assetManager.finishLoading();
		atlas = GameConstants.assetManager.getCrawled(atlasName, TextureAtlas.class);
		
		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
		json.setScale(scale); // Load the skeleton at 50% the size it was in Spine.
		SkeletonData skeletonData = json.readSkeletonData(ResourceCrawler.getRes(jsonPath));
		
		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		
		AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
		state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
		state.setTimeScale(timeScale); // Slow all animations down to 60% speed.
		
	}
	
	/**
	 * updates the skeleton. crucial for rendering, must be called before rendering and outside of the batch encloseure.
	 *
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		state.update(deltaTime); // Update the animation time.
		
		state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
		skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.
	}
	
	/**
	 * renders the com.gabrieljadderson.nightplanetgame.spine object, must be called within Batch.begin and batch.end
	 *
	 * @param batch
	 */
	public void render(Batch batch)
	{
		renderer.draw(batch, skeleton); // Draw the skeleton images.
	}
	
	/**
	 * disposes all of the graphics elements in the memory
	 */
	public void dispose()
	{
		atlas.dispose();
	}
	
	
	public void setPosition(Position position)
	{
		this.position = position;
		skeleton.setPosition(this.position.getX(), this.position.getY());
	}
	
	/**
	 * sets the current animation.
	 *
	 * @param trackIndex    the index of which to set the animation to-
	 * @param animationName the name of the animation
	 * @param loop
	 */
	public void setAnimation(int trackIndex, String animationName, boolean loop)
	{
		state.setAnimation(trackIndex, animationName, loop);
	}
	
	/**
	 * adds an animation to the play queue
	 *
	 * @param trackIndex    the index of the animation to add to
	 * @param animationName name of animation
	 * @param loop          loop?
	 * @param delay         float delay in seconds
	 */
	public void addAnimation(int trackIndex, String animationName, boolean loop, float delay)
	{
		state.addAnimation(trackIndex, animationName, loop, delay);
	}
	
	/**
	 * Gets the position of the skeleton
	 *
	 * @return the position of the skeleton
	 */
	public Position getPosition()
	{
		position.setX(skeleton.getX());
		position.setY(skeleton.getY());
		return position;
	}
	
	/**
	 * Gets the skeleton that is attached to this com.gabrieljadderson.nightplanetgame.spine object.
	 *
	 * @return Skeleton
	 */
	public Skeleton getSkeleton()
	{
		return skeleton;
	}
	
	/**
	 * Gets the animation state and all of the data it contains.
	 *
	 * @return AnimationState state;
	 */
	public AnimationState getState()
	{
		return state;
	}
	
}
