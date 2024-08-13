package io.godelhaze.plank;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class PlankCacheManager implements CacheManager {
    private Plank client;

    public PlankCacheManager(Plank client) {
        this.client = client;
    }

    @Override
    public Cache getCache(String name) {
        return new PlankCache(name, client);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.singleton("plank");
    }
}
