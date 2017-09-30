package com.gabrieljadderson.nightplanetgame.time.tasks;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.tests.net.signup.SignUpClient;

import java.util.TimerTask;

/**
 * @author Gabriel Jadderson
 */
public class SignUpQueue extends TimerTask
{
	
	public static int taskNumber = 2; //!!!! 1 
	
	public static volatile boolean stop = false;
	public static volatile boolean dispose = false;
	
	String e;
	String p;
	String n;
	
	public SignUpQueue(String email, String password, String name)
	{
		e = email;
		p = password;
		n = name;
	}
	
	@Override
	public void run()
	{
		if (!stop && !dispose)
		{
			GameConstants.client_SignUP = new SignUpClient(e, p, n);
			stop = true;
		}
		
		if (dispose)
		{
			GameConstants.time.timer2.cancel();
			GameConstants.time.timer2.purge();
		}
		
	}
	
	public void reconnect()
	{
		stop = false;
	}
	
	public void dispose()
	{
		dispose = true;
	}
	
	
}
