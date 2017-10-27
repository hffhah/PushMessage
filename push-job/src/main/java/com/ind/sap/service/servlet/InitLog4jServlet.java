package com.ind.sap.service.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class InitLog4jServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitLog4jServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("初始化InitLog4jServlet");
		String log4jLocation = config.getInitParameter("log4j-properties-location");
		ServletContext sc = config.getServletContext();
		if (log4jLocation == null) {
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File yoMamaYesThisSaysYoMama = new File(log4jProp);
			if (yoMamaYesThisSaysYoMama.exists()) {
				PropertyConfigurator.configure(log4jProp);
				System.out.println("初始化InitLog4jServlet成功！");
			} else {
				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}
}
