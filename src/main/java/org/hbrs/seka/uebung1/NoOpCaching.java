package org.hbrs.seka.uebung1;

import java.util.List;

public class NoOpCaching<V> implements Caching<V> {

	@Override
	public void cacheResult(String key, List<V> value) {
	}

	@Override
	public List<V> getCachedResult(String key) {
		return List.of();
	}

	@Override
	public boolean isCached(String key) {
		return false;
	}
}
