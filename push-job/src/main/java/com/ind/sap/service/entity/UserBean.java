package com.ind.sap.service.entity;

import com.ind.sap.service.utils.OverrideEquiles;

//序列化用于比较对象
public class UserBean extends OverrideEquiles {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String psd;
	private String deviceId;
	private int roleId; // from db
	private int privilege; // from db

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		return super.equals(obj);
	}
	
	

}
