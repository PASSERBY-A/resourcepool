package com.hp.xo.uip.cmdb.model;

import java.util.Collection;

import com.hp.xo.uip.cmdb.domain.ConfigItem;


public interface CiStore extends Iterable<ConfigItem> {
	/**
	 * 根据CIID获取ConfigItem
	 * @param ciid
	 * @return ConfigItem对象
	 */
	ConfigItem getCi(long id);
//	/**
//	 * 注册CiActionListener
//	 * @param listener
//	 */
//	void addActionListener(CiActionListener listener);
//	/**
//	 * 注销CiActionListener
//	 */
//	void removeActionListener(CiActionListener listener);
	/**
	 * 开始CI变更
	 * @return
	 */
	CiStoreTx beginTx();
	/**
	 * 根据外部cmdb ciid，获取ConfigItem
	 * @param ocId 外部cmdb ciid
	 * @return ConfigItem对象
	 */
	ConfigItem getCiByOneCmdbId(String ocId);
	
	/**
	 * 根据CiActions，更新CiStore，并通知注册的listener
	 * @param actions
	 */
//	void update(Collection<CiAction> actions);
	
	/**
	 * 重新加载Ci，并通知注册的listener
	 */
	void reload();
	
	void reload(String xml);
}
