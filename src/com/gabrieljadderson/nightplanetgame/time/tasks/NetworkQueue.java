package com.gabrieljadderson.nightplanetgame.time.tasks;

import com.gabrieljadderson.nightplanetgame.GameConstants;

import java.util.TimerTask;

/**
 * @author Gabriel Jadderson
 */
public class NetworkQueue extends TimerTask
{
	
	public static int taskNumber = 1; //!!!! 1 
	public static long timeBetweenTasks = 1000; //milliseconds
	public static long initDelay = 0L;
	
	private boolean stop = false;
	
	@Override
	public void run()
	{
		
		if (!stop)
		{
			
			
			sendPlayerPosition();
		} else
		{
			//do something to stop
		}
		
	}
	
	
	/**
	 * i would like to see a better implementation of this..
	 */
	private void sendPlayerPosition()
	{
		
		if (GameConstants.player != null)
		{
			// update the position when the body moves. 
			if ((int) GameConstants.player.getBodyX() != (int) GameConstants.player.getPosition().getX() || (int) GameConstants.player.getBodyY() != (int) GameConstants.player.getPosition().getY())
			{
				GameConstants.player.getPosition().setVector2Position(GameConstants.player.getBody().getPosition());
				GameConstants.client_Position.pushNewPosition(GameConstants.player.getPosition());
			}
		}
	}
	
	
}
