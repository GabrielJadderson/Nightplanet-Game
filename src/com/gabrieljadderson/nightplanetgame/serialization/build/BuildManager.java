package com.gabrieljadderson.nightplanetgame.serialization.build;

import com.gabrieljadderson.nightplanetgame.GameConstants;



public class BuildManager {
	
	public BuildManager() {
		incrementBuild();
	}
	
	private synchronized void incrementBuild() {
		GameConstants gc = new GameConstants();
		BuildTokens bt = new BuildTokens(gc);
		if (BuildSerialization.doesBuildFileExist()) {
        	new BuildSerialization(bt).deserialize();
        	bt.setBuild(bt.getBuild()+1);
        	new BuildSerialization(bt).serialize();
        } else {
        	new BuildSerialization(bt).serialize();
        }
	}

}
