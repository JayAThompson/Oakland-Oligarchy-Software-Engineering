
/**
 * Menu displayed at the top of the window
 * Displays the title of the game (Oakland Oligarchy) and the player who is currently taking their turn
 * Contains the buttons for trade, roll, new game, end game, and help
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel /*implements ActionListener*/{
	public enum MenuEvent{
		ROLL,TRADE,NEW_GAME,END_GAME,HELP,NONE
	}

	public MenuEvent lastEvent;
	//To display turn.
	private JLabel currentPlayer;

	//Buttons that will be needed.
/*	JButton trade = new JButton("TRADE");
	JButton roll = new JButton("ROLL");
*/
	JButton newGame = new JButton("NEW GAME");
	JButton endGame = new JButton("END GAME");
	JButton help  = new JButton("HELP");


	/**
	 * This method displays the name of the player who is currently taking their turn.
	 * @param player The Player object for the player who is currently taking their turn
	 */
	public void drawPlayersTurn(Player player){
		currentPlayer.setText("<html><b>TURN:</b><em>" + player.getName() + "</em></html>");
		this.validate();
		this.repaint();
	}


	/**
	 * Class constructor
	 * @param current The Player object for the player who will take their turn first
	 */
	Menu(Player current) {
		lastEvent = MenuEvent.NONE;
		this.setPreferredSize(new Dimension(1000, 50));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(0, 5));

		//Oakland Oligarchy title.
		JLabel title = new JLabel("<html>OAKLAND<br>OLIGARCHY</html>", SwingConstants.CENTER);
		title.setFont(new Font("Calibri", Font.BOLD, 18));
		this.add(title, 0, 0);

		//Get current player for label.
		currentPlayer = new JLabel("<html><b>TURN:</b><em>" + current.getName() + "</em></html>", SwingConstants.CENTER);
		currentPlayer.setFont(new Font("Courier", Font.PLAIN, 15));
		this.add(currentPlayer, 0, 1);

		//Font for all the buttons, display them.
/*
		trade.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(trade, 0, 2);
		roll.setFont(new Font("Calibri", Font.PLAIN, 15));
		roll.addActionListener(this);
		this.add(roll, 0, 3);
*/		
		newGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(newGame, 0, 2);
		endGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(endGame, 0, 3);
		help.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(help, 0, 4);
	}
/*
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == roll){
			this.lastEvent = MenuEvent.ROLL;

		}
	}
*/
}