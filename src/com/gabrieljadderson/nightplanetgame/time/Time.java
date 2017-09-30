package com.gabrieljadderson.nightplanetgame.time;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Gabriel Jadderson
 */
public class Time extends Thread implements Runnable
{
	
	private static boolean areTimersInitiated = false;
	
	public static Timer timer = new Timer(); //not used yet, loader thread.
	public static Timer timer1; //used for position client
	public static Timer timer2; //used for sign up client
	public static Timer timer3; //used for login client
	public static Timer timer4; //not used
	
	public boolean initTimers()
	{
		timer1 = new Timer();
		timer2 = new Timer();
		timer3 = new Timer();
		timer4 = new Timer();
		return areTimersInitiated = true;
	}
	
	/**
	 * @param task       - task to be scheduled.
	 * @param delay      - delay in milliseconds before task is to be executed.
	 * @param period     - time in milliseconds between successive task executions.]]]}
	 * @param taskNumber - the taskNumber number 0 is for main timer and should not be used use 1 and beyond.
	 */
	public void run(TimerTask task, Long delay, Long period, int taskNumber)
	{
		if (areTimersInitiated)
		{
			super.setDaemon(true);
			if (taskNumber == 1)
			{
				timer1.schedule(task, delay, period);  //initial delay + subsequent rate
			} else if (taskNumber == 2)
			{
				timer2.scheduleAtFixedRate(task, delay, period);
			} else if (taskNumber == 3)
			{
				timer3.scheduleAtFixedRate(task, delay, period);
			} else if (taskNumber == 4)
			{
				timer4.scheduleAtFixedRate(task, delay, period);
			}
		} else
		{
			if (initTimers())
			{
				run(task, delay, period, taskNumber);
			}
		}
		
	}
	
	/**
	 * cancels all current running timers.
	 */
	public static void CUT_AND_DESTROY_ALL_TIMERS()
	{
		//cancel all the timers from descending order.
		timer3.cancel();
		timer3.purge();
		timer2.cancel();
		timer2.purge();
		timer1.cancel();
		timer1.purge();
		
		//main thread.
		timer.cancel();
		timer.purge();
	}
	
	@Override
	public void run()
	{
		initTimers();
	}
}