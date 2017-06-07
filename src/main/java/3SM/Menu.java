import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel{
	
	//To display turn.
	JLabel currentPlayer;
	
	//Buttons that will be needed.
	JButton trade = new JButton("TRADE");
	JButton roll = new JButton("ROLL");
	JButton newGame = new JButton("NEW GAME");
	JButton endGame = new JButton("END GAME");
	JButton help  = new JButton("HELP");

	Menu(Player current) {
		this.setPreferredSize(new Dimension(1000, 50));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(0, 7));

		//Oakland Oligarchy title.
		JLabel title = new JLabel("<html> OAKLAND<br> OLIGARCHY</html>");
		title.setFont(new Font("Calibri", Font.BOLD, 18));
		this.add(title, 0, 0);
		
		//Get current player for label.
		currentPlayer = new JLabel("<html><b>TURN:</b><em>" + current.getName() + "</em></html>", SwingConstants.CENTER);
		currentPlayer.setFont(new Font("Courier", Font.PLAIN, 15));
		this.add(currentPlayer, 0, 1);
		
		//Font for all the buttons, display them.
		trade.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(trade, 0, 2);
		roll.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(roll, 0, 3);
		newGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(newGame, 0, 4);
		endGame.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(endGame, 0, 5);
		help.setFont(new Font("Calibri", Font.PLAIN, 15));
		this.add(help, 0, 6);

	}
}