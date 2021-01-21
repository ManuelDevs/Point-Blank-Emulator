package ru.pb.global.dao;

import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;

/**
 * Автор: Grizly
 * Date: 19.01.14
 * Time: 23:32
 */

public interface EquipmentDao {
	public void recordEquipped(Player player);

	public void recordItems(Player player);

	public boolean containsItem(Player player, PlayerItem item);

}
