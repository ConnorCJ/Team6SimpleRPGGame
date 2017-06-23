package simpleRPGgame;

public class Character {
	
	String name;
	int charID,attack,defense,speed,intelligence,money,maxHP,atTown,eqWeapon,eqArmor,exp;
	
	public Character(String name, int charID){
		this.name = name;
		this.charID = charID;
		attack = defense = speed = intelligence = eqWeapon = eqArmor = 5;
		money = 50;
		maxHP = 10;
		atTown = 1;
		exp = 1000;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharID() {
		return charID;
	}

	public void setCharID(int charID) {
		this.charID = charID;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getAtTown() {
		return atTown;
	}

	public void setAtTown(int atTown) {
		this.atTown = atTown;
	}

	public int getEqWeapon() {
		return eqWeapon;
	}

	public void setEqWeapon(int eqWeapon) {
		this.eqWeapon = eqWeapon;
	}

	public int getEqArmor() {
		return eqArmor;
	}

	public void setEqArmor(int eqArmor) {
		this.eqArmor = eqArmor;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
