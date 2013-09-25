package cn.sccl.tasksupervisor.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SchedulerService {
	
	// 创建一个触发器,定时启动,每隔n秒启动一次，定时停止
	public void addTrigger(String triggerName, long n, String groupName,
			String jobName, Date startTime, Date endTime);

	public void addJob(String jobName, String groupName, String groovyFileName,
			Map<String, Object> paras);

	public void addTrigger(String triggerName, String cronExpression,
			String groupName, String jobName);

	public List<Map<String, Object>> getQrtzTriggers();

	public List<Map<String, Object>> getQrtzJobs();

	public void pauseTrigger(String triggerName, String groupName);

	public void pauseJob(String jobName, String groupName);

	public void resumeTrigger(String triggerName, String groupName);

	public void resumeJob(String jobName, String groupName);

	public boolean removeTrigger(String triggerName, String groupName);

	public boolean removeJob(String jobName, String groupName);

}
