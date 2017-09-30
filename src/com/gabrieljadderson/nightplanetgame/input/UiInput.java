package com.gabrieljadderson.nightplanetgame.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.gabrieljadderson.nightplanetgame.GameConstants;

/**
 * @author Gabriel Jadderson
 */
public class UiInput implements InputProcessor
{
	
	boolean hasPaused = false;
	
	@Override
	public boolean keyDown(int keycode)
	{
		
		//not a great way but what ever. 
		if (keycode == Keys.ESCAPE)
		{
			hasPaused = !hasPaused;
			if (hasPaused)
			{
				GameConstants.gameScreen.pause();
			} else
			{
				GameConstants.gameScreen.resume();
			}
			return true;
		} else
		{
			return false;
		}
		
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
}
