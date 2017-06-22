
/*
 * Center piece of the gameboard for the oakland oligarchy computer gameboard
 * In future this will need to be connected to the implementation of the bank
 * And the dice rolling, as well as the action card system
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoardCenter extends JPanel implements ActionListener{	
	
	private final String diceRollString = "<html><h1>Dice Roll</h1>";
	
	GameBoard.Event event = GameBoard.Event.NONE;
	JLabel dice;
	JPanel turnControls;
	JLabel	turnLabel,purchaseLabel;
	JButton rollButton,purchaseButton,endTurnButton;
	
	BoardCenter(Dimension dim) {
		this.setLayout(new GridLayout(0,2));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(dim);
		
	
		
		turnControls = new JPanel();
		turnControls.setLayout(new BoxLayout(turnControls,BoxLayout.Y_AXIS));
		turnControls.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(turnControls);
//		turnControls.add(purchaseButton);
		
		turnLabel = new JLabel("<html><h1>Turn: <em>no one<em></h1></html>");
		turnControls.add(turnLabel);
/*		
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
		turnControls.add(separator);
*/		
		rollButton = new JButton("Roll the dice");
		rollButton.addActionListener(this);
		rollButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		rollButton.setVisible(false);
		turnControls.add(rollButton);
		//turnControls.add(Box.createRigidArea(new Dimension(0,10)));
/*
		separator = new JSeparator(SwingConstants.HORIZONTAL);
		//separator.setAlignmentX(Component.CENTER_ALIGNMENT);
   		//separator.setPreferredSize(new Dimension(50, 10));
		separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
		turnControls.add(separator);

		
		purchaseLabel = new JLabel("Want to purchse this shiz?");
		purchaseLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		purchaseLabel.setVisible(false);
		turnControls.add(purchaseLabel);
		//turnControls.add(Box.createRigidArea(new Dimension(0,10)));
*/

			
		purchaseButton = new JButton("Purchase Property");
		purchaseButton.addActionListener(this);
		purchaseButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		purchaseButton.setVisible(false);
		turnControls.add(purchaseButton);
		turnControls.add(Box.createRigidArea(new Dimension(0,20)));
/*
		separator = new JSeparator(SwingConstants.HORIZONTAL);
		//separator.setAlignmentX(Component.CENTER_ALIGNMENT);
   		//separator.setPreferredSize(new Dimension(50, 10));
		separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
		turnControls.add(separator);
		
		purchaseLabel = new JLabel(" ");
		purchaseLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		purchaseLabel.setVisible(false);
		turnControls.add(purchaseLabel);
		turnControls.add(Box.createRigidArea(new Dimension(0,10)));
*/

		endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(this);
		endTurnButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		endTurnButton.setVisible(false);
		turnControls.add(endTurnButton);
		
		dice = new JLabel(diceRollString, SwingConstants.CENTER);
		//dice.setFont(new Font("Calibri", Font.PLAIN, 20));
		dice.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(dice);	
		
	}

	public void drawDiceRoll(Player player, int roll1,int roll2){
		dice.setText(diceRollString+"<h3>Player "+player.getName()
				 +" rolled</h3><br><h2>"+Integer.toString(roll1)
				 +" and "+Integer.toString(roll2)+"</h2></html>");
	}
	
	public void drawPlayersTurn(Player player){
		try{
		turnLabel.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
		}catch(Exception e){}
		turnLabel.setOpaque(true);
		turnLabel.setText("<html><h1>Turn: <em>"+player.getName()+"<em></h1></html>");
		//turnLabel.set
		//turnControls.
	}
	
	public void actionPerformed(ActionEvent e) { 
		if(e.getSource() == rollButton){
			event = GameBoard.Event.ROLL;
		}else if(e.getSource() == purchaseButton){
			event = GameBoard.Event.PURCHASE;
		}else if(e.getSource() == endTurnButton){
			event = GameBoard.Event.END_TURN;
		}
	}
	/*
	 *Main method for testing purposes
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(400, 400);
		//Center center = new Center(new Dimension(800, 480));
		//frame.add(center);
		frame.setVisible(true);
	}
}
