package com.gabrieljadderson.nightplanetgame.map;

import com.badlogic.gdx.physics.box2d.*;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.anim.NPC;
import com.gabrieljadderson.nightplanetgame.map.items.ItemObject;
import com.gabrieljadderson.nightplanetgame.map.npc.Hit;
import com.gabrieljadderson.nightplanetgame.map.npc.HitType;
import com.gabrieljadderson.nightplanetgame.map.objects.InGameObject;
import com.gabrieljadderson.nightplanetgame.utils.RandomGen;

import static com.gabrieljadderson.nightplanetgame.map.GameCollisionBits.*;

/**
 * @author Gabriel Jadderson
 */
public class GameCollision implements ContactListener
{
	
	public GameCollision()
	{
	
	
	}
	
	@Override
	public void beginContact(Contact contact)
	{
		// TODO Auto-generated method stub
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();
		
		//TODO: remove these a and b constants and call directly.
		if (a != null && b != null)
		{ //null check just to make sure. - walls are null, so its also a big performance improvement.
//			System.out.println("wall:  a: " + a.getPosition() + " data: " + a.getUserData() + " b: " + b.getPosition() + " data: " + b.getUserData());
//			if (a.getUserData() == null && b.getUserData() != null || b.getUserData() == null && a.getUserData() != null) { //checks if there was a wall colision.
//				System.out.println("wall:  a: " + a.getPosition() + " data: " + a.getUserData() + " b: " + b.getPosition() + " data: " + b.getUserData());
//				contact.getFixtureA().getFilterData().categoryBits;
//				if (a == GameConstants.player.getBody() && b != GameConstants.player.getBody() || b == GameConstants.player.getBody() && a != GameConstants.player.getBody()) {
//					GameConstants.player.getBody().getFixtureList().items[1].getFilterData().maskBits = 1;
//				} 
//			}
			
			if (contact.getFixtureA().getFilterData().categoryBits == BULLET_BIT && contact.getFixtureB().getFilterData().categoryBits == PLAYER_COLLISION_BIT ||
					contact.getFixtureB().getFilterData().categoryBits == BULLET_BIT && contact.getFixtureA().getFilterData().categoryBits == PLAYER_COLLISION_BIT)
			{
				//if hit player, do nothing for now.
			}
			if (contact.getFixtureA().getFilterData().categoryBits == BULLET_BIT && contact.getFixtureB().getFilterData().categoryBits == WALL_COLLISION_BIT ||
					contact.getFixtureB().getFilterData().categoryBits == BULLET_BIT && contact.getFixtureA().getFilterData().categoryBits == WALL_COLLISION_BIT)
			{
				if (a.getUserData() != null)
				{
					if (a.getUserData().equals("BULLET"))
					{
						contact.getFixtureA().getBody().setUserData(new Integer(0));
					}
				} else
				{
					if (b.getUserData() != null)
					{
						if (b.getUserData().equals("BULLET"))
						{
							contact.getFixtureB().getBody().setUserData(new Integer(0));
						}
					}
				}
				//if hit wall, delete bullet.
			}
			
			if (contact.getFixtureA().getFilterData().categoryBits == BULLET_BIT && contact.getFixtureB().getFilterData().categoryBits == NPC_COLLISION_BIT ||
					contact.getFixtureB().getFilterData().categoryBits == BULLET_BIT && contact.getFixtureA().getFilterData().categoryBits == NPC_COLLISION_BIT)
			{
				if (a.getUserData() != null)
				{
					if (a.getUserData().equals("BULLET"))
					{
						contact.getFixtureA().getBody().setUserData(new Integer(0));
					}
					for (NPC npc : GameConstants.NPCs.values())
					{
						if (npc.getDefinition().getBody() == b || npc.getDefinition().getBody() == a)
						{
							RandomGen r = new RandomGen();
							float dd = r.floatRandom(100.0f);
							Hit hit = new Hit(dd, HitType.NORMAL); //TEST: 
							npc.decrementHealth(hit);
							break;
						}
					}
				} else
				{
					if (b.getUserData() != null)
					{
						if (b.getUserData().equals("BULLET"))
						{
							contact.getFixtureB().getBody().setUserData(new Integer(0));
							return;
						}
					}
				}
				//if hit npc, delete bullet. fuck the npc up
			}
			
			
			/* check if user colided with object */
			for (InGameObject object : GameConstants.mapObjects_clickableObjects.values())
			{
				if (object.getBody() == a || object.getBody() == b)
				{
					object.invoke();
				}
			}
			
			
			/* check if user colided with item */
			for (ItemObject item : GameConstants.items.values())
			{
				if (item != null)
				{//set check here for visiblity, in order to prevent querying all items... not eficient.
					if (item.getBody() == a || item.getBody() == b)
					{
						item.pickUpItem();
					}
				}
			}
			
			
		}
		
		
	}
	
	//test
/*	public void getCollisionTilesFrom(TiledMapTileLayer layer, Body a, Body b) {
		if (layer.getCell((int)a.getPosition().x, (int)a.getPosition().y) != null) {
			TiledMapTileLayer.Cell cell = layer.getCell((int)a.getPosition().x, (int)a.getPosition().y);
			System.out.println(cell.getTile().getId()); // Get the ID.
		}
	}*/
	
	@Override
	public void endContact(Contact contact)
	{
		// TODO Auto-generated method stub
//		System.out.println("did collide");
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold)
	{

//		Body a = contact.getFixtureA().getBody();
//		Body b = contact.getFixtureB().getBody();
//		
//		Object dataA = a.getUserData();
//		Object dataB = b.getUserData();
//		// TODO Auto-generated method stub
//		if (dataA.equals("player") && dataB.equals(1)) {
//			Map.removeItem(1);
//		}
//		System.out.println("did collide pre");
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse)
	{
		
		// TODO Auto-generated method stub
//		System.out.println("did collide");
	}
	
	
}

