package com.example.demo.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author i565244
 */
@Service
@Slf4j
public class CacheService {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    CacheKeyCenerator keyCenerator;

    public void batchDeleteCache(String cacheName, List<String> cacheKeys) {
        Cache cache = getCache(cacheName);
        cacheKeys.forEach(key -> deleteCache(cache, key));

    }

    public void deleteCache(Cache deleteCache, String subCacheKey) {
        if (Objects.nonNull(deleteCache)) {
            String tenantCacheKey = keyCenerator.cacheKey(subCacheKey);
            log.debug("Delete cache: [{}] with key: [{}].", subCacheKey, tenantCacheKey);
            deleteCache.evict(tenantCacheKey);
        }
    }

    public Cache getCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }
}
