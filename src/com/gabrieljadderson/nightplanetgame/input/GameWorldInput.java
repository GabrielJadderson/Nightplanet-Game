
package com.gabrieljadderson.nightplanetgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.utils.MaxSizeHashMap;

/**
 * @author Gabriel Jadderson
 */
public class GameWorldInput extends InputAdapter
{
	
	private Array<Integer> keyPressedList = new Array<Integer>();
	private Array<Integer> keyToggledList = new Array<Integer>();
	
	//input processing

//		public boolean scrolled(int amount){
//			cam.zoom += (float)amount * 0.04f;
//			cam.update();
//			return false;
//		}

//		public boolean touchUp(int x, int y, int pointer, int button) {
//			lightMove = !lightMove;
//			return false;
//		}
	
	/* mouse region and player on-screen handling  */
	private MaxSizeHashMap<Integer, Rectangle> screenRegions = new MaxSizeHashMap<Integer, Rectangle>(3);
	private float rect_width = 512f; // 1024/2 for getting 2 regions in a width.
	private float rect_height = 384f; // 768/2 for 2 regions in a height
	private Rectangle r_0_northWest = new Rectangle(0f, 0f, rect_width, rect_height);
	private Rectangle r_1_northEast = new Rectangle(512f, 0f, rect_width, rect_height);
	private Rectangle r_2_southWest = new Rectangle(0f, 384f, rect_width, rect_height);
	private Rectangle r_3_southEast = new Rectangle(512f, 384f, rect_width, rect_height);
	private Rectangle r_4_north = new Rectangle(400f, 0f, 300f, 384f);
	private Rectangle r_5_west = new Rectangle(0f, 242f, 512f, 230f);
	private Rectangle r_6_south = new Rectangle(400f, 384f, 300f, 384f);
	private Rectangle r_7_east = new Rectangle(512f, 242f, 512f, 230f);
	private boolean recordMouseMovement = false; //used to make sure that the user doesn't abuse the system.
	private boolean keypressInvoked = false; //used to make sure that the user doesn't use the mouse and keypad to cheat an navigate faster.
	private int invoke_C = 0;
	/* mouse region and player on-screen handling */
	
	static Integer[] temp = {
			Keys.A,
			Keys.LEFT,
			Keys.W,
			Keys.UP,
			Keys.D,
			Keys.RIGHT,
			Keys.S,
			Keys.DOWN,
			Keys.K,
			Keys.L,
			Keys.F2,
			Keys.ESCAPE};
	
	public static ObjectSet<Integer> validKeys()
	{
		ObjectSet<Integer> validKeys = new ObjectSet<Integer>();
		for (int key : temp)
		{
			validKeys.add(key);
		}
		return validKeys;
	}
	
	private boolean isValidKey(Integer keycode)
	{
		if (keycode != null && GameWorldInput.validKeys().contains(keycode))
		{
			return true;
		}
		return false;
	}
	
	private Integer lastKeyPressed()
	{
		if (keyPressedList.size == 0)
		{
			return null;
		}
		return keyPressedList.get(keyPressedList.size - 1);
	}
	
	private Integer lastKeyToggled()
	{
		if (keyToggledList.size == 0)
		{
			return null;
		}
		return keyToggledList.get(keyToggledList.size - 1);
	}
	
	private void addKeyPressed(int keycode)
	{
		if (isValidKey(keycode))
		{
			removeKeyPressed(keycode);
			keyPressedList.add(keycode);
		}
	}
	
	private void handleKeyToggled(int keycode)
	{
		if (isValidKey(keycode))
		{
			if (isKeyToggled(keycode))
			{
				keyToggledList.removeValue(keycode, true);
			} else
			{
				keyToggledList.add(keycode);
			}
		}
	}
	
	private void removeKeyPressed(int keycode)
	{
		for (Integer keyPressed : new Array<Integer>(keyPressedList))
		{
			if (keyPressed == keycode)
			{
				keyPressedList.removeValue(keyPressed, true);
			}
		}
	}
	
	/**
	 * checks if the touch is invoked and returns an integer if that integer is between 1 and 8 then an action is invoked.
	 *
	 * @return
	 */
	public int isTouchInvoked()
	{
		if (invoke_C != 0 && invoke_C <= 8 && !keypressInvoked && recordMouseMovement)
		{
			return invoke_C;
		} else
		{
			return -1;
		}
	}
	
	public boolean isKeyPressed(int keycode)
	{
		return keyPressedList.contains(keycode, true);
	}
	
	public boolean isKeyToggled(int keycode)
	{
		return keyToggledList.contains(keycode, true);
	}
	
	public boolean isLastKeyPressed(int keycode)
	{
		return lastKeyPressed() == keycode;
	}
	
	public boolean isLastKeyToggled(int keycode)
	{
		return lastKeyToggled() == keycode;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		keypressInvoked = true;
		recordMouseMovement = false;
		if (!recordMouseMovement && keypressInvoked)
		{
			addKeyPressed(keycode);
			handleKeyToggled(keycode);
		}
		return super.keyDown(keycode);
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		keypressInvoked = false;
		removeKeyPressed(keycode);
		
		if (keycode == Keys.NUM_1)
		{
			GameConstants.renderManager.currentShader = GameConstants.renderManager.dayShader;
			System.err.println("dayShader on ");
		} else if (keycode == Keys.NUM_2)
		{
			GameConstants.renderManager.currentShader = GameConstants.renderManager.nightShader;
			System.err.println("nightShader on ");
		} else if (keycode == Keys.NUM_3)
		{
			GameConstants.renderManager.currentShader = GameConstants.renderManager.lightShader;
			System.err.println("lightshader on ");
		} else if (keycode == Keys.NUM_4)
		{
			GameConstants.renderManager.currentShader = GameConstants.renderManager.finalShader;
			System.err.println("finalShader on ");
		} else if (keycode == Keys.SPACE)
		{
			GameConstants.renderManager.lightOscillate = !GameConstants.renderManager.lightOscillate;
			System.err.println("lightOscillate on ");
		} else if (keycode == Keys.NUM_5)
		{
			GameConstants.camera.rotate(0, 0, 0, 20);
			GameConstants.camera.update(true);
		} else if (keycode == Keys.NUM_6)
		{
			GameConstants.camera.rotate(0, 0, 0, -10);
			GameConstants.camera.update(true);
		} else if (keycode == Keys.NUM_7)
		{
			GameConstants.camera.rotate(0, 0, 10, 0);
			GameConstants.camera.update(true);
		} else if (keycode == Keys.NUM_8)
		{
			GameConstants.camera.rotate(0, 0, -10, 0);
			GameConstants.camera.update(true);
		} else if (keycode == Keys.NUM_9)
		{
			GameConstants.camera.rotate(10, 0, 0, 0);
			GameConstants.camera.update(true);
		} else if (keycode == Keys.TAB)
		{
			Gdx.app.exit();
		}
		
		return super.keyUp(keycode);
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		calculateMovement(screenX, screenY);
		return super.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return super.mouseMoved(screenX, screenY);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{ //right click?
		removeMouseInput();
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		startMouseInput();
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	/**
	 * turns the recordmouseMovement boolean off and therefore cannot be used to update the player movement. this stops the timertask in touchDown from running.
	 */
	private void removeMouseInput()
	{
		recordMouseMovement = false;
		invoke_C = -1;
	}
	
	/**
	 * invoked when the touchdown method is invoked this creates a timer task that will update the player movement.
	 */
	private void startMouseInput()
	{
		recordMouseMovement = true;
	}
	
	/**
	 * calculates the movement of the player relative to the mouse input
	 * FIXME: fix this make this easier to calculate and much more efficient than it already is.
	 *
	 * @param screenX
	 * @param screenY
	 */
	private void calculateMovement(float screenX, float screenY)
	{
		if (recordMouseMovement && !keypressInvoked)
		{
			if (r_4_north.contains(screenX, screenY))
			{
				invoke_C = 1;
			} else if (r_6_south.contains(screenX, screenY))
			{
				invoke_C = 2;
			} else if (r_5_west.contains(screenX, screenY))
			{
				invoke_C = 3;
			} else if (r_7_east.contains(screenX, screenY))
			{
				invoke_C = 4;
			} else if (r_0_northWest.contains(screenX, screenY) && !r_4_north.contains(screenX, screenY) && !r_5_west.contains(screenX, screenY))
			{
				invoke_C = 5;
			} else if (r_1_northEast.contains(screenX, screenY) && !r_4_north.contains(screenX, screenY) && !r_7_east.contains(screenX, screenY))
			{
				invoke_C = 6;
			} else if (r_2_southWest.contains(screenX, screenY) && !r_5_west.contains(screenX, screenY) && !r_6_south.contains(screenX, screenY))
			{
				invoke_C = 7;
			} else if (r_3_southEast.contains(screenX, screenY) && !r_6_south.contains(screenX, screenY) && !r_6_south.contains(screenX, screenY))
			{
				invoke_C = 8;
			}
		} else
		{
			invoke_C = -1;
		}
	}
	
}
