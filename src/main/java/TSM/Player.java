

public class Player{
	
	String name;
	int money;
	String color;
	
	Player(String n, int m, String color){
		this.name = n;
		this.setMoney(m);
		this.color=color;
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