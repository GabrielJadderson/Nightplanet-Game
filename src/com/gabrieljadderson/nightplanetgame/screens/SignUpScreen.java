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
import com.gabrieljadderson.nightplanetgame.time.tasks.SignUpQueue;
import com.gabrieljadderson.nightplanetgame.ui.GDialog;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import org.apache.commons.validator.routines.EmailValidator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * @author Gabriel Jadderson
 */
public class SignUpScreen extends DefaultScreen
{
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	
	String email = null;
	String password = null;
	String confirm_password = null;
	String playername = null;
	final int min_PlayerNameLength = 2;
	final int max_PlayerNameLength = 12;
	String errorMSG = "";
	
	GDialog errorDialog;
	TextButton SignUp;
	TextButton cancel;
	TextField emailField;
	TextField passwordField;
	TextField passwordField_confirm;
	TextField nameField;
	Label emailLabel;
	Label passwordLabel;
	Label confirmLabel;
	Label nameLabel;
	
	Dimension screenSize;
	
	SignUpQueue signUpQueue;
	
	int signupCounter = 0;
	
	public SignUpScreen(Game game)
	{
		super(game);
		
		//things in here are initiated upon startup
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
		addUserInterfaceElements();
		showUserInterfaceElements();
	}
	
	public void update()
	{
		
	}
	
	@Override
	public void render(float delta)
	{
//		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 0.023f));
		stage.draw();
		
	}
	
	@Override
	public void hide()
	{
		hideUserInterfaceElements();
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
		GameConstants.inputMultiplexer.removeProcessor(stage);
		stage.dispose();
		skin.dispose();
	}
	
	private void invokeCancel()
	{
		GameConstants.menuScreen.setMode(MenuScreen.NOT_LOGGED_IN);
		game.setScreen(GameConstants.menuScreen);
		SignUp.setDisabled(false);
		emailField.setDisabled(false);
		passwordField.setDisabled(false);
		passwordField_confirm.setDisabled(false);
		nameField.setDisabled(false);
		dispose();
	}
	
	private void invokeSignUp()
	{
		//TODO: all signup stuff here.
		
		//1. validate email
		//2. validate the two passwords 
		//3. validate player name
		//send for signup 
		
		if (validateInput(email, password, confirm_password, playername))
		{
			System.out.println("valid");
			errorDialog = new GDialog("connecting to server", skin, GDialog.POSITION_Center, GDialog.SIZE_XXMedium, Align.center, "mini");
			errorDialog.setLoader();
			stage.addActor(errorDialog);
			signUpQueue = new SignUpQueue(email, confirm_password, playername);
			if (signupCounter > 0) signUpQueue.reconnect();
			else GameConstants.time.run(signUpQueue, 1500L, 5000L, SignUpQueue.taskNumber);
			signupCounter++;
		} else
		{
			System.out.println(errorMSG);
			errorDialog = new GDialog("Error", skin, GDialog.POSITION_Center, GDialog.SIZE_XMedium, Align.center, "mini");
			errorDialog.setMessage(errorMSG);
			errorDialog.setTextButton_1("OK", true);
			stage.addActor(errorDialog);
		}
		
	}
	
	public void userCreationFailed()
	{
		errorDialog.removeLoader();
		errorDialog.setTextButton_1("OK", true);
		errorDialog.setMessage("Failed to create account please try again later");
	}
	
	public void userCreationComplete()
	{
		errorDialog.removeLoader();
		errorDialog.setTextButton_1("OK", true);
		errorDialog.setMessage("Account created sucessfully, you can now login using the Email you provided");
		SignUp.setDisabled(true);
		emailField.setDisabled(true);
		passwordField.setDisabled(true);
		passwordField_confirm.setDisabled(true);
		nameField.setDisabled(true);
		signUpQueue.dispose();
	}
	
	private boolean validateInput(String Email, String Password, String confirm_password, String PlayerName)
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
		if (confirm_password == null || confirm_password.equals(""))
		{
			errorMSG = "Please enter your password again to make sure you got it correctly";
			return false;
		}
		if (PlayerName == null || PlayerName.equals(""))
		{
			errorMSG = "Please name your character";
			return false;
		}
		if (Email.length() <= 5)
		{ //email has to be 6 characters and above
			errorMSG = "Invalid Email adress - Email too short!";
			return false;
		}
		if (password.length() < 7 || password.length() > 25)
		{ //password has to be between 7 and 25 characters long
			errorMSG = "Password must be between 7 and 25 characters long";
			return false;
		}
		if (!password.equals(confirm_password))
		{ //passwords must match
			errorMSG = "The entered passwords did not match";
			return false;
		}
		if (PlayerName.length() < min_PlayerNameLength || PlayerName.length() > max_PlayerNameLength)
		{ //player name must be between 2 and 12 chracters long
			errorMSG = "player name must be between 2 and 12 characters long";
			return false;
		}
		if (!EmailValidator.getInstance().isValid(Email))
		{
			errorMSG = "The Email you provided is not a valid Email adress";
			return false;
		}
		return true;
	}
	
	private void hideUserInterfaceElements()
	{
		emailLabel.setVisible(false);
		emailField.setVisible(false);
		passwordLabel.setVisible(false);
		passwordField.setVisible(false);
		confirmLabel.setVisible(false);
		passwordField_confirm.setVisible(false);
		nameLabel.setVisible(false);
		nameField.setVisible(false);
		SignUp.setVisible(false);
		cancel.setVisible(false);
	}
	
	private void addUserInterfaceElements()
	{
		stage.addActor(emailLabel);
		stage.addActor(emailField);
		stage.addActor(passwordLabel);
		stage.addActor(passwordField);
		stage.addActor(confirmLabel);
		stage.addActor(passwordField_confirm);
		stage.addActor(nameLabel);
		stage.addActor(nameField);
		stage.addActor(SignUp);
		stage.addActor(cancel);
	}
	
	private void showUserInterfaceElements()
	{
		emailLabel.setVisible(true);
		emailField.setVisible(true);
		passwordLabel.setVisible(true);
		passwordField.setVisible(true);
		confirmLabel.setVisible(true);
		passwordField_confirm.setVisible(true);
		nameLabel.setVisible(true);
		nameField.setVisible(true);
		SignUp.setVisible(true);
		cancel.setVisible(true);
	}
	
	private void createUserInterfaceElements()
	{

//		if (cancel == null || passwordField == null || passwordField_confirm == null || emailField == null || nameField == null || SignUp == null) {
		
		LabelStyle labelStyle = skin.get("tintedDialog", LabelStyle.class);
		
		emailLabel = new Label("Email: ", labelStyle);
		emailLabel.setSize(200, 38);
		emailLabel.setPosition((screenSize.getWidth() / 2) - (emailLabel.getWidth() / 2) - 19, (screenSize.getHeight() / 2) + 270);
		emailLabel.setColor(emailLabel.getColor().r, emailLabel.getColor().g, emailLabel.getColor().b, 0);
		emailLabel.addAction(sequence(fadeIn(0.2f)));
		
		emailField = new TextField("", skin);
		emailField.setMessageText("Email");
		emailField.setSize(250, 38);
		emailField.setPosition((screenSize.getWidth() / 2) - emailField.getWidth() / 2, (screenSize.getHeight() / 2) + 240);
		emailField.setBlinkTime(0.5f);
		emailField.setColor(emailField.getColor().r, emailField.getColor().g, emailField.getColor().b, 0);
		emailField.addAction(sequence(fadeIn(0.2f)));
		
		
		passwordLabel = new Label("Password: ", labelStyle);
		passwordLabel.setSize(200, 38);
		passwordLabel.setPosition((screenSize.getWidth() / 2) - (passwordLabel.getWidth() / 2) + 7, (screenSize.getHeight() / 2) + 170);
		passwordLabel.setColor(passwordLabel.getColor().r, passwordLabel.getColor().g, passwordLabel.getColor().b, 0);
		passwordLabel.addAction(sequence(fadeIn(0.2f)));
		
		passwordField = new TextField("", skin);
		passwordField.setMessageText("Password");
		passwordField.setSize(200, 38);
		passwordField.setPosition((screenSize.getWidth() / 2) - passwordField.getWidth() / 2, screenSize.getHeight() / 2 + 140);
		passwordField.setBlinkTime(0.5f);
		passwordField.setPasswordCharacter('*');
		passwordField.setPasswordMode(true);
		passwordField.setColor(passwordField.getColor().r, passwordField.getColor().g, passwordField.getColor().b, 0);
		passwordField.addAction(sequence(fadeIn(0.2f)));
		
		
		confirmLabel = new Label("Confirm: ", labelStyle);
		confirmLabel.setSize(200, 38);
		confirmLabel.setPosition((screenSize.getWidth() / 2) - (confirmLabel.getWidth() / 2) + 7, (screenSize.getHeight() / 2) + 70);
		confirmLabel.setColor(confirmLabel.getColor().r, confirmLabel.getColor().g, confirmLabel.getColor().b, 0);
		confirmLabel.addAction(sequence(fadeIn(0.2f)));
		
		passwordField_confirm = new TextField("", skin);
		passwordField_confirm.setMessageText("Confirm Password");
		passwordField_confirm.setSize(200, 38);
		passwordField_confirm.setPosition((screenSize.getWidth() / 2) - passwordField_confirm.getWidth() / 2, (screenSize.getHeight() / 2) + 40);
		passwordField_confirm.setBlinkTime(0.5f);
		passwordField_confirm.setPasswordCharacter('*');
		passwordField_confirm.setPasswordMode(true);
		passwordField_confirm.setColor(passwordField_confirm.getColor().r, passwordField_confirm.getColor().g, passwordField_confirm.getColor().b, 0);
		passwordField_confirm.addAction(sequence(fadeIn(0.2f)));
		
		
		nameLabel = new Label("Player Name: ", labelStyle);
		nameLabel.setSize(200, 38);
		nameLabel.setPosition((screenSize.getWidth() / 2) - (nameLabel.getWidth() / 2) + 20, (screenSize.getHeight() / 2) - 30);
		nameLabel.setColor(nameLabel.getColor().r, nameLabel.getColor().g, nameLabel.getColor().b, 0);
		nameLabel.addAction(sequence(fadeIn(0.2f)));
		
		nameField = new TextField("", skin);
		nameField.setMessageText("In Game Name");
		nameField.setSize(200, 38);
		nameField.setPosition((screenSize.getWidth() / 2) - nameField.getWidth() / 2, (screenSize.getHeight() / 2) - 60);
		nameField.setColor(nameField.getColor().r, nameField.getColor().g, nameField.getColor().b, 0);
		nameField.addAction(sequence(fadeIn(0.2f)));
		
		SignUp = new TextButton("Sign Up", skin);
		SignUp.setSize(150, 80);
		SignUp.setPosition((screenSize.getWidth() / 2) - (SignUp.getWidth() / 2), (screenSize.getHeight() / 2) - 200);
		SignUp.setColor(SignUp.getColor().r, SignUp.getColor().g, SignUp.getColor().b, 0);
		SignUp.addAction(sequence(fadeIn(0.3f)));
		SignUp.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (emailField.getText().equals("Email")) email = null;
				else email = emailField.getText();
				if (nameField.getText().equals("In Game Name")) playername = null;
				else playername = nameField.getText();
				if (passwordField.getText().equals("Password")) password = null;
				else password = passwordField.getText();
				if (passwordField_confirm.getText().equals("Confirm Password")) confirm_password = null;
				else confirm_password = passwordField_confirm.getText();
				invokeSignUp();
			}
			
			;
		});
		
		cancel = new TextButton("Cancel", skin);
		cancel.setSize(150, 80);
		cancel.setPosition((screenSize.getWidth() / 2) - (cancel.getWidth() / 2), (screenSize.getHeight() / 2) - 335);
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
//		}
	}
	
}
