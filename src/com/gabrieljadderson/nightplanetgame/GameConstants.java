package com.gabrieljadderson.nightplanetgame;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;
import com.gabrieljadderson.nightplanetgame.graphics.anim.NPC;
import com.gabrieljadderson.nightplanetgame.graphics.anim.NpcRenderer;
import com.gabrieljadderson.nightplanetgame.graphics.anim.ObjectRenderer;
import com.gabrieljadderson.nightplanetgame.graphics.objects.GObject;
import com.gabrieljadderson.nightplanetgame.graphics.renderpipeline.RenderManager;
import com.gabrieljadderson.nightplanetgame.input.DebugInput;
import com.gabrieljadderson.nightplanetgame.input.GameWorldInput;
import com.gabrieljadderson.nightplanetgame.input.UiInput;
import com.gabrieljadderson.nightplanetgame.map.Map;
import com.gabrieljadderson.nightplanetgame.map.items.ItemDefinition;
import com.gabrieljadderson.nightplanetgame.map.items.ItemObject;
import com.gabrieljadderson.nightplanetgame.map.objects.InGameObject;
import com.gabrieljadderson.nightplanetgame.messages.GameChatBox;
import com.gabrieljadderson.nightplanetgame.player.Inventory;
import com.gabrieljadderson.nightplanetgame.player.Player;
import com.gabrieljadderson.nightplanetgame.screens.*;
import com.gabrieljadderson.nightplanetgame.shaders.ShaderManager;
import com.gabrieljadderson.nightplanetgame.tests.net.PositionClient;
import com.gabrieljadderson.nightplanetgame.tests.net.signup.LoginClient;
import com.gabrieljadderson.nightplanetgame.tests.net.signup.SignUpClient;
import com.gabrieljadderson.nightplanetgame.time.BackgroundLoader;
import com.gabrieljadderson.nightplanetgame.time.Time;
import com.gabrieljadderson.nightplanetgame.utils.FrameRateCounter;
import com.gabrieljadderson.nightplanetgame.utils.MaxSizeHashMap;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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
 * TODO:
 * @since 26/01/2014
 */
public class GameConstants
{
	
	public static HashMap<String, Path> paths;
	
	public static Loader loader;
	
	//test
	public static NpcRenderer npcRenderer;
	public static ObjectRenderer objectRenderer;
	//test
	
	//====================================================== GAME CONSTANTS ======================================================
	/**
	 * title of game
	 */
	public static String TITLE = "BioGen";
	
	/**
	 * build number
	 */
	public static int BUILD_NUMBER = 0;
	
	/**
	 * version
	 */
	public static String VERSION = "0.1";
	
	/**
	 * debug
	 */
	public static boolean DEBUG = true; //default false.
	
	/**
	 *
	 */
	public static final Vector2 DESKTOP_SIZE = new Vector2(1024f, 768f);
	
	public static boolean CHECKFORUPDATES = true;
	
	public static PositionClient client_Position;
	public static SignUpClient client_SignUP;
	public static LoginClient client_Login = null;
	
	/**
	 * frameRateCounter /FPS
	 */
	public static FrameRateCounter frameRateCounter = new FrameRateCounter();
	
	
	/**
	 * RenderManager
	 */
	public static RenderManager renderManager;
	
	public static ShaderManager shaderManager;
	
	
	public static AssetManager assetManager;
	public static TextureAtlas ta;
	public static TextureAtlas ta1;
	
	/**
	 * true if the game has been paused, false otherwise.
	 */
	public static boolean isPaused;
	
	/**
	 * GameScreen instace
	 */
	public static GameScreen gameScreen;
	/**
	 * GameScreen instace
	 */
	public static MenuScreen menuScreen;
	
	/**
	 * splashScreen instance
	 */
	public static SplashScreen splashScreen;
	
	/**
	 * SignUpScreen instance
	 */
	public static SignUpScreen signUpScreen;
	
	/**
	 * LoginScreen instance
	 */
	public static LoginScreen loginScreen;
	
	public static CharacterSelectionScreen characterSelectionScreen;
	
	/**
	 * gameworld instance
	 */
//    public static GameWorld gameWorld = new GameWorld();
	
	
	/**
	 * timer used for loading non libgdx, java threads.
	 */
	public static final Time time = new Time();
	
	/**
	 * backgroundloader used to load runnables in the background.
	 */
	public static BackgroundLoader backgroundLoader;
	
	/**
	 * Game Chat Box
	 */
	public static GameChatBox chatBox;
	//====================================================== END GAME CONSTANTS ======================================================
	
	public static MaxSizeHashMap<Integer, NPC> NPCs = new MaxSizeHashMap<Integer, NPC>(100); // all ingame npcs
	public static MaxSizeHashMap<Integer, GObject> GameObjects = new MaxSizeHashMap<Integer, GObject>(100); //all ingame objects
	
	//================
	/**
	 * The default time in {@code SECONDS} that all utility threads will go idle
	 * on after not receiving any tasks. This is in place to ensure that threads
	 * that are not receiving work aren't taking up any resources.
	 */
//    public static final int THREAD_TIMEOUT = 45;
	
	/**
	 * The cycle rate of the {@link GameService}.
	 */
//    public static final int CYCLE_RATE = 600;
	
	/**
	 * How long the player will stay logged in for after they have x-logged
	 * during combat.
	 */
//    public static final int LOGOUT_SECONDS = 90;
	//================
	
	
	//====================================================== Game Screen =======================================================
	/**
	 * game tick.
	 */
	public static final float timestep = 1 / 60f;
	/**
	 * accumulator used as a way to update. the accumulator is incremented, if the accumulator is bigger than timestep then update the game.
	 * then decrement the acumulator then repeat the process again.
	 * the code used to increment the accumulator:
	 * <p>
	 * <p>
	 * <code>
	 * float elapsed = Math.min(delta, .25f);
	 * <p>
	 * accumulator += elapsed;
	 * </code>
	 */
	public static float accumulator;
	//====================================================== END GAME SCREEN ===================================================
	//====================================================== Player Constants ======================================================
	
	
	/**
	 * the main player
	 */
	public static volatile Player player;
	
	/**
	 * inputController
	 */
	public static GameWorldInput inputCont = new GameWorldInput();
	
	/**
	 * GameInput
	 */
	private static DebugInput debugInput = new DebugInput();
	
	/**
	 * UI Input
	 */
	private static UiInput uiInput = new UiInput();
	
	/**
	 * inputMultiplexer
	 */
	public static InputMultiplexer inputMultiplexer = new InputMultiplexer(inputCont, debugInput, uiInput);
	
	/**
	 * Actors
	 */
//	public static ConcurrentHashMap<EntityDef, Actor> actors = new ConcurrentHashMap<EntityDef, Actor>();
	
	/**
	 * inventory
	 */
	public static Inventory inventory = new Inventory();//player inventory
	
	/**
	 * the mouse touch point on screen.
	 */
	public static Vector3 mouseTouchPoint = new Vector3();
	//====================================================== END Player Constants ======================================================
	//====================================================== item Constants ======================================================
	/**
	 * a concurrenthashMap holding all the items in the game.
	 */
	public static ConcurrentHashMap<ItemDefinition, ItemObject> items = new ConcurrentHashMap<ItemDefinition, ItemObject>();
	public static ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	//====================================================== END item Constants ======================================================
	//====================================================== map&object Constants ======================================================
	/**
	 * map objects.
	 */
	public static Map map; // the map instance
	public static ConcurrentHashMap<String, TiledMap> maps = new ConcurrentHashMap<String, TiledMap>(); //this holds all the loaded maps in the game. 
	public static World world; //the game world.
	public static MapProperties mapProperties; //the mapProperties
	public static MapLayers mapLayers; //all of the layers in the map
	public static MapLayer mapLayer_ground; //the ground layer in the map. 
	public static TiledMap mapTiledMap; //the tiled map instance of the current instance. 
	public static ConcurrentHashMap<MapObject, Body> mapObjects_collision = new ConcurrentHashMap<MapObject, Body>(); //objects in the collision layer. holds all the colidable objects.
	public static ConcurrentHashMap<Integer, InGameObject> mapObjects_clickableObjects = new ConcurrentHashMap<Integer, InGameObject>(); //holds all the clickable and colidable objects. eg. doors. 
	public static TiledMapTileSets mapTileSet; //tile set in the map.
	public static OrthogonalTiledMapRenderer renderer; // the map renderer.!!
	public static IsometricStaggeredTiledMapRenderer renderer2; // the map renderer.!!
	public static IsometricTiledMapRenderer renderer3; // the map renderer.!!
	public static OrthographicCamera camera; //the camera in the map.
	public static PerspectiveCamera persCamera; //the camera in the map.
	public static OrthographicCamera hudCamera; //the HUD camera in the map.
	public static RayHandler rayHandler;
	public static float ambientLight;
	public static boolean ownsBatch; //if the map class owns the painted batch
	public static Batch batch = new SpriteBatch(5460); //the batch used to draw the 2d rectangles
	//	public static PolygonSpriteBatch polygonBatch = new PolygonSpriteBatch(); // Required to render meshes. SpriteBatch can't render meshes.
	public static float pixelsPerMeter; //used for coords??
	public static int velocityIterations, positionIterations;
	public static int mapPixelWidth, mapPixelHeight;
	public static int tilePixelWidth, tilePixelHeight;
	public static float map_max_X, map_max_Y;
	public static int mapWidth, mapHeight; //map height and map width.
	
	public static Player cameraTarget;
	
	
	public static final String COLLISION_NAME = "collision"; //name of the colission layer in the map.
	public static final String FOREGROUND_NAME = "foreground"; //name of the foreground in the map.
	public static final String CANVAS_NAME = "canvas"; //name of the canvas in the map.
	public static final String GROUNDDETAIL_NAME = "grounddetail"; //name of the grounddetail in the map.
	public static final String GROUND_NAME = "ground"; //name of the ground in the map.
	//====================================================== END map&object Constants ======================================================
	
	//====================================================== sprite Constants ======================================================
	/**
	 * player texturing
	 */
	public static SpriteSheet SPRITE_SHEET;
	//	public static final SpriteSheet SPRITE_SHEET_FIRE = new SpriteSheet(Gdx.files.classpath("objects/fire/fire.png"), 16, 18, 2, 2);
	public static final int SPRITE_SHEET_ID = 5; //the id of the spritesheet 
	public static final boolean SPRITE_SHEET_GENDER_MALE = true; //the gender of the player
	//====================================================== END sprite Constants ======================================================
	
	public static ResourceLoader resourceLoader;
	
	public static boolean toggleDebug()
	{
		return DEBUG = !DEBUG;
	}
	
	public GameConstants()
	{
		npcRenderer = new NpcRenderer();
		objectRenderer = new ObjectRenderer();
		
		shaderManager = new ShaderManager();
		
		assetManager = new AssetManager();
		
		backgroundLoader = new BackgroundLoader();
		
		chatBox = new GameChatBox();
		
		resourceLoader = new ResourceLoader();
		
		world = new World(new Vector2(), true);
		
		rayHandler = new RayHandler(world, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		rayHandler.setAmbientLight(ambientLight);
		rayHandler.setBlur(true);
		rayHandler.setBlurNum(5);
		rayHandler.setCulling(true);
		rayHandler.setShadows(true);
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
	}
}
