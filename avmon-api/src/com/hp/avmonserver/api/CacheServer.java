package com.hp.avmonserver.api;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;

public class CacheServer {

    private String configFile;
    private boolean disabled;
    
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    ICacheManager<IMemcachedCache> manager;

    IMemcachedCache cache = null;

    public synchronized boolean check(){
        if(disabled){
            return false;
        }
        
        if (cache == null) {
            start();
        }
        
        return true;
    }
    
    public Object get(String key) {
        
        if(!check()){
            return null;
        }

        if (cache != null) {
            return cache.get(key);
        }
        return null;
    }

    public boolean put(String key, Object value) {
        

        if(!check()){
            return false;
        }
        
        if (cache != null) {
            cache.put(key, value);
            return true;
        }
        return false;
    }

    public void stop() {
        if(!disabled){
            manager.stop();
        }
    }

    public void start() {
        if(!disabled){
            manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
            manager.setConfigFile(getConfigFile());
            manager.setResponseStatInterval(5 * 1000);
            manager.start();
            cache = manager.getCache("mclient0");
        }
    }

}
