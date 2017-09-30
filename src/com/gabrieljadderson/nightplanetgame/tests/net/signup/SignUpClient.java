package com.gabrieljadderson.nightplanetgame.tests.net.signup;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.player.Player;
import com.gabrieljadderson.nightplanetgame.security.PasswordEncryptionService;
import com.gabrieljadderson.nightplanetgame.tests.net.ClientConstants;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

/**
 * @author Gabriel Jadderson
 */
public class SignUpClient implements Runnable
{
	
	Client client;
	
	public SignUpClient(String email, String password, String name)
	{
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.start();
		
		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		SignUpNetwork.register(client);
		
		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener()
		{
			public void connected(Connection c)
			{
				
			}
			
			public void received(Connection c, Object object)
			{
				
				if (object instanceof SignUpNetwork.RegistrationRequired)
				{
					System.out.println("registraton requried");
					SignUpNetwork.Register register = new SignUpNetwork.Register();
					register.name = name;
					register.email = StringUtils.lowerCase(email);
					register.salt = generateSalt();
					register.password = generatePass(password, register.salt); // encrypt all of this...
					//wait wait wait if registrationRequired then the server needs to create a new character and send it to the client. 
					c.sendTCP(register);
					System.out.println("registraton sent");
				}
				
				if (object instanceof SignUpNetwork.RegistrationFailed)
				{
					GameConstants.signUpScreen.userCreationFailed();
					System.out.println("registraton failed");
					client.close();
				}
				
				if (object instanceof SignUpNetwork.RegistrationComplete)
				{
					Player.PLAYER_NAME = name;
					GameConstants.signUpScreen.userCreationComplete();
					System.out.println("registraton complete");
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
			}
		}));
		
		
		try
		{
			InetAddress REMOTE_ADDRESS = InetAddress.getByName(ClientConstants.HOST);
			client.connect(5000, REMOTE_ADDRESS, ClientConstants.SIGN_UP_SERVER_PORT_TCP, ClientConstants.SIGN_UP_SERVER_PORT_UDP);
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
	
	
	public void dispose()
	{
		if (client.isConnected())
			client.stop();
	}
	
	
	public void run()
	{
		System.out.println("Started SignUpClient");
		
	}
	
}
