package com.gabrieljadderson.nightplanetgame;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Gabriel Jadderson on 24-09-2017.
 */
public class ResourceLoader
{
	
	public File retrieveNative(String nativeFileName)
	{
		return loadResource("RESOURCES/natives/" + nativeFileName);
	}
	
	public InputStream retrieveNativeStreamed(String nativeFileName)
	{
		return loadResourceStream("RESOURCES/natives/" + nativeFileName);
	}
	
	public void loadNatives()
	{
		System.out.println(retrieveNative("gdx64.dll").toString());
	}
	
	public File loadResource(String name)
	{
		try
		{
			String pngpath = this.getClass().getResource(name).getFile();
			File file = new File(pngpath);
			
			if (file != null)
			{
				System.out.println("loaded resource: " + name);
				return file;
			}
		} catch (Exception e)
		{
			System.err.println("Failed to load resource: " + name);
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAbsolutePath(String name)
	{
		try
		{
			String pngpath = this.getClass().getResource(name).getFile();
			File file = new File(pngpath);
			
			if (file != null)
			{
				System.out.println("loaded resource: " + name);
				return file.getAbsolutePath();
			}
		} catch (Exception e)
		{
			System.err.println("Failed to load resource: " + name);
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCanonicalPath(String name)
	{
		try
		{
			String pngpath = this.getClass().getResource(name).getFile();
			File file = new File(pngpath);
			
			if (file != null)
			{
				System.out.println("loaded resource: " + name);
				return file.getCanonicalPath();
			}
		} catch (Exception e)
		{
			System.err.println("Failed to load resource: " + name);
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAbsoluteResourcePath(String file)
	{
		return getAbsolutePath("RESOURCES" + "/" + file);
	}
	
	public String getCanonicalResourcePath(String file)
	{
		return getCanonicalPath("RESOURCES" + File.separator + file);
	}
	
	
	public InputStream loadResourceStream(String name)
	{
		try
		{
			return this.getClass().getResourceAsStream(name);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
