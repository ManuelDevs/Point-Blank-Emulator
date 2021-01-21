package ru.pb.global.data.loaders;

/**
 * @author Felixx
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.data.holder.AbstractHolder;

/**
 * @author Felixx
 */
public abstract class AbstractLoader<H extends AbstractHolder> {
	protected final Logger _log = LoggerFactory.getLogger(getClass());
	protected H holder;

	protected abstract void readData();

	protected abstract void load();

	protected H getHolder() {
		return holder;
	}

	public void reload() {
		info("reload start...");
		holder.clear();
		load();
	}

	public void info(String st, Exception e) {
		_log.info(st, e);
	}

	public void info(String st) {
		_log.info(st);
	}
}
