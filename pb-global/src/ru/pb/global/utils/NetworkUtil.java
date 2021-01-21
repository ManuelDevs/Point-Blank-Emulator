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

package ru.pb.global.utils;

import io.netty.buffer.ByteBuf;
import ru.pb.global.models.IPMask;
import ru.pb.global.models.IPNetwork;
import ru.pb.global.models.IPRange;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Felixx, sjke
 */
public class NetworkUtil {

	public static final int BUFFERED_STATE = 0;
	public static final int UNBUFFERED_STATE = 353;

	public static final int STRING_IP_SIZE = 15;

	private static final String NEWLINE = String.format("%n");
	private static final String[] BYTE2HEX = new String[256];
	private static final String[] HEXPADDING = new String[16];
	private static final String[] BYTEPADDING = new String[16];
	private static final char[] BYTE2CHAR = new char[256];

	private static final String IP_PATTERN = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";
	private static final String MAC_PATTERN = "%02X%s";

	public static final String IP_DEFAULT = "127.0.0.1";

	private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	static {
		int i;
		for (i = 0; i < 10; i++) {
			StringBuilder buf = new StringBuilder(3);
			buf.append(" 0");
			buf.append(i);
			BYTE2HEX[i] = buf.toString();
		}
		for (; i < 16; i++) {
			StringBuilder buf = new StringBuilder(3);
			buf.append(" 0");
			buf.append((char) ('a' + i - 10));
			BYTE2HEX[i] = buf.toString();
		}
		for (; i < BYTE2HEX.length; i++) {
			StringBuilder buf = new StringBuilder(3);
			buf.append(' ');
			buf.append(Integer.toHexString(i));
			BYTE2HEX[i] = buf.toString();
		}
		for (i = 0; i < HEXPADDING.length; i++) {
			int padding = HEXPADDING.length - i;
			StringBuilder buf = new StringBuilder(padding * 3);
			for (int j = 0; j < padding; j++) {
				buf.append("   ");
			}
			HEXPADDING[i] = buf.toString();
		}
		for (i = 0; i < BYTEPADDING.length; i++) {
			int padding = BYTEPADDING.length - i;
			StringBuilder buf = new StringBuilder(padding);
			for (int j = 0; j < padding; j++) {
				buf.append(' ');
			}
			BYTEPADDING[i] = buf.toString();
		}
		for (i = 0; i < BYTE2CHAR.length; i++) {
			if (i <= 0x1f || i >= 0x7f) {
				BYTE2CHAR[i] = '.';
			} else {
				BYTE2CHAR[i] = (char) i;
			}
		}
	}

	public static String printData(String eventName, ByteBuf buf) {
		int length = buf.readableBytes();
		int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
		StringBuilder dump = new StringBuilder(rows * 80 + eventName.length() + 16);

		dump.append(eventName).append(" [").append(length).append('B').append(']');
		dump.append(NEWLINE + "         +-------------------------------------------------+" +
				NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" +
				NEWLINE + "+--------+-------------------------------------------------+----------------+");

		final int startIndex = buf.readerIndex();
		final int endIndex = buf.writerIndex();

		int i;
		for (i = startIndex; i < endIndex; i++) {
			int relIdx = i - startIndex;
			int relIdxMod16 = relIdx & 15;
			if (relIdxMod16 == 0) {
				dump.append(NEWLINE);
				dump.append(Long.toHexString(relIdx & 0xFFFFFFFFL | 0x100000000L));
				dump.setCharAt(dump.length() - 9, '|');
				dump.append('|');
			}
			dump.append(BYTE2HEX[buf.getUnsignedByte(i)]);
			if (relIdxMod16 == 15) {
				dump.append(" |");
				for (int j = i - 15; j <= i; j++) {
					dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
				}
				dump.append('|');
			}
		}

		if ((i - startIndex & 15) != 0) {
			int remainder = length & 15;
			dump.append(HEXPADDING[remainder]);
			dump.append(" |");
			for (int j = i - remainder; j < i; j++) {
				dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
			}
			dump.append(BYTEPADDING[remainder]);
			dump.append('|');
		}

		dump.append(NEWLINE + "+--------+-------------------------------------------------+----------------+");

		return dump.toString();
	}

	public static byte[] parseIpToBytes(String ip) {
		InetAddress inetAddr = null;
		try {
			inetAddr = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inetAddr.getAddress();
	}

	public static String parseIp(String ip) {
		Matcher matcher = Pattern.compile(IP_PATTERN).matcher(ip);
		if (matcher.find()) {
			return matcher.group();
		}
		return IP_DEFAULT;
	}

	public static String parseMac(byte[] mac) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < mac.length; k++) {
			sb.append(String.format(MAC_PATTERN, mac[k], (k < mac.length - 1) ? ":" : ""));
		}
		return sb.toString();
	}

	public static String dumpHexString(byte[] array) {
		return dumpHexString(array, 0, array.length);
	}

	public static String dumpHexString(byte[] array, int offset, int length) {
		StringBuilder result = new StringBuilder();

		byte[] line = new byte[16];
		int lineIndex = 0;

		result.append("\n0x");
		result.append(toHexString(offset));

		for (int i = offset; i < offset + length; i++) {
			if (lineIndex == 16) {
				result.append(" ");

				for (int j = 0; j < 16; j++) {
					if (line[j] > ' ' && line[j] < '~') {
						result.append(new String(line, j, 1));
					} else {
						result.append(".");
					}
				}

				result.append("\n0x");
				result.append(toHexString(i));
				lineIndex = 0;
			}

			byte b = array[i];
			result.append(" ");
			result.append(HEX_DIGITS[(b >>> 4) & 0x0F]);
			result.append(HEX_DIGITS[b & 0x0F]);

			line[lineIndex++] = b;
		}

		if (lineIndex != 16) {
			int count = (16 - lineIndex) * 3;
			count++;
			for (int i = 0; i < count; i++) {
				result.append(" ");
			}

			for (int i = 0; i < lineIndex; i++) {
				if (line[i] > ' ' && line[i] < '~') {
					result.append(new String(line, i, 1));
				} else {
					result.append(".");
				}
			}
		}
		return result.toString();
	}

	public static String toHexString(byte b) {
		return toHexString(toByteArray(b));
	}

	public static String toHexString(byte[] array) {
		return toHexString(array, 0, array.length);
	}

	public static String toHexString(byte[] array, int offset, int length) {
		char[] buf = new char[length * 2];

		int bufIndex = 0;
		for (int i = offset; i < offset + length; i++) {
			byte b = array[i];
			buf[bufIndex++] = HEX_DIGITS[(b >>> 4) & 0x0F];
			buf[bufIndex++] = HEX_DIGITS[b & 0x0F];
		}
		return new String(buf);
	}

	public static String toHexString(int i) {
		return toHexString(toByteArray(i));
	}

	public static byte[] toByteArray(int i) {
		byte[] array = new byte[4];

		array[3] = (byte) (i & 0xFF);
		array[2] = (byte) ((i >> 8) & 0xFF);
		array[1] = (byte) ((i >> 16) & 0xFF);
		array[0] = (byte) ((i >> 24) & 0xFF);

		return array;
	}

	private static int toByte(char c) {
		if (c >= '0' && c <= '9') return (c - '0');
		if (c >= 'A' && c <= 'F') return (c - 'A' + 10);
		if (c >= 'a' && c <= 'f') return (c - 'a' + 10);

		throw new RuntimeException("Invalid hex char '" + c + "'");
	}

	public static byte[] hexStringToByteArray(String hexString) {
		String enc = hexString.replace(":", "").replace("-", "").replace(" ", "");
		int length = enc.length();
		byte[] buffer = new byte[length / 2];

		for (int i = 0; i < length; i += 2) {
			buffer[i / 2] = (byte) ((toByte(enc.charAt(i)) << 4) | toByte(enc.charAt(i + 1)));
		}
		return buffer;
	}

	/**
	 * Конвертировать адрес в низкоуровневый целочисленный формат
	 *
	 * @param addr адрес
	 * @return адрес в целочисленном формате
	 */
	public static long ipToLong(Inet4Address addr) {
		byte[] octets = addr.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	/**
	 * Проверить заданный адрес на нахождение в диапазоне
	 *
	 * @param ip адрес
	 * @return true если находится в диапазоне, иначе false
	 */
	public static boolean inRange(Inet4Address ip, long rawStartIp, long rawEndIp) {
		long testIp = ipToLong(ip);
		return rawStartIp <= testIp && rawEndIp >= testIp;
	}

	/**
	 * Конвертировать адрес из низкоуровнего в строку.
	 *
	 * @param ip адрес
	 * @return строка адреса.
	 */
	public static String longToIp(long ip) {
		String s = "";
		for (int i = 0; i < 4; i++) {
			if (!s.isEmpty())
				s = "." + s;
			s = (ip & 0xff) + s;
			ip = ip >> 8;
		}
		return s;
	}

	/**
	 * Конвертировать IPv4 адресс в целое число.
	 */
	public static int addrToInt(Inet4Address i4addr) {
		byte[] ba = i4addr.getAddress();
		return (ba[0] << 24)
				| ((ba[1] & 0xFF) << 16)
				| ((ba[2] & 0xFF) << 8)
				| (ba[3] & 0xFF);
	}

	/**
	 * Проверить заданный адрес на нахождение в диапазоне
	 *
	 * @param testAddr адрес
	 * @return true если находится в диапазоне, иначе false
	 */
	public static boolean matches(Inet4Address testAddr, int addrInt, int maskInt) {
		int testAddrInt = addrToInt(testAddr);
		return ((addrInt & maskInt) == (testAddrInt & maskInt));
	}

	/**
	 * Проверить заданный адрес на нахождение в диапазоне
	 *
	 * @param ip
	 * @return boolean
	 */
	public static boolean isInRange(String ip, IPNetwork ipNetwork) {
		Inet4Address ip4v = null;
		try {
			ip4v = (Inet4Address) InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			return false;
		}
		if (ipNetwork instanceof IPMask) {
			return matches(ip4v, ((IPMask) ipNetwork).getAddrInt(), ((IPMask) ipNetwork).getMaskInt());
		}
		long testIp = ipToLong(ip4v);
		return ((IPRange) ipNetwork).getRawStartIp() <= testIp && ((IPRange) ipNetwork).getRawEndIp() >= testIp;
	}

}