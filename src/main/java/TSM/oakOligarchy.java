
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
		controls.writeLine("***********Action Tile***********");
		int action = (int)(Math.random()*8+1);
		switch(action){
			case 1:
				controls.writeLine(tab+"you lost your Pitt ID!");
				controls.writeLine(tab+"go to panther central and pay the bank $20(on top of any rent)");
				currPlayer.money-=20;
				//tile 10 is pantherc
				movePlayerTo(currPlayer,10);
				//currPlayer.money
				break;
			case 2:
				controls.writeLine(tab+"You begged your parents for money.");
				controls.writeLine(tab+"They were feeling generous and gave you $1750");
				currPlayer.money += 1750;
				break;
			case 3:
				controls.writeLine(tab+"You need some cheap grub.");
				controls.writeLine(tab+"go to the Dirty O");
				movePlayerTo(currPlayer,32);
				break;
			case 4:
				controls.writeLine(tab+"Your student loan money came in.");
				controls.writeLine(tab+"You get $10000 from the bank");
				currPlayer.money += 10000;
				break;
			case 5:
				controls.writeLine(tab+"Your got drunk at Papa D's and lost your wallet (again...)");
				controls.writeLine(tab+"You've lost $"+ currPlayer.money/10+" and moved to Papa D's");
				currPlayer.money -= currPlayer.money/10;
				movePlayerTo(currPlayer,34);
				break;
			case 6:
				controls.writeLine(tab+"Your walked around schenley park and pondered the futility of life");
				controls.writeLine(tab+"Luckily existential crises have monetary value in Oakland Oligarchy");
				controls.writeLine(tab+"Your newfound nihilism has doubled your dough! and you've moved to Schenley park");
				currPlayer.money *=2;
				movePlayerTo(currPlayer,26);
				break;
			case 7:
				controls.writeLine(tab+"The other players have ganged up robbed you.");
				for(Player player : players){
					if(player != currPlayer && currPlayer.money>300 && player.money>0){
						controls.writeLine(tab + player.name+" took $300");
						player.money+=300;
						currPlayer.money -=300;
					}
				}
				break;			
			case 8:
				controls.writeLine(tab + "You see that Dippy is wearing your hat.");
				controls.writeLine(tab + "You sue the Carnegie Museum of Natural History for psychological damages");
				controls.writeLine(tab + "You've earned $5000, way to go you hard worker");
				currPlayer.money += 5000;
				break;
//			case 9:
//				controls.writeLine(tab + "Finals are around the corner, go to hillman
		}
		controls.writeLine("*********************************");
		board.updateMoney();

	}

	static void passGo(){
		controls.writeLine(tab + "You passed start and collected $1000");
		currPlayer.money += 1000;
	}
	
	/**
	 * Move the marker for a player to the tile tileNum
	 * the tileNums are the indeces of the tileInfo data in GameBoard.java
	 * @param player The Player object for the player whose marker must be moved
	 * @param spaces The number of spaces the marker will be moved forward
	 */
	static void movePlayerTo(Player player, int tileNum){
		board.erasePlayer(player);
		if(tileNum < player.tileIndex){
			passGo();
		}
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
		if(player.tileIndex + spaces >=36){
			passGo();
		}
		player.tileIndex = (player.tileIndex+spaces)%36;
		
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
		controls.writeLine("\n"+currPlayer.name+"'s turn");
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
		if(tmp.owner != null){
			controls.hidePurchaseButton();
		}
		else{
			controls.showPurchaseButton();
		}
		if(tmp.owner != null && tmp.owner != currPlayer && tmp.rent>0){
			currPlayer.money -= tmp.rent;
			tmp.owner.money+=tmp.rent;
			board.updateMoney();
			controls.writeLine(tab+currPlayer.name + " paid $" + Integer.toString(tmp.rent) + " in rent to " + tmp.owner.name);
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
