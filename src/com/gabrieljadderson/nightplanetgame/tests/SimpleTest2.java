package com.gabrieljadderson.nightplanetgame.tests;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.gabrieljadderson.nightplanetgame.spine.*;
import com.gabrieljadderson.nightplanetgame.spine.AnimationState.AnimationStateListener;
import com.gabrieljadderson.nightplanetgame.spine.attachments.BoundingBoxAttachment;

/**
 * @author Gabriel Jadderson
 */
public class SimpleTest2 extends ApplicationAdapter
{
	OrthographicCamera camera;
	SpriteBatch batch;
	SkeletonRenderer renderer;
	SkeletonRendererDebug debugRenderer;
	
	TextureAtlas atlas;
	Skeleton skeleton;
	SkeletonBounds bounds;
	AnimationState state;
	
	public void create()
	{
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(true);
		debugRenderer = new SkeletonRendererDebug();
		
		atlas = new TextureAtlas(Gdx.files.internal("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/assets/objects/dragon/0.25/dragon_01.atlas"));
		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
		json.setScale(0.6f); // Load the skeleton at 60% the size it was in Spine.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/assets/objects/dragon/dragon.json"));
		
		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		skeleton.setPosition(250, 20);
//		skeleton.setAttachment("head-bb", "head"); // Attach "head" bounding box to "head-bb" slot.
		
		bounds = new SkeletonBounds(); // Convenience class to do hit detection with bounding boxes.
		
		AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
		stateData.setMix("flying", "flying", 0.2f);
//		stateData.setMix("jump", "run", 0.2f);
//		stateData.setMix("jump", "jump", 0);
		
		state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
		state.setTimeScale(0.3f); // Slow all animations down to 30% speed.
		state.addListener(new AnimationStateListener()
		{
			public void event(int trackIndex, Event event)
			{
				System.out.println(trackIndex + " event: " + state.getCurrent(trackIndex) + ", " + event.getData().getName() + ", "
						+ event.getInt());
			}
			
			public void complete(int trackIndex, int loopCount)
			{
				System.out.println(trackIndex + " complete: " + state.getCurrent(trackIndex) + ", " + loopCount);
			}
			
			public void start(int trackIndex)
			{
				System.out.println(trackIndex + " start: " + state.getCurrent(trackIndex));
			}
			
			public void end(int trackIndex)
			{
				System.out.println(trackIndex + " end: " + state.getCurrent(trackIndex));
			}
		});
		
		// Set animation on track 0.
		state.setAnimation(0, "flying", true);
		
		Gdx.input.setInputProcessor(new InputAdapter()
		{
			final Vector3 point = new Vector3();
			
			public boolean touchDown(int screenX, int screenY, int pointer, int button)
			{
				camera.unproject(point.set(screenX, screenY, 0)); // Convert window to world coordinates.
				bounds.update(skeleton, true); // Update SkeletonBounds with current skeleton bounding box positions.
				if (bounds.aabbContainsPoint(point.x, point.y))
				{ // Check if inside AABB first. This check is fast.
					BoundingBoxAttachment hit = bounds.containsPoint(point.x, point.y); // Check if inside a bounding box.
					if (hit != null)
					{
						System.out.println("hit: " + hit);
						skeleton.findSlot("head").getColor().set(Color.RED); // Turn head red until touchUp.
					}
				}
				return true;
			}
			
			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				skeleton.findSlot("head").getColor().set(Color.WHITE);
				return true;
			}
			
			public boolean keyDown(int keycode)
			{
				state.setAnimation(0, "flying", false); // Set animation on track 0 to jump.
				state.addAnimation(0, "flying", true, 0); // Queue run to play after jump.
				return true;
			}
		});
	}
	
	public void render()
	{
		state.update(Gdx.graphics.getDeltaTime()); // Update the animation time.
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
		skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.
		
		// Configure the camera, SpriteBatch, and SkeletonRendererDebug.
		camera.update();
		batch.getProjectionMatrix().set(camera.combined);
		debugRenderer.getShapeRenderer().setProjectionMatrix(camera.combined);
		
		batch.begin();
		renderer.draw(batch, skeleton); // Draw the skeleton images.
		batch.end();
		
		debugRenderer.draw(skeleton); // Draw debug lines.
	}
	
	public void resize(int width, int height)
	{
		camera.setToOrtho(false); // Update camera with new size.
	}
	
	public void dispose()
	{
		atlas.dispose();
	}
	
	public static void main(String[] args) throws Exception
	{
		new LwjglApplication(new SimpleTest2());
	}
}