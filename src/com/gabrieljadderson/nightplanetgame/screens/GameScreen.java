
package com.gabrieljadderson.nightplanetgame.screens;

import box2dLight.PointLight;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectSet;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.gameplay.LunarBar;
import com.gabrieljadderson.nightplanetgame.map.Map;
import com.gabrieljadderson.nightplanetgame.utils.Direction;
import com.gabrieljadderson.nightplanetgame.utils.LightBuilder;
import com.gabrieljadderson.nightplanetgame.utils.MapUtils;
import com.gabrieljadderson.nightplanetgame.utils.TextUtils;

import static com.gabrieljadderson.nightplanetgame.GameConstants.*;

/**
 * Copyright (c) 2016-onwards NightPlanet /vGabriel Howard Jadderson
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * <p>
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p>
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * * Neither the name of 'Shaven Puppy' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO:FIX THIS WHOLE CLASS MAKE IT SIMPLER TO READ AND MODULAR!
 * @since 20/12/2015
 */
public class GameScreen extends DefaultScreen
{
	
	//test
	CameraInputController camCont;
	//test
	
	private BitmapFont font;
	private GlyphLayout metrics;
	private String text;
	
	Sound ambientSoundSFX;
	long ambientSoundSFX_ID;
	
	public LunarBar lunarBar;
	
	public GameScreen(Game game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		map = new Map(maps.get("testmap4")); //...
		map.setAmbientLight(0.0f); //default .4 .2 is nice .15 is ok.
//		map.setZoom(.35f);
//		map.setZoom(.40f);
		map.setZoom(1.1f);
		
		map.loadPlayer(); //loads the player and sets the map centered on.
		
		npcRenderer.createAI();//after the player body is loaded we can create the AI
		
		map.loadItems(); //loads all the items in the game. 
		
		player.getPosition().setVector2Position(new Vector2(1600, 2700));
		
		
		PointLight light = LightBuilder.createPointLight(map.getRayHandler(), 800, Color.WHITE, map.toMeters(1850f), 1f, 0f, 0f, false);
//		PointLight light = LightBuilder.createPointLight(map.getRayHandler(), 50, Color.BLACK, map.toMeters(350f), 1f, 0f, 0f, false);
		player.attachLight(light);
		
		
		PointLight fogOfWar = LightBuilder.createPointLight(map.getRayHandler(), 820, Color.WHITE, map.toMeters(1000f), 0f, 0f, 0f, true);
//		PointLight fogOfWar = LightBuilder.createPointLight(map.getRayHandler(), 820, Color.BLACK, map.toMeters(320f), 0f, 0f, 0f, true);
		player.attachLight(fogOfWar);
		
		
		hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0f);
		
		// Listen to all controllers
//        Controllers.addListener(new GamePadListener());
		
		//debug font
		font = new BitmapFont();
		text = GameConstants.TITLE + " " + GameConstants.VERSION + "   BUILD: " + GameConstants.BUILD_NUMBER;
		metrics = new GlyphLayout(font, text);

//        ambientSoundSFX = GameConstants.assetManager.get("sounds/SFX/SFXGame.4.ogg", Sound.class);
		GameConstants.assetManager.loadFromCrawler("amb_cave_entr_stairs.ogg", Sound.class);
		GameConstants.assetManager.finishLoading();
		ambientSoundSFX = GameConstants.assetManager.getCrawled("amb_cave_entr_stairs.ogg", Sound.class);
		ambientSoundSFX_ID = ambientSoundSFX.loop(0.4f);
		
		
		lunarBar = new LunarBar();

//        camCont = new CameraInputController(camera);
		camCont = new CameraInputController(persCamera);
		GameConstants.inputMultiplexer.addProcessor(camCont);
//        Gdx.input.setInputProcessor(camCont);
	}
	
	public void update(float delta)
	{
		if (!GameConstants.isPaused)
		{
			
			if (Gdx.input.justTouched())
			{
				if (Gdx.input.isButtonPressed(Buttons.RIGHT))
				{
					player.proj.shoot(new Vector2(GameConstants.mouseTouchPoint.x, GameConstants.mouseTouchPoint.y));
				}
			}
			
			ObjectSet<Direction> directions = new ObjectSet<Direction>();
			
			if (inputCont.isKeyPressed(Keys.W) || inputCont.isKeyPressed(Keys.UP))
			{
				directions.add(Direction.NORTH);
			}
			if (inputCont.isKeyPressed(Keys.S) || inputCont.isKeyPressed(Keys.DOWN))
			{
				directions.add(Direction.SOUTH);
			}
			if (inputCont.isKeyPressed(Keys.A) || inputCont.isKeyPressed(Keys.LEFT))
			{
				directions.add(Direction.WEST);
			}
			if (inputCont.isKeyPressed(Keys.D) || inputCont.isKeyPressed(Keys.RIGHT))
			{
				directions.add(Direction.EAST);
			}
			if (Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 1)
				{
					directions.add(Direction.NORTH);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 2)
				{
					directions.add(Direction.SOUTH);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 3)
				{
					directions.add(Direction.WEST);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 4)
				{
					directions.add(Direction.EAST);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 5)
				{
					directions.add(Direction.NORTH);
					directions.add(Direction.WEST);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 6)
				{
					directions.add(Direction.NORTH);
					directions.add(Direction.EAST);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 7)
				{
					directions.add(Direction.SOUTH);
					directions.add(Direction.WEST);
				}
				if (inputCont.isTouchInvoked() != -1 && inputCont.isTouchInvoked() == 8)
				{
					directions.add(Direction.SOUTH);
					directions.add(Direction.EAST);
				}
			}
			
			
			player.move(MapUtils.directionSetToDirection(directions), delta);
			
			
			map.update(timestep);
//		hudCamera.update();
			mouseTouchPoint.set(GameConstants.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
			
			lunarBar.update();
			
			world.step(delta, 8, 3);//WORLD UPDATE
		}
	}
	
	@Override
	public void render(float delta)
	{
		if (!GameConstants.isPaused)
		{
			
			
			camCont.update();
			GameConstants.persCamera.update();
			GameConstants.camera.update(); //adds a bit of smoothness
//		GameConstants.hudCamera.update();
			float elapsed = Math.min(delta, .25f);
			accumulator += elapsed;
			while (accumulator >= timestep)
			{
				update(elapsed);
				accumulator -= timestep;
			}
			
			
			map.render();
			
			Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			map.getBatch().setProjectionMatrix(hudCamera.combined);
			batch.setProjectionMatrix(normalProjection);
			
			map.getBatch().begin();
			
			
			chatBox.Render(map.getBatch());
			drawHUD(map.getBatch());
			
			
			map.getBatch().end();
			frameRateCounter.submitReading();
		}
	}
	
	@Override
	public void hide()
	{
		dispose();
	}
	
	@Override
	public void dispose()
	{
		GameConstants.SPRITE_SHEET.dispose();
		map.dispose();
		font.dispose();
	}
	
	public void drawHUD(Batch batch)
	{
		Gdx.graphics.setTitle("FPS: " + frameRateCounter.getFrameRate() + " FPS_NVIDIA: " + frameRateCounter.getNvidiaFrameRate());
		final float x = (Gdx.graphics.getWidth() - metrics.width) / 3f;//center
		final float y = Gdx.graphics.getHeight() - metrics.height; //center
		font.draw(batch, text, x - 150, y);
		
		//badly put together... CLEANME: clean this.
		String centered = TextUtils.center(player.PLAYER_NAME, player.PLAYER_NAME.length());
//	    String centered2 = TextUtils.cent
		Vector3 dc = camera.project(new Vector3(player.getBodyX(), player.getBodyY(), 0)); //this is a pain in the ass.
		font.setColor(Color.GOLD);
		Vector2 de = TextUtils.getCoordsForACenteredString(centered, (float) player.PLAYER_NAME.length(), 0f, font);
//		font.setColor(Color.SALMON);
		font.draw(batch, centered, dc.x + de.x + 25, dc.y + 100f);
		
		lunarBar.render(batch);
		// end.
	}
	
	public Map getMap()
	{
		return map;
	}
	
	
	@Override
	public void pause()
	{
		System.err.println("paused");
		//TODO: display that the game is paused and ui here. 
		inputMultiplexer.removeProcessor(inputCont);
		GameConstants.isPaused = true;
		ambientSoundSFX.stop();
	}
	
	@Override
	public void resume()
	{
		System.err.println("resumed");
		//remove that ui. displayed in the pause
		inputMultiplexer.addProcessor(inputCont);
		GameConstants.isPaused = false;
		
		ambientSoundSFX.loop(0.4f);
	}
	
	
}
