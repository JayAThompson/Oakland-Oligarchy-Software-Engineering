
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
	//the following field is the index of the player and the player's corresponding swing components in many arrays
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
	 * Trades property with other players
	 */
	public static void tradeProperty() {
		String tradeOptions[] = new String[players.size() - 1];
		int j = 0;
		for (int i = 0; i <  players.size(); i++) {
			if (!players.get(i).equals(currPlayer)) {
				 tradeOptions[j] = players.get(i).getName();
				 j++;
			}
		}
		String s = (String) JOptionPane.showInputDialog(
                window,
                "Who would you like to trade?",
                "Trade Player",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tradeOptions,
                tradeOptions[0]);
				
		Player tradeWith = null;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(s)) {
				tradeWith = players.get(i);
			}
		}
		
		JCheckBox tradeInitiator[] = new JCheckBox[currPlayer.properties.size()];
		JCheckBox tradeRecipient[] = null;
		try {
			tradeRecipient = new JCheckBox[tradeWith.properties.size()];
		} catch(NullPointerException npe) {
			return;
		}
		JPanel boxes = new JPanel();
		JPanel play1 = new JPanel(new GridLayout(currPlayer.properties.size() + 3,1));
		JPanel play2 = new JPanel(new GridLayout(tradeWith.properties.size() + 3,1));
		
		JLabel currPlayerName = new JLabel(currPlayer.getName() + ":");
		play1.add(currPlayerName);
		if (currPlayer.properties.size() > 0){
			for (int i = 0; i < currPlayer.properties.size(); i++) {
				tradeInitiator[i] = new JCheckBox(currPlayer.properties.get(i).getPropertyName());
				play1.add(tradeInitiator[i]);
			}
		}
		
		JLabel moneyLabel1 = new JLabel("Money:");
		JTextField play1Money = new JTextField();
		play1.add(moneyLabel1);
		play1.add(play1Money);
		
		JLabel tradeWithName = new JLabel(tradeWith.getName() + ":");
		play2.add(tradeWithName);
		if (tradeWith.properties.size() > 0){			
			for (int i = 0; i < tradeWith.properties.size(); i++ ) {
				tradeRecipient[i] = new JCheckBox(tradeWith.properties.get(i).getPropertyName());
				play2.add(tradeRecipient[i]);
			}
		}
		
		JLabel moneyLabel2 = new JLabel("Money:");
		JTextField play2Money = new JTextField();
		play2.add(moneyLabel2);
		play2.add(play2Money);
		
		boxes.add(play1, BorderLayout.EAST);
		boxes.add(play2, BorderLayout.WEST);
		
		int cont = JOptionPane.showConfirmDialog(null, boxes, "Trade", JOptionPane.OK_CANCEL_OPTION);
		if (cont == JOptionPane.CANCEL_OPTION) {
			return;
		}
		String confirmString = "The current trade is:\n";
		int playerOneTrades[] = new int[tradeInitiator.length];
		j = 0;
		for (int i = 0; i < tradeInitiator.length; i++) {
			if (tradeInitiator[i].isSelected()) {
				confirmString = confirmString + currPlayer.properties.get(i).getPropertyName() + "\n";
				playerOneTrades[j] = i;
				j++;
			}
		}
		int money1 = 0;
		try {
			money1 = Integer.parseInt(play1Money.getText());
			confirmString = confirmString + "$" + money1 + "\n" ;
		} catch (NumberFormatException nme) {}
		
		confirmString += "For:\n";
		int playerTwoTrades[] = new int [tradeRecipient.length];
		j = 0;
		for (int i = 0; i < tradeRecipient.length; i++) {
			if (tradeRecipient[i].isSelected()) {
				confirmString = confirmString + tradeWith.properties.get(i).getPropertyName() + "\n";
				playerTwoTrades[j] = i;
				j++;
			}
		}
		int money2 = 0;
		try {
			money2 = Integer.parseInt(play2Money.getText());
			confirmString =confirmString + "$" + money2 + "\n" ;
		} catch (NumberFormatException nme) {}
		confirmString = confirmString + tradeWith.getName() + " is this okay?";
		cont = JOptionPane.showConfirmDialog(null, confirmString, "Confirm Trade?", JOptionPane.YES_NO_OPTION);
		if (cont  == JOptionPane.NO_OPTION) {
			return;
		}
		else {
			for (int i = 0; i < tradeInitiator.length; i++) {
				if (playerOneTrades[i] == 0 && i > 0 ) {
					break;
				}
				tradeWith.properties.add(currPlayer.properties.get(playerOneTrades[i]));
				String replaceWith = ">" + currPlayer.properties.get(playerOneTrades[i]).getPropertyName() + "\n"; //one problem is here, probably to do with trading multiple properties and removal form the arraylist updating the index
				tradeWith.setPropertyString(tradeWith.getPropertyString() + replaceWith);
				currPlayer.setPropertyString(currPlayer.getPropertyString().replace(replaceWith, ""));
				currPlayer.removeProperty(currPlayer.properties.get(playerOneTrades[i]).getPropertyName(), tradeWith);
				currPlayer.setMoney(currPlayer.getMoney() + money2 - money1);
			}
			
			for (int i = 0; i < tradeRecipient.length; i++) {
				if (playerTwoTrades[i] == 0  && i > 0) {
					break;
				}
				
				currPlayer.properties.add(tradeWith.properties.get(playerTwoTrades[i]));
				String replaceWith = ">" + tradeWith.properties.get(playerTwoTrades[i]).getPropertyName() + "\n";
				currPlayer.setPropertyString(currPlayer.getPropertyString() + replaceWith);
				tradeWith.setPropertyString(tradeWith.getPropertyString().replace(replaceWith, ""));
				tradeWith.removeProperty(tradeWith.properties.get(playerTwoTrades[i]).getPropertyName(), currPlayer);
				tradeWith.setMoney(tradeWith.getMoney() + money1 - money2);
			}
			board.boardCenter.drawProperties(players.indexOf(tradeWith));
			board.boardCenter.drawProperties(players.indexOf(currPlayer));
			board.boardCenter.updateMoneyLabels();
			
		}
	
	}
	
	/**
	* This method houses most of the logic for the actual game. 
	* 
	*/
	private static void nextTurn(){
		final Controls.Event preRollEvents[] = {Controls.Event.ROLL, Controls.Event.TRADE};
		final Controls.Event postRollEvents[] = {Controls.Event.END_TURN, Controls.Event.PURCHASE, Controls.Event.TRADE};
		final Controls.Event postPurchaseEvents[] = {Controls.Event.END_TURN, Controls.Event.TRADE};
		int roll1 = rollDice();
		int roll2 = rollDice();
		Controls.Event event;		
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();
		
		currPlayerIndex = (currPlayerIndex+1)%players.size();
		currPlayer = players.get(currPlayerIndex);
		controls.drawPlayerTurnLabel(currPlayer);
		controls.writeLine(currPlayer.name+"'s turn");
		menu.drawPlayersTurn(currPlayer);		
		controls.showRollButton();
		controls.showTradeButton();
		
		do {
		event = waitForControlEvents(preRollEvents, 2);
			if (event == preRollEvents[0]){
				controls.writeLine(tab+currPlayer.name + " rolled a " + Integer.toString(roll1) + " and a "+ Integer.toString(roll2));
				controls.hideRollButton();
				movePlayer(currPlayer,roll1+roll2); 
			}
			else {
				tradeProperty();
			}
		} while (event != preRollEvents[0]);
		
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
		
		
		do {
			event = waitForControlEvents(postRollEvents,3);
			if(event == postRollEvents[1]){
				purchaseCurrentProperty();
				controls.hidePurchaseButton();
				
			}
			else if (event == postRollEvents[2]) {
				tradeProperty();
			}
		}while (event != postRollEvents[0] && event != postRollEvents[1]); //to make sure you can't double purchase a place
		if (event != postRollEvents[0]){ //to not overwrite an above end turn event
			do{
				event = waitForControlEvents(postPurchaseEvents, 2);
				if (event != postPurchaseEvents[0]) {
					tradeProperty();
				}
				
			} while (event  !=  postPurchaseEvents[0]);
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
