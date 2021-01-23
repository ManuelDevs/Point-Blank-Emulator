package ru.pb.global.enums;

public enum RoomBalance {
	NONE(0),
	QTY(1),
	RANK(2);
	
	private int _value;

	RoomBalance(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
