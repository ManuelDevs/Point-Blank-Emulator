package ru.pb.global.service;

import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.EquipmentDao;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;


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
	
	public void registerItem(Player entity, PlayerItem playerItem) {
		equipmentDao.registerItem(entity, playerItem);
	}
}
