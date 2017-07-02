import java.util.ArrayList;


/**
 * Contains player information, such as name, amount of money, color of marker,
 * and current location on the board
 */

public class Player{

	String name;
	public String propertyString;
	public int money;
	String color;
	public int tileIndex;
	ArrayList<Tile> properties;
	
	/*this is only to be called if the player is on the tile*/
	public void purchaseProperty(Tile tile){
		this.money -= tile.propertyValue;
		properties.add(tile);
		propertyString = "";
		for(Tile tmp : properties){
			/*
			if(properties.indexOf(tmp) != properties.size()-1){
				propertyString+=tmp.propertyName+" ";
			}
			*/
			propertyString+=">"+tmp.propertyName+"\n";
		}
	}
	
		/**
	 * Class constructor
	 * @param name The name of the player
	 * @param money The amount of money the player has
	 * @param color The color of the player's marker on the board
	 */
	Player(String name, int dollaz, String color){
		this.name = name;
		this.setMoney(dollaz);
		this.color=color;
		this.tileIndex = 0;
		this.properties = new ArrayList<Tile>();
	}

	/**
	 * Get the amount of money the player has
	 * @return int The amount of money the player has
	 */
	public int getMoney(){
		return this.money;
	}

	/**
	 * Set the amount of money the player has
	 * @param m The new amount of money the player has
	 */
	public void setMoney(int m){
		if (m < 0) money = 0;
		this.money = m;
	}

	/**
	 * Get the name of the player
	 * @return String The name of the player
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Set the name of the player
	 * @param n The new name of the player
	 */
	public void setName(String n){
		this.name = n;
	}
}
