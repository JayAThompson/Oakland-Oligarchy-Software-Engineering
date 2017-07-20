/**
 * Main program for Oakland Oligarchy game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.io.*;


public class oakOligarchy{
	static JFrame window;
	static Controls controls;
	static GameBoard board;
	static Menu menu;
	static ArrayList<Player> players;
	static Runnable runSave = new Runnable(){ 
		public void run(){
			saveGame();
		}
	};
	//these two variables are the player who's turn it is
	static Player currPlayer;
	//the following field is the index of the player and the player's corresponding swing components in many arrays
	static int currPlayerIndex;
	//with this field you can change the bechavior of the log text area
	static final String tab = " -> ";

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
		//menu.drawPlayer(players.get(0));
		//this is the start of the actual game
		menu.startClock();
		playGame();
	}
	
	oakOligarchy(Object[] gameStuffs){
		board = (GameBoard)gameStuffs[0];
		menu = (Menu)gameStuffs[1];
		controls = (Controls)gameStuffs[2];
		currPlayer = (Player)gameStuffs[3];
		currPlayerIndex = (int)gameStuffs[4];
		players = controls.players;
		window = new JFrame("Oakland Oligarchy");
		//default size for the JFrame
		window.setSize(1500, 1080);
		window.add(menu, BorderLayout.NORTH);
		window.add(controls, BorderLayout.WEST);
		window.add(board.getBoard(), BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		menu.startClock();
		//board.addPlayersToTiles(players);
		controls.writeLine("****** game loaded ******");
		
		nextTurn(false);
		playGame();
	}
	
	
	private static void playGame(){
		while(!winnerExists()){
			menuAction(menu.getEvent());
			nextTurn(true);
		}
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
	/**
	 */
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
			return;
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
		JOptionPane.showMessageDialog(null, "You win, " + winner.getName() + ", with a bid of " + maxVal, "Auction", JOptionPane.DEFAULT_OPTION);
		winner.setMoney(winner.getMoney() - maxVal);
		winner.properties.add(tile);
		winner.setPropertyString(winner.getPropertyString() + ">" + tile.getPropertyName() + "\n");
		board.boardCenter.drawProperties(index);
		board.boardCenter.updateMoneyLabels();
		
		
	}
	
	/**
	* This method houses most of the logic for the actual game. 
	* 
	*/
	private static void nextTurn(boolean nextPlayer){
		final Controls.Event preRollEvents[] = {Controls.Event.ROLL, Controls.Event.TRADE};
		final Controls.Event postRollEvents[] = {Controls.Event.END_TURN, Controls.Event.PURCHASE, Controls.Event.TRADE};
		final Controls.Event postPurchaseEvents[] = {Controls.Event.END_TURN, Controls.Event.TRADE};
		int roll1 = rollDice();
		int roll2 = rollDice();
		Controls.Event event;		
		controls.hidePurchaseButton();
		controls.hideEndTurnButton();
		if(nextPlayer){
			currPlayerIndex = (currPlayerIndex+1)%players.size();
		}
		currPlayer = players.get(currPlayerIndex);
		controls.drawPlayerTurnLabel(currPlayer);
		controls.writeLine(currPlayer.name+"'s turn");	
		controls.showRollButton();
		controls.showTradeButton();
		
		do {
		menuAction(menu.getEvent());
		event = waitForControlEvents(preRollEvents, 2);
			menu.hideSaveButton();
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
			event = waitForControlEvents(postRollEvents,3);
			if(event == postRollEvents[1]){
				purchaseCurrentProperty();
				controls.hidePurchaseButton();
				
			}
			else if (event == postRollEvents[2]) {
				tradeProperty();
			}
		}while (event != postRollEvents[0] && event != postRollEvents[1]); //to make sure you can't double purchase a place
		
		Tile tile = board.tiles.get(currPlayer.tileIndex);
		if(tile.owner == null && tile.propertyValue>0){
			auction(tile);
		}
		
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
		menu.showSaveButton();
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
	*This method creates a new game. it does this by disposing all the objects and recalling the main constructor
	*/
	private static void newGame(){
		//JOptionPane tmp = new JOptionPane("sup?");
		window.dispose();
		menu=null;
		controls=null;
		board=null;
		//window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		new oakOligarchy();
		//tmp.;
	}
	
	/**
	*this method saves the game by putting the important objects in an object array then serializing them all
	*/	
	private static void saveGame(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		Object[] gameStuffs = new Object[5];
		gameStuffs[0]=board;
		gameStuffs[1]=menu;
		gameStuffs[2]=controls;
		gameStuffs[3]=currPlayer;
		gameStuffs[4]=currPlayerIndex;
		
		try {
			fout = new FileOutputStream("objs.game");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(gameStuffs);
			System.out.println("Done");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "file not saved", "File error", JOptionPane.INFORMATION_MESSAGE);
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "file not saved", "File error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "file not saved", "File error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		controls.writeLine("****** game saved ******");
		//window.revalidate();
		window.repaint();
	}
	
	/**
	*This method loads a saved game file. it reads in an object array and then calls a special constructor to remake all the game
	*/
	private static void loadGame(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		Object[] gameStuffs = new Object[5];


		try {

			fin = new FileInputStream("objs.game");
			ois = new ObjectInputStream(fin);
			gameStuffs = (Object[]) ois.readObject();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Corrupted game file", "File error", JOptionPane.INFORMATION_MESSAGE);
			return;
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Corrupted game file", "File error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Corrupted game file", "File error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}

		}
		
		window.dispose();
		menu=null;
		controls=null;
		board=null;
		currPlayer=null;
		//window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		new oakOligarchy(gameStuffs);
		//tmp.;
		
	}
	
	
	/**
	*This is the method that performs all of the menu bar actions
	*/
	public static void menuAction(Menu.Event event){
		switch(event){
			case NEW_GAME:
				newGame();
				break;
			case END_GAME:
				System.exit(0);
				break;
			case LOAD_GAME:
				loadGame();
				break;
			case SAVE_GAME:
				//saveGame();
				SwingUtilities.invokeLater(runSave);
				break;
			case HELP:
				controls.writeLine("HELP ME PLEASE PLZ PLZ PLZ");
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
	}
}
