package fr.latlon.service.memcached;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import org.springframework.stereotype.Service;

/**
 * Wrapper over the real @link{MemcacheService}
 *
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Service
public class SyncMemcachedService {

    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

    public MemcacheService getMemcache() {
        return this.syncCache;
    }

}
