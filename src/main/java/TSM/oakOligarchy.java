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
	static Player currPlayer;
	static int currPlayerIndex;
	
	//constructor initializes our JFrame
	oakOligarchy(){
		window = new JFrame("Oakland Oligarchy");
		//default size or the JFrame

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
	//
	public static int rollDice(){
		return (int)(Math.random()*6+1);
	}
	
	void takeTurn(Player player){
		menu.drawPlayer(player);
	}
	static void movePlayer(Player player,int spaces){
		board.erasePlayer(currPlayer);
		player.tile = (player.tile+spaces)%36;
		
		board.drawPlayer(currPlayer);
	}
	
	
	public static void waitForEvent(){
		if(menu.lastEvent != Menu.MenuEvent.NONE){
			if(menu.lastEvent == Menu.MenuEvent.ROLL){
				int roll1 = rollDice();
				int roll2 = rollDice();
				movePlayer(currPlayer,roll1+roll2);
				board.drawDiceRoll(currPlayer,roll1,roll2);
				currPlayerIndex = (currPlayerIndex+1)%players.size();
				currPlayer = players.get(currPlayerIndex);
				menu.drawPlayer(currPlayer);
			}
			menu.lastEvent = Menu.MenuEvent.NONE;
		}
		
	}
	
	//main houses the highest layer of our application logic
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
		playerInfo.drawPlayerInfo();
		for(Player player : players){	
			board.drawPlayer(player);
		}
		currPlayerIndex=0;
		currPlayer = players.get(0);
		menu.drawPlayer(players.get(0));
		while(true){
			waitForEvent();
			try {
			   Thread.sleep(10);
			} catch(InterruptedException e) {
			}
		}
		
	}

	
}