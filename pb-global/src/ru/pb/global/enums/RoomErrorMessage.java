package ru.pb.global.enums;

/**
 * Сообщения об ошибках в комнате
 *
 * @author DarkSkeleton
 */
public enum RoomErrorMessage {
	EVENT_ERROR_EVENT_READY_WEAPON_EQUIP(0x800010AB), /**
	 * Возникла ошибка используемого оружия. Перезагрузите игру. *
	 */
	EVENT_ERROR_NO_REAL_IP(0x80001008), /**
	 * Игра начнется при наличии одного или более игроков в противоположной команде. *
	 */
	EVENT_ERROR_NO_READY_TEAM(0x80001009);
	/**
	 * Игра начнется, когда в противоположной команде хотя бы один игрок будет в состоянии готовности. *
	 */

	private int _value;

	RoomErrorMessage(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
