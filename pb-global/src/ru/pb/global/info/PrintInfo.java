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
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.global.info;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Felixx, sjke
 */
public class PrintInfo {

	private static final PrintInfo instance = new PrintInfo();
	private static final Logger log = LoggerFactory.getLogger(PrintInfo.class);
	private static final long startTime = System.currentTimeMillis();

	private PrintInfo() {
	}

	public static PrintInfo getInstance() {
		return instance;
	}

	public void printSection(String s) {
		s = "-[ " + s + " ]";
		while(s.length() < 79) {
			s = "=" + s;
		}
		log.info(s);
	}

	private void print(String g, Object... a) {
		log.info(String.format(g, a));
	}

	public void printLoadInfos() {
		long seconds = (System.currentTimeMillis() - startTime) / 1000;
		print("Loaded in %d sec(%.3f min)", seconds, (seconds / 60F));
		print("Used %s, max %s.", MemoryInfo.getMemoryUsedMb(), MemoryInfo.getMemoryMaxMb());
		print("Used %.2f%% percent memory.", MemoryInfo.getMemoryUsagePercent());
	}

	public void printLibInfo() {
		for(File f : new File("./libs/").listFiles()) {
			if(f.getName().endsWith(".jar")) {
				String version = null;
				String revision = null;
				try {
					JarInputStream jar = new JarInputStream(new FileInputStream(f));
					Manifest man = jar.getManifest();

					if(man != null) {
						version = man.getMainAttributes().getValue("Implementation-Version");
						revision = man.getMainAttributes().getValue("Implementation-Revision");

						if(version == null || revision == null) {
							if(man.getEntries() != null) {
								for(Attributes a : man.getEntries().values()) {
									if(version == null) {
										version = a.getValue("Implementation-Version");
									}
									if(revision == null) {
										revision = a.getValue("Implementation-Revision");
									}
								}
							}
						}
					}
				} catch(IOException e) {
				}

				if(version != null) {
					print("version (" + f.getName() + "): " + version);
				} else if(revision != null) {
					print("revision (" + f.getName() + "): " + revision);
				} else {
					print("version (" + f.getName() + "): none");
				}
			}
		}
		printSection(null);
	}
}