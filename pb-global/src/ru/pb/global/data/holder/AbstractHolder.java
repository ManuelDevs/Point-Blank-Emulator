package ru.pb.global.data.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.utils.StringUtils;

/**
 * @author Felixx
 */
public abstract class AbstractHolder {
	protected Logger log = LoggerFactory.getLogger(getClass());

	public void log() {
		log.info(String.format("Loaded %d %s(s) count.", size(), StringUtils.afterSpaceToUpperCase(getClass().getSimpleName().replace("Holder", "")).toLowerCase()));
	}

	public void info(String s) {
		log.info(s);
	}

	public abstract int size();

	public abstract void clear();
}