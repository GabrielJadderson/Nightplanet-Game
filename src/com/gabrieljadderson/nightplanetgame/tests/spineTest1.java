package com.gabrieljadderson.nightplanetgame.tests;

/**
 * @author Gabriel Jadderson
 */
public class spineTest1
{

////	ShapeRenderer shapeRenderer;
//	SkeletonRenderer skeletonRenderer;
//	SpriteBatch batch;
//	
//	NPCAI AI;
//	
//	Position lastPosition;
//	
//	TextureAtlas atlas; //dragon 
//	public Skeleton skeleton; //dragon
//	AnimationState state; //dragon
//	
//	
//	Animation animation;
//	float time;
//	Array<Event> events = new Array();
//
//	Body groundBody;
//	Vector2 vector = new Vector2();
//	
//	Position position;

//	NPCDef def;
	
	public void create()
	{
//		shapeRenderer = new ShapeRenderer();
//		skeletonRenderer = new SkeletonRenderer();
//		skeletonRenderer.setPremultipliedAlpha(true);
//		batch = new SpriteBatch(10);
////		position = new Position(44, 7);
//		
//		atlas = new TextureAtlas(Gdx.files.internal("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/assets/objects/dragon/0.25/dragon_01.atlas"));
//		
//		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
//		json.setScale(0.003f); // Load the skeleton at 50% the size it was in Spine.
//		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("C:/Users/mulli/Dropbox/Development/Java Projects/NightPlanetGame-master/core/assets/objects/dragon/dragon.json"));
//		animation = skeletonData.findAnimation("flying");	
//		
//		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
//		skeleton.setPosition(44, 7); //really?...
//		skeleton.updateWorldTransform();
//		
//		
		
		
		//test2
		
		
		//test2


//		// Create a body for each attachment. Note it is probably better to create just a few bodies rather than one for each
//		// region attachment, but this is just an example.
//		for (Slot slot : skeleton.getSlots()) {
//			if (!(slot.getAttachment() instanceof Box2dAttachment)) continue;
//			Box2dAttachment attachment = (Box2dAttachment)slot.getAttachment();
//			PolygonShape boxPoly = new PolygonShape();
//			boxPoly.setAsBox(attachment.getWidth() / 2 * attachment.getScaleX(),
//				attachment.getHeight() / 2 * attachment.getScaleY(), vector.set(attachment.getX(), attachment.getY()),
//				attachment.getRotation() * MathUtils.degRad);
//				
//			
//			BodyDef boxBodyDef = new BodyDef();
//			boxBodyDef.type = BodyType.DynamicBody;
//			boxBodyDef.gravityScale = -1f;
//			boxBodyDef.linearDamping = 2.0f;
//			
//			FixtureDef charFixtureDef = new FixtureDef();
//			charFixtureDef.density = 0f;
//			charFixtureDef.shape = boxPoly;
//			charFixtureDef.filter.groupIndex = -2;
//			charFixtureDef.isSensor = true;
//			
//			boxBodyDef.bullet = false;
//			attachment.body = GameConstants.world.createBody(boxBodyDef);
//			attachment.body.createFixture(charFixtureDef);
//			
//			boxPoly.dispose();
//			
//			attachment.body.setUserData("NPC::0");
//			
//			bodies.add(attachment.body);
//		}

////		NPCDef def = GameConstants.NPCs.get(0001);
//		def = new NPCDef();
//		def.setAtlasPath("objects/dragon/0.25/dragon_01.atlas");
//		def.setJsonPath("objects/dragon/dragon.json");
//		def.setAnimationName("animation");
//		def.setScale(0.003f);
//		def.setId("0001");
//		
//		def.createNPC(new Position(44, 7));

//		AI = new NPCAI(new Position(44,7), new Dimension(MapUtils.pixelsToMeters((int) skeleton.getData().getWidth()), MapUtils.pixelsToMeters((int) skeleton.getData().getHeight())), GameConstants.player.getBody());
	}
	
	public void render()
	{
//		float delta = Gdx.graphics.getDeltaTime();
//		float remaining = delta;
////		float remaining2 = delta;
//		while (remaining > 0) {
//			float d = Math.min(0.016f, remaining);
////			AI.update();
//			def.update();
//			GameConstants.world.step(d, 8, 3);
//			time += d;
//			remaining -= d;
//		}
//		
////		updateOrientation(delta);
//		def.updateOrientation(delta);
//		
		
		// Configure the camera, SpriteBatch, and SkeletonRendererDebug.
//		GameConstants.camera.update();
//		batch.getProjectionMatrix().set(GameConstants.camera.combined);
//		
//		batch.begin();
////
//		animation.apply(skeleton, time, time, true, events);
//////		skeleton.setX(skeleton.getX() + 8 * delta); //add AI to change the position.
////		skeleton.setX(AI.getPosition().getX()-0.05f);
////		skeleton.setY(AI.getPosition().getY()-0.25f);
////		skeleton.updateWorldTransform();
////		skeletonRenderer.draw(batch, skeleton);
//		
////		def.render(batch);
//
//		batch.end();


//		// Position each attachment body. DO YOU REALLY NEED THIS??
//		for (Slot slot : skeleton.getSlots()) {
//			if (!(slot.getAttachment() instanceof Box2dAttachment)) continue;
//			Box2dAttachment attachment = (Box2dAttachment)slot.getAttachment();
//			if (attachment.body == null) continue;
//			float x = skeleton.getX() + slot.getBone().getWorldX();
//			float y = skeleton.getY() + slot.getBone().getWorldY();
//			float rotation = slot.getBone().getWorldRotation();
//			attachment.body.setTransform(x, y, rotation * MathUtils.degRad);
//			System.out.println(MapUtils.pixelsToMeters((int) skeleton.getData().getWidth()) + " height: " + MapUtils.pixelsToMeters((int) skeleton.getData().getHeight()));
//		}
		
	
	}


//	public void updateOrientation(float remaining2) {
//		while (remaining2 > 0) {
//			float d = Math.min(0.040f, remaining2);
//			if (lastPosition != null) {
//				if (AI.getPosition().getX() < lastPosition.getX()) {
//					if (!skeleton.getFlipX()) {
//						skeleton.setFlipX(true);
////						System.out.println("Xless");
//						lastPosition = AI.getPosition();
//					}
//				} else {
////					System.out.println("xMORE");
//					if (skeleton.getFlipX()) {
//						skeleton.setFlipX(false);
//						lastPosition = AI.getPosition();
//					}
//				}
//				if (AI.getPosition().getY() < lastPosition.getY()) {
////					System.out.println("Yless");
//					lastPosition = AI.getPosition();
//				} else {
////					System.out.println("YMORE");
//					lastPosition = AI.getPosition();
//				}
//			} else {
//				lastPosition = AI.getPosition();
//			}
//			remaining2 -= d;
//		}
//	}
	
	
}