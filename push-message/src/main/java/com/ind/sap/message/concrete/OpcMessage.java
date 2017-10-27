package com.ind.sap.message.concrete;

import com.ind.sap.message.BasicMessage;

public class OpcMessage extends BasicMessage {

	@Override
	public String getMessage(int roleId) {
		map.put(1, "opc message:1111");
		map.put(2, "opc message:2222");
		map.put(3, "opc message:3333");
		map.put(4, "opc message:4444");
		map.put(5, "opc message:5555");
		map.put(6, "opc message:6666");
		map.put(7, "opc message:7777");
		map.put(8, "opc message:8888");
		map.put(9, "opc message:9999");
		map.put(10, "opc message:101010");

		return map.get(roleId);
	}

}
