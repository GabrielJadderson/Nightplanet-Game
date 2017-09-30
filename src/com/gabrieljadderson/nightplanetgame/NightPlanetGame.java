
package com.gabrieljadderson.nightplanetgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.gabrieljadderson.nightplanetgame.screens.*;
import com.gabrieljadderson.nightplanetgame.serialization.build.BuildManager;
import com.gabrieljadderson.nightplanetgame.time.tasks.NetworkQueue;

import java.util.ArrayDeque;
import java.util.Queue;

import static com.gabrieljadderson.nightplanetgame.GameConstants.*;

/**
 * @author Gabriel Jadderson
 */
public class NightPlanetGame extends Game
{
	
	public static final String TITLE = "Night Planet";
	public static final Vector2 DESKTOP_SIZE = new Vector2(1024f, 768f);
	public static final Vector2 GWT_SIZE = new Vector2(800f, 600f);
	
	@Override
	public void create()
	{
		new GameConstants();
		
		loader = new Loader();
		
		signUpScreen = new SignUpScreen(this);
		loginScreen = new LoginScreen(this);
		characterSelectionScreen = new CharacterSelectionScreen(this);
		//splashScreen = new SplashScreen(this);
//		setScreen(splashScreen);
//		setScreen(signUpScreen);
//		setScreen(characterSelectionScreen);
		
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		
		setScreen(menuScreen);
		
    	
    	/* init buildmanager */
		new BuildManager();
		
		//TODO: add a client sending/reciving all packets.
//        new JsonGenerator().generateRandomNpc();

//        loader = new Loader(); // this sohuld be in splashscreen.
		
		
		GameConstants.backgroundLoader.start(createBackgroundTasks());
		createBackgroundTimerTasks();

        
        /* init input controllers */
		Gdx.input.setInputProcessor(GameConstants.inputMultiplexer);
		
		
      	



      	/* init screens */
		
		//			setScreen(gameScreen);
		//			setScreen(menuScreen);
		
		
	}
	
	@Override
	public void render()
	{
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0f, 1f);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render();
	}
	
	/**
	 * Returns a queue containing all of the background tasks that will be
	 * executed by the background loader. Please note that the loader may use
	 * multiple threads to load the utilities concurrently, so utilities that
	 * depend on each other <b>must</b> be executed in the same task to ensure
	 * thread safety.
	 *
	 * @return the queue of background tasks.
	 */
	public Queue<Runnable> createBackgroundTasks()
	{
		Queue<Runnable> tasks = new ArrayDeque<>();
//        tasks.add(new NpcDefinitionLoader());
//        tasks.add(new ItemDefinitionLoader());
//        tasks.add(new WeaponPoisonLoader());
//        tasks.add(new MessageOpcodeLoader());
//        tasks.add(new MessageSizeLoader());
//        tasks.add(ConnectionHandler::parseIPBans);
//        tasks.add(new NpcNodeLoader());
//        tasks.add(new ShopLoader());
//        tasks.add(new ItemNodeLoader());
//        tasks.add(new ObjectNodeLoader());
//        tasks.add(new NpcDropTableLoader());
//        tasks.add(new WeaponAnimationLoader());
//        tasks.add(new WeaponInterfaceLoader());
//        tasks.add(new EquipmentRequirementLoader());
//        tasks.add(new ObjectNodeRemoveLoader());
//        tasks.add(new NpcDropCacheLoader());
//        tasks.add(World.getPlugins()::init);
		
		//tasks.add(client_Position = new PositionClient());
		return tasks;
	}
	
	public void createBackgroundTimerTasks()
	{
		GameConstants.time.run(new NetworkQueue(), NetworkQueue.initDelay, NetworkQueue.timeBetweenTasks, NetworkQueue.taskNumber);
	}
	
}
