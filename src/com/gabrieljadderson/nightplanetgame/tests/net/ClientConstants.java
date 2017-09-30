package com.gabrieljadderson.nightplanetgame.tests.net;

import java.util.HashSet;

/**
 * @author Gabriel Jadderson
 */
public class ClientConstants
{
	
	public static HashSet<Character> loggedInCharacters = new HashSet<Character>();
	
	/* Available port range: 52000-52015 */
	static public final int MAIN_SERVER_PORT_TCP = 52000;
	static public final int MAIN_SERVER_PORT_UDP = 52001;
	
	static public final int SIGN_UP_SERVER_PORT_TCP = 52002;
	static public final int SIGN_UP_SERVER_PORT_UDP = 52003;
	
	static public final int LOGIN_SERVER_PORT_TCP = 52004;
	static public final int LOGIN_SERVER_PORT_UDP = 52005;
	
	//	static public final String HOST = "biogenserver2.noip.me";
	static public final String HOST = "localhost";
//	String ip = InetAddress.getByName(HOST).getCanonicalHostName();
	
	static public final int CLIENT_TIMEOUT = 500;
	
	
}
