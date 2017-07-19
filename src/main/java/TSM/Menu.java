
/**
 * Menu displayed at the top of the window
 * Displays the title of the game (Oakland Oligarchy) and the time elapsed while playing the game
 * Contains the buttons for trade, roll, new game, end game, and help
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel implements ActionListener{
	public enum Event{NONE,NEW_GAME,END_GAME,SAVE_GAME,LOAD_GAME,HELP };

	public Event event;
	//To display clock
	private JLabel clock;
	private Clock elapsedTime;

	//Buttons that will be needed.
/*	JButton trade = new JButton("TRADE");
	JButton roll = new JButton("ROLL");
*/
	JButton newGame = new JButton("NEW GAME");
	JButton endGame = new JButton("END GAME");
	JButton saveGame = new JButton("SAVE GAME");
	JButton loadGame = new JButton("LOAD GAME");
	JButton help  = new JButton("HELP");


	/**
	 * Class constructor
	 */
	Menu() {
		event = Menu.Event.NONE;
		this.setPreferredSize(new Dimension(1000, 50));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(0, 7));

		//Oakland Oligarchy title.
		JLabel title = new JLabel("<html>OAKLAND<br>OLIGARCHY</html>", SwingConstants.CENTER);
		title.setFont(new Font("Calibri", Font.BOLD, 18));
		this.add(title, 0, 0);

		// Initialize clock
		clock = new JLabel("", SwingConstants.CENTER);
		clock.setFont(new Font("Courier", Font.PLAIN, 16));
		this.add(clock, 0, 1);
		elapsedTime = new Clock(clock);
		elapsedTime.startClock();

		//Font for all the buttons, display them.
/*
		trade.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(trade, 0, 2);
		roll.setFont(new Font("Calibri", Font.PLAIN, 15));
		roll.addActionListener(this);
		this.add(roll, 0, 3);
*/
		newGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		newGame.addActionListener(this);
		this.add(newGame, 0, 2);
		endGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		endGame.addActionListener(this);
		this.add(endGame, 0, 3);
		saveGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		saveGame.addActionListener(this);
		this.add(saveGame, 0, 4);
		loadGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		loadGame.addActionListener(this);
		this.add(loadGame, 0, 5);
		help.setFont(new Font("Calibri", Font.PLAIN, 15));
		help.addActionListener(this);
		this.add(help, 0, 6);
	}

		/**
	 * This method returns the value of event
	 * it also clears the event field
	 */
	public Event getEvent(){
		Event tmp = event;
		event = Event.NONE;
		return tmp;
	}

	/**
	 * This method is used to tell whether a button has been pressed
	 * it is used to busy wait in the oakoli file
	 */
	public boolean pollForEvent(){
		if(this.event == Event.NONE){
			return false;
		}
		return true;
	}


	/**
	* This method is the action listener for the buttons
	* Controls.event is set to the value of the button pressed
	*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == endGame){
			event = Menu.Event.END_GAME;
			endGame.setVisible(false);
		}else if(e.getSource() == newGame){
			event = Menu.Event.NEW_GAME;
		}else if(e.getSource() == loadGame){
			event = Menu.Event.LOAD_GAME;
		}else if (e.getSource() == saveGame) {
			event = Menu.Event.SAVE_GAME;
		}else if (e.getSource() == help) {
			event = Menu.Event.HELP;
		}

	}

	/**
	 * Set the value of gameOver in the Clock class to true to signal the end of the game
	 */
	public void stopClock() {
		elapsedTime.setGameOver(true);
	}
}
