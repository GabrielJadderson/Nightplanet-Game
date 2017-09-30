package com.gabrieljadderson.nightplanetgame;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.bullet.Bullet;

public class DesktopLauncher
{
	
	
	public static void main(String[] arg)
	{
		ResourceCrawler.crawl();
		new ResourceLoader().loadNatives();
		
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = NightPlanetGame.TITLE;
		config.width = (int) NightPlanetGame.DESKTOP_SIZE.x;
		config.height = (int) NightPlanetGame.DESKTOP_SIZE.y;
		config.initialBackgroundColor = Color.WHITE;
		config.samples = 2;
		config.useHDPI = true;
//		config.useGL30 = true;
//		config.useCPUSynch = false;
		config.vSyncEnabled = true;
		config.resizable = false;
		config.fullscreen = false;
		config.allowSoftwareMode = false;
//		config.backgroundFPS = 120;
//		config.foregroundFPS = 120;
//		config.useGL30 = true;
		
		config.addIcon(ResourceCrawler.getResource("icon_256.png"), Files.FileType.Absolute);
		config.addIcon(ResourceCrawler.getResource("icon_64.png"), Files.FileType.Absolute);
		config.addIcon(ResourceCrawler.getResource("icon_32.png"), Files.FileType.Absolute);
		config.addIcon(ResourceCrawler.getResource("icon_16.png"), Files.FileType.Absolute);
		
		Box2D.init();
		Bullet.init();
		
		new LwjglApplication(new NightPlanetGame(), config);
	}
}
