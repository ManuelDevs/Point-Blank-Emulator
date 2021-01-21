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

package ru.pb.global.info;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

/**
 * @author: Felixx
 * Date: 13.09.13
 * Time: 7:44
 */
public class MemoryInfo {
	public static double getMemoryUsagePercent() {
		MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		return 100.0F * (float) heapMemoryUsage.getUsed() / (float) heapMemoryUsage.getMax();
	}

	public static double getMemoryFreePercent() {
		return 100.0D - getMemoryUsagePercent();
	}

	public static String getMemoryMaxMb() {
		return getMemoryMax() / 1048576L + " mb";
	}

	public static String getMemoryUsedMb() {
		return getMemoryUsed() / 1048576L + " mb";
	}

	public static long getMemoryMax() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();
	}

	public static long getMemoryUsed() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
	}
}
