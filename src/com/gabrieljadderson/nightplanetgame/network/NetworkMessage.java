package com.gabrieljadderson.nightplanetgame.network;

/**
 * @author Gabriel Jadderson
 */
public class NetworkMessage
{
	
	// Use these to send messages of different "types".
	// With bytes you can only have 255 different Messages.
	// Use "int" for example, to enable ~4.294.967.300 different Messages.
	public static final byte DISCONNECT = 1;
	public static final byte WHATEVER = 2;
	
	// The Message itself, so either 1 or 2.
	public byte msgValue;
	
	
}
