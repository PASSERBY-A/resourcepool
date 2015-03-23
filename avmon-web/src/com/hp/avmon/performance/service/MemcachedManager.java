package com.hp.avmon.performance.service;

import java.util.Date;

import com.alisoft.xplatform.asf.cache.memcached.client.MemCachedClient;
import com.alisoft.xplatform.asf.cache.memcached.client.SockIOPool;

public class MemcachedManager {

	 private static MemCachedClient mcc = new MemCachedClient();
	    
	    static {
	       // 创建服务器列表及其权重
	       String[] servers = {"127.0.0.1:11211"};
	       Integer[] weights = {3};
	       
	       // 创建Socket连接池对象
	       SockIOPool pool = SockIOPool.getInstance();
	       
	       // 设置服务器信息
	       pool.setServers(servers);
	       pool.setWeights(weights);
	       pool.setFailover(true);
	       
	       // 设置初始连接数、最小和最大连接数以及最大处理时间
	       pool.setInitConn(5);
	       pool.setMinConn(5);
	       pool.setMaxConn(250);
	       pool.setMaxIdle(1000*60*60*6);
	       
	       // 设置主线程睡眠时间
	       pool.setMaintSleep(30);
	       
	       // 设置TCP参数、连接超时等
	       pool.setNagle(false);
	       pool.setSocketTO(3000);
	       pool.setSocketConnectTO(0);
	       pool.setAliveCheck(true);
	       
	       // 初始化连接池
	       pool.initialize();
	       
	       // 压缩设置，超过指定大小（单位为K）的数据都会被压缩
	       mcc.setCompressEnable(true);
	       mcc.setCompressThreshold(64 * 1024);
	    }
	    
	    /**
	     * 无参构造
	     */
	    protected MemcachedManager (){
	       
	    }
	    
	    // 受保护的对象
	    protected static MemcachedManager instance = new MemcachedManager();
	    
	    /**
	     * 为受保护的对象提供一个公共的访问方法
	     */
	    public static MemcachedManager getInstance () {
	       return instance;
	    }
	    
	    /**
	     * 添加对象到缓存中，构成方法重载
	     * @param key
	     * @param value
	     * @return
	     */
	    public boolean add(String key,Object value) {
	       return mcc.add(key, value);
	    }
	    public boolean add (String key,Object value,Date expiry) {
	       return mcc.add(key, value,expiry);
	    }
	    
	    public boolean replace (String key,Object value) {
	       return mcc.replace(key, value);
	    }
	    
	    public boolean replace (String key,Object value,Date expiry)
	    {
	       return mcc.replace(key, value, expiry);
	    }
	    
	    /**
	     * 根据指定的关键字获取对象
	     */
	    public Object get(String key) {
	       return mcc.get(key);
	    }

	
}
