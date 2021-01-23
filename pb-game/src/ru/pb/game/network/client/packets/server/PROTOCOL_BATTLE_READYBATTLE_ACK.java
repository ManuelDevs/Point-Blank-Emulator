package ru.pb.game.network.client.packets.server;

import java.util.List;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

public class PROTOCOL_BATTLE_READYBATTLE_ACK extends ServerPacket {

	private Room room;
	private List<RoomSlot> startingSlot;
	
	public PROTOCOL_BATTLE_READYBATTLE_ACK(Room room, List<RoomSlot> startingSlot) {
		super(3426);
		
		this.room = room;
		this.startingSlot = startingSlot;		
	}
	
	@Override
	public void writeImpl() {
		writeH(room.getMapId());
		writeC(room.getStage4v4());
		writeC(room.getType());
		writeC(startingSlot.size());
		
		for(RoomSlot slot : startingSlot)
		{
			writeC(slot.getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_RED).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_BLUE).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_HEAD).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_ITEM).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_DINO).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.PRIM).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.SUB).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.MELEE).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.THROWING).getItem().getId());
			writeD(slot.getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.ITEM).getItem().getId());
			writeD(0);
			writeC(0); // TODO: Add Title 1
			writeC(0); // TODO: Add Title 2
			writeC(0); // TODO: Add Title 3
			writeD(0); // TODO: Add check if version is 1.15.42
		}
	}

}
