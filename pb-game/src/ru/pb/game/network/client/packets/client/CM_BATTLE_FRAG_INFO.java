/*
 * Java Server Emulator Project Blackout / PointBlank
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BATTLE_FRAG_INFO;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.*;

/**
 * @author: Felixx
 * Date: 02.10.13
 * Time: 6:45
 */
public class CM_BATTLE_FRAG_INFO extends ClientPacket {

	private FragInfos fragInfos = new FragInfos();

	public CM_BATTLE_FRAG_INFO(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		fragInfos.setVicTimIdx(readC()); // WTF? killsCount - 1, если не убиваеш себя... если и себя то 0 будет если пострадал 1 бот и сам игрок.
		fragInfos.setKillsCount(readC()); // Всего померло.
		fragInfos.setKiller(readC()); // Слот убившего.
		fragInfos.setKillWeapon(readD()); // Орудие убийства.
		fragInfos.setUnkBytes(readB(13)); // Хз что там

		for (int i = 1; i <= fragInfos.getKillsCount(); i++) {
			Frag frag = new Frag();
			frag.setUnkC1(readC()); //
			frag.setDeathMask(readC()); //
			frag.setUnkC3(readC()); //
			frag.setUnkC4(readC()); //
			frag.setUnk13bytes(readB(13));

			fragInfos.addFrag(i, frag);
		}
	}

	@Override
	public void runImpl() {
		Room room = getConnection().getRoom();
		Player player = getConnection().getPlayer();
		if (room != null && player != null) {
			RoomSlot killer = room.getRoomSlot(fragInfos.getKiller());
			for (int i = 1; i <= fragInfos.getKillsCount(); i++) {
				Frag frag = fragInfos.getFrag(i);
				if (frag != null) {
					boolean suicide = frag.getDeatSlot() == fragInfos.getKiller();
					RoomSlot death = room.getRoomSlot(frag.getDeatSlot());
					if (!suicide) {
						killer.setAllKills(killer.getAllKills() + 1);
						killer.setOneTimeKills(killer.getOneTimeKills() + 1);

						int weaponHeadNum = fragInfos.getWeaponHeadNum();
						killer.setKillMessage(0);
						if (fragInfos.getKillsCount() > 1) {
							if ((weaponHeadNum == 8030) || (weaponHeadNum == 9030)) {
								killer.setKillMessage(2);
							} else {
								killer.setKillMessage(1);
							}
						} else {
							int killMessage = 0;
							if (frag.getDeathMask() >> 4 == 3) { //TODO:: понять что это и всунуть в Frag!
								killMessage = 4;
							} else if ((frag.getDeathMask() >> 4 == 1) && (frag.getDeathMask() >> 2 == 1) && (weaponHeadNum == 7020)) {
								killMessage = 6;
							}

							if (killMessage > 0) {
								int lastMessage = killer.lastKillState >> 12;

								if (killMessage == 4) {
									if (lastMessage != 4) {
										killer.repeatLastState = false;
										killer.setOneTimeKills(0);
									}

									killer.setOneTimeKills(killer.getOneTimeKills() + 1);
									killer.lastKillState = killMessage << 12 | killer.getOneTimeKills();

									int countedKill = killer.lastKillState & 0xF;

									if (killer.repeatLastState) {
										if (countedKill > 1)
											killer.setKillMessage(5);
										else
											killer.setKillMessage(4);
									} else {
										killer.setKillMessage(4);
										killer.repeatLastState = true;
									}
								} else if (killMessage == 6) {
									if (lastMessage != 6) {
										killer.repeatLastState = false;
										killer.setOneTimeKills(0);
									}

									killer.setOneTimeKills(killer.getOneTimeKills() + 1);
									killer.lastKillState = killMessage << 12 | killer.getOneTimeKills();

									int countedKill = killer.lastKillState & 0xF;

									if (killer.repeatLastState) {
										if (countedKill > 1)
											killer.setKillMessage(6);
									} else
										killer.repeatLastState = true;
								} else if (killMessage == 0) {
									//TODO: Што это ваще ? Накой?
									//if (killer.getOneTimeKills() == 1)
									//killer.setKillMessage(3);
									//else if (killer.getOneTimeKills() == 2)
									//killer.setKillMessage(3);
								}
							} else {
								killer.lastKillState = 0;
								killer.repeatLastState = false;
							}
						}

						//Добавляем общий счет команде
						if (frag.getDeatSlot() % 2 == 0) {    // Если помер красный
							room.setRedDeaths(room.getRedDeaths() + 1);
							room.setBlueKills(room.getBlueKills() + 1);
						} else {
							room.setBlueDeaths(room.getBlueDeaths() + 1);
							room.setRedKills(room.getRedKills() + 1);
						}
						death.setOneTimeKills(0);
						death.setKillMessage(0);
						death.setLastKillMessage(0);
						death.lastKillState = 0;
					}
					death.setAllDeahts(death.getAllDeath() + 1);
					//Добавляем очки в бою с ботами
					if (room.getSpecial() == 6) {
						RoomSlot slot = room.getRoomSlot(fragInfos.getKiller());
						int AILevel = room.getRoomSlotByPlayer(room.getLeader()).getId() % 2 == 0 ? room.getAiLevel() + room.getBlueDeaths() / 20 : room.getAiLevel() + room.getRedDeaths() / 20;
						int AIScore = 10 + room.getRoomSlot(fragInfos.getKiller()).getOneTimeKills() * AILevel;
						slot.setBotScore(slot.getBotScore() + AIScore);
					}
				}
			}

			for (Player member : room.getPlayers().values()) {
				if (SlotState.SLOT_STATE_BATTLE == room.getRoomSlotByPlayer(member).getState()) {
					member.getConnection().sendPacket(new SM_BATTLE_FRAG_INFO(room, fragInfos));
				}
			}
		}
	}
}