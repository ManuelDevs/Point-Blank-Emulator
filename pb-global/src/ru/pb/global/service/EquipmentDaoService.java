package ru.pb.global.service;

import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.EquipmentDao;
import ru.pb.global.models.Player;

/**
 * Сервис для работы с инвентарем персонажа
 * - Целью сервиса является корректное сохранение игровых вещей персонажа
 * при выходе из игры. Под сохранением понимается добавление новых, изменение старых
 * и удаление ненужных вещей в базу данных по результатам игровой деятельности за сессию.
 * *Под сессией понимается промежуток игровой деятельности начинающийся входом на сервер
 * и заканчивающийся выходом с сервера
 * Авторы: Felixx, Grizly
 * Date: 10.12.13
 * Time: 23:02
 */

public class EquipmentDaoService {
	private static final EquipmentDao equipmentDao = DaoManager.getInstance().getEquipmentDao();

	private EquipmentDaoService() {
	}

	public static EquipmentDaoService getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final EquipmentDaoService INSTANCE = new EquipmentDaoService();
	}

	public void writeBag(Player entity) {
		equipmentDao.recordItems(entity);
	}

	public void writeEquipped(Player entity) {
		equipmentDao.recordEquipped(entity);
	}
}
