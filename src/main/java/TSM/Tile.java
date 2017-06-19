import javax.swing.*;


public class Tile{
	
	boolean isActionTile;
	String propertyName;
	int propertyValue;
	String owner;
	//String playersHere;
	//ArrayList<Player> players = new ArrayList<Player>();
	JPanel namePanel,markerPanel;
	Tile(JPanel panel, String name, int value) {
		this.namePanel=panel;
		this.propertyName=name;
		this.propertyValue=value;
	}
	
	public JPanel getPanel(){
		return namePanel;
	}
/*
	//adds a player and draws their marker
	void addPlayer(Player player){
		players.add(player);
		
		for(Player tempPlayer : players){
			
		}
	}
	*/
}