
/**
 * Center piece of the game board for Oakland Oligarchy
 * In future this will need to be connected to the implementation of the bank
 * And the dice rolling, as well as the action card system
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Center extends JPanel{

	private JLabel bank;
	private JLabel dice;
	private JLabel cardPile;
	private JLabel currCard;

	/**
	 * Class constructor
	 * Set the layout, border, and preferred size
	 * Initialize panels for the card pile, current card, dice roll, and bank
	 */
	public Center() {
		this.setLayout(new GridLayout(0,4));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(800, 480));

		cardPile = new JLabel("<html>Cards</html>", SwingConstants.CENTER);
		cardPile.setFont(new Font("Calibri", Font.PLAIN, 20));
		cardPile.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(cardPile, 0, 0);

		currCard = new JLabel("<html>Current<br>Card</html>", SwingConstants.CENTER);
		currCard.setFont(new Font("Calibri", Font.PLAIN, 20));
		currCard.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(currCard, 0, 1);

		dice = new JLabel("<html>Dice Roll</html>", SwingConstants.CENTER);
		dice.setFont(new Font("Calibri", Font.PLAIN, 20));
		dice.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(dice, 0, 0);

		bank = new JLabel("<html>The Bank</html>", SwingConstants.CENTER);
		bank.setFont(new Font("Calibri", Font.PLAIN, 20));
		bank.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(bank, 0, 0);
	}

	/**
	 * Main method for testing purposes
	 * @param args Unused
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(400, 400);
		Center center = new Center();
		frame.add(center);
		frame.setVisible(true);
	}
}
