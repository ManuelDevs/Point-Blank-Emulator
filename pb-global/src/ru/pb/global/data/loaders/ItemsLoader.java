package ru.pb.global.data.loaders;

import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.ItemsDao;
import ru.pb.global.data.holder.ItemHolder;

public class ItemsLoader extends DatabaseLoader<ItemHolder> {

	public static ItemsLoader getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final ItemsLoader INSTANCE = new ItemsLoader();
	}

	private static final ItemsDao itemsDao = DaoManager.getInstance().getItemsDao();


	private ItemsLoader() {
		super(ItemHolder.getInstance());
	}

	@Override
	protected void readData() {
		getHolder().getAllItems().putAll(itemsDao.getAllItems());
	}
}
