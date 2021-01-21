package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_CLAN_LIST;
import ru.pb.global.models.Clan;
import ru.pb.global.service.ClanDaoService;

import java.util.List;

public class CM_1451 extends ClientPacket {

	private List<Clan> _clans = null;

	public CM_1451(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		_clans = ClanDaoService.getInstance().getAllClans();
	}

	@Override
	protected void runImpl() {
		//sendPacket(new SM_1452(_clans.size()));
		sendPacket(new SM_CLAN_LIST(_clans));
	}
}
