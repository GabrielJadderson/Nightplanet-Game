package com.gabrieljadderson.nightplanetgame.map.objects;

import box2dLight.ConeLight;
import box2dLight.Light;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.anim.ItemAnimator;


/**
 * @author Gabriel Jadderson
 */
public class InGameObject
{
	
	int id; //the id of the object
	String name;
	Vector2 position; //the position.
	float rotation; //rotation of the object
	int changeMapValue; //if -1 the map will not be changed
	boolean clickable; //if true the object can trigger an ingame change?. 
	boolean isvisible; //returns true if the object is visible, false otherwise.
	int doorState; //0 closed, 1 open.
	Vector2 size;
	float width;
	float height;
	float x;
	float y;
	MapObject mpObject;
	Body body;
	
	private Array<Light> lights;
	ConeLight f;
	private ItemAnimator itemAnimator;
	
	/**
	 * c'tor
	 *
	 * @param mapObject the mapobject retrieved from the loaded map.
	 * @param body,     the body of that object.
	 */
	public InGameObject(MapObject mapObject, Body body)
	{
		if (mapObject != null && body != null)
		{
			this.body = body;
			this.mpObject = mapObject;
			
			
			this.name = mapObject.getName();
			this.isvisible = mapObject.isVisible();
			this.changeMapValue = Integer.parseInt((String) mapObject.getProperties().get("change:map"));
			this.id = Integer.parseInt((String) mapObject.getProperties().get("id"));
			this.clickable = Boolean.parseBoolean((String) mapObject.getProperties().get("clickable"));
			this.width = Float.parseFloat((String) mapObject.getProperties().get("width"));
			this.height = Float.parseFloat((String) mapObject.getProperties().get("height"));
			this.x = Float.parseFloat((String) mapObject.getProperties().get("posX"));
			this.y = Float.parseFloat((String) mapObject.getProperties().get("posY"));
			if (mapObject.getProperties().get("door").equals("closed"))
			{
				this.doorState = 0;
			} else if (mapObject.getProperties().get("door").equals("open"))
			{
				this.doorState = 1;
			}
			
			
			//the position of the ingame object is different because the object is relative to the map so we must substract its position from the map first.
			//e.g. testmap 3 has a x_max property of 1600, if the object has coords y = 212 then we must substract 212 from 1600. 212-1600 = ca. 1400
			//the 1400 is then used as a reference because the map starts it tiles from the top left where as the engine draws the tiles from the bottom left.
			//so what we're actually doing is flipping the y-axis. 
			this.position = new Vector2(x / GameConstants.pixelsPerMeter, (GameConstants.map_max_Y - y) / GameConstants.pixelsPerMeter);
			this.size = new Vector2(width / GameConstants.pixelsPerMeter, height / GameConstants.pixelsPerMeter);
			
			body.setActive(true);
			body.setType(BodyType.StaticBody);
			body.setAwake(false);
			body.getPosition().set(position);
			
			itemAnimator = new ItemAnimator(); //ia
			lights = new Array<Light>();

//			itemAnimtor.animateLight(7.3f, 0.2f, 0.1f, 0.15f, 0.19f, -1f, 0f, true);
//			itemAnimtor.animateLightCircle(1200f, 0.015f, true);

//			f = LightBuilder.createConeLight(GameConstants.map.getRayHandler(), 800, Color.BLUE, GameConstants.map.toMeters(20f), -90f, 900f, 0f, 0f, 200f, true);
			f = null;
			attachLight(f);
//			itemAnimator.flickerLights(f); //animation
		
		}
	}
	
	/**
	 * this is invoked when colision is iminent
	 */
	public void invoke()
	{
		System.out.println("name: " + name + " id: " + id + "pos: " + position.x + " y: " + position.y + " size: " + size);
		System.out.println("player x: " + GameConstants.player.getBodyX() + " y: " + GameConstants.player.getBodyY());
	}
	
	/**
	 * the update protocol for the object
	 */
	public void update()
	{

//		itemAnimator.update();
//
//		for (int i = lights.size - 1; i >= 0; i--) {
//			Light light = lights.get(i);
//			float lightX = body.getPosition().x + (size.x / 2f);
//			float lightY = body.getPosition().y + ((size.y / 2f) - 0.25f);
//			itemAnimator.setLightX(lightX); itemAnimator.setLightY(lightY);
//			light.setPosition(itemAnimator.getCircleX(), itemAnimator.getCircleY());
//		}
		
		//checks for inputs. 
		if (Gdx.input.justTouched())
		{
			if (contains(GameConstants.mouseTouchPoint.x, GameConstants.mouseTouchPoint.y))
			{
				if (Gdx.input.isButtonPressed(Buttons.RIGHT))
				{ //right button
					System.out.println("right - object: " + this.name + " " + this.id);
				}
				if (Gdx.input.isButtonPressed(Buttons.LEFT))
				{ //left button
					System.out.println("left - object: " + this.name + " " + this.id);
				}
			}
		}
		
	}
	
	
	/**
	 * checks if the given float x and y contains within the x-pos and y-pos and width and height of this item.
	 *
	 * @param x point x coordinate
	 * @param y point y coordinate
	 * @return whether the point is contained in the rectangle
	 */
	public boolean contains(float x, float y)
	{//TODO: FIXME: FLIP THE POSITION SO THAT THE DRAWING OF THE X AND Y is drawn from top left instead of bottom left????
		return this.position.x <= x && this.position.x + this.size.x >= x && this.position.y >= y && this.position.y - this.size.y <= y;
	}
	
	/**
	 * gets the body of this object.
	 *
	 * @return body
	 */
	public Body getBody()
	{
		return this.body;
	}
	
	/**
	 * gets the id of the object
	 *
	 * @return the object id number.
	 */
	public int getID()
	{
		return this.id;
	}
	
	/**
	 * gets the name of the object
	 *
	 * @return the name of the object.
	 */
	public String getName()
	{
		return this.name;
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
	
	
}
