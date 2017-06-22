import java.util.ArrayList;


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
			propertyString+=tmp.propertyName+"\n";
		}
	}
	
	Player(String name, int dollaz, String color){
		this.name = name;
		this.setMoney(dollaz);
		this.color=color;
		this.tileIndex = 0;
		this.properties = new ArrayList<Tile>();
	}

	public int getMoney(){
		return this.money;
	}

	public void setMoney(int m){
		if (m < 0) money = 0;
		this.money = m;
	}
	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name = n;
	}
}
