package io.godelhaze.plank;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;


public class PlankCache implements Cache {
    private String name;
    private Plank client;

    public PlankCache(String name, Plank client) {
        this.name = name;
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public Object getNativeCache() {
        return client;
    }

    @Override
    public ValueWrapper get(Object key) {
        String value = client.get(key.toString());
        return (value != null ? new SimpleValueWrapper(value) : null);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        String value = client.get(key.toString());
        return (value != null ? type.cast(value) : null);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public CompletableFuture<?> retrieve(Object key) {
        return Cache.super.retrieve(key);
    }

    @Override
    public <T> CompletableFuture<T> retrieve(Object key, Supplier<CompletableFuture<T>> valueLoader) {
        return Cache.super.retrieve(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        client.put(key.toString(), value.toString());
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return Cache.super.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        client.remove(key.toString());
    }

    @Override
    public boolean evictIfPresent(Object key) {
        return Cache.super.evictIfPresent(key);
    }

    @Override
    public void clear() {
        client.clear();
    }

    @Override
    public boolean invalidate() {
        return Cache.super.invalidate();
    }
}
