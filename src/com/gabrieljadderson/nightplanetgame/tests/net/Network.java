package com.gabrieljadderson.nightplanetgame.tests.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.

/**
 * @author Gabriel Jadderson
 */
public class Network
{
	
	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(RegistrationRequired.class);
		kryo.register(Register.class);
		kryo.register(AddCharacter.class);
		kryo.register(UpdateCharacter.class);
		kryo.register(RemoveCharacter.class);
		kryo.register(Character.class);
		kryo.register(MoveCharacter.class);
	}
	
	static public class Login
	{
		public String name;
	}
	
	static public class RegistrationRequired
	{
	}
	
	static public class Register
	{
		public String name;
		public String otherStuff;
	}
	
	static public class UpdateCharacter
	{
		public int id;
		public float x, y;
		public int GUID;
	}
	
	static public class AddCharacter
	{
		public Character character;
	}
	
	static public class RemoveCharacter
	{
		public int UIN;
	}
	
	static public class MoveCharacter
	{
		public float x, y;
		public float proj_x, proj_y;
	}
}