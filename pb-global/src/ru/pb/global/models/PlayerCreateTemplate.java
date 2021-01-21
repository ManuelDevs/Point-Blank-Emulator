package ru.pb.global.models;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Felixx
 */
public class PlayerCreateTemplate {
	private CopyOnWriteArrayList<PlayerItem> items = new CopyOnWriteArrayList<PlayerItem>();

	public PlayerCreateTemplate(int money, int gp) {
		startgp = gp;
		startmoney = money;
	}

	private int startgp, startmoney;

	public PlayerEqipment getEqipment() {
		PlayerEqipment inv = new PlayerEqipment();
		for (PlayerItem item : items) {
			item.initConsumeLost();
			inv.addItem(item, item.isEquipped());
		}
		return inv;
	}

	public void addPlayerItem(PlayerItem item) {
		items.add(item);
	}

	public CopyOnWriteArrayList<PlayerItem> getItems() {
		return items;
	}
}
