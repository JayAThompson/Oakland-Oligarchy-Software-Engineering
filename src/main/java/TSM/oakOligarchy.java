
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
	static Controls controls;
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
		controls = new Controls();
		window.add(menu, BorderLayout.NORTH);
		window.add(controls, BorderLayout.WEST);
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

	/**
	* This method waits for the given event. 
	* These events are currently generated through button presses.
	* the thread busy waits while waiting for the event. Sleeping every ms
	* @param event the event to wait for
	*/
	public static void waitForControlEvent(Controls.Event event){
		while(true){
			while(!controls.pollForEvent()){
				try {
			   		Thread.sleep(1);
				} catch(InterruptedException e) {}

			}
			if(controls.getEvent() == event){
				break;
			}			
		}
	}
	
	/**
	* This method waits for the given events. 
	* These events are currently generated through button presses.
	* the thread busy waits while waiting for the events. Sleeping every ms
	* @param event[] an array containing the list of events to wait for
	* @param numEvents the number of entries in the event array
	* @return the event of the list that caused the method to stop busy waiting
	*/
	public static Controls.Event waitForControlEvents(Controls.Event event[], int numEvents){
		Controls.Event tmp;
		while(true){
			while(!controls.pollForEvent()){
				try {
			   		Thread.sleep(1);
				} catch(InterruptedException e) {}
			}
			tmp = controls.getEvent();
			for(int i=0;i<numEvents;i++)
				if(event[i] == tmp){
					return tmp;
				}
		}
	}
	
	/**
	*This method purchases property
	*/
	private static void purchaseCurrentProperty(){
		Tile tile = board.tiles.get(currPlayer.tileIndex);
		if(tile.owner == null && tile.propertyValue>0){
			currPlayer.purchaseProperty(tile);
			tile.setOwner(currPlayer);
			board.updateMoney();
			board.drawProperties(currPlayerIndex);
		}
	}
	
	/**
	* This method houses most of the logic for the actual game. 
	* 
	*/
	private static void nextTurn(){
		final Controls.Event postRollEvents[] = {Controls.Event.END_TURN, Controls.Event.PURCHASE};
		int roll1 = rollDice();
		int roll2 = rollDice();
		Controls.Event event;		
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();
		
		currPlayerIndex = (currPlayerIndex+1)%players.size();
		currPlayer = players.get(currPlayerIndex);
		controls.drawPlayerTurnLabel(currPlayer);
		menu.drawPlayersTurn(currPlayer);		
		controls.showRollButton();
		
		waitForControlEvent(Controls.Event.ROLL);
		controls.writeLine(currPlayer.name + " rolled a " + Integer.toString(roll1) + " and a "+ Integer.toString(roll2));

		controls.hideRollButton();
		movePlayer(currPlayer,roll1+roll2);
		controls.showPurchaseButton();
		controls.showEndTurnButton();
		//getting charged that money
		Tile tmp = board.tiles.get(currPlayer.tileIndex);
		if(tmp.owner != null){
			controls.hidePurchaseButton();
		}
		else{
			controls.showPurchaseButton();
		}
		if(tmp.owner != null && tmp.owner != currPlayer){
			currPlayer.money -= tmp.rent;
			tmp.owner.money+=tmp.rent;
			board.updateMoney();
			controls.writeLine(currPlayer.name + " paid $" + Integer.toString(tmp.rent) + " in rent to " + tmp.owner.name);
		}
		
		
		event = waitForControlEvents(postRollEvents,2);
		if(event == postRollEvents[1]){
			purchaseCurrentProperty();
		}
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();

	}
	

	/**
	 * Main method to house the highest layer of our application logic
	 * @param args Unused
	 */
	public static void main(String[] args){
		new oakOligarchy();

		//sleeping while the player info is collected
		while(controls.playerDataFlag == false){
			try {
			   Thread.sleep(50);
			} catch(InterruptedException e) {
			}
		}
		controls.drawTurnButtons();
		players=controls.players;
		board.boardCenter.initPlayerInfo(players);
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
