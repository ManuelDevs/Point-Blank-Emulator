package ru.pb.global.enums;

public enum RoomErrorMessage {
	EVENT_ERROR_EVENT_READY_WEAPON_EQUIP(0x800010AB),
	EVENT_ERROR_NO_REAL_IP(0x80001008),
	BATTLE_FIRST_HOLE(0x8000100B),
	BATTLE_FIRST_HOLE_MAIN(0x8000100A),
	EVENT_ERROR_NO_READY_TEAM(0x80001009);

	private int _value;

	RoomErrorMessage(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
