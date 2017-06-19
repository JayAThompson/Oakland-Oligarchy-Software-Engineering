import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class oakOligarchy{
	JFrame window = new JFrame("Oakland Oligarchy");
	//static Information playerInfo;
	
	int numberOfPlayers = 0;
	int turns = 0;
	Player current = null;
	
	ArrayList<Player> players = new ArrayList<>();
	
	oakOligarchy(){
		System.out.println("got here");

		//default size or the JFrame
		window.setSize(1300, 720);
		
		//adding dummy data into the menu
		Menu menu = new Menu(new Player("no one",0,"yo"));
		window.add(menu, BorderLayout.NORTH);
		
		Information playerInfo = new Information();
		window.add(playerInfo, BorderLayout.WEST);
		
		GameBoard board = new GameBoard();
		window.add(board.getBoard(), BorderLayout.CENTER);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		//sleeping while the player info is collected
		while(playerInfo.playerDataFlag == false){
			try {
			   Thread.sleep(50);
			} catch(InterruptedException e) {
			}
		}
		this.players=playerInfo.players;
		playerInfo.drawPlayerInfo();
		//System.out.println("got here");
	}
	
	public int getIndexOfCurrentPlayer(){
		if(turns == 0) return 0;
		else{
			return turns % numberOfPlayers;
		}
	}
	
	public static void main(String[] args){
		new oakOligarchy();
		
	}

	
}