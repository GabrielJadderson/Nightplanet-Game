package com.gabrieljadderson.nightplanetgame;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;
import com.gabrieljadderson.nightplanetgame.graphics.anim.Animation;
import com.gabrieljadderson.nightplanetgame.graphics.anim.NPC;
import com.gabrieljadderson.nightplanetgame.graphics.objects.GObject;
import com.gabrieljadderson.nightplanetgame.graphics.objects.ObjectDefinition;
import com.gabrieljadderson.nightplanetgame.graphics.objects.ObjectParser;
import com.gabrieljadderson.nightplanetgame.map.items.ItemDefinition;
import com.gabrieljadderson.nightplanetgame.map.items.ItemObject;
import com.gabrieljadderson.nightplanetgame.map.items.ItemParser;
import com.gabrieljadderson.nightplanetgame.map.npc.NPCParser;
import com.gabrieljadderson.nightplanetgame.map.npc.NpcDefinition;
import com.gabrieljadderson.nightplanetgame.shaders.ShaderParser;
import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Paths;

/**
 * @author Gabriel Jadderson
 */
public class Loader
{
	
	public static final String dir_MUSIC = GameConstants.resourceLoader.getAbsoluteResourcePath("assets/sounds/MUSIC/");
	public static final String dir_SFX = GameConstants.resourceLoader.getAbsoluteResourcePath("assets/sounds/SFX/");
	public static final String dir_MAPS = GameConstants.resourceLoader.getAbsoluteResourcePath("assets/maps/");
	public static final String dir_SHADERS = GameConstants.resourceLoader.getAbsoluteResourcePath("assets/Shaders/");
	
	boolean complete = false;
	
	public Loader()
	{
		
		//TODO: create a json paerser that has a loader class type and the path of each resource e.g:  someobject,path: "objects/loading_bar/0.25/Loading_Bar.atlas", loaderClass: TextureAtlas.class
		
		GameConstants.SPRITE_SHEET = new SpriteSheet(ResourceCrawler.getRes("entities.png"), 16, 18, 2, 2);
		
		
		ResourceCrawler.paths.forEach((k, v) -> {
			
			String s = v.getFileName().toString();
			if (Files.getFileExtension(s).equals("atlas"))
			{
				GameConstants.assetManager.loadFromCrawler(s, TextureAtlas.class);
				GameConstants.assetManager.update();
				System.out.println("loaded TextureAtlas: " + v);
			} else if (Files.getNameWithoutExtension(s).contains("SFXGame"))
			{
				GameConstants.assetManager.loadFromCrawler(s, Sound.class);
				GameConstants.assetManager.update();
				System.out.println("loaded Sound: " + v);
			} else if (Files.getNameWithoutExtension(s).contains("MSCGame"))
			{
				GameConstants.assetManager.loadFromCrawler(s, Music.class);
				GameConstants.assetManager.update();
				System.out.println("loaded Music: " + v);
			}
		});
		
		loadItems();
		loadMaps();
		loadSounds();
		loadAllShaders();
		
		loadNpcs();
		loadObjects();
		
		GameConstants.assetManager.finishLoading();
		GameConstants.assetManager.update();

//		GameConstants.ta = GameConstants.assetManager.get("objects/fire/whiteFlame.atlas", TextureAtlas.class);
//		GameConstants.ta1 = GameConstants.assetManager.get("objects/loading_animation/loading_animation.atlas", TextureAtlas.class);
//		GameConstants.sprite = GameConstants.ta.createSprite("0009");
//		GameConstants.sprite.setScale(10);

//		Music test1 = GameConstants.assetManager.get("sounds/MUSIC/Time-Running-Out.mp3", Music.class);
//		Sound test2 = GameConstants.assetManager.get("sounds/SFX/SFXGame.624.ogg", Sound.class);

//		test1.setLooping(true);
//		test1.play();

//		long d = test2.play();
//		test2.setLooping(d, true);;
		
		complete = true;
	}
	
	
	public void loadNpcs()
	{
		try
		{
			java.nio.file.Files.walk(Paths.get(NPCParser.DIR)).filter(x -> Files.getFileExtension(x.getFileName().toString()).equals("json")).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					System.out.println("walk in ");
					System.out.println(x.getFileName().toString());
					int npcId = Integer.parseInt(Files.getNameWithoutExtension(x.getFileName().toString()));
					NpcDefinition npcDef = new NPCParser(npcId).deserialize();
					NPC npc = new NPC();
					npc.createNPC(npcDef);
					GameConstants.NPCs.put(npcId, npc);
					System.out.println("loaded: " + x.getFileName().toString());
				}
			});
			System.out.println("loaded: " + GameConstants.NPCs.size() + " NPCs");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadObjects()
	{
		
		try
		{
			java.nio.file.Files.walk(Paths.get(ObjectParser.DIR)).filter(x -> Files.getFileExtension(x.getFileName().toString()).equals("json")).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					int ObjectID = Integer.parseInt(Files.getNameWithoutExtension(x.getFileName().toString()));
					ObjectDefinition od = new ObjectParser(ObjectID).deserialize();
					GObject go = new GObject(od);
					GameConstants.GameObjects.put(ObjectID, go);
					System.out.println("loaded: " + x.getFileName().toString());
				}
			});
			System.out.println("loaded: " + GameConstants.GameObjects.size() + " Objects");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void loadItems()
	{
		try
		{
			java.nio.file.Files.walk(Paths.get(ItemParser.DIR)).filter(x -> Files.getFileExtension(x.getFileName().toString()).equals("json")).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					int itemId = Integer.parseInt(Files.getNameWithoutExtension(x.getFileName().toString()));
					ItemDefinition itemDefinition = new ItemParser(itemId).deserialize();
					itemDefinition.setAnimations(Animation.extract(GameConstants.SPRITE_SHEET, 4, true)); //change this to the itemdefs from json.
					itemDefinition.setV_position(new Vector2(itemDefinition.getPositionX(), itemDefinition.getPositionY()));
					GameConstants.items.put(itemDefinition, new ItemObject());
					
					System.out.println("loaded: " + x.getFileName().toString());
				}
			});
			
			System.out.println("loaded: " + GameConstants.items.size() + " items");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadMaps()
	{
		try
		{
			java.nio.file.Files.walk(Paths.get(dir_MAPS)).filter(x -> Files.getFileExtension(x.getFileName().toString()).equals("tmx")).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					TiledMap loadedMap = new TmxMapLoader().load(x.toFile().getAbsolutePath());
					GameConstants.maps.put(x.getFileName().toString().substring(0, x.getFileName().toString().length() - 4), loadedMap);
					System.out.println("loaded: " + x.getFileName().toString());
				}
			});
			GameConstants.assetManager.update();
			System.out.println("loaded: " + GameConstants.maps.size() + " maps");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadSounds()
	{
		try
		{
			int[] counters = {0, 0};
			java.nio.file.Files.walk(Paths.get(dir_MUSIC)).filter(x -> (Files.getFileExtension(x.getFileName().toString()).equals("mp3") || Files.getFileExtension(x.getFileName().toString()).equals("ogg") || Files.getFileExtension(x.getFileName().toString()).equals("wav"))).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					GameConstants.assetManager.load(x.toFile().getAbsolutePath(), Music.class); //take a closer look at the pathing of this.
					System.out.println("loaded: " + x.getFileName().toString());
					counters[0]++;
				}
			});
			GameConstants.assetManager.update();
			
			java.nio.file.Files.walk(Paths.get(dir_SFX)).filter(x -> (Files.getFileExtension(x.getFileName().toString()).equals("mp3") || Files.getFileExtension(x.getFileName().toString()).equals("ogg") || Files.getFileExtension(x.getFileName().toString()).equals("wav"))).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					GameConstants.assetManager.load(x.toFile().getAbsolutePath(), Sound.class); //take a closer look at the pathing of this.
					System.out.println("loaded: " + x.getFileName().toString());
					counters[1]++;
				}
			});
			GameConstants.assetManager.update();
			System.out.println("loaded: " + counters[0] + " music-tracks");
			System.out.println("loaded: " + counters[1] + " sound-effects");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadAllShaders()
	{
		try
		{
			int[] counters = {0, 0};
			java.nio.file.Files.walk(Paths.get(dir_SHADERS)).forEach(x -> {
				if (!java.nio.file.Files.isDirectory(x))
				{
					String fn = x.getFileName().toString();
					if (Files.getFileExtension(fn).equals("glsl"))
					{
						if (Files.getNameWithoutExtension(fn).contains("_vert"))
						{
							GameConstants.shaderManager.shaders_vert.put(FilenameUtils.removeExtension(fn), new FileHandle(x.toFile()).readString());
							counters[0]++;
						} else if (Files.getNameWithoutExtension(fn).contains("_frag"))
						{
							GameConstants.shaderManager.shaders_frag.put(FilenameUtils.removeExtension(fn), new FileHandle(x.toFile()).readString());
							counters[1]++;
						}
					} else if (Files.getFileExtension(fn).equals("vert"))
					{
						GameConstants.shaderManager.shaders_vert.put(FilenameUtils.removeExtension(fn), new FileHandle(x.toFile()).readString());
						counters[0]++;
					} else if (Files.getFileExtension(fn).equals("frag"))
					{
						GameConstants.shaderManager.shaders_frag.put(FilenameUtils.removeExtension(fn), new FileHandle(x.toFile()).readString());
						counters[1]++;
					}
					System.out.println("loaded: " + x.getFileName().toString());
				}
			});
			GameConstants.assetManager.update();
			System.out.println("loaded: " + GameConstants.maps.size() + " maps");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		GameConstants.assetManager.update();
		System.out.println("loaded: " + GameConstants.shaderManager.shaders_vert.size() + " Vertex Shaders");
		System.out.println("loaded: " + GameConstants.shaderManager.shaders_frag.size() + " Fragment Shaders");
		new ShaderParser(); //deserializes all current shaders when done from the SHADER_CONFIG file.
	}
	
	public boolean isComplete()
	{
		return complete;
	}
	
}
