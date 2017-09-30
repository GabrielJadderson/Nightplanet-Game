package com.gabrieljadderson.nightplanetgame.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.utils.Direction;

/**
 * @author Gabriel Jadderson
 */
public class GamePadListener implements ControllerListener
{
	
	@Override
	public void connected(Controller controller)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disconnected(Controller controller)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean buttonDown(Controller controller, int buttonCode)
	{
		
		return false;
	}
	
	@Override
	public boolean buttonUp(Controller controller, int buttonCode)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value)
	{
		
		// Left Stick
		if (axisCode == XBox360Pad.AXIS_LEFT_X)
		{
			System.out.println("left x axis " + value);
			GameConstants.player.move(Direction.WEST, value);
		}
		
		if (axisCode == XBox360Pad.AXIS_LEFT_Y)
//            sprite.translateY(-10f * value);
			
			// Right stick
			if (axisCode == XBox360Pad.AXIS_RIGHT_X)
//            sprite.rotate(10f * value);
				return false;
		
		return false;
	}
	
	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
