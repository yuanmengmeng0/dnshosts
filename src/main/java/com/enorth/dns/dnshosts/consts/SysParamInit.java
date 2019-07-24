package com.enorth.dns.dnshosts.consts;

import cn.com.enorth.utility.app.timer_task.enums.EnumHostRole;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 负责初始化系统参数的类
 * @author mu bingxin
 *
 */
public class SysParamInit {
	
	// 一个单例
	private static SysParamInit instance = null;

	
	// 为本地参数准备的，而且为了适用CachDao
	private Map<String, String> localParamMap = new HashMap<String, String>();
	
	private Set<EnumHostRole> localHostRole = new HashSet<EnumHostRole>();
	
	public void putLocalParam(String key, String value){
		this.localParamMap.put(key, value);
	}
	
	public Map<String, String> getLocalParamMap(){
		return this.localParamMap;
	}
	
	public void addHostRole(EnumHostRole role){
		this.localHostRole.add(role);
	}
	
	public Set<EnumHostRole> getHostRole(){
		return this.localHostRole;
	}
	
	private SysParamInit(){
	}

	
	/**
	 * 初始化
	 * @throws SQLException
	 */
	public void init() throws SQLException{

	}

	/**
	 * 返回一个单例
	 * @return
	 * @throws SQLException
	 */
	public static SysParamInit getInstance(){
		if (instance == null){
			instance = new SysParamInit();
		}
		return instance;
	}
	
}
