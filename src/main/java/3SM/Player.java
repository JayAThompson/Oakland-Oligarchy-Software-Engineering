public class Player{
	String name;
	int money;

	Player(String n, int m){
		this.name = name;
		this.setMoney(money);
	}

	public int getMoney(){
		return this.money;	
	}
	public void setMoney(int m){
		if (money < 0)
			money = 0;
		this.money = money;
	}
	public String getName(){
		return this.name;
	}
	
	public void setMoney(String n){
		this.name = name;
	}
}