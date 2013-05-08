package be.fomp.carcassonne.view;

import java.awt.Color;



public interface GameView extends View{
	
	int WINDOW_W = 1250;
	int WINDOW_H = 700;
	
	int MENU_H = 20;
	int TILE_W = 60;
	int TILE_H = 60;
	
	int SCORE_AREA_W = 250;
	int SCORE_AREA_H = 200;
	int SCORE_AREA_X = 10;
	int SCORE_AREA_Y = 50;
	
	int CONTROL_AREA_W = 250;
	int CONTROL_AREA_H = 250;
	int CONTROL_AREA_X = SCORE_AREA_X;
	int CONTROL_AREA_Y = SCORE_AREA_H + SCORE_AREA_Y + 10;
	

	int GAME_AREA_X = Math.max(CONTROL_AREA_W,CONTROL_AREA_W) + SCORE_AREA_X *2;
	int GAME_AREA_Y = 50;
	int GAME_AREA_W = WINDOW_W - GAME_AREA_X - SCORE_AREA_X;
	int GAME_AREA_H = WINDOW_H - GAME_AREA_Y - SCORE_AREA_Y;
	
	Color TILE_BACKGROUND = Color.BLACK;
}
