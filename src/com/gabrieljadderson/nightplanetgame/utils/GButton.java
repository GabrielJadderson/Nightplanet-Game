package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;

/**
 * @author Gabriel Jadderson
 */
public class GButton
{
	
	/**
	 * SpineObject
	 */
	SpineObject spine;
	boolean spineEnabled = false;
	/**
	 * Button Position
	 */
	Position position;
	/**
	 * Button Size
	 */
	Dimension size;
	/**
	 * Button Rectangle used for calculations.
	 */
	Rectangle r;
	/**
	 * tells us if we should check for projection or not. if this is enabled the mouse will be projected to ingame coords and checked with the button.
	 * THE BUTTON HAS TO HAVE VALID PROJECTED COORDS RELATIVE TO THE BATCH - THE BUTTON WILL NOT BE PROJECTED, ONLY THE MOUSE POSITION.
	 */
	boolean projected = false;
	
	/**
	 * Sound Effect, invoked when the button hasFocus
	 */
	Sound sfx = null;
	long sfxControl;
	
	/**
	 * Has been clicked/invoked
	 * the button is clicked when the mouse has clicked with the left mouse button.
	 */
	boolean isClicked = false;
	/**
	 * has gotten Focus.
	 * the button has focus when the mouse is within its rectangular bounds
	 */
	boolean hasFocus = false;
	
	/**
	 * displaySound
	 */
	boolean didPlaySound;
	
	/**
	 * The update speed of the button to perform the calculations.
	 */
	float updateSpeed = 0.005f;
	
	/**
	 * Creates a new Game Button with the specified position and the size, the projected boolean is used to indicate whether this button is projected or not.
	 * if the button is projected, the calculations will have to be different. setting the {@code projected} to true does not project the button.
	 * but it will project the mouse location relative to the button when calculating.
	 *
	 * @param position  position of the button, projected coordinates or screen coordiantes
	 * @param size      the size either projected or not.
	 * @param projected true if the button you are creating is part of the in-game world coords, false if the button is a UI element relative to the screen.
	 */
	public GButton(Position position, Dimension size, boolean projected)
	{
		this.position = position;
		this.size = size;
		this.projected = projected;
		
		r = new Rectangle(this.position.getX(), this.position.getY(), size.getWidth(), size.getHeight());
	}
	
	/**
	 * update method.
	 */
	private void update(float deltaTime)
	{
		if (spineEnabled) spine.update(deltaTime);
		
		Vector3 mouseTouchPoint;
		if (!projected)
			mouseTouchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		else
			mouseTouchPoint = GameConstants.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		if (r.contains(mouseTouchPoint.x, mouseTouchPoint.y))
		{
			if (!hasFocus) hasFocus = true;
			if (Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				isClicked = true;
				//TODO play animation here.
			}
		} else
		{
			if (hasFocus) hasFocus = false;
			if (isClicked) isClicked = false;
		}
		
		
	}
	
	/**
	 * the Rendering method must be included and rendered whenever this button has been created.
	 *
	 * @param batch
	 */
	public void render(Batch batch)
	{
		float delta = Gdx.graphics.getDeltaTime();
		float remaining = delta;
		while (remaining > 0)
		{
			float d = Math.min(updateSpeed, remaining);
			update(delta);
			remaining -= d;
		}
		
		if (spineEnabled) spine.render(batch);
		
		
	}
	
	private void playSFX()
	{
		sfxControl = sfx.play();
		sfx.setPan(sfxControl, 0f, 5f);
	}
	
	/**
	 * gets the focus state of the button
	 *
	 * @return true if the button has the mouse hovering on the rectangle of this button, false if the mouse is outside of the rectangle.
	 */
	public boolean hasFocus()
	{
		return hasFocus;
	}
	
	/**
	 * gets the invoke state
	 *
	 * @return true if this button is clicked, false otherwise.
	 */
	public boolean isInvoked()
	{
		return isClicked;
	}
	
	/**
	 * gets the com.gabrieljadderson.nightplanetgame.spine animation for this button
	 *
	 * @return the com.gabrieljadderson.nightplanetgame.spine object
	 */
	public SpineObject getSpine()
	{
		return spine;
	}
	
	/**
	 * gets the position of the button.
	 *
	 * @return the button position.
	 */
	public Position getPosition()
	{
		return position;
	}
	
	/**
	 * sets the position of this button
	 *
	 * @param position
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	/**
	 * sets the com.gabrieljadderson.nightplanetgame.spine animation for this button
	 */
	public void setSpine(String atlas, String json, float scale, float timeScale, boolean premultipliedAlpha, String animation, boolean loopAnimation)
	{
		spine = new SpineObject(atlas, json, scale, timeScale, premultipliedAlpha);
		spine.setPosition(position);
		spine.setAnimation(0, animation, loopAnimation);
	}
	
	/**
	 * sets the animation
	 *
	 * @param animation Animation name
	 */
	public void setAnimation(String animation)
	{
		spine.setAnimation(0, animation, false); //no need to loop...
	}
	
	/**
	 * sets the com.gabrieljadderson.nightplanetgame.spine
	 *
	 * @param spine
	 */
	public void setSpine(SpineObject spine)
	{
		if (spine != null) this.spine = spine;
	}
	
	/**
	 * sets the sound effect to be played when this button is pressed.
	 *
	 * @param sfxName only the name of the sound e.g. "SFX111.ogg"
	 */
	public void setSound(String sfxName)
	{
		if (sfxName != null)
		{
			sfx = GameConstants.assetManager.get("sounds/SFX/" + sfxName);
		}
	}
	
}
