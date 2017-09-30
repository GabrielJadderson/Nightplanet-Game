package com.gabrieljadderson.nightplanetgame.tests.net;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;
import com.gabrieljadderson.nightplanetgame.player.Player;
import com.gabrieljadderson.nightplanetgame.tests.net.Network.*;
import com.gabrieljadderson.nightplanetgame.utils.Position;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * @author Gabriel Jadderson
 */
public class PositionClient implements Runnable
{
	
	Client client;
//	String name; // the name of the player
	
	/**
	 * The queue of tasks that will be sent to the server.
	 */
//    private final Queue<String> sendQueue = new ArrayDeque<>(); //instead of string replace the object with a class that holds something i guess 
	@Override
	public void run()
	{
		System.out.println("Network Protocol started");
		
		//TODO initiate all of the queuing stuff to be sent.
	}
	
	public PositionClient()
	{
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.start();
		
		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);
		
		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener()
		{
			public void connected(Connection connection)
			{
				
			}
			
			public void received(Connection connection, Object object)
			{
				if (object instanceof RegistrationRequired)
				{
					Register register = new Register();
					register.name = Player.PLAYER_NAME;
					register.otherStuff = "misc";
					
					//wait wait wait if registrationRequired then the server needs to create a new character and send it to the client. 
					client.sendTCP(register);
				}
				
				if (object instanceof AddCharacter)
				{
					AddCharacter msg = (AddCharacter) object;
					addCharacter(msg.character);
					return;
				}
				
				if (object instanceof UpdateCharacter)
				{
					updateCharacter((UpdateCharacter) object);
					return;
				}
				
				if (object instanceof RemoveCharacter)
				{
					RemoveCharacter msg = (RemoveCharacter) object;
					removeCharacter(msg.UIN);
					return;
				}
			}
			
			public void disconnected(Connection connection)
			{
				System.exit(0);//really...
			}
		}));
		
		
		try
		{
			InetAddress REMOTE_ADDRESS = InetAddress.getByName(ClientConstants.HOST);
			client.connect(5000, REMOTE_ADDRESS, ClientConstants.MAIN_SERVER_PORT_TCP, ClientConstants.MAIN_SERVER_PORT_UDP);
			// Server communication after connection can go here, or in Listener#connected().
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		Login login = new Login();
		login.name = Player.PLAYER_NAME;
		client.sendTCP(login);
		
		
	}
	
	
	HashMap<String, Character> characters = new HashMap<String, Character>();

//		public String inputName () {
//			String input = (String)JOptionPane.showInputDialog(null, "Name:", "Connect to server", JOptionPane.QUESTION_MESSAGE,
//				null, null, "Test");
//			if (input == null || input.trim().length() == 0) System.exit(1);
//			return input.trim();
//		}

//		public String inputOtherStuff () {
//			String input = (String)JOptionPane.showInputDialog(null, "Other Stuff:", "Create account", JOptionPane.QUESTION_MESSAGE,
//				null, null, "other stuff");
//			if (input == null || input.trim().length() == 0) System.exit(1);
//			return input.trim();
//		}
	
	public void addCharacter(Character character)
	{
		characters.put(character.UIN, character);
		System.out.println(character.playername + " added at " + character.x + ", " + character.y);
	}
	
	public void updateCharacter(UpdateCharacter msg)
	{
		Character character = characters.get(msg.id);
		if (character == null) return;
		character.x = msg.x;
		character.y = msg.y;
//			System.out.println(character.name + " moved to " + character.x + ", " + character.y);
	}
	
	public void removeCharacter(int id)
	{
		Character character = characters.remove(id);
		if (character != null) System.out.println(character.playername + " removed");
	}
	
	
	public void pushNewPosition(Position position)
	{
		MoveCharacter msg = new MoveCharacter();
		msg.x = position.getX();
		msg.y = position.getY();
		if (msg != null && position != null) client.sendTCP(msg);
	}
	
}