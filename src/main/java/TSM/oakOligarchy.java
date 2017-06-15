import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class oakOligarchy{
	private JFrame window = new JFrame("Oakland Oligarchy");

	private int numberOfPlayers = 0;
	private int turns = 0;
	private Player current = null;

	private ArrayList<Player> players = new ArrayList<>();

	//The "whole squad"
	private Player atta = new Player("Atta", 10000);
	private Player jay = new Player ("Jay", 12500);
	private Player anastasia = new Player("Anastasia", 15000);
	private Player stephen = new Player("Stephen", 17500);

	public oakOligarchy(){
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

		GameBoard board = new GameBoard();
		window.add(board.getBoard(), BorderLayout.CENTER);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
