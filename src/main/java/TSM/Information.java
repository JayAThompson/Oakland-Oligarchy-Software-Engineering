
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

public class Information extends JPanel {

	public ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<JTextArea> propertyText = new ArrayList<JTextArea>();
	private ArrayList<JLabel> moneyLabels = new ArrayList<JLabel>();
	
	private int START_MONEY=750*20;
	private int MAX_NAME_LEN = 20;
	//this flag is to be set by the the form to submit new player names
	public boolean playerDataFlag = false;

	/**
	 * Class constructor
	 * Set border, preferred size, and layout
	 * Collect player info
	 */
	Information(){
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setPreferredSize(new Dimension(285, 1000));
		//this.setBounds(0,0,285,1000);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.collectPlayerInfo();
	}
	
	public void drawPlayerInfo(Player player){
		propertyText.get(players.indexOf(player)).setText(player.propertyString);
		moneyLabels.get(players.indexOf(player)).setText("MONEY: $" +player.getMoney());
		//this.revalidate();
		//this.repaint();
	}
	/**
	 * This method draws the information for the players (name, information, and marker color) in the panel.
	 */	
	public void initPlayerInfo(){
		JSeparator separator;
		JLabel name;
		this.removeAll();
		this.add(new JLabel("<html><h1>Player Information</h1></html>",SwingConstants.LEFT));
		for(Player player : players){
				//adding a separator to tell the players apart
				separator = new JSeparator(SwingConstants.HORIZONTAL);
				separator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
				this.add(separator);
				//outerpanel houses two other panels. one panel has the text info about the player and the other has the player color
				//JPanel outerPanel = new JPanel(new GridLayout(1,2));
				//innerPanel houses the player info
				JPanel innerPanel = new JPanel();
				this.add(innerPanel);
				innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
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
			
			 	name = new JLabel("<html><u>"+player.getName()+"</u></html>",SwingConstants.LEFT);
				name.setFont(new Font("Calibri", Font.BOLD, 15));
				
				innerPanel.add(name);
				JLabel tmpLabel = new JLabel("MONEY: "+ player.money, SwingConstants.LEFT); 
				innerPanel.add( tmpLabel );
				moneyLabels.add(tmpLabel);
				innerPanel.add(new JLabel("<html>PROPERTIES:</html>", SwingConstants.LEFT));
				JTextArea tmp = new JTextArea();
				tmp.setLineWrap(true);
				tmp.setMaximumSize(new Dimension(200,1000));
				try{
					tmp.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
				}catch(Exception e){}
				innerPanel.add(tmp);
				propertyText.add(tmp);
				this.add(Box.createRigidArea(new Dimension(0,10)));
		}
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
