package be.fomp.carcassonne.model.beans;

import java.io.Serializable;

import be.fomp.carcassonne.model.Observable;

public interface GameMapBean extends Observable, Serializable {
	TileBean[][] getMap();
	
	void setMap(TileBean[][] map);
	
	Integer getActiveX();
	Integer getActiveY();
	Integer getActiveFollowerLocation();

	
	void setActiveXPos(Integer xPos);
	void setActiveYPos(Integer yPos);
	void setActiveFollowerLocation(Integer location);
}
