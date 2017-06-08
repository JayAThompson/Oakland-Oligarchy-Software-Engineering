<<<<<<< HEAD
package TSM;

=======
>>>>>>> workingDr
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class oakOligarchy{
	JFrame window = new JFrame("Oakland Oligarchy");
	
	int numberOfPlayers = 0;
	int turns = 0;
	Player current = null;
	
	ArrayList<Player> players = new ArrayList<>();
	
	//The "whole squad"
	Player atta = new Player("Atta", 10000);
	Player jay = new Player ("Jay", 12500);
	Player anastasia = new Player("Anastasia", 15000);
	Player stephen = new Player("Stephen", 17500);
		
	oakOligarchy(){
		players.add(atta);
		numberOfPlayers++;
		players.add(jay);
		numberOfPlayers++;
		players.add(anastasia);
		numberOfPlayers++;
		players.add(stephen);
		System.out.println(players);
		window.setSize(1000, 1000);
		
		//Find current player and add to menu
		current = players.get(getIndexOfCurrentPlayer());
		Menu menu = new Menu(current);
		window.add(menu, BorderLayout.NORTH);
		
		Information playerInfo = new Information(players);
		window.add(playerInfo, BorderLayout.WEST);
		
<<<<<<< HEAD
=======
		GameBoard board = new GameBoard();
		window.add(board.getBoard(), BorderLayout.CENTER);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
>>>>>>> workingDr
		window.setVisible(true);
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