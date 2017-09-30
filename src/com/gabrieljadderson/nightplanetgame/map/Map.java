
package com.gabrieljadderson.nightplanetgame.map;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.anim.NPC;
import com.gabrieljadderson.nightplanetgame.graphics.renderpipeline.RenderManager;
import com.gabrieljadderson.nightplanetgame.map.items.Item;
import com.gabrieljadderson.nightplanetgame.map.items.ItemDefinition;
import com.gabrieljadderson.nightplanetgame.map.items.ItemObject;
import com.gabrieljadderson.nightplanetgame.map.objects.InGameObject;
import com.gabrieljadderson.nightplanetgame.player.Player;
import com.gabrieljadderson.nightplanetgame.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map.Entry;

import static com.gabrieljadderson.nightplanetgame.GameConstants.*;


/**
 * @author Gabriel Jadderson
 */
public class Map implements Disposable
{ //PLS FIX THIS WHOLE FKING THING, MAKE IT MODULAR AND MAKE CHANGEMAP
	
	private TiledMap tiledMap;
	
	private Texture pixel; //wtf is this
	private Box2DDebugRenderer b2dr; //wtf is this.
	private int inc = 0;
	
	
	//shader test
//    public static Shader shader;
//    public static spineTest1 com.gabrieljadderson.nightplanetgame.spine;
	
	//shader test
	
	public Map(TiledMap tiledMap)
	{
		MapProperties prop = tiledMap.getProperties();
		
		mapWidth = prop.get("width", Integer.class);
		mapHeight = prop.get("height", Integer.class);
		tilePixelWidth = prop.get("tilewidth", Integer.class);
		tilePixelHeight = prop.get("tileheight", Integer.class);
		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
		
		//map custom properties.
		Object x_max = prop.get("x_max", Integer.class);
		Object y_max = prop.get("y_max", Integer.class);
		if (y_max != null || x_max != null)
		{
			map_max_X = Float.parseFloat(x_max.toString());
			map_max_Y = Float.parseFloat(y_max.toString());
		} else
		{
			map_max_X = 0;
			map_max_Y = 0;
		}
		//map custom properties.
		
		
		createMap(tiledMap, tilePixelWidth);
		ownsBatch = true;

//        shader = new Shader();
//        com.gabrieljadderson.nightplanetgame.spine = new spineTest1();
//        com.gabrieljadderson.nightplanetgame.spine.create();
	
	}
	
	public void createMap(TiledMap tiledMap, float pixelsPerMeter)
	{
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width / pixelsPerMeter, height / pixelsPerMeter);
		persCamera = new PerspectiveCamera(width / pixelsPerMeter, height / pixelsPerMeter, 0f);//
		
		this.tiledMap = tiledMap;
		renderer = new OrthogonalTiledMapRenderer(tiledMap, 1f / pixelsPerMeter, batch);
		renderer2 = new IsometricStaggeredTiledMapRenderer(tiledMap, 1f / pixelsPerMeter, batch);
		renderer3 = new IsometricTiledMapRenderer(tiledMap, 1f / pixelsPerMeter, batch);
		
		
		ambientLight = 0f;
		/*
		//world = new World(new Vector2(), true);
		rayHandler = new RayHandler(world, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		rayHandler.setAmbientLight(ambientLight);
		rayHandler.setBlur(true);
		rayHandler.setBlurNum(5);
		rayHandler.setCulling(true);
		rayHandler.setShadows(true);
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		*/
		
		GameConstants.pixelsPerMeter = pixelsPerMeter;
		velocityIterations = 6;
		positionIterations = 3;
		
		
		pixel = new Texture(ResourceCrawler.getRes("pixel.png"));
		b2dr = new Box2DDebugRenderer();
		
		//asssigning all the map info.
		GameConstants.mapLayers = tiledMap.getLayers();
		GameConstants.mapProperties = tiledMap.getProperties();
		GameConstants.mapTileSet = tiledMap.getTileSets();
		GameConstants.mapLayer_ground = tiledMap.getLayers().get(GameConstants.GROUND_NAME);
		GameConstants.mapTiledMap = this.tiledMap;
		//assigning all the map info.
		
		//GameConstants.GameObjects.get(3).setActive(true);
		
		MapUtils.create_SingleTileAnimation_FromLayer("AnimationLayer", 0.10f);
		MapUtils.create_LIGHTS_FromLayer("LightLayer");
		MapUtils.create_NPCs_FromLayer("NPCLayer");
		
		MapUtils.createCollision(this, tiledMap.getLayers().get(GameConstants.COLLISION_NAME).getObjects());
		MapUtils.loadClickableObjects();
		
		world.setContactListener(new GameCollision());
		
		GameConstants.renderManager = new RenderManager();
	}
	
	
	public void update(float timeStep)
	{
		if (cameraTarget != null)
		{
			camera.position.x += (cameraTarget.getBodyX() - camera.position.x) * .2f;
			camera.position.y += (cameraTarget.getBodyY() - camera.position.y) * .2f;
		}
		
		camera.update();
		
		GameConstants.renderManager.updateWorldRendering(timeStep);
		world.step(timeStep, velocityIterations, positionIterations);
		rayHandler.update();


//        TODO: update all players.
//        for (Entry<EntityDef, Actor> player : GameConstants.actors2.entrySet()) {
//        	if (player.getValue().getUserData() != null) {
//            	if (player.getValue().getUserData().equals("player")) {
//            		player.getValue().update();
//            	}
//        	}
//        }
		player.update();
		
		for (Entry<Integer, NPC> npc : GameConstants.NPCs.entrySet())
		{
			if (npc.getValue().getDefinition().isActive())
			{

//				npc.getValue().update();
				
				float delta = Gdx.graphics.getDeltaTime();
				float remaining = delta;
				while (remaining > 0)
				{
					float d = Math.min(npc.getValue().getDefinition().getUpdateSpeed(), remaining);
					npc.getValue().update();
					npc.getValue().getDefinition().animationTime += d;
					remaining -= d;
				}
				
			}
		}
		
		
		//clickable objects, like doors, stairs etc.
		for (Entry<Integer, InGameObject> entryClickableObject : GameConstants.mapObjects_clickableObjects.entrySet())
		{
			if (entryClickableObject.getValue() != null)
			{
				entryClickableObject.getValue().update();
			}
		}
		
		//npc deletion?
		for (NPC npc : GameConstants.NPCs.values())
		{
			if (npc.getDefinition().getBody().getUserData() != null)
			{
				if (npc.getDefinition().getBody().getUserData().toString().contains("DELETE"))
				{
					npc.reset(world);
				}
			}
		}
		
		//TODO: make this cleaner and fix it maybe with java 8
		for (Entry<ItemDefinition, ItemObject> entryUpdate : GameConstants.items.entrySet())
		{
			if (entryUpdate.getKey() != null && entryUpdate.getValue() != null)
			{
				if (entryUpdate.getKey().getId() != -1 && entryUpdate.getKey().getName() != null)
				{
					if (entryUpdate.getValue().getUserData() != null)
					{
						ItemObject item = entryUpdate.getValue();
						ItemDefinition itemdef = entryUpdate.getKey();
						item.update();
						if (item.getUserData() instanceof String)
						{
							if (item.getUserData().toString().contains("item:delete"))
							{
								int id = Integer.parseInt(StringUtils.replace((String) item.getUserData(), "item:delete:", "", 11));
								if (removeItem(item, itemdef, world) == null)  //removes item and checking if the item returns null if it does it is removed
									System.out.println("removed item: " + id);
								else
									System.out.println("failed to remove item " + id);
							}
						}
					}
				} else
				{
					System.err.println("userDATA null");
				}
			}
		}
		
	}
	
	public void render()
	{
//        batch.enableBlending();
		
		renderer.setView(camera);
//        renderer.setView(camera.combined, 40, 7, 100, 100);
		renderer.render();

//        renderer3.setView(camera.combined, 40, 7, 100, 100);
//        renderer3.render();
		persCamera.update();
		
		batch.begin(); // nromal batch
		
		
		for (MapLayer layer : tiledMap.getLayers())
		{
			if ((layer instanceof TiledMapTileLayer) && !layer.getName().equalsIgnoreCase(GameConstants.FOREGROUND_NAME))
			{
				renderer.renderTileLayer((TiledMapTileLayer) layer);
				
			}
		}


//        renderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get(GameConstants.FOREGROUND_NAME)); //renders the foreground after every item, players, npcs are rendered
		batch.end(); // normal batch
		
		
		GameConstants.renderManager.renderWorld(batch);

//        npcRenderer.render();
//        objectRenderer.render();
		
		
		batch.begin();
		renderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get(GameConstants.FOREGROUND_NAME)); //renders the foreground after every item, players, npcs are rendered
		batch.end();
		
		
		rayHandler.setCombinedMatrix(camera);
		rayHandler.render();
		
		if (DEBUG)
		{
			b2dr.setDrawJoints(true);
			b2dr.setDrawAABBs(true);
			;
			b2dr.render(world, camera.combined);
		}
		
		
	}


//    public Player addPlayer(Object ID, ActorDef def) {
//        Player player = new Player(ID, def);
//        GameConstants.actors.put(def, player);
//        return player;
//    }
	
	//adding item
	public <T extends Item> T addItem(ItemDefinition def)
	{
		assert def.getName() != null;
		T item = Item.create(def, world, pixelsPerMeter);
		System.out.println("addItem - id: " + def.getId() + " name: " + def.getName() + " position: " + def.getV_position());
		return item;
	}

//    public <T extends Actor> T addActor(ItemDefinition def, Object ID) {
////    	assert def.position != null;
//    	T actor = Actor.create(def, world, pixelsPerMeter, ID);
////    	System.out.println("addItem - id: " + def.getId() + " name: " + def.getName() + " position: " + def.getV_position());
//    	return actor;
//    }
//    
//    public <T extends Actor> T addActor(EntityDef def, Object ID) {
//    	T actor = Actor.create(def, world, pixelsPerMeter, ID);
//    	actors2.put(def, actor);
//    	return actor;
//    }
//    
//    public ActorDef removeActor(Actor actor) {
//        return Actor.destroy(actor);
//    }
	
	//    public void centerOn(Entity entity) {
//        cameraTarget = entity;
//    }
	public void centerOn(Player player)
	{
		cameraTarget = player;
	}
	
	//adding item
//    public Actor addActor(PlayerDefinition def, Object ID) {
//    	assert Player.position != null;
//    	T actor = Actor.create(def, world, pixelsPerMeter, ID);
//    	 Player.createPlayer(ID, world, pixelsPerMeter);
//        GameConstants.actors.put(def, actor);
//    	System.out.println("added actor " + ID);
//    	return Player;
//    }

//    /**
//     * FIXME: fix this make it modular
//     * @param player
//     * @return
//     */
//    public boolean removeActor(Player player) {
//    	if (player.body.getUserData() instanceof Boolean) {
//    		return true;
//    	} else {
//            player.destroy();
//    		return false;
//    	}
//    }
	
	//removing item
	public ItemDefinition removeItem(Item item)
	{
		return Item.destroy(item);
	}

//    public void centerOn(Entity player) {
//        cameraTarget = player;
//    }
	
	public float toMeters(float pixels)
	{
		return pixels / pixelsPerMeter;
	}
	
	public float toPixels(float meters)
	{
		return meters * pixelsPerMeter;
	}
	
	@Override
	public void dispose()
	{
		if (ownsBatch)
		{
			batch.dispose();
		}
		
		world.dispose();
		rayHandler.dispose();
		b2dr.dispose();
	}
	
	public Batch getBatch()
	{
		return batch;
	}
	
	public int getWidth()
	{
		return tiledMap.getProperties().get("width", Integer.class);
	}
	
	public int getHeight()
	{
		return tiledMap.getProperties().get("height", Integer.class);
	}
	
	public int getTileWidth()
	{
		return tiledMap.getProperties().get("tilewidth", Integer.class);
	}
	
	public int getTileHeight()
	{
		return tiledMap.getProperties().get("tileheight", Integer.class);
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public RayHandler getRayHandler()
	{
		return rayHandler;
	}
	
	public float getAmbientLight()
	{
		return ambientLight;
	}
	
	public void setAmbientLight(float ambientLight)
	{
		rayHandler.setAmbientLight(ambientLight);
		GameConstants.ambientLight = ambientLight;
	}
	
	public float getZoom()
	{
		return camera.zoom;
	}
	
	public void setZoom(float zoom)
	{
		camera.zoom = zoom;
	}
	
	private Item removeItem(ItemObject item, ItemDefinition itemdef, World world)
	{
		item.reset(world);
		GameConstants.items.remove(itemdef);
		item = null;
		return item;
	}
	
	/**
	 * loads all the items.
	 */
	public void loadItems()
	{
		for (Entry<ItemDefinition, ItemObject> item : items.entrySet())
		{
			ItemDefinition di = item.getKey();
			items.put(di, addItem(di));
		}
	}
	
	/**
	 * loads the main player
	 */
	public void loadPlayer()
	{
//		EntityDef playerDef = new EntityDef();
//		
//		playerDef.animations = Animation.extract(SPRITE_SHEET, SPRITE_SHEET_ID, SPRITE_SHEET_GENDER_MALE);
//		playerDef.position.set(800, 1000);
//		GameConstants.player = addActor(playerDef, new String("player"));
//		actors2.put(playerDef, GameConstants.player);
		
		GameConstants.player = new Player().createPlayer(new String("player"), world, pixelsPerMeter);
		
		centerOn(GameConstants.player); //centers the map on the player
	}
	
	public void loadNewMap(TiledMap tiledmap)
	{
		MapProperties prop = tiledMap.getProperties();

//      int mapWidth = prop.get("width", Integer.class);
//      int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
//      int tilePixelHeight = prop.get("tileheight", Integer.class);

//      int mapPixelWidth = mapWidth * tilePixelWidth;
//      int mapPixelHeight = mapHeight * tilePixelHeight;
		
		createMap(tiledMap, tilePixelWidth);
		ownsBatch = true;

//      setAmbientLight(.4f);
//      setZoom(.35f);
////      GameConstants.player.teleport(800, 1000);
//      centerOn(GameConstants.player);
	}
	
}
