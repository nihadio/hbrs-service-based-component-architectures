package org.hbrs.seka.uebung1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapCaching<V> implements Caching<V> {
	private final Map<String, List<V>> cache = new HashMap<>();

	@Override
	public void cacheResult(String key, List<V> value) {
		cache.put(key, value);
	}

	@Override
	public List<V> getCachedResult(String key) {
		return cache.get(key);
	}

	@Override
	public boolean isCached(String key) {
		return cache.containsKey(key);
	}
}
