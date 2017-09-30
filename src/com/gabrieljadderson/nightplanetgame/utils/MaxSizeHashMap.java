package com.gabrieljadderson.nightplanetgame.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Gabriel Jadderson
 */
public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V>
{
	
	private final int MAX_SIZE;
	
	public MaxSizeHashMap(int maxSize)
	{
		this.MAX_SIZE = maxSize;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
	{
		return size() > MAX_SIZE;
	}
	
	public int getMaxSize()
	{
		return MAX_SIZE;
	}
	
}