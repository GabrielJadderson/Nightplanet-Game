package com.gabrieljadderson.nightplanetgame.tests.net.signup;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.security.PasswordEncryptionService;
import com.gabrieljadderson.nightplanetgame.tests.net.ClientConstants;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

/**
 * @author Gabriel Jadderson
 */
public class LoginClient implements Runnable
{
	
	Client client;
	
	boolean isLoggedIn = false;
	
	public LoginClient(String email, String password)
	{
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.start();
		
		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		LoginNetwork.register(client);
		
		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener()
		{
			public void connected(Connection c)
			{
				
			}
			
			public void received(Connection c, Object object)
			{
				
				if (object instanceof LoginNetwork.LoginRequired)
				{
					LoginNetwork.ValidateRegistry vr = new LoginNetwork.ValidateRegistry();
					vr.email = StringUtils.lowerCase(email);
					vr.password = password;
					c.sendTCP(vr);
				}
				
				if (object instanceof LoginNetwork.LoginFailed)
				{
					GameConstants.loginScreen.userLoginFailed();
					c.close();
				}
				
				if (object instanceof LoginNetwork.LoginComplete)
				{
					System.out.println("LOGIN COMPLETE");
					//connect to main server 1st then handle the player. 
//					GameConstants.client_Position.run();
					isLoggedIn = true;
					GameConstants.loginScreen.userLoginComplete();
				}

//				if (object instanceof AddCharacter) {
//					AddCharacter msg = (AddCharacter)object;
//					addCharacter(msg.character);
//					return;
//				}
//
//				if (object instanceof UpdateCharacter) {
//					updateCharacter((UpdateCharacter)object);
//					return;
//				}
//
//				if (object instanceof RemoveCharacter) {
//					RemoveCharacter msg = (RemoveCharacter)object;
//					removeCharacter(msg.UIN);
//					return;
//				}
			}
			
			public void disconnected(Connection c)
			{
//				System.exit(0);//really...
				GameConstants.client_Login = null;
			}
		}));
		
		
		try
		{
			InetAddress REMOTE_ADDRESS = InetAddress.getByName(ClientConstants.HOST);
			client.connect(5000, REMOTE_ADDRESS, ClientConstants.LOGIN_SERVER_PORT_TCP, ClientConstants.LOGIN_SERVER_PORT_UDP);
			// Server communication after connection can go here, or in Listener#connected().
		} catch (Exception ex)
		{
			ex.printStackTrace();
			GameConstants.signUpScreen.userCreationFailed();
			
		}

//		Login login = new Login();
//		login.name = Player.PLAYER_NAME;
//		client.sendTCP(login);
	
	
	}
	
	
	/**
	 * get salt
	 */
	private byte[] generateSalt()
	{
		PasswordEncryptionService pes = new PasswordEncryptionService();
		byte[] salt = null;
		try
		{
			salt = pes.generateSalt();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			pes = null;
		}
		return salt;
	}
	
	/**
	 * get password
	 */
	private byte[] generatePass(String p, byte[] s)
	{
		PasswordEncryptionService pes = new PasswordEncryptionService();
		byte[] pe = null;
		try
		{
			pe = pes.getEncryptedPassword(p, s);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			pes = null;
		}
		return pe;
	}
	
	
	public boolean isLoginComplete()
	{
		return isLoggedIn;
	}
	
	
	public void run()
	{
		System.out.println("Started SignUpClient");
		
	}
	
}
