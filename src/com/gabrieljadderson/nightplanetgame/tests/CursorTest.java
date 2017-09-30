package com.gabrieljadderson.nightplanetgame.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

/**
 * @author Gabriel Jadderson
 */
public class CursorTest
{
	Cursor cursor1;
	Cursor cursor2;
	Cursor cursor3;
	boolean cursorActive = false;
	
	public void create()
	{
		
		Pixmap pixmap1 = new Pixmap(Gdx.files.internal("data/bobargb8888-32x32.png"));
		cursor1 = Gdx.graphics.newCursor(pixmap1, 16, 16);
		
		Pixmap pixmap2 = new Pixmap(32, 32, Format.RGBA8888);
		pixmap2.setColor(Color.RED);
		pixmap2.fillCircle(16, 16, 8);
		cursor2 = Gdx.graphics.newCursor(pixmap2, 16, 16);
		
		Pixmap pixmap3 = new Pixmap(32, 32, Format.RGBA8888);
		pixmap3.setColor(Color.BLUE);
		pixmap3.fillCircle(16, 16, 8);
		cursor3 = Gdx.graphics.newCursor(pixmap3, 16, 16);
		
	}
	
	public void render()
	{
		// set the clear color and clear the screen.
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isTouched())
		{
			Gdx.graphics.setCursor(cursor1);
		} else
		{
			cursorActive = !cursorActive;
			if (cursorActive)
			{
				Gdx.graphics.setCursor(cursor2);
			} else
			{
				Gdx.graphics.setCursor(cursor3);
			}
		}
	}
}