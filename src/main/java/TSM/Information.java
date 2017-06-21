

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class Information extends JPanel {
	
	public ArrayList<Player> players = new ArrayList<>();
	private int START_MONEY=1000;
	private int MAX_NAME_LEN = 20;
	//this flag is to be set by the the form to submit new player names
	public boolean playerDataFlag = false;
	
	public void drawPlayerInfo(){
		JSeparator seperator;
		JLabel name;
		this.removeAll();
		this.add(new JLabel("<html><h1>Player Information</h1></html>",SwingConstants.CENTER));
		for(Player player : players){
				//adding a separator to tell the players apart
				seperator = new JSeparator(SwingConstants.HORIZONTAL);
				seperator.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
				this.add(seperator);
				//outerpanel houses two other panels. one panel has the text info about the player and the other has the player color
				JPanel outerPanel = new JPanel(new GridLayout(1,2));
				//innerPanel houses the player info
				JPanel innerPanel = new JPanel();
				innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
				//info goes in first spot in grid
				outerPanel.add(innerPanel);
				this.add(outerPanel);
				//tmpPanel houses the player color info
				JPanel tmpPanel = new JPanel();
				tmpPanel.setOpaque(true);
				try{
				tmpPanel.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
				//tmpPanel.setBackground(Color.GREEN);
				}catch(Exception e){}
				outerPanel.add(tmpPanel);
			 	name = new JLabel("<html><u>"+player.getName()+"</u></html>");
				name.setFont(new Font("Calibri", Font.BOLD, 15));
				innerPanel.add(name);
				innerPanel.add( new JLabel("MONEY: "+ player.money, SwingConstants.CENTER));
				innerPanel.add(new JLabel("PROPERTIES: ", SwingConstants.CENTER));
				this.add(Box.createRigidArea(new Dimension(0,10)));
		}
		this.validate();
		this.repaint();
	}
	
	//this method is to be called if the new game button is selected, or by default when oakOli runs
	//it collects player names from the user
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
					players.add(new Player(p3Field.getText(),START_MONEY,"black"));
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
	


	//this internal class is here to enforce a max player name len
	public class JTextFieldLimit extends PlainDocument {
	  private int limit;

	  JTextFieldLimit(int limit) {
	   super();
	   this.limit = limit;
	   }

	  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
		  super.insertString(offset, str, attr);
		}
	  }
	}
	Information(){
		
	/*	
		this.players=players;
		
		for(Player player : players){
			JLabel name = new JLabel("<html><u>"+player.getName()+"</u></html>", SwingConstants.CENTER);
			name.setFont(new Font("Calibri", Font.BOLD, 15));
			labels.add(name);
			JLabel money = new JLabel("MONEY: "+ player.getMoney(), SwingConstants.CENTER);
			labels.add(money);
			JLabel owned = new JLabel("PROPERTIES: ", SwingConstants.CENTER);
			labels.add(owned);
		}
		*/
		//getting all of the player info 
		
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setPreferredSize(new Dimension(285, 1000));
		//this.setBounds(0,0,285,1000);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.collectPlayerInfo();

	}

}