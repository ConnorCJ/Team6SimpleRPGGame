package simpleRPGgame;

public class Damage {
	public int damage, flags;
	public static final int DMG_CRIT = 1;
	public static final int DMG_DODGE = 2;
	public static final int DMG_DEFBONUS = 4;
	
	public Damage()
	{
		damage = 0;
		flags = 0;
	}
	
	public Damage(int damage, int flags)
	{
		this.damage = damage;
		this.flags = flags;
	}
}
