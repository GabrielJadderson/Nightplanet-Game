package com.gabrieljadderson.nightplanetgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;
import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;
import com.gabrieljadderson.nightplanetgame.utils.Position;

import static com.gabrieljadderson.nightplanetgame.GameConstants.accumulator;
import static com.gabrieljadderson.nightplanetgame.GameConstants.timestep;

/**
 * @author Gabriel Jadderson
 */
public class MenuScreen extends DefaultScreen
{
	
	public static short NOT_LOGGED_IN = 0; //if not logged in this mode should be active
	public static short LOGGED_IN = 1; //if logged in, this mode must be active.
	short currentMode = 0;
	
	SpriteBatch batch;
	Stage stage = null;
	
	SpineObject loadingAnimation;
	
	SpineObject menu;
	SpineObject menu_logged_in;
	
	Texture bkg; //menu bkg
	
	private Button login;
	private Button signup;
	private Button play;
	private Button settings;
	private Button quit;
	Skin skin = null;
	
	boolean loginHoover = false;
	boolean signupHoover = false;
	boolean playHoover = false;
	boolean settingsHoover = false;
	boolean quitHoover = false;
	
	Music test1;
	Sound sfx2;
	long sfx2Control;
	
	boolean playerIsLoggedIn = false;
	
	
	public MenuScreen(Game game)
	{
		super(game);
		
		sfx2 = GameConstants.assetManager.get(ResourceCrawler.getResource("SFXGame.9.ogg"));
		
		test1 = GameConstants.assetManager.get(ResourceCrawler.getResource("MSCGame.2.mp3"), Music.class);
		test1.setVolume(0.4f);
		test1.setLooping(true);
		
	}
	
	@Override
	public void show()
	{
		
		if (stage == null)
		{
			stage = new Stage();
			batch = new SpriteBatch(10);
			bkg = SpriteSheet.loadPNG_tex(ResourceCrawler.getRes("bkg_1.jpg"));
			
			test1.play();
			
			loadingAnimation = new SpineObject(
					"Loading_Animation.atlas",
					"Loading_Animation.json",
					0.6f,
					1.0f,
					false);
			loadingAnimation.setPosition(new Position(Gdx.graphics.getWidth() / 1.1f, 10));
			loadingAnimation.setAnimation(0, "animation", true);
			
			
			menu = new SpineObject(
					"spinosaurus.atlas",
					"spinosaurus.json",
					0.7f,
					1.0f,
					false);
			menu.setPosition(new Position(menu.getSkeleton().getData().getWidth() - Gdx.graphics.getWidth() - 150, 400));
			menu.setAnimation(0, "animation_mode_0", true);
			menu.addAnimation(0, "idle_mode_0", true, 1f);
			
			menu_logged_in = new SpineObject(
					"spinosaurus.atlas",
					"spinosaurus.json",
					0.7f,
					1.0f,
					false);
			menu_logged_in.setPosition(new Position(menu_logged_in.getSkeleton().getData().getWidth() - Gdx.graphics.getWidth() - 150, 400));
			menu_logged_in.setAnimation(0, "animation_mode_1", true);
			menu_logged_in.addAnimation(0, "idle_mode_1", true, 1f);
			
			
			menu.getState().setTimeScale(1.5f);
			menu.getState().getTracks().get(0).setTimeScale(1.0f);
			menu_logged_in.getState().setTimeScale(1.5f);
			menu_logged_in.getState().getTracks().get(0).setTimeScale(1.0f);
			
			skin = new Skin(ResourceCrawler.getRes("uiskin.json")); //ui skin
			//login button//
			login = new Button(skin);
			login.setSize(198, 78);
			login.setPosition(402, 325);
			login.setColor(Color.CLEAR);
			login.addListener(new ClickListener()
			{
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					sfx2Control = sfx2.play();
					sfx2.setVolume(sfx2Control, 0.4f);
					menu.setAnimation(1, "login_on", false);
					loginHoover = true;
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					login();
					System.out.println("login cliecked");
					super.clicked(event, x, y);
				}
			});
			signup = new Button(skin);
			signup.setSize(335, 80);
			signup.setPosition(338, 237);
			signup.setColor(Color.CLEAR);
			signup.addListener(new ClickListener()
			{
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					sfx2Control = sfx2.play();
					sfx2.setVolume(sfx2Control, 0.4f);
					menu.setAnimation(2, "signup_on", false);
					signupHoover = true;
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					signup();
					super.clicked(event, x, y);
				}
			});
			play = new Button(skin);
			play.setSize(179, 63);
			play.setPosition(423, 254);
			play.setColor(Color.CLEAR);
			play.addListener(new ClickListener()
			{
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					sfx2Control = sfx2.play();
					sfx2.setVolume(sfx2Control, 0.4f);
					menu_logged_in.setAnimation(1, "play_on", false);
					playHoover = true;
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					play();
					super.clicked(event, x, y);
				}
			});
			settings = new Button(skin);
			settings.setSize(330, 76);
			settings.setPosition(349, 157);
			settings.setColor(Color.CLEAR);
			settings.addListener(new ClickListener()
			{
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					sfx2Control = sfx2.play();
					sfx2.setVolume(sfx2Control, 0.4f);
					if (currentMode == LOGGED_IN)
						menu_logged_in.setAnimation(2, "settings_on", false);
					else if (currentMode == NOT_LOGGED_IN)
						menu.setAnimation(4, "settings_on", false);
					settingsHoover = true;
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					settings();
					super.clicked(event, x, y);
				}
			});
			quit = new Button(skin);
			quit.setSize(165, 71);
			quit.setPosition(424, 82);
			quit.setColor(Color.CLEAR);
			quit.addListener(new ClickListener()
			{
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					sfx2Control = sfx2.play();
					sfx2.setVolume(sfx2Control, 0.4f);
					if (currentMode == LOGGED_IN)
						menu_logged_in.setAnimation(3, "quit_on", false);
					else if (currentMode == NOT_LOGGED_IN)
						menu.setAnimation(5, "quit_on", false);
					quitHoover = true;
					super.enter(event, x, y, pointer, fromActor);
				}
				
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					quit();
					super.clicked(event, x, y);
				}
			});
			stage.addActor(login);
			stage.addActor(signup);
			stage.addActor(play);
			stage.addActor(settings);
			stage.addActor(quit);
		}
		
		if (currentMode == 0)
		{
			login.setVisible(true);
			signup.setVisible(true);
			play.setVisible(false);
		} else if (currentMode == 1)
		{
			play.setVisible(true);
			login.setVisible(false);
			signup.setVisible(false);
		}
		settings.setVisible(true);
		quit.setVisible(true);
		
		GameConstants.inputMultiplexer.addProcessor(stage);
	}
	
	public void update(float delta)
	{
		
		if (currentMode == LOGGED_IN)
		{
			
			if (!play.isOver() && playHoover)
			{
				menu_logged_in.addAnimation(3, "play_off", false, 0);
				playHoover = false;
			}
			
			if (!settings.isOver() && settingsHoover)
			{
				menu_logged_in.addAnimation(4, "settings_off", false, 0);
				settingsHoover = false;
			}
			
			if (!quit.isOver() && quitHoover)
			{
				menu_logged_in.addAnimation(5, "quit_off", false, 0);
				quitHoover = false;
			}
			
		} else if (currentMode == NOT_LOGGED_IN)
		{
			
			if (!login.isOver() && loginHoover)
			{
				menu.addAnimation(1, "login_off", false, 0);
				loginHoover = false;
			}
			
			if (!signup.isOver() && signupHoover)
			{
				menu.addAnimation(2, "signup_off", false, 0);
				signupHoover = false;
			}
			
			if (!settings.isOver() && settingsHoover)
			{
				menu.addAnimation(4, "settings_off", false, 0);
				settingsHoover = false;
			}
			
			if (!quit.isOver() && quitHoover)
			{
				menu.addAnimation(5, "quit_off", false, 0);
				quitHoover = false;
			}
			
		}

//		if (Gdx.input.isTouched()) {
//			System.err.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
//		}
		
		if (!playerIsLoggedIn && GameConstants.client_Login != null && GameConstants.client_Login.isLoginComplete())
		{
			setMode(LOGGED_IN);
			GameConstants.inputMultiplexer.addProcessor(stage);
			play.setVisible(true);
			login.setVisible(false);
			signup.setVisible(false);
			settings.setVisible(true);
			quit.setVisible(true);
			playerIsLoggedIn = true;
		}
		
	}
	
	private void login()
	{
//		setMode(LOGGED_IN);
//		game.setScreen(GameConstants.loginScreen);
		play();
	}
	
	private void signup()
	{
		game.setScreen(GameConstants.signUpScreen);
	}
	
	private void play()
	{
		if (GameConstants.loader.isComplete())
		{
			game.setScreen(GameConstants.gameScreen);
			dispose();
		}
	}
	
	private void settings()
	{
		//TODO display settings screen.
	}
	
	private void quit()
	{
		Gdx.app.exit();
	}
	
	
	@Override
	public void render(float delta)
	{
		if (!GameConstants.isPaused)
		{
			
			
			float elapsed = Math.min(delta, .25f);
			accumulator += elapsed;
			while (accumulator >= timestep)
			{
				update(elapsed);
				accumulator -= timestep;
			}
			
			
			loadingAnimation.update(Gdx.graphics.getDeltaTime());
			
			menu.update(Gdx.graphics.getDeltaTime());
			menu_logged_in.update(Gdx.graphics.getDeltaTime());
			
			
			batch.begin();
			
			
			batch.draw(bkg, -500, -200);
			
			
			if (currentMode == LOGGED_IN)
			{
				menu_logged_in.render(batch);
			} else if (currentMode == NOT_LOGGED_IN)
			{
				menu.render(batch);
			}
			
			loadingAnimation.render(batch);
			
			stage.act(Math.min(Gdx.graphics.getDeltaTime(), 0.023f));
			stage.draw();
			
			
			batch.end();
		}
	}
	
	
	@Override
	public void hide()
	{
//		login.remove();
//		signup.remove();
//		play.remove();
//		settings.remove();
//		quit.remove();
		GameConstants.inputMultiplexer.removeProcessor(stage);
		login.setVisible(false);
		signup.setVisible(false);
		play.setVisible(false);
		settings.setVisible(false);
		quit.setVisible(false);
	}
	
	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		GameConstants.isPaused = true;
		test1.pause();
	}
	
	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		GameConstants.isPaused = false;
		test1.play();
	}
	
	@Override
	public void dispose()
	{
		GameConstants.inputMultiplexer.removeProcessor(stage);
		loadingAnimation.dispose();
		menu.dispose();
		bkg.dispose();
		test1.stop();
		test1.dispose();
//		batch.dispose();
	}
	
	public void setMode(short mode)
	{
		if (mode == 0 && currentMode != 0)
		{
			play.setVisible(false);
			login.setVisible(true);
			signup.setVisible(true);
			menu.setAnimation(0, "animation_mode_0", true);
			menu.addAnimation(0, "idle_mode_0", true, 1f);
			currentMode = 0;
		} else if (mode == 1 && currentMode != 1)
		{
			menu.getState().clearTracks();
			menu_logged_in.getState().clearTracks();
			login.setVisible(false);
			signup.setVisible(false);
			play.setVisible(true);
			menu_logged_in.setAnimation(0, "animation_mode_1", true);
			menu_logged_in.addAnimation(0, "idle_mode_1", true, 1f);
			currentMode = 1;
		}
	}
	
}
