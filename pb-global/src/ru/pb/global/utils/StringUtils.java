package ru.pb.global.utils;

/**
 * @author Felixx
 */
public class StringUtils {
	/**
	 * Ставит первую букву в верхний регистр
	 *
	 * @param st
	 * @return
	 */
	public static String firstToUpperCase(String st) {
		char[] chars = st.toCharArray();
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];

			if (Character.isUpperCase(ch)) {
				buf.append(" ");
			}

			if (i == 0) {
				ch = Character.toUpperCase(ch);
			}

			buf.append(ch);
		}

		return buf.toString();
	}

	public static String afterSpaceToUpperCase(String st) {
		char[] chars = st.toCharArray();
		StringBuffer buf = new StringBuffer();

		for (char ch : chars) {
			if (Character.isUpperCase(ch)) {
				buf.append(" ");
			}

			buf.append(Character.toLowerCase(ch));
		}

		return buf.toString();
	}

	public static String first(String st) {
		char[] chars = st.toCharArray();
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];

			if (i == 0) {
				ch = Character.toUpperCase(ch);
			}

			buf.append(ch);
		}

		return buf.toString();
	}
}
