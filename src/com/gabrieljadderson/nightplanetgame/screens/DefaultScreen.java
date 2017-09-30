package com.gabrieljadderson.nightplanetgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * an abstract class that imeplements the screen interface and is used for creating screens.
 * simply extend this class.
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * @since 14/12/2015
 */

public abstract class DefaultScreen implements Screen
{
	
	/**
	 * game instance
	 */
	protected Game game;
	
	/**
	 * constructor that takes in a game instance.
	 *
	 * @param game
	 */
	public DefaultScreen(Game game)
	{
		this.game = game;
	}
	
	/**
	 * resize the screen into a new width and height given by the parameters.
	 *
	 * @param newWidth
	 * @param newHeight
	 */
	public void resize(int newWidth, int newHeight)
	{
	
	}
	
	/**
	 * clears the screen by GL20 buffer bit and invokes the clearcolor method:
	 * {@code
	 * Gdx.gl.glClearColor(0, 0, 0, 1);
	 * }
	 */
	public void clearScreen()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * abstract
	 */
	public abstract void pause();
	
	/**
	 * abstract
	 */
	public abstract void resume();
	
	/**
	 * abstract
	 */
	public abstract void dispose();
	
	
}
