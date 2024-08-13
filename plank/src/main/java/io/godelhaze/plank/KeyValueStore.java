package io.godelhaze.plank;

public interface KeyValueStore {
    void put(String key, String value);

    String get(String key);

    void remove(String key);

    void clear();
}
