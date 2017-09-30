package com.gabrieljadderson.nightplanetgame;

import com.badlogic.gdx.files.FileHandle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Gabriel Jadderson on 25-09-2017.
 */
public class ResourceCrawler
{
	public static HashMap<String, Path> paths = new HashMap<>();
	
	public ResourceCrawler()
	{
	
	}
	
	public static void crawl()
	{
		try
		{
			Files.walk(Paths.get(new ResourceLoader().getAbsoluteResourcePath(""))).filter(x -> !Files.isDirectory(x)).forEach(x -> {
				paths.put(x.getFileName().toString(), x);
			});
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Path get(String fileName)
	{
		if (fileName != null)
			if (!fileName.trim().equals(""))
				if (paths.containsKey(fileName))
				{
					return paths.get(fileName);
				} else
				{
					System.out.println("File not found in Crawler: " + fileName);
				}
		
		return null;
	}
	
	public static String getResource(String filename)
	{
		return get(filename).toString();
	}
	
	public static FileHandle getRes(String fileName)
	{
		return new FileHandle(getResource(fileName));
	}
}
