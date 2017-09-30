package com.gabrieljadderson.nightplanetgame.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

/**
 * Created by Gabriel Jadderson on 25-09-2017.
 */
public class BulkFileRenamer
{
	public static void main(String[] args)
	{
		
		try
		{
			String path = "C:\\Users\\mulli\\OneDrive\\Dokumenter\\IdeaProjects\\NightPlanetGame\\src\\com\\gabrieljadderson\\nightplanetgame\\RESOURCES\\assets\\objects\\npcSplash\\splash2\\images_font";
			
			String prefix = "IMAGE_HIT_SPLASH_";
			String postfix = "";
			String extension = ".png";
			
			HashMap<Integer, Path> pathMap = new HashMap<>();
			
			Files.walk(Paths.get(path)).forEachOrdered(x -> {
				if (!Files.isDirectory(x))
				{
					pathMap.put(Integer.parseInt(com.google.common.io.Files.getNameWithoutExtension(x.getFileName().toString())), x);
				}
			});
			
			
			pathMap.forEach((k, v) -> {
				String filename = prefix + k + postfix + extension;
				try
				{
					Files.move(Paths.get(v.toString()), Paths.get(v.getParent().toString() + "\\" + filename), StandardCopyOption.REPLACE_EXISTING);
					
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			});
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
