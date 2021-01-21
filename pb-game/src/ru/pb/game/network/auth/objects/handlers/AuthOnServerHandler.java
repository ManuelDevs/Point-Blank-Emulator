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

package ru.pb.game.network.auth.objects.handlers;

import org.omg.CORBA.ObjectHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.game.network.auth.AuthConnection;
import ru.pb.global.network.objects.Handler;
import ru.pb.global.network.objects.GameAuth.AuthOnServer;
import ru.pb.global.network.objects.GameAuth.RequestGameServerList;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

public class AuthOnServerHandler implements Handler<AuthOnServer, AuthConnection> {

	private static final Logger log = LoggerFactory.getLogger(ObjectHolder.class);

	@Override
	public void call(AuthOnServer obj, final AuthConnection connection) {
		switch (obj.getResponse()) {
			case NOT_FOUND:
				log.error("GameServer is not authenticated at AuthClient side, shutting down!");
				break;
			case INCORRECT:
				log.info("GameServer failed authorization at AuthClient side, shutting down!");
				break;
			case ALREADY_REGISTERED:
				log.info("GameServer is already registered at AuthClient side, shutting down!");
				break;
			case AUTHED:
				log.info("GameServer successfully registered at AuthClient side!");
				ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						connection.sendObject(new RequestGameServerList());
					}
				}, 0, 30);
				break;
			default:
				log.info("GameServer failed authorization at AuthClient side, unknow response: " + obj.getResponse().name());
		}
	}
}
