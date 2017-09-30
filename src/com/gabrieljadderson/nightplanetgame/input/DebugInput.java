package com.gabrieljadderson.nightplanetgame.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.gabrieljadderson.nightplanetgame.GameConstants;

/**
 * @author Gabriel Jadderson
 */
public class DebugInput implements InputProcessor
{
	
	boolean enable_ZOOM = false;
	
	
	@Override
	public boolean keyDown(int keycode)
	{
		
		
		if (keycode == Keys.F5)
		{
//			GameConstants.NPCs.get(1).decrementHealth(new Hit(new RandomGen().floatRandom(100), HitType.NORMAL));
			GameConstants.gameScreen.lunarBar.increase();
		}
		if (keycode == Keys.F6)
		{
			GameConstants.gameScreen.lunarBar.decrease();
		}
		if (keycode == Keys.F7)
		{
		}
		if (keycode == Keys.F8)
		{
		}
		if (keycode == Keys.F9)
		{
		}
		if (keycode == Keys.F10)
		{
		}
		
		
		if (keycode == Keys.F3)
		{
//			GameConstants.chatBox.sendPlayerMessage("HELLO FROM THE OTHER SIEDE ");
			GameConstants.chatBox.FadeCoord.FadeIn(0.5f);
		}
		if (keycode == Keys.F4)
		{
//			GameConstants.chatBox.sendPlayerMessage("HELLO FROM THE OTHER SIEDE ");
			GameConstants.chatBox.FadeCoord.FadeOut(1f);
		}
		
		if (keycode == Keys.F1)
		{
			enable_ZOOM = !enable_ZOOM;
			if (enable_ZOOM) System.out.println("debug mouse wheel zoom enabled.");
			else System.out.println("debug mouse wheel zoom disabled.");
		}

//		System.out.println("" + keycode);
		if (keycode == Keys.F2)
		{
			GameConstants.toggleDebug();
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
	{ //right click?
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
		if (enable_ZOOM)
		{
			if (amount > 0)
			{
				GameConstants.camera.zoom += 0.2f;
				System.out.println(GameConstants.camera.zoom);
			} else
			{
				GameConstants.camera.zoom -= 0.2f;
				System.out.println(GameConstants.camera.zoom);
			}
		}
		return false;
	}
	
}
