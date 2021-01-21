package ru.pb.global.service;

import ru.pb.global.dao.ClanDao;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.models.Clan;

import java.util.List;

/**
 * Сервис для работы с кланами
 * TODO: доделать
 *
 * @author: DarkSkeleton
 */
public class ClanDaoService {

	private static final ClanDao clanDao = DaoManager.getInstance().getClanDao();

	private ClanDaoService() {
	}

	public List<Clan> getAllClans() {
		return clanDao.getAllClans();
	}

	public void CreateClan(Clan clan) {
		clanDao.CreateClan(clan);
	}

	public static ClanDaoService getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final ClanDaoService INSTANCE = new ClanDaoService();
	}
}