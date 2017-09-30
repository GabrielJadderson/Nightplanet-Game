package com.gabrieljadderson.nightplanetgame;

/**
 * @author Gabriel Jadderson
 */
public class GameWorld implements Runnable
{

//    private static CharacterList<Player> players = new CharacterList<>(2000);
	
	private String name;
	
	
	public GameWorld(String nv)
	{
		name = nv;
		
	}
	
	public GameWorld()
	{
	
	}
	
	@Override
	public void run()
	{
		if (name != null)
		{
			System.out.println(name);
		} else
		{
			System.out.println("GameWorld initiated.");
		}
		
	}
	
	
}
