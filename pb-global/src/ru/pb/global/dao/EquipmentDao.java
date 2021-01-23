package ru.pb.global.dao;

import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;

public interface EquipmentDao {
	public void recordEquipped(Player player);

	public void recordItems(Player player);

	public boolean containsItem(Player player, PlayerItem item);
	
	public void registerItem(Player player, PlayerItem item);

}
