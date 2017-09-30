package com.gabrieljadderson.nightplanetgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;

/**
 * Gdx.app.postRunnable(new ResourceLoaderTimer());
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * @since 14/12/2015
 */
public class SplashScreen extends DefaultScreen
{
	
	SpriteBatch batch;
	SpineObject bkg;
	
	boolean allAssetsLoaded = false;
	boolean animationFinished = false;
	
	Sound sfx;
	long sfxControl;
	boolean didPlaySound = false;
	
	public SplashScreen(Game game)
	{
		super(game);
		
	}
	
	@Override
	public void show()
	{
		sfx = GameConstants.assetManager.get(ResourceCrawler.getResource("Distortion_Wave_03.mp3"));
		
		batch = new SpriteBatch(1);
		
		/*
		bkg = new SpineObject(
				"nightplanet_splash_05.atlas",
				"nightplanet_splash.json",
				0.6f,
				1.8f,
				false);
		bkg.setPosition(new Position(-90, Gdx.graphics.getHeight() - 60));
		bkg.setAnimation(0, "animation", false);
		
		bkg.getState().addListener(new AnimationStateListener()
		{
			@Override
			public void start(int trackIndex)
			{
			}
			
			@Override
			public void event(int trackIndex, Event event)
			{
			}
			
			@Override
			public void end(int trackIndex)
			{
				if (GameConstants.loader.isComplete())
				{
					switchScreen();
					dispose();
				} else
				{
					bkg.addAnimation(0, "animation", false, 0);
					sfx.play();
				}
			}
			
			@Override
			public void complete(int trackIndex, int loopCount)
			{
			}
		});
		*/
		
		switchScreen();
		dispose();
		
	}
	
	public void update(float delta)
	{
		if (!didPlaySound)
		{
			sfxControl = sfx.play();
			didPlaySound = true;
		}
	}
	
	public void switchScreen()
	{
		game.setScreen(GameConstants.menuScreen);
//		game.setScreen(GameConstants.characterSelectionScreen);
	}
	
	@Override
	public void render(float delta)
	{
		clearScreen();
		float elapsed = Math.min(delta, .25f);
		GameConstants.accumulator += elapsed;
		while (GameConstants.accumulator >= GameConstants.timestep)
		{
			update(elapsed);
			GameConstants.accumulator -= GameConstants.timestep;
		}
		
		//bkg.update(Gdx.graphics.getDeltaTime());
		
		
		//batch.begin();
		//bkg.render(batch);
		//batch.end();
		
		
	}
	
	
	@Override
	public void hide()
	{
		
	}
	
	@Override
	public void pause()
	{
		
	}
	
	@Override
	public void resume()
	{
		
	}
	
	@Override
	public void dispose()
	{
		sfx.stop();
		sfx.dispose();
		sfxControl = 0L;
	}
	
}
