
/**
 * Game board for Oakland Oligarchy
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GameBoard extends JPanel{

	// 2-D array containing the title and the price for each tile on the game board
	private static final String[][] tileInfo= new String[][]{{"Start","0"},
															 {"Five Guys","2000"},
															 {"Noodles n' Company","2500"},
															 {"actiontile","0"},
															 {"Primanti Bros.","5000"},
															 {"actiontile","0"},
															 {"Panera Bread","3500"},
															 {"McDonald's","1250"},
															 {"Sorrento's Pizza","750"},
															 {"Go to Hillman","0"},
															 {"Forbes Hall","35000"},
															 {"Benedum Hall","45000"},
															 {"actiontile","0"},
															 {"Litchfield Towers","25000"},
															 {"actiontile","0"},
															 {"Sennott Square","20000"},
															 {"William Pitt Union","25000"},
															 {"Cathedral of Learning","250000"},
															 {"Hillman Library","20000"},
															 {"Schenley Plaza","15000"},
															 {"Carnegie Library","25000"},
															 {"actiontile","0"},
															 {"Carnegie Museum of Art","150000"},
															 {"Carnegie Museum of Natural History","100000"},
															 {"actiontile","0"},
															 {"Phipps Conservatory","75000"},
															 {"Schenley Park","10000"},
															 {"Go to Hillman","0"},
															 {"Union Grill","1500"},
															 {"Lulu's Noodles","1250"},
															 {"actiontile","0"},
															 {"Razzy Fresh","1000"},
															 {"The Original Hot Dog Shop","500"},
															 {"actiontile","0"},
															 {"Papa D's","750"},
															 {"actiontile","0"}};

	public static ArrayList<Tile> tiles = new ArrayList<Tile>();

	BoardCenter boardCenter = new BoardCenter(new Dimension(640, 400));

	
	JPanel north;
	JPanel south;
	JPanel east;
	JPanel west;

	JPanel board = new JPanel(new GridBagLayout());

/* 	Tile northSet[];
	Tile southSet[];
	Tile eastSet [];
	Tile westSet[]; */

	/**
	 * Class constructor
	 * Initialize panels, set layout and preferred size, create tiles and add
	 * them to the game board panel
	 */
	public GameBoard() { //add parameters later for rigid jail/home tile maybe?

		this.north = new JPanel();
		this.south = new JPanel();
		this.east  = new JPanel();
		this.west = new JPanel();
	    
		this.north.setLayout(new GridLayout(1, 8));
		this.south.setLayout(new GridLayout(1, 8));
		this.east.setLayout (new GridLayout(8, 1));
		this.west.setLayout(new GridLayout(8, 1));
		
		 this.board.setPreferredSize(new Dimension(960, 600));
		 this.north.setPreferredSize(new Dimension(640, 100));
		 this.south.setPreferredSize(new Dimension(640, 100));
		 this.east.setPreferredSize(new Dimension(160, 400));
		 this.west.setPreferredSize(new Dimension(160, 400));

		createTiles();

		//these for loops set the tile locations on the gameboard
		for(int i=1;i<9;i++){
			north.add(tiles.get(i).getPanel());
		}
		
		for(int i=10;i<19;i++){
			east.add(tiles.get(i).getPanel());
		}
		for(int i=26;i>=20;i--){
			south.add(tiles.get(i).getPanel());
		}
		for(int i=35;i>=28;i--){
			west.add(tiles.get(i).getPanel());
		}

		GridBagConstraints g = new GridBagConstraints();
		//northwest corner
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 2;
		g.gridheight = 2;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(tiles.get(0).namePanel, g);
		
		//northeast corner
		g.gridx = 10;
		g.gridy = 0;
		g.gridwidth = 2;
		g.gridheight = 2;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(tiles.get(9).namePanel, g);
		
		//southeast corner
		g.gridx = 10;
		g.gridy = 10;
		g.gridwidth = 2;
		g.gridheight = 2;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(tiles.get(18).namePanel, g);
		
		//southwest corner
		g.gridx = 0;
		g.gridy = 10;
		g.gridwidth = 2;
		g.gridheight = 2;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(tiles.get(27).namePanel, g);
		
		//north		
		g.gridx = 2;
		g.gridy = 0;
		g.gridwidth = 8;
		g.gridheight = 2;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(north, g);

		//center
		g.gridx = 2;
		g.gridy = 2;
		g.gridwidth = 8;
		g.gridheight = 8;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(boardCenter, g);

		//south
		g.gridx = 2;
		g.gridy = 10;
		g.gridwidth = 8;
		g.gridheight = 2;
		g.ipadx = 0;
		g.ipady = 0;
		board.add(south, g);

		//east
		g.gridx = 10;
		g.gridy = 2;
		g.gridwidth = 2;
		g.gridheight = 8;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(east, g);

		//west
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		g.gridheight = 8;
		g.ipadx = 0;
		g.ipady = 0;
		g.fill = GridBagConstraints.BOTH;
		board.add(west, g);
	}

	/**
	 * Return game board JPanel
	 * @return JPanel This returns the game board panel.
	 */
	public JPanel getBoard() {
		return board;
	}

	/**
	 * This method initializes all of the tile text and values.
	 * It is the method that actually draws the tiles.
	 */
	private void createTiles(){
		//int count++;
		int textSize=10;
			for(int i=0;i<36;i++){
				JPanel tilePanel = new JPanel();
				if(i==0 || i==9 || i==19 || i==28){
					tilePanel.setPreferredSize(new Dimension(160, 100));
					tilePanel.setLayout(new GridLayout(1,2));

				}
				if((i>=1 && i<=8)||(i>=20 && i<=26)){
					tilePanel.setLayout(new GridLayout(2,1));
				}else{
					tilePanel.setLayout(new GridLayout(1,2));
				}
				StringBuilder labelText = new StringBuilder("<html><b>"+tileInfo[tiles.size()][0]+"</b><br>");
				if (!tileInfo[tiles.size()][1].equals("0"))
				{
					labelText.append("$"+tileInfo[tiles.size()][1]);
				}
				JLabel label = new JLabel(new String(labelText));
				label.setFont(new Font("Calibri", Font.PLAIN, textSize));
				tilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
				tilePanel.add(label);
				/*
				JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
				separator.setMaximumSize( new Dimension(2, 40) );
				separator.setAlignmentX(Component.LEFT_ALIGNMENT);
				tilePanel.add(separator);
				*/

				tiles.add(new Tile(tilePanel, tileInfo[tiles.size()][0], Integer.parseInt(tileInfo[tiles.size()][1]), i));
			}
	}

/*
	public void drawPlayersTurn(Player player){
		boardCenter.drawPlayersTurn(player);
	}
*/	

	/**
	 * This method draws a marker for the player on their current tile location.
	 * @param player This is the Player object for the player whose marker will be drawn.
	 */
	public void drawPlayer(Player player){
		tiles.get(player.tileIndex).addPlayer(player);
	}

	/**
	 * This method erases a marker for the player from their current tile location.
	 * @param player This is the Player object for the player whose marker will be erased.
	 */
	public void erasePlayer(Player player){
		tiles.get(player.tileIndex).removePlayer(player);
	}
	/*
	public void drawDiceRoll(Player player,int dice1,int dice2){
		boardCenter.drawDiceRoll(player,dice1,dice2);
	}
	
	public void showRollButton(){
		boardCenter.rollButton.setVisible(true);
	}
	
	public void hideRollButton(){
		boardCenter.rollButton.setVisible(false);	
	}
	
	public void showPurchaseButton(){
		boardCenter.purchaseButton.setVisible(true);
	}
	
	public void hidePurchaseButton(){
		boardCenter.purchaseButton.setVisible(false);	
	}	
	
	public void showEndTurnButton(){
		boardCenter.endTurnButton.setVisible(true);
	}
	
	public void hideEndTurnButton(){
		boardCenter.endTurnButton.setVisible(false);	
	}		
	
	/*this method returns whether some event has happened (button)*/
/*	public boolean pollForEvent(){
		if(boardCenter.event == Event.NONE){
			return false;
		}
		return true;
	}
*/	
	/*this method returns what event happened and also clears the event*/
/*	public Event getEvent(){
//		Event event = boardCenter.event;
//		boardCenter.event = Event.NONE;
		return event;
	}
*/	//public void addPlayerToTile()
	

	/**
	 * Main method for testing purposes
	 * @param args Unused
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1400, 720);
		GameBoard board = new GameBoard();
		frame.add(board.getBoard());
		frame.setVisible(true);
	}
}
