package com.gabrieljadderson.nightplanetgame.tests.net.signup;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * @author Gabriel Jadderson
 */
public class LoginNetwork
{
	
	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(LoginRequired.class);
		kryo.register(ValidateRegistry.class);
		kryo.register(Character.class);
		kryo.register(LoginComplete.class);
		kryo.register(LoginFailed.class);
	}
	
	static public class LoginRequired
	{
	}
	
	static public class ValidateRegistry
	{
		String email;
		String password;
	}
	
	static public class LoginComplete
	{
		Character character;
	}
	
	static public class LoginFailed
	{
	}
	
}
