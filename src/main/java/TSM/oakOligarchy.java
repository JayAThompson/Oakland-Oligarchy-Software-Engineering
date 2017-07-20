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
		controls.writeLine("***********Action Tile***********");				controls.writeLine("******Action Tile******");
		int action = (int)(Math.random()*8+1);
		switch(action){
			case 1:
				controls.writeLine(tab+"You lost your Pitt ID!");
				controls.writeLine(tab+"Go to Panther Central and pay the bank $20 (on top of any rent)");
				currPlayer.money-=20;
				//tile 10 is pantherc
				movePlayerTo(currPlayer,10);
				//currPlayer.money
				break;
			case 2:
				controls.writeLine(tab+"You begged your parents for money");
				controls.writeLine(tab+"They were feeling generous and gave you $1750");
				currPlayer.money += 1750;
				break;
			case 3:
				controls.writeLine(tab+"You need some cheap grub");
				controls.writeLine(tab+"Go to the Dirty O");
				movePlayerTo(currPlayer,32);
				break;
			case 4:
				controls.writeLine(tab+"Your student loan money came in");
				controls.writeLine(tab+"You get $10000 from the bank");
				currPlayer.money += 10000;
				break;
			case 5:
				controls.writeLine(tab+"You got drunk at Papa D's and lost your wallet (again...)");
				controls.writeLine(tab+"You've lost $"+ currPlayer.money/10+" and moved to Papa D's");
				currPlayer.money -= currPlayer.money/10;
				movePlayerTo(currPlayer,34);
				break;
			case 6:
				controls.writeLine(tab+"You walked around Schenley Park and pondered the futility of life");
				controls.writeLine(tab+"Luckily existential crises have monetary value in Oakland Oligarchy");
				controls.writeLine(tab+"Your newfound nihilism has doubled your dough! and you've moved to Schenley Park");
				currPlayer.money *=2;
				movePlayerTo(currPlayer,26);
				break;
			case 7:
				controls.writeLine(tab+"The other players have ganged up and robbed you.");
				for(Player player : players){
					if(player != currPlayer && currPlayer.money>300 && player.money>0){
						controls.writeLine(tab + player.name+" took $300");
						player.money+=300;
						currPlayer.money -=300;
					}
				}
				break;
			case 8:
				controls.writeLine(tab + "You see that Dippy is wearing your hat");
				controls.writeLine(tab + "You sue the Carnegie Museum of Natural History for psychological damages");
				controls.writeLine(tab + "You've earned $5000, way to go you hard worker");
				currPlayer.money += 5000;
				break;
		}
		controls.writeLine("*********************************");
		board.updateMoney();
	}

	/**
	 * Reward a player every time they pass go
	 */
	public static void passGo(){
		controls.writeLine(tab + "You passed start and collected $1000");
		currPlayer.money += 1000;
		board.boardCenter.updateMoneyLabels();
	}

	/**
	 * Move a player to jail (Hillman Library)
	 */
	public static void goToJail() {
		controls.writeLine("**************** Go to Hillman *****************");
		controls.writeLine(tab + currPlayer.getName() + " has an exam and has been banished to Hillman.");
		controls.writeLine("************************************************");
		currPlayer.setInJail(true);
		movePlayerTo(currPlayer, 18);
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
			menuAction(menu.getEvent());
			while(!controls.pollForEvent()){
				menuAction(menu.getEvent());
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
			menuAction(menu.getEvent());
			while(!controls.pollForEvent()){
			menuAction(menu.getEvent());
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
	public static boolean tradeProperty(boolean rentTrade) {
		String tradeOptions[] = new String[players.size() - 1];
		Player tradeWith = null;
		boolean hasTraded = false;
		
		int j = 0;
		if (!rentTrade) {
		
			
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
					
			
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getName().equals(s)) {
					tradeWith = players.get(i);
				}
			}
		}
		
		else {
			tradeWith = board.tiles.get(currPlayer.tileIndex).getOwner();
		}
		
		JCheckBox tradeInitiator[] = new JCheckBox[currPlayer.properties.size()];
		JCheckBox tradeRecipient[] = null;
		try {
			tradeRecipient = new JCheckBox[tradeWith.properties.size()];
		} catch(NullPointerException npe) {
			return false;
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
			return false;
		}
		String confirmString = "The current trade is:\n";
		Tile playerOneTrades[] = new Tile[tradeInitiator.length];
		j = 0;
		for (int i = 0; i < tradeInitiator.length; i++) {
			if (tradeInitiator[i].isSelected()) {
				confirmString = confirmString + currPlayer.properties.get(i).getPropertyName() + "\n";
				playerOneTrades[j] = currPlayer.properties.get(i);
				j++;
			}
		}
		int money1 = 0;
		try {
			money1 = Integer.parseInt(play1Money.getText());
			confirmString = confirmString + "$" + money1 + "\n" ;
		} catch (NumberFormatException nme) {}

		confirmString += "For:\n";
		Tile playerTwoTrades[] = new Tile [tradeRecipient.length];
		j = 0;
		for (int i = 0; i < tradeRecipient.length; i++) {
			if (tradeRecipient[i].isSelected()) {
				confirmString = confirmString + tradeWith.properties.get(i).getPropertyName() + "\n";
				playerTwoTrades[j] = tradeWith.properties.get(i);
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
			return false;
		}
		else {
			for (int i = 0; i < tradeInitiator.length; i++) {
				if (playerOneTrades[i] == null) {
					break;
				}
				tradeWith.properties.add(playerOneTrades[i]);
				String replaceWith = ">" + playerOneTrades[i].getPropertyName() + "\n"; //one problem is here, probably to do with trading multiple properties and removal form the arraylist updating the index
				tradeWith.setPropertyString(tradeWith.getPropertyString() + replaceWith);
				currPlayer.setPropertyString(currPlayer.getPropertyString().replace(replaceWith, ""));
				currPlayer.removeProperty(playerOneTrades[i].getPropertyName(), tradeWith);
			}
			if (playerOneTrades[0] != null) {
				hasTraded = true;
			}
			currPlayer.setMoney(currPlayer.getMoney() + money2 - money1);

			for (int i = 0; i < tradeRecipient.length; i++) {
				if (playerTwoTrades[i] == null) {
					break;
				}

				currPlayer.properties.add(playerTwoTrades[i]);
				String replaceWith = ">" + playerTwoTrades[i].getPropertyName() + "\n";
				currPlayer.setPropertyString(currPlayer.getPropertyString() + replaceWith);
				tradeWith.setPropertyString(tradeWith.getPropertyString().replace(replaceWith, ""));
				tradeWith.removeProperty(playerTwoTrades[i].getPropertyName(), currPlayer);

			}
			tradeWith.setMoney(tradeWith.getMoney() + money1 - money2);
			board.boardCenter.drawProperties(players.indexOf(tradeWith));
			board.boardCenter.drawProperties(players.indexOf(currPlayer));
			board.boardCenter.updateMoneyLabels();
		}
		return hasTraded;
	}

	/**
	 * Auctions a property
	 * @param the tile being auctioned
	 */
	public static void auction(Tile tile) {
		JPasswordField bids[] = new JPasswordField[players.size()];
		int index = 0;
		int maxVal = 0;
		for (int i = 0; i < players.size(); i++) {
			bids[i] = new JPasswordField();
			JOptionPane.showConfirmDialog(null, bids[i], "Your Bid, " + players.get(i).getName(), JOptionPane.OK_CANCEL_OPTION);
			try {
				int temp = Integer.parseInt(bids[i].getText());
				if (temp > maxVal && temp <= players.get(i).getMoney()) {
					index = i;
					maxVal = temp;
				}
			} catch (NumberFormatException nme) {}

		}
		Player winner = players.get(index);

		if (maxVal > 0) {
			JOptionPane.showMessageDialog(null, "You win, " + winner.getName() + ", with a bid of " + maxVal, "Auction", JOptionPane.DEFAULT_OPTION);
			tile.setOwner(winner);
			winner.setMoney(winner.getMoney() - maxVal);
			winner.properties.add(tile);
			winner.setPropertyString(winner.getPropertyString() + ">" + tile.getPropertyName() + "\n");
			board.boardCenter.drawProperties(index);
			board.boardCenter.updateMoneyLabels();
		}
		
		
	}
	
	public static void sellToBank() {
		JCheckBox ownedProperties[] = new JCheckBox[currPlayer.properties.size()];
		JLabel bankPrice[] = new JLabel[currPlayer.properties.size()];
		
		JPanel boxes = new JPanel();
		JPanel play1 = new JPanel(new GridLayout(currPlayer.properties.size() + 1,1));
		JPanel bank = new JPanel(new GridLayout(currPlayer.properties.size() + 1,1));
		

		JLabel currPlayerName = new JLabel(currPlayer.getName() + ":");
		play1.add(currPlayerName);
		
		JLabel bankName = new JLabel("Bank buyback price:");
		bank.add(bankName);
		
		for (int i = 0; i < currPlayer.properties.size(); i++) {
				ownedProperties[i] = new JCheckBox(currPlayer.properties.get(i).getPropertyName());
				play1.add(ownedProperties[i]);
				
				bankPrice[i] = new JLabel(Integer.toString(currPlayer.properties.get(i).getPropertyValue() / 2));
				bank.add(bankPrice[i]);
			}
			
		boxes.add(bank, BorderLayout.EAST);
		boxes.add(play1, BorderLayout.WEST);
		
		int cont = JOptionPane.showConfirmDialog(null, boxes, "Sell to Bank", JOptionPane.OK_CANCEL_OPTION);
		if (cont == JOptionPane.CANCEL_OPTION) {
			return;
		}
		
		String confirmString = "The current sale is:\n";
		Tile playerOneTrades[] = new Tile[ownedProperties.length];
		
		int payout = 0;
		int j = 0;
		
		for (int i = 0; i < ownedProperties.length; i++) {
			if (ownedProperties[i].isSelected()) {
				confirmString = confirmString + currPlayer.properties.get(i).getPropertyName() + "\n";
				payout += currPlayer.properties.get(i).getPropertyValue() / 2;
				playerOneTrades[j] = currPlayer.properties.get(i);
				j++;
			}
		}
		
		confirmString += "For:\n" + payout + "\nIs this okay?";
		
		cont = JOptionPane.showConfirmDialog(null, confirmString, "Confirm Trade?", JOptionPane.YES_NO_OPTION);
		if (cont  == JOptionPane.NO_OPTION) {
			return;
		}
		else {
			for (int i = 0; i < ownedProperties.length; i++) {
				if (playerOneTrades[i] == null) {
					break;
				}
				
				String replaceWith = ">" + playerOneTrades[i].getPropertyName() + "\n"; //one problem is here, probably to do with trading multiple properties and removal form the arraylist updating the index
				currPlayer.setPropertyString(currPlayer.getPropertyString().replace(replaceWith, ""));
				currPlayer.removeProperty(playerOneTrades[i].getPropertyName(), null);
			}
			
			currPlayer.setMoney(currPlayer.getMoney() + payout);
			board.boardCenter.drawProperties(players.indexOf(currPlayer));
			board.boardCenter.updateMoneyLabels();
		}
		
		if (currPlayer.properties.size() == 0) {
			controls.hideSellBankButton();
		}
		
	}
	
	
	public static boolean playerLoss() {
		if (currPlayer.getMoney() <= 0 && currPlayer.properties.size() == 0) {
			
			currPlayer.setPropertyString("Lost");
			board.boardCenter.drawProperties(currPlayerIndex);
			board.boardCenter.removeMoneyLabel(currPlayerIndex);
			
			players.remove(currPlayer);
			
			return true;
		}
		return false;
	}
	
	

	/**
	* This method houses most of the logic for the actual game.
	*
	*/
	private static void nextTurn(){
		final Controls.Event preRollEvents[] = {Controls.Event.ROLL, Controls.Event.TRADE, Controls.Event.SELL};
		final Controls.Event postRollEvents[] = {Controls.Event.END_TURN, Controls.Event.PURCHASE, Controls.Event.TRADE, Controls.Event.SELL};
		final Controls.Event postPurchaseEvents[] = {Controls.Event.END_TURN, Controls.Event.TRADE, Controls.Event.SELL};
		int roll1 = rollDice();
		int roll2 = rollDice();
		Controls.Event event;
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();
		controls.hideSellBankButton();

		currPlayerIndex = (currPlayerIndex+1)%players.size();
		currPlayer = players.get(currPlayerIndex);
		controls.drawPlayerTurnLabel(currPlayer);
		controls.writeLine(currPlayer.name+"'s turn");	
		
		if (currPlayer.isInJail()) {
			currPlayer.incrementJailedTurns();
			Object[] options = {"Roll for doubles", "Pay the fine", "Keep studying"};
			int selectedOption = JOptionPane.showOptionDialog(null,
										"To leave Hillman, you must roll doubles, pay the $500 fine, or keep studying for " + currPlayer.getRemainingJailedTurns() + " more turns. What would you like to do?",
										"You are stuck in Hillman Library!",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.QUESTION_MESSAGE,
										null,
										options,
										null);
			if (selectedOption == 0) {
				controls.writeLine(tab + currPlayer.name + " rolled a " + Integer.toString(roll1) + " and a " + Integer.toString(roll2));
				if (roll1 == roll2) {
					controls.writeLine(tab + "You escaped from Hillman!");
					currPlayer.setInJail(false);
				} else {
					if (currPlayer.getRemainingJailedTurns() == 0) {
						controls.writeLine(tab + "You have studied long enough. You are no longer banished to Hillman!");
					} else {
						controls.writeLine(tab + "You are still stuck in Hillman.");
					}
				}
			} else if (selectedOption == 1) {
				if (currPlayer.getMoney() >= 500) {
					currPlayer.money -= 500;
					board.boardCenter.updateMoneyLabels();
					controls.writeLine(tab + "You paid the $500 fine and escaped from Hillman!");
					currPlayer.setInJail(false);
				} else {
					if (currPlayer.getRemainingJailedTurns() == 0) {
						controls.writeLine(tab + "You have studied long enough. You are no longer banished to Hillman!");
					} else {
						controls.writeLine(tab + "You cannot afford to pay the fine and will remain in Hillman.");
					}
				}
			} else if (selectedOption == 2) {
				if (currPlayer.getRemainingJailedTurns() == 0) {
					controls.writeLine(tab + "You have studied long enough. You are no longer banished to Hillman!");
				} else {
					controls.writeLine(tab + "You will remain in Hillman to study for your exam.");
				}
			}
		} else {
		
		controls.showRollButton();
		controls.showTradeButton();
		if (currPlayer.properties.size() > 0) {
			controls.showSellBankButton();
		}
		
		do {
		menuAction(menu.getEvent());
		event = waitForControlEvents(preRollEvents, 3);
			if (event == preRollEvents[0]){
				controls.writeLine(tab+currPlayer.name + " rolled a " + Integer.toString(roll1) + " and a "+ Integer.toString(roll2));
				controls.hideRollButton();
				movePlayer(currPlayer,roll1+roll2); 
			}
			else if (event == preRollEvents[2]) {
				sellToBank();
			}
			else {
				tradeProperty(false);
			}
		} while (event != preRollEvents[0]);
		
		
		controls.showEndTurnButton();
		//getting charged that money
		Tile tmp = board.tiles.get(currPlayer.tileIndex);
		if(tmp.propertyName.equals("actiontile")){
			controls.hidePurchaseButton();
			actionTileFun();
			//reget tmp because actionTileFun can move players
			tmp = board.tiles.get(currPlayer.tileIndex);
		}else if (tmp.propertyName.equals("Go to Hillman")) {
				goToJail();
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
			}
			else if (currPlayer.properties.size() > 0) {
				
				boolean traded = false;
				do {
					traded = tradeProperty(true);
				}while (!traded);
			}
			else {
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

		do {
				event = waitForControlEvents(postRollEvents,4);
				if(event == postRollEvents[1]){
					purchaseCurrentProperty();
					controls.hidePurchaseButton();

				}
				else if (event == postRollEvents[2]) {
					tradeProperty(false);
				}
				else if (event == postRollEvents[3]) {
					sellToBank();
				}
			}while (event != postRollEvents[0] && event != postRollEvents[1]); //to make sure you can't double purchase a place
			
		
		Tile tile = board.tiles.get(currPlayer.tileIndex);
		if(tile.owner == null && tile.propertyValue>0){
			auction(tile);
		}
		
		if (event != postRollEvents[0]){ //to not overwrite an above end turn event
			do{
				event = waitForControlEvents(postPurchaseEvents, 3);
				if (event == postPurchaseEvents[1]) {
					tradeProperty(false);
				}
				else if (event == postPurchaseEvents[2]) {
					sellToBank();
				}
				
			} while (event  !=  postPurchaseEvents[0]);
		}
		
		if (playerLoss()) {
			controls.writeLine(tab+currPlayer.getName() + " has no properties nor money, and has lost the game.");
		}
		
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();

		}
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
	*This is the method that performs all of the menu bar actions
	*/
	public static void menuAction(Menu.Event event){
		switch(event){
			case NEW_GAME:
				controls.writeLine("new game");
				break;
			case END_GAME:
				controls.writeLine("end game");
				break;
			case LOAD_GAME:
				controls.writeLine("load game");
				break;
			case SAVE_GAME:
				controls.writeLine("save game");
				break;
			case HELP:
				JOptionPane help = new JOptionPane(
					"<html><div style='margin:0px 10px'>Here are the rules.<br>" +
					"<ul><li>To begin the game, enter the names of the players in the sidebar on the left. The first player to take their turn will be chosen randomly.</li>" +
					"<li>On a player's turn, they must roll two dice. Their token will be moved forward the number of spaces as rolled on the dice.</li>" +
					"<li>If the player lands on an unowned property, they are given the option to buy the property for the price listed on the tile. If they chose not to purchase the property or cannot afford to purchase the property, the property will be auctioned off among all the other players and sold to the highest bidder.</li>" +
					"<li>If the current player is the owner of the property, nothing will happen, but if another player owns the property, the current player will pay the rent on the property to the owner. If the current player cannot afford the rent, they will be able to trade properties to pay off what they owe.</li>" +
					"<li>If the player lands on an action tile, some random action will be taken. This random action could involve receiving money, having money taken away, or their token being moved to another tile.</li>" +
					"<li>If the player lands on a Go to Hillman tile, their token will be moved to the Hillman Library tile. To leave Hillman Library, the player must roll doubles or pay the $500 fine. If they fail to roll doubles or pay the fine, they may leave after three turns.</li>" +
					"<li>A player will always have the option to engage in trade with other players during their turn.</li>" +
					"<li>A player can always sell properties back to the bank for half price during their turn.</li>" +
					"<li>If a player passes the Start tile, they will gain an additional $1000.</li>" +
					"<li>A player will win the game when all of the other players have run out of assets (no money or properties).</li></ul></div></html>",
					JOptionPane.PLAIN_MESSAGE);
				help.setPreferredSize(new Dimension(700, 400));
				JDialog helpDlg = help.createDialog(null, "Help");
				helpDlg.show();
				break;
			case NONE:
				break;
			default:
				break;
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
			menuAction(menu.getEvent());
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
		//this is the start of the actual game
		while(!winnerExists()){
			menuAction(menu.getEvent());
			nextTurn();
		}
	}
}
