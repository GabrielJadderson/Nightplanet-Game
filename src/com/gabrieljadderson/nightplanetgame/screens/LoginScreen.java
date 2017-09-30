package com.gabrieljadderson.nightplanetgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.time.tasks.LoginQueue;
import com.gabrieljadderson.nightplanetgame.ui.GDialog;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * @author Gabriel Jadderson
 */
public class LoginScreen extends DefaultScreen
{
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	
	String email = null;
	String password = null;
	String errorMSG = "";
	
	GDialog errorDialog;
	TextButton login;
	TextButton cancel;
	TextField emailField;
	TextField passwordField;
	Label emailLabel;
	Label passwordLabel;
	
	LoginQueue loginQueue;
	int loginCounter = 0;
	
	Dimension screenSize;
	
	boolean playerGrantedAccess = false;
	private float accumulator = 0f;
	private float timestep = 0f;
	
	public LoginScreen(Game game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		batch = new SpriteBatch();
		stage = new Stage();
		GameConstants.inputMultiplexer.addProcessor(stage);
		
		skin = new Skin(ResourceCrawler.getRes("uiskin.json"));
		screenSize = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		createUserInterfaceElements();
	}
	
	public void update()
	{
		if (!playerGrantedAccess && GameConstants.menuScreen.currentMode == MenuScreen.LOGGED_IN)
		{
			game.setScreen(GameConstants.menuScreen);
			System.err.println("screen CHANGED?");
			playerGrantedAccess = true;
		}
	}
	
	@Override
	public void render(float delta)
	{
		float elapsed = Math.min(delta, .25f);
		accumulator += elapsed;
		while (accumulator >= timestep)
		{
			update();
			accumulator -= timestep;
		}
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 0.023f));
		stage.draw();
	}
	
	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose()
	{
//		GameConstants.inputMultiplexer.removeProcessor(stage);
		stage.dispose();
		skin.dispose();
	}
	
	private void invokeCancel()
	{
		GameConstants.menuScreen.setMode(MenuScreen.NOT_LOGGED_IN);
		game.setScreen(GameConstants.menuScreen);
		login.setDisabled(false);
		emailField.setDisabled(false);
		passwordField.setDisabled(false);
		dispose();
	}
	
	private void invokeLogin()
	{
		//TODO: all signup stuff here.
		
		//1. validate email
		//2. validate the two passwords 
		//3. validate player name
		//send for signup 
		
		if (validateInput(email, password))
		{
			System.out.println("valid");
			errorDialog = new GDialog("Logging in", skin, GDialog.POSITION_Center, GDialog.SIZE_XXMedium, Align.center, "mini");
			errorDialog.setLoader();
			stage.addActor(errorDialog);
			//connect to login server
			if (loginCounter > 0)
			{
				loginQueue.setEmail(email);
				loginQueue.setPassword(password);
				loginQueue.reconnect();
			} else
			{
				loginQueue = new LoginQueue(email, password);
				GameConstants.time.run(loginQueue, 500L, 5000L, LoginQueue.taskNumber);
			}
			loginCounter++;
		} else
		{
			System.out.println(errorMSG);
			errorDialog = new GDialog("Error", skin, GDialog.POSITION_Center, GDialog.SIZE_XMedium, Align.center, "mini");
			errorDialog.setMessage(errorMSG);
			errorDialog.setTextButton_1("OK", true);
			stage.addActor(errorDialog);
		}
		
	}
	
	public void userLoginFailed()
	{
		errorDialog.removeLoader();
		errorDialog.setTextButton_1("OK", true);
		errorDialog.setMessage("Login failed incorrect Email or password");
	}
	
	public synchronized void userLoginComplete()
	{
		GameConstants.menuScreen.setMode(MenuScreen.LOGGED_IN);
//		game.setScreen(GameConstants.menuScreen); //not gl contexted?
		login.setDisabled(true);
		emailField.setDisabled(true);
		passwordField.setDisabled(true);
	}
	
	private boolean validateInput(String Email, String Password)
	{
		if (Email == null || Email.equals(""))
		{
			errorMSG = "Please enter your Email adress!";
			return false;
		}
		if (Password == null || Password.equals(""))
		{
			errorMSG = "Please enter a password!";
			return false;
		}
		return true;
	}

//	private void hideUserInterfaceElements() {
//		emailLabel.setVisible(false);
//		emailField.setVisible(false);
//		passwordLabel.setVisible(false);
//		passwordField.setVisible(false);
//		login.setVisible(false);
//		cancel.setVisible(false);
//	}
	
	private void addUserInterfaceElements()
	{
		stage.addActor(emailLabel);
		stage.addActor(emailField);
		stage.addActor(passwordLabel);
		stage.addActor(passwordField);
		stage.addActor(login);
		stage.addActor(cancel);
	}
	
	private void showUserInterfaceElements()
	{
		emailLabel.setVisible(true);
		emailField.setVisible(true);
		passwordLabel.setVisible(true);
		passwordField.setVisible(true);
		login.setVisible(true);
		cancel.setVisible(true);
	}
	
	private void createUserInterfaceElements()
	{
		LabelStyle labelStyle = skin.get("tintedDialog", LabelStyle.class);
		
		emailLabel = new Label("Email: ", labelStyle);
		emailLabel.setSize(200, 38);
		emailLabel.setPosition((screenSize.getWidth() / 2) - (emailLabel.getWidth() / 2) - 19, (screenSize.getHeight() / 2) + 110);
		emailLabel.setColor(emailLabel.getColor().r, emailLabel.getColor().g, emailLabel.getColor().b, 0);
		emailLabel.addAction(sequence(fadeIn(0.2f)));
		
		emailField = new TextField("", skin);
		emailField.setMessageText("Email");
		emailField.setSize(250, 38);
		emailField.setPosition((screenSize.getWidth() / 2) - emailField.getWidth() / 2, (screenSize.getHeight() / 2) + 80);
		emailField.setBlinkTime(0.5f);
		emailField.setColor(emailField.getColor().r, emailField.getColor().g, emailField.getColor().b, 0);
		emailField.addAction(sequence(fadeIn(0.2f)));
		
		
		passwordLabel = new Label("Password: ", labelStyle);
		passwordLabel.setSize(200, 38);
		passwordLabel.setPosition((screenSize.getWidth() / 2) - (passwordLabel.getWidth() / 2) + 7, (screenSize.getHeight() / 2) + 30);
		passwordLabel.setColor(passwordLabel.getColor().r, passwordLabel.getColor().g, passwordLabel.getColor().b, 0);
		passwordLabel.addAction(sequence(fadeIn(0.2f)));
		
		passwordField = new TextField("", skin);
		passwordField.setMessageText("Password");
		passwordField.setSize(200, 38);
		passwordField.setPosition((screenSize.getWidth() / 2) - passwordField.getWidth() / 2, (screenSize.getHeight() / 2) - 0);
		passwordField.setBlinkTime(0.5f);
		passwordField.setPasswordCharacter('*');
		passwordField.setPasswordMode(true);
		passwordField.setColor(passwordField.getColor().r, passwordField.getColor().g, passwordField.getColor().b, 0);
		passwordField.addAction(sequence(fadeIn(0.2f)));
		
		
		login = new TextButton("Login", skin);
		login.setSize(110, 60);
		login.setPosition((screenSize.getWidth() / 2) - (login.getWidth() / 2), (screenSize.getHeight() / 2) - 110);
		login.setColor(login.getColor().r, login.getColor().g, login.getColor().b, 0);
		login.addAction(sequence(fadeIn(0.3f)));
		login.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (emailField.getText().equals("Email")) email = null;
				else email = emailField.getText();
				if (passwordField.getText().equals("Password")) password = null;
				else password = passwordField.getText();
				invokeLogin();
			}
			
			;
		});
		
		cancel = new TextButton("Cancel", skin);
		cancel.setSize(110, 60);
		cancel.setPosition((screenSize.getWidth() / 2) - (cancel.getWidth() / 2), (screenSize.getHeight() / 2) - 205);
		cancel.setColor(cancel.getColor().r, cancel.getColor().g, cancel.getColor().b, 0.5f);
		cancel.setColor(cancel.getColor().r, cancel.getColor().g, cancel.getColor().b, 0);
		cancel.addAction(sequence(fadeIn(0.3f)));
		cancel.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				invokeCancel();
			}
			
			;
		});
		addUserInterfaceElements();
		showUserInterfaceElements();
	}
	
}
