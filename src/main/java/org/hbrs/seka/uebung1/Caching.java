package org.hbrs.seka.uebung1;

import java.util.List;

public interface Caching<V> {
	void cacheResult(String key, List<V> value);

	List<V> getCachedResult(String key);

	boolean isCached(String key);
}
