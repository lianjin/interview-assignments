package com.example.tinyurl.storage;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jinlian
 */
@Repository
public class TinyUrlStorage {

    public final static int DEFAULT_MAX_CACHE_SIZE = 1024 * 1024;
    public final static int DEFAULT_INIT_SIZE = 1024;
    private int maxSize = DEFAULT_MAX_CACHE_SIZE;

    private Map<Integer, String> longHash2Tiny = new HashMap<>();
    private LinkedHashMap<String, String> tiny2Long = new LinkedHashMap<String, String>(DEFAULT_INIT_SIZE) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            if (size() > getMaxSize()) {
                longHash2Tiny.remove(hash(eldest.getValue()));
                return true;
            }
            return false;
        }

        @Override
        public String put(String key, String value) {
            longHash2Tiny.put(hash(value), key);
            return super.put(key, value);
        }
    };

    int getMaxSize() {
        return maxSize;
    }

    void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getLongByTiny(String tinyCode) {
        return tiny2Long.get(tinyCode);
    }

    public String getTinyByLong(String longUrl) {
        return longHash2Tiny.get(hash(longUrl));
    }

    public void add(String tinyUrl, String longUrl) {
        tiny2Long.put(tinyUrl, longUrl);
    }

    private int hash(String fullUrl) {
        return fullUrl.hashCode() ^ (fullUrl.hashCode() >>> 16);
    }
}
