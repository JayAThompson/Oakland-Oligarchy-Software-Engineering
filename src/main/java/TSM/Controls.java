
/**
 * Collect player information and display it in a panel on the left side of the window
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class Controls extends JPanel implements ActionListener{
	public enum Event{ ROLL,PURCHASE,END_TURN,NONE };	
	
	public ArrayList<Player> players = new ArrayList<Player>();
//	private ArrayList<JTextArea> propertyText = new ArrayList<JTextArea>();
//	private ArrayList<JLabel> moneyLabels = new ArrayList<JLabel>();
	JButton rollButton,purchaseButton,endTurnButton;

	
	private int START_MONEY=750*20;
	private int MAX_NAME_LEN = 20;
	//this flag is to be set by the the form to submit new player names
	public boolean playerDataFlag = false;
	Event event = Event.NONE;
	/**
	 * Class constructor
	 * Set border, preferred size, and layout
	 * Collect player info
	 */
	Controls(){
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setPreferredSize(new Dimension(285, 1000));
		//this.setBounds(0,0,285,1000);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.collectPlayerInfo();
	}
	
	public void drawTurnButtons(){
		JSeparator separator;
		this.removeAll();
		rollButton = new JButton("Roll the dice");
		rollButton.addActionListener(this);
		rollButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		rollButton.setVisible(true);
		this.add(rollButton);
		//turnControls.add(Box.createRigidArea(new Dimension(0,10)));

		separator = new JSeparator(SwingConstants.HORIZONTAL);
		//separator.setAlignmentX(Component.CENTER_ALIGNMENT);
   		//separator.setPreferredSize(new Dimension(50, 10));
		separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
		this.add(separator);

		
/*		purchaseLabel = new JLabel("Want to purchse this shiz?");
		purchaseLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		purchaseLabel.setVisible(false);
		this.add(purchaseLabel);
*/		//turnControls.add(Box.createRigidArea(new Dimension(0,10)));


			
		purchaseButton = new JButton("Purchase Property");
		purchaseButton.addActionListener(this);
		purchaseButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		purchaseButton.setVisible(false);
		this.add(purchaseButton);
		this.add(Box.createRigidArea(new Dimension(0,20)));

		separator = new JSeparator(SwingConstants.HORIZONTAL);
		//separator.setAlignmentX(Component.CENTER_ALIGNMENT);
   		//separator.setPreferredSize(new Dimension(50, 10));
		separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
		this.add(separator);
		
/*		purchaseLabel = new JLabel(" ");
		purchaseLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		purchaseLabel.setVisible(false);
		this.add(purchaseLabel);
		this.add(Box.createRigidArea(new Dimension(0,10)));
*/

		endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(this);
		endTurnButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		endTurnButton.setVisible(false);
		this.add(endTurnButton);
		this.validate();
		this.repaint();
	}
	
	public boolean pollForEvent(){
		if(this.event == Event.NONE){
			return false;
		}
		return true;
	}
	
/*	
	public void drawPlayerInfo(Player player){
		propertyText.get(players.indexOf(player)).setText(player.propertyString);
		moneyLabels.get(players.indexOf(player)).setText("MONEY: $" +player.getMoney());
		//this.revalidate();
		//this.repaint();
	}
*/
	
	/**
	 * This method is to be called if the new game button is selected, or by default when oakOli runs
	 * It collects player names from the user.
	 */
	public void collectPlayerInfo(){
		int numberOfPlayers=0;

		playerDataFlag=false;
		//adding the prompt for the user
		JLabel prompt = new JLabel("<html><body style='text-align=center; width: 200 px'>Please enter the names of the players to start the game <br>(minium of two players)");
		//prompt.setBackground(new Color(0,0,0,0));
		this.add(Box.createRigidArea(new Dimension(0,10)));
		JSeparator seperator = new JSeparator(SwingConstants.HORIZONTAL);
		seperator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
		this.add(new JLabel("<html><h1>New Game!"));
		this.add(seperator);
		this.add(prompt);
		seperator = new JSeparator(SwingConstants.HORIZONTAL);
		seperator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
		this.add(seperator);

		//adding in the textfields to get player names
		JTextField p1Field = new JTextField();
		p1Field.setPreferredSize(new Dimension(285,30));
		p1Field.setMaximumSize( p1Field.getPreferredSize() );
		p1Field.setDocument(new JTextFieldLimit(MAX_NAME_LEN));

		JTextField p2Field = new JTextField();
		p2Field.setPreferredSize(new Dimension(285,30));
		p2Field.setMaximumSize( p1Field.getPreferredSize() );
		p2Field.setDocument(new JTextFieldLimit(MAX_NAME_LEN));

		JTextField p3Field = new JTextField();
		p3Field.setPreferredSize(new Dimension(285,30));
		p3Field.setMaximumSize( p1Field.getPreferredSize() );
		p3Field.setDocument(new JTextFieldLimit(MAX_NAME_LEN));

		JTextField p4Field = new JTextField();
		p4Field.setPreferredSize(new Dimension(285,30));
		p4Field.setMaximumSize( p1Field.getPreferredSize() );
		p4Field.setDocument(new JTextFieldLimit(MAX_NAME_LEN));

		this.add(new JLabel("Player 1:"));
		this.add(p1Field);
		this.add(Box.createRigidArea(new Dimension(0,10)));

		this.add(new JLabel("Player 2:"));
		this.add(p2Field);
		this.add(Box.createRigidArea(new Dimension(0,10)));

		this.add(new JLabel("Player 3:"));
		this.add(p3Field);
		this.add(Box.createRigidArea(new Dimension(0,10)));

		this.add(new JLabel("Player 4:"));
		this.add(p4Field);
		this.add(Box.createRigidArea(new Dimension(0,10)));

		//adding a button that alows to collect the user names
		JSeparator seperator2 = new JSeparator(SwingConstants.HORIZONTAL);
		seperator2.setMaximumSize( new Dimension(Integer.MAX_VALUE, 5) );
		this.add(seperator2);
		JButton submitButton = new JButton("Submit player names");

		//this is the button listener for the submit button
		//the text fields must meet the conditions
		submitButton.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
				int count=0;
				if(!p1Field.getText().trim().equals("")){
					count++;
					players.add(new Player(p1Field.getText(),START_MONEY,"yellow"));

				}
				if(!p2Field.getText().trim().equals("")){
					count++;
					players.add(new Player(p2Field.getText(),START_MONEY,"green"));

				}
				if(!p3Field.getText().trim().equals("")){
					players.add(new Player(p3Field.getText(),START_MONEY,"red"));
					count++;
				}
				if(!p4Field.getText().trim().equals("")){
					players.add(new Player(p4Field.getText(),START_MONEY,"blue"));
					count++;
				}
				if(count<2){
					players.clear();
				}else{
					playerDataFlag=true;
				}
			}
		} );
		this.add(submitButton);
	}

	
	public void actionPerformed(ActionEvent e) { 
		if(e.getSource() == rollButton){
			event = Controls.Event.ROLL;
		}else if(e.getSource() == purchaseButton){
			event = Controls.Event.PURCHASE;
		}else if(e.getSource() == endTurnButton){
			event = Controls.Event.END_TURN;
		}

	}

	
	/**
	 * This internal class is here to enforce a max player name len
	 */
	public class JTextFieldLimit extends PlainDocument {
	  private int limit;

	  /**
	   * Class constructor
	   */
	  JTextFieldLimit(int limit) {
	   super();
	   this.limit = limit;
	  }

	  /**
	   *
	   * @param offset
	   * @param str
	   * @param attr
	   * @throws BadLocationException
	   */
	  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
		  super.insertString(offset, str, attr);
		}
	  }
	}

}
