package com.ind.sap.message.concrete;

import com.ind.sap.message.BasicMessage;

public class CimMessage extends BasicMessage {

	@Override
	public String getMessage(int roleId) {
		map.put(1, "cim message:1111");
		map.put(2, "cim message:2222");
		map.put(3, "cim message:3333");
		map.put(4, "cim message:4444");
		map.put(5, "cim message:5555");
		map.put(6, "cim message:6666");
		map.put(7, "cim message:7777");
		map.put(8, "cim message:8888");
		map.put(9, "cim message:9999");
		map.put(10, "cim message:101010");
		return map.get(roleId);
	}

}
