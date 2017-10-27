package com.ind.sap.message.concrete;

import com.ind.sap.message.BasicMessage;

public class DbMessage extends BasicMessage {

	@Override
	public String getMessage(int roleId) {
		map.put(1, "db message:1111");
		map.put(2, "db message:2222");
		map.put(3, "db message:3333");
		map.put(4, "db message:4444");
		map.put(5, "db message:5555");
		map.put(6, "db message:6666");
		map.put(7, "db message:7777");
		map.put(8, "db message:8888");
		map.put(9, "db message:9999");
		map.put(10, "db message:101010");

		return map.get(roleId);
	}

}
