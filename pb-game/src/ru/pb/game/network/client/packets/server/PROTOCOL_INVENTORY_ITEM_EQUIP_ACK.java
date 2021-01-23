package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.utils.DateTimeUtil;

public class PROTOCOL_INVENTORY_ITEM_EQUIP_ACK extends ServerPacket {

	private int error;
	private PlayerItem item;
	
	public PROTOCOL_INVENTORY_ITEM_EQUIP_ACK(int error, PlayerItem item) {
		super(535);
		
		this.error = error;
		this.item = item;
	}

	@Override
	public void writeImpl() {
		if(error != 1 && item == null)
			error = 0x80000000;
		
		writeD(error);
		if(error != 1)
			return;
		
		writeD(DateTimeUtil.getDateTime());
		writeQ(item.getId());
		writeD(item.getItem().getId());
		writeC(item.getConsumeLost());
		writeD(item.getCount());
	}

}
