package com.gabrieljadderson.nightplanetgame.serialization.build;

import com.gabrieljadderson.nightplanetgame.GameConstants;

public class BuildTokens {
	
	GameConstants gc;
	
	public BuildTokens(GameConstants gcc) {
		gc = gcc;
	}
	
	
	/*
	 * game Title
	 */
	public void setGameTitle(String newTitle) {
		gc.TITLE = newTitle;
	}
	public String getGameTitle() {
		return gc.TITLE;
	}
	
	/*
	 * build number
	 */
	public void setBuild(int newBuild) {
		gc.BUILD_NUMBER = newBuild;
	}
	public int getBuild() {
		return gc.BUILD_NUMBER;
	}
	
	/*
	 * version
	 */
	public void setVersion(String newVer) {
		gc.VERSION = newVer;
	}
	public String getVersion() {
		return gc.VERSION;
	}
	
	

}
