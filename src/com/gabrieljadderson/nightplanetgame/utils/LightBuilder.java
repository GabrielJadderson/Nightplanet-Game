package com.gabrieljadderson.nightplanetgame.utils;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.gabrieljadderson.nightplanetgame.map.GameCollisionBits;

/**
 * @author Gabriel Jadderson
 */
public class LightBuilder
{
	
	/**
	 * creates a PointLight MIN_RAYS = 3;
	 */
	public static PointLight createPointLight(RayHandler rayHandler, int rays, Color c, float dist, float softness, float x, float y, boolean xRay)
	{
		PointLight pl = new PointLight(rayHandler, rays, c, dist, x, y);
		
		pl.setSoftnessLength(softness);
		pl.setSoft(true);
		pl.setXray(xRay);
		pl.setContactFilter(GameCollisionBits.LIGHT_BIT, GameCollisionBits.GROUP_INDEX_LIGHT, GameCollisionBits.LIGHT_MASK_BIT);
		return pl;
	}
	
	/**
	 * creates a ConeLight MIN_RAYS = 3;
	 */
	public static ConeLight createConeLight(RayHandler rayHandler, int rays, Color c, float dist, float dir, float cone, float x, float y, float softness, boolean xRay)
	{
		ConeLight cl = new ConeLight(rayHandler, rays, c, dist, x, y, dir, cone);
		cl.setSoftnessLength(softness);
		cl.setXray(xRay);
		cl.setContactFilter(GameCollisionBits.LIGHT_BIT, GameCollisionBits.GROUP_INDEX_LIGHT, GameCollisionBits.LIGHT_MASK_BIT);
		return cl;
	}
	
}
