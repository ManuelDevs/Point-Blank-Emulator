package ru.pb.global.service;

import ru.pb.global.dao.AdminProfileDao;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.models.AdminProfile;

import java.util.List;

/**
 * Автор: Grizly
 * Date: 19.12.13
 * Time: 1:18
 */
public class AdminProfileDaoService {
	private static final AdminProfileDao adminprofileDao = DaoManager.getInstance().getAdminProfileDao();
	private static final AdminProfileDaoService INSTANCE = new AdminProfileDaoService();

	public List<AdminProfile> getAll() {
		return adminprofileDao.getAll();
	}

	public static AdminProfileDaoService getInstance() {
		return INSTANCE;
	}
}
