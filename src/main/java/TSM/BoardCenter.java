
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
	
	/**
	 * class constructor.
	 * basically makes an empty Jpanel
	 */
	BoardCenter(Dimension dim) {
		//this.setLayout(new GridLayout(0,2));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(dim);
	}
	
	/**
	 * This method draws the player names and initial money/properties
	 *
	 */
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
			

			
		 	name = new JLabel("<html><u>"+player.getName()+"</u></html>");
			name.setFont(new Font("Calibri", Font.BOLD, 15));
			name.setOpaque(true);
			try{
				name.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
			}catch(Exception e){}
			panel.add(name);
			
			JLabel tmpLabel = new JLabel("MONEY: $"+ player.money);
			tmpLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add( tmpLabel );
			moneyLabels.add(tmpLabel);
			panel.add(new JLabel("<html>PROPERTIES:</html>"));
			JTextArea tmp = new JTextArea();
			tmp.setLineWrap(true);
			tmp.setWrapStyleWord(true);
			tmp.setMaximumSize(new Dimension(200,1000));
			try{
				tmp.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
			}catch(Exception e){}
			panel.add(tmp);
			propertyText.add(tmp);
			//this.add(Box.createRigidArea(new Dimension(0,10)));
		}
		this.validate();
		this.repaint();
	}
	
	/**
	 * This method draws each players money on the center of the gameboard
	 * 
	 */
	public void updateMoneyLabels(){
		int i=0;
		for(JLabel label : moneyLabels){
			label.setText("MONEY: $" + players.get(i++).money);
		}
		this.validate();
		this.repaint();
	}
	
	/**
	 * This method draws the list of player properties on the center of the gameboard
	 * 
	 */
	public void drawProperties(int playerIndex){
		propertyText.get(playerIndex).setText(players.get(playerIndex).propertyString);
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
