
/*
 * Center piece of the gameboard for the oakland oligarchy computer gameboard
 * In future this will need to be connected to the implementation of the bank
 * And the dice rolling, as well as the action card system
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class BoardCenter extends JPanel{	
	
/*	
 	JLabel dice;
	JPanel turnControls;
	JLabel	turnLabel,purchaseLabel;
	JButton rollButton,purchaseButton,endTurnButton;
*/
	public ArrayList<Player> players;
	public ArrayList<JPanel> playerPanels = new ArrayList<JPanel>();
	private ArrayList<JTextArea> propertyText = new ArrayList<JTextArea>();
	private ArrayList<JLabel> moneyLabels = new ArrayList<JLabel>();
	
	BoardCenter(Dimension dim) {
		//this.setLayout(new GridLayout(0,2));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(dim);
	}
	
	
	public void initPlayerInfo(ArrayList<Player> playerArray){
		JSeparator separator;
		JLabel name;
		players = playerArray;
		this.removeAll();
		this.setLayout(new GridLayout(1,players.size()));
		//this.add(new JLabel("<html><h1>Player Information</h1></html>",SwingConstants.LEFT));
		for(Player player : players){
			//adding a separator to tell the players apart
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			this.add(panel);
			//separator = new JSeparator(SwingConstants.HORIZONTAL);
			//separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
			
			//this.add(separator);
			//outerpanel houses two other panels. one panel has the text info about the player and the other has the player color
			//JPanel outerPanel = new JPanel(new GridLayout(1,2));
			//innerPanel houses the player info
			//JPanel innerPanel = new JPanel();
			//this.add(innerPanel);
			//innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
			//info goes in first spot in grid
			//outerPanel.add(innerPanel);
			//this.add(outerPanel);
			//tmpPanel houses the player color info
			//JPanel tmpPanel = new JPanel();
			//tmpPanel.setOpaque(true);
			try{
//				tmpPanel.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
				//tmpPanel.setBackground(Color.GREEN);
			}catch(Exception e){}
			
		 	name = new JLabel("<html><u>"+player.getName()+"</u></html>");
			name.setFont(new Font("Calibri", Font.BOLD, 15));
				
			panel.add(name);
			JLabel tmpLabel = new JLabel("MONEY: "+ player.money);
			tmpLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add( tmpLabel );
			moneyLabels.add(tmpLabel);
			panel.add(new JLabel("<html>PROPERTIES:</html>"));
			JTextArea tmp = new JTextArea();
			tmp.setLineWrap(true);
			tmp.setMaximumSize(new Dimension(200,1000));
			try{
				tmp.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
			}catch(Exception e){}
			//panel.add(tmp);
			propertyText.add(tmp);
			//this.add(Box.createRigidArea(new Dimension(0,10)));
		}
		this.validate();
		this.repaint();
	}
	
	
	
		
/*	
		
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


		endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(this);
		endTurnButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		endTurnButton.setVisible(false);
		turnControls.add(endTurnButton);
		
		dice = new JLabel(diceRollString, SwingConstants.CENTER);
		//dice.setFont(new Font("Calibri", Font.PLAIN, 20));
		dice.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(dice);	
*/	
//	}
/*
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
*/	
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
