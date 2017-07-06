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
	public ArrayList<Tile> properties;
	
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
	/**
	 * Get the string of properties
	 * @return String The string of properties
	*/
	public String getPropertyString() {
			return this.propertyString;
	}
	/**
	 * Set the property string
	 * @param n The new string of properties
	 */
	public void setPropertyString(String n) {
		this.propertyString = n;
	}
	/**
	 * Remove property from the property array list
	 * @param the string name of the property to remove
	 * @param the player object who is new owner of the property or null if none
	 */
	public void removeProperty(String propName, Player newOwner) {
		for (int i = 0; i < properties.size(); i++) {
			Tile theProp = properties.get(i);
			if (theProp.propertyName.equals(propName)) {
				theProp.setOwner(newOwner);
				properties.remove(i);
				break;
			}
		}
	}
	/**
	 *Remove property from the property array list
	 * @param the property tile 
	 * @param the player object who is the new owner, or null if none
	 */
	public void removeProperty(Tile property, Player newOwner) {
		Tile theProp = properties.get(properties.indexOf(property));
		theProp.setOwner(newOwner);
		properties.remove(property);
	}
}
