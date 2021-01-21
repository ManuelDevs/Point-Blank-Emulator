package ru.pb.global.data.holder;

import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.models.Item;
import ru.pb.global.models.PlayerItem;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Felixx
 */
public class ItemHolder extends AbstractHolder {
	public static ItemHolder getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final ItemHolder INSTANCE = new ItemHolder();
	}

	private static ConcurrentMap<Integer, Item> items = new ConcurrentSkipListMap<Integer, Item>();

	private ItemHolder() {
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public void clear() {
		items.clear();
	}

	public ConcurrentMap<Integer, Item> getAllItems() {
		return items;
	}

	/**
	 * @param loc
	 * @param itemId
	 * @param count
	 * @return
	 */
	public PlayerItem createItem(final ItemLocation loc, final int itemId, final int count) {
		Item template = getTemplate(itemId);
		if (template == null) {
			log.info("Item Template missing for Id: " + itemId); // TODO логирование в файл, из инвенторя (addItem(item) если итем нулл то читер.)
			return null;
		}
		return new PlayerItem(loc, getTemplate(itemId), count, true);
	}

	public Item getTemplate(final int id) {
		return items.get(id);
	}
}
