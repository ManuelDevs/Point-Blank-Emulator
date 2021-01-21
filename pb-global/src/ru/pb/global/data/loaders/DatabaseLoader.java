package ru.pb.global.data.loaders;

import ru.pb.global.data.holder.AbstractHolder;

/**
 * @author Felixx
 */
public abstract class DatabaseLoader<H extends AbstractHolder> extends AbstractLoader<H> {

	protected DatabaseLoader(H hold) {
		holder = hold;
		load();
	}

	@Override
	protected final void load() {
		readData();
		if (holder != null) {
			holder.log();
		}
	}
}
