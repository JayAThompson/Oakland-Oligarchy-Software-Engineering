import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;


public class Tile{
	
	boolean isActionTile;
	String propertyName;
	int propertyValue;
	String owner;
	//String players;
	ArrayList<Player> players = new ArrayList<Player>();
	JPanel namePanel,markerPanel;
	
	Tile(JPanel panel, String name, int value) {
		this.namePanel=panel;
		this.propertyName=name;
		this.propertyValue=value;
		this.markerPanel = new JPanel();
		this.namePanel.add(markerPanel);
	}
	
	public JPanel getPanel(){
		return namePanel;
	}

	//adds a player and draws their marker
	public void addPlayer(Player player){
		players.add(player);
		drawMarkers();
	}
	public void removePlayer(Player player){
		players.remove(player);
		drawMarkers();
	}
	
	private void drawMarkers(){
		int markersToDraw = players.size();
		this.namePanel.remove(markerPanel);
		//this.markerPanel.removeAll();
		this.markerPanel = new JPanel(new GridLayout(markersToDraw,1));
		for(Player player : players){	
			JPanel tmpPanel = new JPanel();
			tmpPanel.setOpaque(true);
			try{
			tmpPanel.setBackground((Color)Class.forName("java.awt.Color").getField(player.color).get(null));
			//tmpPanel.setBackground(Color.GREEN);
			}catch(Exception e){}
			this.markerPanel.add(tmpPanel);
			//this.markerPanel.add(new JLabel("hi"));

		}
		this.namePanel.add(markerPanel);
		//System.out.println();
		//this.markerPanel.validate();
		//this.markerPanel.repaint();
		this.namePanel.validate();
		this.namePanel.repaint();
	}
	
}