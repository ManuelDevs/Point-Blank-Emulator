package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BATTLE_ERRORMESSAGE;
import ru.pb.game.network.client.packets.server.SM_BATTLE_LEAVE;
import ru.pb.global.enums.BattleErrorMessage;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * Пакет сетевых неполадок(приходит от клиента при возникновении неполадок между хостом и игроком)
 *
 * @author DarkSKeleton
 */
public class CM_BATTLE_NETWORK_PROBLEM extends ClientPacket {
	public CM_BATTLE_NETWORK_PROBLEM(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
	}

	@Override
	protected void runImpl() {
		Player player = getConnection().getPlayer();
		Room room = getConnection().getRoom();
		RoomSlot slot = room.getRoomSlotByPlayer(player);
		slot.setState(SlotState.SLOT_STATE_NORMAL);
		for(Player player1 : room.getPlayers().values())
		{
			RoomSlot slot1 = room.getRoomSlotByPlayer(player1);
			if(slot1.getState().ordinal() == 13)
				player1.getConnection().sendPacket(new SM_BATTLE_LEAVE(slot.getId()));
		}
		getConnection().sendPacket(new SM_BATTLE_ERRORMESSAGE(BattleErrorMessage.EVENT_ERROR_EVENT_BATTLE_TIMEOUT_CS));
		log.info("Player " + player.getName() + " connection problem... Return room...");

	}
}
