
/**
 * Main program for Oakland Oligarchy game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class oakOligarchy{
	static JFrame window;
	static Information playerInfo;
	static GameBoard board;
	static Menu menu;
	static ArrayList<Player> players;
	
	//these two variables are the player
	static Player currPlayer;
	static int currPlayerIndex;

	/**
	 * Class constructor
	 * Initializes our JFrame
	 */
	oakOligarchy(){
		window = new JFrame("Oakland Oligarchy");
		//default size for the JFrame
		window.setSize(1500, 1080);

		//adding dummy data into the menu
		menu = new Menu(new Player("no one",0,"yo"));
		board = new GameBoard();
		playerInfo = new Information();
		window.add(menu, BorderLayout.NORTH);
		window.add(playerInfo, BorderLayout.WEST);
		window.add(board.getBoard(), BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	/**
	 * Creates a random dice roll
	 * @return int The integer value of the dice roll
	 */
	public static int rollDice(){
		return (int)(Math.random()*6+1);
	}





	/**
	 * Move the marker for a player forward a certain number of spaces
	 * @param player The Player object for the player whose marker must be moved
	 * @param spaces The number of spaces the marker will be moved forward
	 */
	static void movePlayer(Player player,int spaces){
		board.erasePlayer(currPlayer);
		player.tileIndex = (player.tileIndex+spaces)%36;
		board.drawPlayer(currPlayer);
	}

	public static void waitForBoardEvent(GameBoard.Event event){
		while(true){
			while(!board.pollForEvent()){
				try {
			   		Thread.sleep(1);
				} catch(InterruptedException e) {}

			}
			if(event == board.getEvent()){
				break;
			}			
		}
	}
	
	public static GameBoard.Event waitForBoardEvents(GameBoard.Event event[], int numEvents){
		GameBoard.Event tmp;
		while(true){
			while(!board.pollForEvent()){
				try {
			   		Thread.sleep(1);
				} catch(InterruptedException e) {}
			}
			tmp = board.getEvent();
			for(int i=0;i<numEvents;i++)
				if(event[i] == tmp){
					return tmp;
				}
		}
	}
	
	private static void purchaseCurrentProperty(){
		Tile tile = board.tiles.get(currPlayer.tileIndex);
		if(tile.owner == null && tile.propertyValue>0){
			currPlayer.purchaseProperty(tile);
			playerInfo.drawPlayerInfo(currPlayer);
		}
	}
	private static void nextTurn(){
		final GameBoard.Event postRollEvents[] = {GameBoard.Event.END_TURN, GameBoard.Event.PURCHASE};
		int roll1 = rollDice();
		int roll2 = rollDice();
		GameBoard.Event event;

		board.hidePurchaseButton();
		board.hideEndTurnButton();
		
		currPlayerIndex = (currPlayerIndex+1)%players.size();
		currPlayer = players.get(currPlayerIndex);
		menu.drawPlayersTurn(currPlayer);
		board.drawPlayersTurn(currPlayer);
		board.showRollButton();
		
		waitForBoardEvent(GameBoard.Event.ROLL);
		board.hideRollButton();
		movePlayer(currPlayer,roll1+roll2);
		board.drawDiceRoll(currPlayer,roll1,roll2);
		board.showPurchaseButton();
		board.showEndTurnButton();
		//getting charged that money
		Tile tmp = board.tiles.get(currPlayer.tileIndex);
		if(tmp.owner != null && tmp.owner != currPlayer){
			currPlayer.money -= tmp.rent;
			tmp.owner.money+=tmp.rent;
			
		}
		
		
		event = waitForBoardEvents(postRollEvents,2);
		if(event == postRollEvents[1]){
			purchaseCurrentProperty();
		}
		board.hidePurchaseButton();
		board.hideEndTurnButton();

	}
	

	/**
	 * Main method to house the highest layer of our application logic
	 * @param args Unused
	 */
	public static void main(String[] args){
		new oakOligarchy();

		//sleeping while the player info is collected
		while(playerInfo.playerDataFlag == false){
			try {
			   Thread.sleep(50);
			} catch(InterruptedException e) {
			}
		}
		players=playerInfo.players;
		playerInfo.initPlayerInfo();
		for(Player player : players){
			board.drawPlayer(player);
		}
		currPlayerIndex=(int)(Math.random()*players.size());
		currPlayer = players.get(currPlayerIndex);
		//menu.drawPlayer(players.get(0));
		//this is the start of the actual game
		while(true){
			nextTurn();
		}
	}
}
