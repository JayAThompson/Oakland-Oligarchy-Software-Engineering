
/**
 * Center piece of the game board for Oakland Oligarchy
 * In future this will need to be connected to the implementation of the bank
 * And the dice rolling, as well as the action card system
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Center extends JPanel{

	JLabel bank;
	JLabel dice;
	JLabel cardPile;
	JLabel currCard;
	String diceRollString = "<html><h1>Dice Roll</h1>";

	/**
	 * Method to display the dice roll on the board in the center panel
	 * @param player The Player object for the player who has rolled the die
	 * @param roll1 The value of the roll of the first die
	 * @param roll2 The value of the roll of the second die
	 */
	public void drawDiceRoll(Player player, int roll1,int roll2){
		dice.setText(diceRollString+"<h3>Player "+player.getName()+" rolled</h3><br><h1>"+Integer.toString(roll1)+" and "+Integer.toString(roll2));
	}

	/**
	 * Class constructor
	 * Set layout, border, and preferred size
	 * Initialize panels for card pile, current card, dice roll, and bank
	 */
	Center(Dimension dim) {
		this.setLayout(new GridLayout(0,4));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(dim);

		cardPile = new JLabel("<html>Cards</html>", SwingConstants.CENTER);
		cardPile.setFont(new Font("Calibri", Font.PLAIN, 20));
		cardPile.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(cardPile, 0, 0);

		currCard = new JLabel("<html>Current<br>Card</html>", SwingConstants.CENTER);
		currCard.setFont(new Font("Calibri", Font.PLAIN, 20));
		currCard.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(currCard, 0, 1);

		dice = new JLabel(diceRollString, SwingConstants.CENTER);
		//dice.setFont(new Font("Calibri", Font.PLAIN, 20));
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
		Center center = new Center(new Dimension(800, 480));
		frame.add(center);
		frame.setVisible(true);
	}
}
