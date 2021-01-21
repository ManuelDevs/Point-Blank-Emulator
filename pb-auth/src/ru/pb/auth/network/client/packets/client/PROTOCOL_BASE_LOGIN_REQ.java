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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.auth.network.client.packets.client;

import ru.pb.auth.controller.AccountController;
import ru.pb.auth.network.client.packets.ClientPacket;
import ru.pb.auth.network.client.packets.server.PROTOCOL_BASE_LOGIN_ACK;
import ru.pb.global.enums.LoginAccess;
import ru.pb.global.enums.State;
import ru.pb.global.utils.NetworkUtil;

public class PROTOCOL_BASE_LOGIN_REQ extends ClientPacket {

	private String login, password, version, localIp;
	private int loginLength, passwordLength;

	public PROTOCOL_BASE_LOGIN_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		version = readC() + "." + readH() + "." + readH();
		readC();
		loginLength = readC();
		passwordLength = readC();
		login = readS(loginLength).trim();
		password = readS(passwordLength).trim();
		getConnection().setMac(NetworkUtil.parseMac(readB(6)));
		readH();
		
		readC();
		String localIp = readC() + "." + readC() + "." + readC() + "." + readC();
	}

	@Override
	public void runImpl() {
		State state = getConnection().getAccount() == null ? AccountController.getInstance().accountLogin(login, password, getConnection(), version) : State.AUTHED;
		switch (state) {
			case AUTHED: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_SUCCESS, getConnection().getAccount()));
				break;
			}
			case INVALID: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_LOGIN, getConnection().getAccount()));
				break;
			}
			case BLOCKED: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_EVENT_LOG_IN_BLOCK_ACCOUNT, getConnection().getAccount()));
				break;
			}
			case IP_BLOCKED: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_EVENT_LOG_IN_TIME_OUT2, getConnection().getAccount()));
				break;
			}
			case ERROR_AUTH: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT, getConnection().getAccount()));
				break;
			}
			case ALREADY_EXIST: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_EVENT_LOG_IN_ALEADY_LOGIN, getConnection().getAccount()));
				break;
			}
			case REGION_BLOCKED: {
				sendPacket(new PROTOCOL_BASE_LOGIN_ACK(LoginAccess.EVENT_ERROR_EVENT_LOG_IN_REGION_BLOCKED, getConnection().getAccount()));
				break;
			}
			default: {
			}
		}
	}
}
