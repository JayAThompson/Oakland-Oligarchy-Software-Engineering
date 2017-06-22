
/*
 * Individual tiles on the game board
 * - Contains the property name and value
 * - Contains a panel for the text and a panel for the player markers
 */

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

public class Tile{

	boolean isActionTile;
	String propertyName;
	int propertyValue;
	String owner;
	int tileIndex;
	//String players;
	ArrayList<Player> players = new ArrayList<Player>();
	JPanel namePanel,markerPanel;

	/*
	 * Create a new tile object from a JPanel object, property name, and property value
	 */
	Tile(JPanel panel, String name, int value,int index) {
		this.namePanel=panel;
		this.propertyName=name;
		this.propertyValue=value;
		this.markerPanel = new JPanel();
		this.namePanel.add(markerPanel);
		this.tileIndex=index;
	}

	// Return the panel associated with this tile that contains the name
	public JPanel getPanel(){
		return namePanel;
	}

	//adds a player and draws their marker
	public void addPlayer(Player player){
		players.add(player);
		drawMarkers();
	}

	// Removes a player and draws their marker
	public void removePlayer(Player player){
		players.remove(player);
		drawMarkers();
	}

	// Redraws the marker panel, adding the appropriate player markers
	private void drawMarkers(){
		int markersToDraw = players.size();
		this.namePanel.remove(markerPanel);
		//this.markerPanel.removeAll();
		if((tileIndex>=10 && tileIndex<=17)||(tileIndex>=28 && tileIndex<=35)){
			this.markerPanel = new JPanel(new GridLayout(1,markersToDraw));
		}else{
			this.markerPanel = new JPanel(new GridLayout(markersToDraw,1));
		}
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
