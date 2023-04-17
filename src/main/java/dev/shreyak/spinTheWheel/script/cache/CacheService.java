package dev.shreyak.spinTheWheel.script.cache;

import java.util.concurrent.TimeUnit;

public interface CacheService {
    void put(String key, Object value, long timeout, TimeUnit timeUnit);
    Object get(String key);
    void delete(String key);
}
