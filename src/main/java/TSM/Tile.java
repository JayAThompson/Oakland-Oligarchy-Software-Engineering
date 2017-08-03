
/*
 * Individual tiles on the game board
 * - Contains the property name and value
 * - Contains a panel for the text and a panel for the player markers
 */

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.io.Serializable;


public class Tile implements Serializable{
	
	int rent = 100;

	public boolean isActionTile;
	public String propertyName;
	//String pr;
	public int propertyValue;
	public Player owner = null;
	public int tileIndex;
	//String players;
	ArrayList<Player> players;
	JPanel namePanel,markerPanel;


	/*
	 * Create a new tile object from a JPanel object, property name, and property value
	 */
	Tile(JPanel panel, String name, int value,int index,int rent) {
		players = new ArrayList<Player>();
		this.namePanel=panel;
		this.propertyName=name;
		this.propertyValue=value;
		this.markerPanel = new JPanel();
		this.namePanel.add(markerPanel);
		this.tileIndex=index;
		this.rent = rent;
		if(value == 0){
			this.owner = new Player("dummy",1,"blue");
		}
	}

	public void clearAll(){
		//players.
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

	//sets the owner of this tile
	public void setOwner(Player player){
		this.owner=player;
		try {
			this.namePanel.setBackground((Color)Class.forName("java.awt.Color").getField(this.owner.color).get(null));
		} catch (Exception e) {
			this.namePanel.setBackground(null);
				}
	}
	
	// returns the owner of this tile
	public Player getOwner() {
		return this.owner;
	}
	

	// Removes a player and draws their marker
	public void removePlayer(Player player){
		players.remove(player);
		drawMarkers();
	}
	
	//Returns the name of the property
	public String getPropertyName() {
		return this.propertyName;
	}
	
	//returns property value
	public int getPropertyValue() {
		return this.propertyValue;
	}

	// Redraws the marker panel, adding the appropriate player markers
	public void drawMarkers(){
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
