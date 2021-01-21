package ru.pb.global.enums;

/**
 * Сообщения об ощибках в бою
 * <p/>
 * User: DarkSkeleton
 */
public enum BattleErrorMessage {
	EVENT_ERROR_FIRST_HOLE(0x8000100B), /**
	 * Невозможно присоединиться к лидеру комнаты.\nПереход в Зал ожидания. *
	 */
	EVENT_ERROR_FIRST_MAINLOAD(0x8000100A), /**
	 * Присоединение к игре не состоялось. *
	 */
	EVENT_ERROR_EVENT_BATTLE_TIMEOUT_CN(0x80001006), /**
	 * Игра окончена в связи с сетевыми неполадками. *
	 */
	EVENT_ERROR_EVENT_BATTLE_TIMEOUT_CS(0x80001007);

	private int _value;

	BattleErrorMessage(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
