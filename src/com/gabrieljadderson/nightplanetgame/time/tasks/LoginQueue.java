package com.gabrieljadderson.nightplanetgame.time.tasks;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.tests.net.signup.LoginClient;

import java.util.TimerTask;

/**
 * @author Gabriel Jadderson
 */
public class LoginQueue extends TimerTask
{
	
	public static int taskNumber = 3; //!!!! 3
	
	public static volatile boolean stop = false;
	public static volatile boolean dispose = false;
	
	String e;
	String p;
	
	public LoginQueue(String email, String password)
	{
		e = email;
		p = password;
	}
	
	@Override
	public void run()
	{
		if (!stop && !dispose)
		{
			if (GameConstants.client_Login == null)
				GameConstants.client_Login = new LoginClient(e, p);
			stop = true;
		}
		
		if (dispose)
		{
			GameConstants.time.timer3.cancel();
			GameConstants.time.timer3.purge();
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
	
	public void setEmail(String e)
	{
		this.e = e;
	}
	
	public void setPassword(String p)
	{
		this.p = p;
	}
	
	
}
