package ru.pb.global.enums;

public enum Modes {
	BOT(1),
	DEATHMATCH(2),
	ELIMINATE(8),
	BOMB(16),
	HEADHUNTER(32),
	SHOTGUN(128),
	KNUCKLE(256),
	ZOMBIE(1024),
	BOT_HARD(2048),
	CHAOS(4096);
	
	private int _value;

	Modes(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
