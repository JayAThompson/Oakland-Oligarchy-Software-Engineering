
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
import javax.swing.text.DefaultCaret;
import java.util.ArrayList;


public class Controls extends JPanel implements ActionListener{
	public enum Event{ ROLL,PURCHASE,END_TURN,TRADE,NONE };	
	
	public ArrayList<Player> players = new ArrayList<Player>();
//	private ArrayList<JTextArea> propertyText = new ArrayList<JTextArea>();
//	private ArrayList<JLabel> moneyLabels = new ArrayList<JLabel>();
	JButton rollButton,purchaseButton,endTurnButton,tradeButton;
	JLabel turnLabel;
	JTextArea log;
	JScrollPane pane;
	
	private int START_MONEY=500*20;
	private int MAX_NAME_LEN = 12;
	//this flag is to be set by the the form to submit new player names
	public boolean playerDataFlag = false;
	Event event = Event.NONE;
	
	/*The following one line methods are self explanatory*/
	public void hideRollButton(){
		rollButton.setVisible(false);
	}
	
	public void hidePurchaseButton(){
		purchaseButton.setVisible(false);
	}
	
	public void hideEndTurnButton(){
		endTurnButton.setVisible(false);
	}
	
	public void hideTradeButton() {
		tradeButton.setVisible(false);
	}
	
	public void showRollButton(){
		rollButton.setVisible(true);
	}
	
	public void showPurchaseButton(){
		purchaseButton.setVisible(true);
	}
	
	public void showEndTurnButton(){
		endTurnButton.setVisible(true);
	}	
	
	public void showTradeButton(){
		tradeButton.setVisible(true);
	}
	
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
	
	/**
	 * This method updates the string that tells the players whose turn it is
	 * it sets the background of the string to be the player color
	 */
	public void drawPlayerTurnLabel(Player player){
		turnLabel.setText("Turn: "+ player.name);
		turnLabel.setOpaque(true);
		try{
		turnLabel.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
		}catch(Exception e){}
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
	 * This method writes the string passed to it with a newline char to the game log
	 * it also scrolls the pane to the most recent line
	 * @param s the string to write to the game log
	 */
	public void writeLine(String s){
		log.append(s + "\n");
		JScrollBar sb = pane.getVerticalScrollBar();
		sb.setValue( sb.getMaximum() );
	}
	
	/**
	* This method initializes the contols panel with buttons and the log
	*/
	public void drawTurnButtons(){
		JSeparator separator;
		this.removeAll();
		
		turnLabel = new JLabel("<html>TURN: </html>");
		turnLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		this.add(turnLabel);
		
		separator = new JSeparator(SwingConstants.HORIZONTAL);
		//separator.setAlignmentX(Component.CENTER_ALIGNMENT);
   		//separator.setPreferredSize(new Dimension(50, 10));
		separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
		this.add(separator);
		
		this.add(Box.createRigidArea(new Dimension(0,20)));
		rollButton = new JButton("Roll the dice");
		rollButton.addActionListener(this);
		rollButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		rollButton.setVisible(true);
		this.add(rollButton);
		//turnControls.add(Box.createRigidArea(new Dimension(0,10)));



		
/*		purchaseLabel = new JLabel("Want to purchse this shiz?");
		purchaseLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		purchaseLabel.setVisible(false);
		this.add(purchaseLabel);
*/		//turnControls.add(Box.createRigidArea(new Dimension(0,10)));


			
		purchaseButton = new JButton("Purchase Property");
		purchaseButton.addActionListener(this);
		purchaseButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		purchaseButton.setVisible(true);
		this.add(purchaseButton);
		this.add(Box.createRigidArea(new Dimension(0,20)));
		
		tradeButton = new JButton("Trade Property");
		tradeButton.addActionListener(this);
		tradeButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		tradeButton.setVisible(true);
		this.add(tradeButton);
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
		endTurnButton.setVisible(true);
		this.add(endTurnButton);
		this.add(Box.createRigidArea(new Dimension(0,40)));
		
		
		JLabel logLabel = new JLabel("Game Log:");
		logLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
		this.add(logLabel);
		
		log = new JTextArea("The Game Begins\n");
		log.setLineWrap(true);
		log.setWrapStyleWord(true);
		pane = new JScrollPane(log);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(pane);
		
		this.validate();
		this.repaint();
	}
	
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
					players.add(new Player(p4Field.getText(),START_MONEY,"cyan"));
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

	/**
	* This method is the action listener for the buttons
	* Controls.event is set to the value of the button pressed
	*/
	public void actionPerformed(ActionEvent e) { 
		if(e.getSource() == rollButton){
			event = Controls.Event.ROLL;
		}else if(e.getSource() == purchaseButton){
			event = Controls.Event.PURCHASE;
		}else if(e.getSource() == endTurnButton){
			event = Controls.Event.END_TURN;
		}else if (e.getSource() == tradeButton) {
			event = Controls.Event.TRADE;
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
