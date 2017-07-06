
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

	//these two variables are the player who's turn it is
	static Player currPlayer;
	//the following fild is the index of the player and the player's corresponding swing components in many arrays
	static int currPlayerIndex;
	//with this field you can change the bechavior of the log text area
	static String tab = " -> ";

	/**
	 * Class constructor
	 * Initializes our JFrame
	 */
	oakOligarchy(){
		window = new JFrame("Oakland Oligarchy");
		//default size for the JFrame
		window.setSize(1500, 1080);

		//adding dummy data into the menu
		menu = new Menu();
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
	 *This method does all the actiontile stuffs
	 */
	public static void actionTileFun(){
		controls.writeLine("******Action Tile******");
		int action = 1;
		switch(action){
			case 1:
				controls.writeLine(tab+"you lost your Pitt ID");
				controls.writeLine(tab+"go to panther central and pay the bank $20(on top of any rent)");
				currPlayer.money-=20;
				//tile 10 is pantherc
				movePlayerTo(currPlayer,10);
				//currPlayer.money
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
		}
		controls.writeLine("***********************");
		board.updateMoney();

	}

	/**
	 * Move the marker for a player to the tile tileNum
	 * the tileNums are the indeces of the tileInfo data in GameBoard.java
	 * @param player The Player object for the player whose marker must be moved
	 * @param spaces The number of spaces the marker will be moved forward
	 */
	static void movePlayerTo(Player player, int tileNum){
		board.erasePlayer(player);
		player.tileIndex = tileNum;
		board.drawPlayer(player);
	}

	/**
	 * Move the marker for a player forward a certain number of spaces
	 * @param player The Player object for the player whose marker must be moved
	 * @param spaces The number of spaces the marker will be moved forward
	 */
	static void movePlayer(Player player,int spaces){
		board.erasePlayer(player);
		player.tileIndex = (player.tileIndex+spaces)%36;
		try{
			Thread.sleep(1);
		}catch(Exception e){}
		board.drawPlayer(player);
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
			controls.writeLine(tab+currPlayer.name+" purchased "+tile.propertyName );
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
		controls.writeLine(currPlayer.name+"'s turn");
		controls.showRollButton();

		waitForControlEvent(Controls.Event.ROLL);
		controls.writeLine(tab+currPlayer.name + " rolled a " + Integer.toString(roll1) + " and a "+ Integer.toString(roll2));
		controls.hideRollButton();

		movePlayer(currPlayer,roll1+roll2);
		controls.showPurchaseButton();
		controls.showEndTurnButton();
		//getting charged that money
		Tile tmp = board.tiles.get(currPlayer.tileIndex);
		if(tmp.propertyName.equals("actiontile")){
			actionTileFun();
			//reget tmp because actionTileFun can move players
			tmp = board.tiles.get(currPlayer.tileIndex);
		}
		if(tmp.owner != null || tmp.propertyValue>currPlayer.getMoney()){
			controls.hidePurchaseButton();
		}
		else{
			controls.showPurchaseButton();
		}
		if(tmp.owner != null && tmp.owner != currPlayer && tmp.rent>0){
			if (tmp.rent <= currPlayer.money) {
				currPlayer.money -= tmp.rent;
				tmp.owner.money+=tmp.rent;
				board.updateMoney();
				controls.writeLine(tab+currPlayer.name + " paid $" + Integer.toString(tmp.rent) + " in rent to " + tmp.owner.name);
			} else {
				/*
				 * TODO Allow player to trade properties to cover the rent
				 * For now, pay what is possible and print out in the log that the user
				 * could not afford the rent and owes money.
				 */
				int currMoney = currPlayer.money;
				currPlayer.money -= currMoney;
				tmp.owner.money += currMoney;
				board.updateMoney();
				controls.writeLine(tab+currPlayer.getName() + " could not afford the $" + Integer.toString(tmp.rent)
				 	+ " rent and owes $" + Integer.toString(tmp.rent - currMoney) + " in rent to " + tmp.owner.getName());
			}
		}

		event = waitForControlEvents(postRollEvents,2);
		if(event == postRollEvents[1]){
			purchaseCurrentProperty();
		}
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();
	}

	/**
	 * Check the money and properties of each player to see if only one player has any assets remaining.
	 * If only one player has any remaining assets, they have won the game, and the game will end.
	 * @return boolean True if a winner exists, False if there is no winner
	 */
	private static boolean winnerExists() {
		int loserCount = 0;
		Player winner = null;
		for (Player p : players) {
			if (p.getMoney() == 0 && p.properties.size() == 0) {
				loserCount++;
			} else {
				winner = p;
			}
		}

		if (loserCount == players.size() - 1) {
			menu.stopClock();
			controls.hideRollButton();
			controls.writeLine("============ GAME OVER ============");
			controls.writeLine(winner.getName() + " has won the game!");
			controls.writeLine("===================================");
			JOptionPane.showMessageDialog(null, winner.getName() + " has won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			return false;
		}
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
		while(!winnerExists()){
			nextTurn();
		}
	}
}
