package ru.pb.global.enums;

public enum MainMaps {
	TUTORIAL(44),
	DEATHMATCH(1),
	BOMB(25),
	CHAOS(35),
	ELIMINATE(11),
	DEFENSE(39),
	AI(1),
	DINO(40),
	SNIPER(1),
	SHOTGUN(1),
	HEADHUNTER(1),
	KNUCKLE(1),
	CROSSCOUNTER(54),
	DESTROYER(1);
	
	
	private int _value;

	MainMaps(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
