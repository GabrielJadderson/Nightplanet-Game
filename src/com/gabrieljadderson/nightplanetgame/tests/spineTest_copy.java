package com.gabrieljadderson.nightplanetgame.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.spine.*;
import com.gabrieljadderson.nightplanetgame.spine.attachments.AtlasAttachmentLoader;
import com.gabrieljadderson.nightplanetgame.spine.attachments.RegionAttachment;

/**
 * @author Gabriel Jadderson
 */
public class spineTest_copy
{
	ShapeRenderer shapeRenderer;
	SkeletonRenderer skeletonRenderer;
	SpriteBatch batch;
	
	
	TextureAtlas atlas;
	Skeleton skeleton;
	AnimationState state;
	
	
	Animation animation;
	float time;
	Array<Event> events = new Array();
	
	Body groundBody;
	Vector2 vector = new Vector2();
	
	public void create()
	{
		shapeRenderer = new ShapeRenderer();
		skeletonRenderer = new SkeletonRenderer();
		skeletonRenderer.setPremultipliedAlpha(true);
		batch = new SpriteBatch(10);
		
		atlas = new TextureAtlas(Gdx.files.internal("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/assets/objects/dragon/0.25/dragon_01.atlas"));
		
		// This loader creates Box2dAttachments instead of RegionAttachments for an easy way to keep
		// track of the Box2D body for each attachment.
		AtlasAttachmentLoader atlasLoader = new AtlasAttachmentLoader(atlas)
		{
			public RegionAttachment newRegionAttachment(Skin skin, String name, String path)
			{
				Box2DExample.Box2dAttachment attachment = new Box2DExample.Box2dAttachment(name);
				AtlasRegion region = atlas.findRegion(attachment.getName());
				if (region == null) throw new RuntimeException("Region not found in atlas: " + attachment);
				attachment.setRegion(region);
				return attachment;
			}
		};
		
		SkeletonJson json = new SkeletonJson(atlasLoader); // This loads skeleton JSON data, which is stateless.
		json.setScale(0.003f); // Load the skeleton at 50% the size it was in Spine.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/assets/objects/dragon/dragon.json"));
		animation = skeletonData.findAnimation("flying");
		
		
		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		skeleton.setPosition(44, 4);
		skeleton.updateWorldTransform();
		
		// Create a body for each attachment. Note it is probably better to create just a few bodies rather than one for each
		// region attachment, but this is just an example.
		for (Slot slot : skeleton.getSlots())
		{
			if (!(slot.getAttachment() instanceof Box2DExample.Box2dAttachment)) continue;
			Box2DExample.Box2dAttachment attachment = (Box2DExample.Box2dAttachment) slot.getAttachment();
			
			PolygonShape boxPoly = new PolygonShape();
			boxPoly.setAsBox(attachment.getWidth() / 2 * attachment.getScaleX(),
					attachment.getHeight() / 2 * attachment.getScaleY(), vector.set(attachment.getX(), attachment.getY()),
					attachment.getRotation() * MathUtils.degRad);
			
			BodyDef boxBodyDef = new BodyDef();
			boxBodyDef.type = BodyType.DynamicBody;
			boxBodyDef.bullet = false;
			attachment.body = GameConstants.world.createBody(boxBodyDef);
			attachment.body.createFixture(boxPoly, 1);
			
			boxPoly.dispose();
		}
	}
	
	public void render()
	{
		float delta = Gdx.graphics.getDeltaTime();
		float remaining = delta;
		while (remaining > 0)
		{
			float d = Math.min(0.016f, remaining);
//			GameConstants.world.step(d, 20, 100);
			time += d;
			remaining -= d;
		}
		
		
		// Configure the camera, SpriteBatch, and SkeletonRendererDebug.
		GameConstants.camera.update();
		batch.getProjectionMatrix().set(GameConstants.camera.combined);
		
		batch.begin();
		
		animation.apply(skeleton, time, time, true, events);
//		skeleton.setX(skeleton.getX() + 8 * delta); //add AI to change the position.
		skeleton.updateWorldTransform();
		skeletonRenderer.draw(batch, skeleton);
		
		batch.end();
		
		
		// Position each attachment body.
		for (Slot slot : skeleton.getSlots())
		{
			if (!(slot.getAttachment() instanceof Box2DExample.Box2dAttachment)) continue;
			Box2DExample.Box2dAttachment attachment = (Box2DExample.Box2dAttachment) slot.getAttachment();
			if (attachment.body == null) continue;
			float x = skeleton.getX() + slot.getBone().getWorldX();
			float y = skeleton.getY() + slot.getBone().getWorldY();
			float rotation = slot.getBone().getWorldRotation();
			attachment.body.setTransform(x, y, rotation * MathUtils.degRad);
		}
		
		
	}
	
}