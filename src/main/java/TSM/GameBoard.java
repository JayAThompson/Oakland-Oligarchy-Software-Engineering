

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JPanel{
	
	Center center = new Center();
	
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
	    
		this.north.setLayout(new GridLayout(0, 8));
		this.south.setLayout(new GridLayout(0, 8));
		this.east.setLayout (new GridLayout(10, 0));
		this.west.setLayout(new GridLayout(10, 0));
		
		 this.board.setPreferredSize(new Dimension(720, 720));
		 this.north.setPreferredSize(new Dimension(576, 72));
		 this.south.setPreferredSize(new Dimension(576, 72));
		 this.east.setPreferredSize(new Dimension(72, 720));
		 this.west.setPreferredSize(new Dimension(72, 720));
		
		setTile(north);
		setTile(south);
		setTile(east);
		setTile(west);
		
		//north
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 1;
		g.gridy = 0;
		g.gridwidth = 8;
		g.gridheight = 1;
		g.ipadx = 0;
		g.ipady = 80;
		g.fill = GridBagConstraints.BOTH;
		board.add(north, g);
		
		//center
		g.gridx = 1;
		g.gridy = 1;
		g.gridwidth = 8;
		g.gridheight = 8;
		g.ipadx = 100;
		g.ipady = 360;
		g.fill = GridBagConstraints.BOTH;
		board.add(center, g);
		
		//south
		g.gridx = 1;
		g.gridy = 9;
		g.gridwidth = 8;
		g.gridheight = 1;
		g.ipadx = 0;
		g.ipady = 0;
		board.add(south, g);
		
		//east
		g.gridx = 9;
		g.gridy = 0;
		g.gridwidth = 1;
		g.gridheight = 10;
		g.ipadx = 100;
		g.ipady = 400;
		g.fill = GridBagConstraints.BOTH;
		board.add(east, g);
		
		//west
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 1;
		g.gridheight = 10;
		g.ipadx = 100;
		g.ipady = 400;
		g.fill = GridBagConstraints.NONE;
		board.add(west, g);
		

		
	}
	
	public JPanel getBoard() {
		return board;
	}
	
	private static void setTile(JPanel panel) {
		GridLayout layout = (GridLayout)panel.getLayout();
		int iterations = 0;

		if (layout.getRows() == 0) {
			iterations = layout.getColumns();
			
			for (int i = 0; i < iterations; i++) {
				JLabel label = new JLabel("<html>Label</html>"); //technically correct, the best kind!
				label.setFont(new Font("Calibri", Font.PLAIN, 15));
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.add(label, 0, i);
			}
		}
		else {
			iterations = layout.getRows();
			
			for (int i = 0; i < iterations; i++) {
				JLabel label = new JLabel("<html>Label</html>", SwingConstants.CENTER);
				label.setFont(new Font("Calibri", Font.PLAIN, 15));
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.add(label, i, 0); 
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1400, 720);
		GameBoard board = new GameBoard();
		frame.add(board.getBoard());
		frame.setVisible(true);
	}
}