/*
 * Java Server Emulator Project Blackout / PointBlank
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.global.properties;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ð—Ð°Ð³Ñ€ÑƒÐ·Ñ‡Ð¸Ðº Ð½Ð°Ñ�Ñ‚Ñ€Ð¾ÐµÐº
 * 
 * @author sjke
 */
public abstract class PropertiesLoader {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static final Properties PROPERTIES = new Properties();

	protected PropertiesLoader(String fname) {
		try {
			PROPERTIES.load(this.getClass().getClassLoader().getResource(fname).openStream());
			log.info("Loaded properties from file '" + fname + "'");
		} catch(Exception e) {
			log.error("Error load file: [" + fname + "] Application shutdown!", e);
			System.exit(1);
		}
	}

	public Boolean loadBoolean(String key) {
		Boolean value = Boolean.valueOf(PROPERTIES.getProperty(key));
		return value;
	}

	public Integer loadInteger(String key) {
		Integer value = Integer.valueOf(PROPERTIES.getProperty(key));
		return value;
	}

	public String loadString(String key) {
		String value = PROPERTIES.getProperty(key);
		return value;
	}
}