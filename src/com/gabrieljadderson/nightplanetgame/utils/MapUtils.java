
package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;
import com.gabrieljadderson.nightplanetgame.map.GameCollisionBits;
import com.gabrieljadderson.nightplanetgame.map.Map;
import com.gabrieljadderson.nightplanetgame.map.objects.InGameObject;

import java.util.Map.Entry;

/**
 * @author Gabriel Jadderson
 */
public final class MapUtils
{
	
	public static Animation.Type directionToWalk(Direction direction)
	{
		switch (direction)
		{
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				return Animation.Type.WALK_NORTH;
			case SOUTH:
			case SOUTHEAST:
			case SOUTHWEST:
				return Animation.Type.WALK_SOUTH;
			case WEST:
				return Animation.Type.WALK_WEST;
			case EAST:
				return Animation.Type.WALK_EAST;
			default:
				return null;
		}
	}
	
	public static Direction directionSetToDirection(ObjectSet<Direction> directions)
	{
		if (directions.contains(Direction.NORTH) && directions.contains(Direction.SOUTH))
		{
			directions.remove(Direction.NORTH);
			directions.remove(Direction.SOUTH);
		}
		if (directions.contains(Direction.EAST) && directions.contains(Direction.WEST))
		{
			directions.remove(Direction.EAST);
			directions.remove(Direction.WEST);
		}
		if (directions.contains(Direction.NORTHEAST) && directions.contains(Direction.SOUTHWEST))
		{
			directions.remove(Direction.NORTHEAST);
			directions.remove(Direction.SOUTHWEST);
		}
		if (directions.contains(Direction.NORTHWEST) && directions.contains(Direction.SOUTHEAST))
		{
			directions.remove(Direction.NORTHWEST);
			directions.remove(Direction.SOUTHEAST);
		}
		if (directions.contains(Direction.NORTH))
		{
			if (directions.contains(Direction.EAST))
			{
				return Direction.NORTHEAST;
			}
			if (directions.contains(Direction.WEST))
			{
				return Direction.NORTHWEST;
			}
		}
		if (directions.contains(Direction.SOUTH))
		{
			if (directions.contains(Direction.EAST))
			{
				return Direction.SOUTHEAST;
			}
			if (directions.contains(Direction.WEST))
			{
				return Direction.SOUTHWEST;
			}
		}
		if (directions.size == 1)
		{
			return directions.first();
		}
		return null;
	}

//	public static Direction walkToDirection(Animation.Type animationType) {
//		switch (animationType) {
//		case WALK_NORTH:
//			return Direction.NORTH;
//		case WALK_SOUTH:
//			return Direction.SOUTH;
//		case WALK_WEST:
//			return Direction.WEST;
//		case WALK_EAST:
//			return Direction.EAST;
//		default:
//			return null;
//		}
//	}
	
	// public static Direction motionToDirection(Vector2 motion) {
	// if (motion.x != 0) {
	// if (motion.x < 0) {
	// return Direction.WEST;
	// } else if (motion.x > 0) {
	// return Direction.EAST;
	// }
	// } else if (motion.y != 0) {
	// if (motion.y > 0) {
	// return Direction.NORTH;
	// } else if (motion.y < 0) {
	// return Direction.SOUTH;
	// }
	// }
	//
	// return null;
	// }
	
	public static void createCollision(Map map, MapObjects objects)
	{
		for (MapObject object : objects)
		{
			Shape shape = MapUtils.createShape(map, object);
			if (shape != null)
			{
				BodyDef bodyDef = new BodyDef();
				bodyDef.fixedRotation = true;
				bodyDef.type = BodyType.StaticBody;
				
				Body body = map.getWorld().createBody(bodyDef);
				FixtureDef fixture = new FixtureDef();
				fixture.shape = shape;
				fixture.density = 1.0f;
				fixture.filter.categoryBits = GameCollisionBits.WALL_COLLISION_BIT;
				fixture.filter.groupIndex = GameCollisionBits.GROUP_INDEX_WALL;
				fixture.filter.maskBits = GameCollisionBits.WALL_MASK_BIT;
				body.createFixture(fixture);
				GameConstants.mapObjects_collision.put(object, body);
				shape.dispose();
			}
		}
	}
	
	public static void loadClickableObjects()
	{
		for (Entry<MapObject, Body> entry : GameConstants.mapObjects_collision.entrySet())
		{
			MapObject mo = entry.getKey();
			Body ob = entry.getValue();
			if (ob != null && mo != null)
			{
				if (mo.getName() != null)
				{
					if (mo.getName().contains("np_object_"))
					{
						InGameObject object = new InGameObject(mo, ob);
						GameConstants.mapObjects_clickableObjects.put(object.getID(), object);
					}
				}
			}
		}
	}
	
	private static Shape createShape(Map map, MapObject mapObject)
	{
		if (mapObject instanceof RectangleMapObject)
		{
			RectangleMapObject rectObject = (RectangleMapObject) mapObject;
			Rectangle oldRect = rectObject.getRectangle();
			Rectangle newRect = new Rectangle();
			newRect.x = map.toMeters(oldRect.x);
			newRect.y = map.toMeters(oldRect.y);
			newRect.width = map.toMeters(oldRect.width);
			newRect.height = map.toMeters(oldRect.height);
			return MapUtils.createRectangle(map, newRect);
		} else if (mapObject instanceof CircleMapObject)
		{
			CircleMapObject circleObject = (CircleMapObject) mapObject;
			return MapUtils.createCircle(map, circleObject.getCircle());
		} else if (mapObject instanceof EllipseMapObject)
		{
			// not supported
		} else if (mapObject instanceof PolylineMapObject)
		{
			PolylineMapObject polylineObject = (PolylineMapObject) mapObject;
			return MapUtils.createChain(map, polylineObject.getPolyline());
		} else if (mapObject instanceof PolygonMapObject)
		{
			PolygonMapObject polygonObject = (PolygonMapObject) mapObject;
			return MapUtils.createPolygon(map, polygonObject.getPolygon());
		}
		return null;
	}
	
	private static CircleShape createCircle(Map map, Circle circle)
	{
		CircleShape circleShape = new CircleShape();
		circleShape.setPosition(new Vector2(map.toMeters(circle.x), map.toMeters(circle.y)));
		circleShape.setRadius(map.toMeters(circle.radius));
		return circleShape;
	}
	
	private static PolygonShape createRectangle(Map map, Rectangle rectangle)
	{
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2, rectangle.getCenter(new Vector2()), 0);
		return polygonShape;
	}
	
	private static PolygonShape createPolygon(Map map, Polygon polygon)
	{
		float[] vertices = polygon.getTransformedVertices();
		for (int i = vertices.length - 1; i >= 0; i--)
		{
			vertices[i] = map.toMeters(vertices[i]);
		}
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.set(vertices);
		return polygonShape;
	}
	
	private static ChainShape createChain(Map map, Polyline polyline)
	{
		float[] vertices = polyline.getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		for (int i = 0; i < worldVertices.length; i++)
		{
			worldVertices[i] = new Vector2(map.toMeters(vertices[i * 2]), map.toMeters(vertices[i * 2 + 1]));
		}
		ChainShape cs = new ChainShape();
		cs.createChain(worldVertices);
		return cs;
	}
	
	/**
	 * creates and handles all animations for a tile in a layer. as input is takes a tileset of frames, the tileset
	 * must have an appropriate property called "PROPERTY::TILESET::FRAME" followed by an integer for the frame number to iterate through.
	 * etc. 1,2,3,4,5,6... then the tileset must be placed in the animationLayer a specific layer for querying animations.
	 * this layer can have any frame of the tileset, but try to use the first frame eg. the first tile in your tileset.
	 * the AnimationLayer must specify in the properties the amount of different tileset animations it contains. e.g one tileset = 1 animation
	 * if you have a water tileset you want to animate and a flame tileset you want to animate that is 2.
	 * if the AnimationLayer - PROPERRTY::ANIMATION::AMOUNT = 0 then no animation is displayed so make sure to update that number accordingly.
	 *
	 * @param LayerToQuery   the layer to query for animations e.g AnimationLayer is the default.
	 * @param animationSpeed 0.15f should be fine.
	 */
	public static void create_SingleTileAnimation_FromLayer(String LayerToQuery, float animationSpeed)
	{
		MaxSizeHashMap<Integer, TiledMapTileSet> tileSet = new MaxSizeHashMap<Integer, TiledMapTileSet>(8); //hashmap holding our stuff
		TiledMapTileLayer layer = (TiledMapTileLayer) GameConstants.mapTiledMap.getLayers().get(LayerToQuery); //get the layer instance
		Array<String> setNames = new Array<String>(8); //array containing all of the tileset names.
		Array<StaticTiledMapTile> animationTiles = new Array<StaticTiledMapTile>(); //array containing our animatedTiles
		if (layer != null)
		{ //checking if the layer is not null ofc.
			if (layer.getProperties() != null)
			{ //properties null check. for the layer.
				int AnimationAmount = Integer.parseInt((String) layer.getProperties().get("PROPERTY::ANIMATION::AMOUNT"));
				for (int i = 1; i <= AnimationAmount; i++)
				{ //traverse over the properties and get the amount of
					if (i == 10) //important, because the size is limited to 9 only
						break;
					setNames.add((String) layer.getProperties().get("PROPERTY::ANIMATION::NAME_" + i));//add the names we got from the properties into the array.
				}
			}
			int count = 0; //
			for (String name : setNames)
			{ //traverse through all the of the setnames
				if (count == 8)
					break;
				tileSet.put(count, GameConstants.mapTiledMap.getTileSets().getTileSet(name)); //get the count and get the tileset from the map and put it into the hashmap
				count++;
			}
			for (Entry<Integer, TiledMapTileSet> set : tileSet.entrySet())
			{ //getting the hashmap entries and traversing.
				for (TiledMapTile Tile : set.getValue())
				{ //traversing over the set AND OVER THE TILES SEPERATELY
					if (Tile.getProperties().containsKey("PROPERTY::TILESET::FRAME"))
					{ //check if the tile has the correct properties set-up. also eliminates conflicting tiles, and returns only the tiles with the property we want.
						animationTiles.add(new StaticTiledMapTile(Tile.getTextureRegion()));
					}
				}
			}
			for (int x = 0; x < layer.getWidth(); x++)
			{ //traversing the x-axis of the layer to find the cells we are looking for.
				for (int y = 0; y < layer.getHeight(); y++)
				{ //traversing the y-axis of the layer to find the cells we are looking for.
					TiledMapTileLayer.Cell cell = layer.getCell(x, y); //we create an instance of each cell
					if (cell != null && cell.getTile().getProperties().containsKey("PROPERTY::TILESET::FRAME"))
					{//check if that cell above is not null and its properties too.
						cell.setTile(new AnimatedTiledMapTile(animationSpeed, animationTiles));
					}
				}
			}
		}
	}
	
	
	/**
	 * queries a layer for tileset called 'Light@64x64' whereever these tiles are a light is created from the properties
	 * given within the tiles.
	 *
	 * @param LayerToQuery default is "LightPropertyLayer", consult ur map for more info.
	 */
	public static void create_LIGHTS_FromLayer(String LayerToQuery)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) GameConstants.mapTiledMap.getLayers().get(LayerToQuery);
		if (layer != null)
		{
			for (int x = 0; x < layer.getWidth(); x++)
			{
				for (int y = 0; y < layer.getHeight(); y++)
				{
					TiledMapTileLayer.Cell cell = layer.getCell(x, y);
					if (cell != null)
					{
						if (cell.getTile() != null)
						{
							if (cell.getTile().getProperties() != null)
							{
								int RAYS = Integer.parseInt((String) cell.getTile().getProperties().get("LIGHT::RAYS"));
								float CONE = Float.parseFloat((String) cell.getTile().getProperties().get("LIGHT::CONE"));
								float DIRECTION = Float.parseFloat((String) cell.getTile().getProperties().get("LIGHT::DIRECTION"));
								float DISTANCE = Float.parseFloat((String) cell.getTile().getProperties().get("LIGHT::DISTANCE"));
								float SOFTNESS = Float.parseFloat((String) cell.getTile().getProperties().get("LIGHT::SOFTNESS"));
								boolean X_RAY = Boolean.parseBoolean((String) cell.getTile().getProperties().get("LIGHT::X-RAY"));
								Color COLOR = TextUtils.formatColor((String) cell.getTile().getProperties().get("LIGHT::COLOR"));
								if (cell.getTile().getProperties().get("LIGHT::TYPE").equals("CONE"))
								{
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("TOP"))
									{
										LightBuilder.createConeLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, DIRECTION, CONE, x, y, SOFTNESS, X_RAY);
									}
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("BOT"))
									{
										LightBuilder.createConeLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, DIRECTION, CONE, x, y, SOFTNESS, X_RAY);
									}
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("LEFT"))
									{
										LightBuilder.createConeLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, DIRECTION, CONE, x, y, SOFTNESS, X_RAY);
									}
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("RIGHT"))
									{
										LightBuilder.createConeLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, DIRECTION, CONE, x, y, SOFTNESS, X_RAY);
									}
								}
								if (cell.getTile().getProperties().get("LIGHT::TYPE").equals("POINT"))
								{
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("TOP"))
									{
										LightBuilder.createPointLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, SOFTNESS, x, y, X_RAY);
									}
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("BOT"))
									{
										LightBuilder.createPointLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, SOFTNESS, x, y, X_RAY);
									}
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("LEFT"))
									{
										LightBuilder.createPointLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, SOFTNESS, x, y, X_RAY);
									}
									if (cell.getTile().getProperties().get("LIGHT::POSITION").equals("RIGHT"))
									{
										LightBuilder.createPointLight(GameConstants.rayHandler, RAYS, COLOR, DISTANCE, SOFTNESS, x, y, X_RAY);
									}
								}
								cell.setTile(null); //important, we dont want to render the cells that contain the light, but create a light instance of each cell's properties.
							}
						}
					}
				}
			}
		}
	}
	
	
	public static void create_NPCs_FromLayer(String LayerToQuery)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) GameConstants.mapTiledMap.getLayers().get(LayerToQuery);
		int counter_boss = 0;
		int counter_npc = 0;
		if (layer != null)
		{
			for (int x = 0; x < layer.getWidth(); x++)
			{
				for (int y = 0; y < layer.getHeight(); y++)
				{
					TiledMapTileLayer.Cell cell = layer.getCell(x, y);
					if (cell != null)
					{
						if (cell.getTile() != null)
						{
							if (cell.getTile().getProperties() != null)
							{
								
								if (cell.getTile().getProperties().containsKey("BOSS_NPC::ID"))
								{ //if a boss, handle as a boss.
									int ID = Integer.parseInt((String) cell.getTile().getProperties().get("BOSS_NPC::ID"));
									System.out.println("BOSS ID: " + ID + " Xpos: " + x + " Ypos: " + y);
									GameConstants.NPCs.get(ID).moveTo(x, y);
									counter_boss++;
								} else if (cell.getTile().getProperties().containsKey("NPC::ID"))
								{ // if an npc, handle as an npc.
									int ID = Integer.parseInt((String) cell.getTile().getProperties().get("NPC::ID"));
									System.out.println("NPC ID: " + ID + " Xpos: " + x + " Ypos: " + y);
									//							GameConstants.NPCs.get(ID).moveTo(x,y);
									//TODO ENABLE THIS, currently the tiled map contains 3 npcs one is boss one is not, but we are only loading 1 npc atm, so fix this.
									counter_npc++;
								}
								
								cell.setTile(null); //important, we dont want to render the cell.
							}
						}
					}
				}
			}
			System.out.println("Loaded: " + counter_npc + " NPCs\n" + "Loaded: " + counter_boss + " Bosses");
		}
	}
	
	
	public static Vector3 screenPixelsToMeters(float x, float y)
	{
		return GameConstants.camera.unproject(new Vector3(x, y, 0));
	}
	
	public static Vector3 MetersToScreenPixels(float x, float y)
	{
		return GameConstants.camera.project(new Vector3(x, y, 0));
	}
	
	/**
	 * converts the given pixel value into world coordinates. this is done maunally by multiplying by 0.02f
	 *
	 * @param pixels
	 * @return
	 */
	public static float pixelsToMeters(int pixels)
	{
		return (float) pixels * 0.02f;
	}
	
	/**
	 * given the world coordinate "meters" converts it to screen coordinates. this is done manually by multiplying with 50.0f
	 *
	 * @param meters
	 * @return
	 */
	public static int metersToPixels(float meters)
	{
		return (int) (meters * 50.0f);
	}
}
