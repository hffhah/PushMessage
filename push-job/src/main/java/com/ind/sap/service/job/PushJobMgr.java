package com.ind.sap.service.job;


public class PushJobMgr {
	public static String JOB_NAME = "JOB_PUSH";
	public static String TRIGGER_NAME = "TRIGGER_PUSH";
	public static String JOB_GROUP_NAME = "JOB_GROUP_PUSH";
	public static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_PUSH";

	/**
	 * 开启2秒一次任务调度
	 */
	public static void startJob() {
		try {
			System.out.println("【系统启动】开始(每1秒输出一次)...");
			QuartzManager.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, PushJob.class,
					"0/2 * * * * ?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止任务调度
	 */
	public static void stopJob() {
		try {
			QuartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void test() {
		try {
			System.out.println("【系统启动】开始(每1秒输出一次)...");
			QuartzManager.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, PushJob.class,
					"0/1 * * * * ?");

			Thread.sleep(5000);
			System.out.println("【修改时间】开始(每5秒输出一次)...");
			QuartzManager.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?");

			Thread.sleep(6000);
			System.out.println("【移除定时】开始...");
			QuartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
			System.out.println("【移除定时】成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
