
/**
 * Contains player information, such as name, amount of money, color of marker,
 * and current location on the board
 */

public class Player{

	String name;
	public int money;
	String color;
	public int tile;

	/**
	 * Class constructor
	 * @param n The name of the player
	 * @param m The amount of money the player has
	 * @param color The color of the player's marker on the board
	 */
	Player(String n, int m, String color){
		this.name = n;
		this.setMoney(m);
		this.color=color;
		this.tile = 0;
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
