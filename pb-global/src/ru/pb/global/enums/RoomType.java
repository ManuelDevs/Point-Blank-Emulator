package ru.pb.global.enums;

public enum RoomType {
	NONE(0),
	TDM(1),
	BOMB(2),
	DESTROY(3),
	ANNIHILATION(4),
	DEFENSE(5),
	SUDDEN_DEATH(6),
	BOSS(7),
	HEAD_HUNTER(8),
	HEAD_KILLER(9),
	TUTORIAL(10),
	DOMINATION(11),
	CROSS_COUNTER(12),
	CHAOS(13),
	ESCORT(14);
	
	private int _value;

	RoomType(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
