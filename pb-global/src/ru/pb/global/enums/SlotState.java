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

package ru.pb.global.enums;

/**
 * Состояние слотов
 *
 * @author sjke
 */
public enum SlotState {
	SLOT_STATE_EMPTY,
	SLOT_STATE_CLOSE,
	SLOT_STATE_SHOP,
	SLOT_STATE_INFO,
	SLOT_STATE_CLAN,
	SLOT_STATE_INVENTORY,
	SLOT_STATE_OUTPOST,
	SLOT_STATE_NORMAL,
	SLOT_STATE_READY,
	SLOT_STATE_LOAD,
	SLOT_STATE_RENDEZVOUS,
	SLOT_STATE_PRESTART,
	SLOT_STATE_BATTLE_READY,
	SLOT_STATE_BATTLE;
}