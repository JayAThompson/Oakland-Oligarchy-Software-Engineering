import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class GameBoard extends JPanel{
	private static final String[][] tileInfo= new String[][]{{"name1","100"},
											{"name2","200"},
											{"name3","300"},
											{"name4","400"},
											{"name5","500"},
											{"name6","600"},
											{"name7","700"},
											{"name8","800"},
											{"name9","900"},
											{"name10","1000"},
											{"name11","1100"},
											{"name12","1200"},
											{"name13","1300"},
											{"name14","1400"},
											{"name15","1500"},
											{"name16","1600"},
											{"name17","1700"},
											{"name18","1800"},
											{"name19","1900"},
											{"name20","2000"},
											{"name21","2100"},
											{"name22","2200"},
											{"name23","2300"},
											{"name24","2400"},
											{"name25","2500"},
											{"name26","2600"},
											{"name27","2700"},
											{"name28","2800"},
											{"name29","2900"},
											{"name30","3000"},
											{"name31","3100"},
											{"name32","3200"},
											{"name33","3300"},
											{"name34","3400"},
											{"name35","3500"},
											{"name36","3600"}};
	
	public static ArrayList<Tile> tiles = new ArrayList<Tile>();
	Center center = new Center(new Dimension(640, 400));
	
	JPanel north;
	JPanel south;
	JPanel east;
	JPanel west;
	
	JPanel board = new JPanel(new GridBagLayout());
	
/* 	Tile northSet[];
	Tile southSet[];
	Tile eastSet [];
	Tile westSet[]; */
	
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
		 //this.west.setMaximumSize(new Dimension(72, 500));
		
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
		board.add(center, g);
		
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
	
	public JPanel getBoard() {
		return board;
	}
	
	//this method initializes all of the tile text and values
	//it is the method that actually draws the tiles
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
				JLabel label = new JLabel("<html><b>"+tileInfo[tiles.size()][0]+"</b><br>$"+tileInfo[tiles.size()][1]); 
				label.setFont(new Font("Calibri", Font.PLAIN, textSize));
				tilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
				tilePanel.add(label);
				/*
				JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
				separator.setMaximumSize( new Dimension(2, 40) );
				separator.setAlignmentX(Component.LEFT_ALIGNMENT);
				tilePanel.add(separator);
				*/
				
				tiles.add(new Tile(tilePanel,label.getText(),Integer.parseInt(tileInfo[tiles.size()][1])));
			}


	}

	public void drawPlayer(Player player){
		tiles.get(player.tile).addPlayer(player);
	}
	public void erasePlayer(Player player){
		tiles.get(player.tile).removePlayer(player);
	}
	public void drawDiceRoll(Player player,int dice1,int dice2){
		center.drawDiceRoll(player,dice1,dice2);
	}
	
	//public void addPlayerToTile()

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1400, 720);
		GameBoard board = new GameBoard();
		frame.add(board.getBoard());
		frame.setVisible(true);
	}
}