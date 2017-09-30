package com.gabrieljadderson.nightplanetgame.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Gabriel Jadderson
 * @Since 08/07/2015
 * @Version 0.1
 * The obvious advantages of my approach (with regards to the previous solutions) are:
 * Separation of concerns - you don't want trivial things like FPS count calculations polluting the 'main loop' of your program. Thus, isolate this logic into a separate component/class.
 * Independence of the GUI component framework - it is best to steer clear of framework-dependent solutions whenever possible. In the current case this is easy - just avoid referencing things like Graphics or JLabel amongst the calculations and you're good.
 * All measurements stored in a hashmap - Hashmaps are known for their quick look-up times (the time complexity is nearly O(1)). Thus, doing the necessary comparisons should not have any noticeable impact on the overall performance of your application.
 * Can be used with multiple javax.Swing.Timers or threads - call submitReading() on the timer/thread whose performance you are trying to assess while running getFrameRate() from another timer/thread to get the result (for example - you could write it to a JLabel, file, etc).
 * (PS.: Note that with Swing, all painting operations need to be completed on the Event Dispatcher Thread (EDT). Thus, no drawing work should be delegated to other (raw) threads without the use of special commands such as SwingUtilities.invokeLater(..)).
 */

public class FrameRateCounter
{
	
	public static Map<Long, Long> measurementTimes;
	
	public FrameRateCounter()
	{
		measurementTimes = new HashMap<Long, Long>();
	}
	
	public synchronized void submitReading()
	{
		long measurementTime = System.nanoTime();
		long invalidationTime = System.nanoTime() + 1000000000;
		measurementTimes.put(measurementTime, invalidationTime);
	}
	
	public int getFrameRate()
	{
		performInvalidations();
		return measurementTimes.size();
	}
	
	public int getNvidiaFrameRate()
	{
		performInvalidations();
		return measurementTimes.size() * 2;
	}
	
	private synchronized void performInvalidations()
	{
		long currentTime = System.nanoTime();
		Iterator<Long> i = measurementTimes.keySet().iterator();
		while (i.hasNext())
		{
			long measurementTime = i.next();
			long invalidationTime = measurementTimes.get(measurementTime);
			if (invalidationTime < currentTime)
			{
				i.remove();
			}
		}
	}
}