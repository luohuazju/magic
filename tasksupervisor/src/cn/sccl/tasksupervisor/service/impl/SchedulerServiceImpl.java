package cn.sccl.tasksupervisor.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import cn.sccl.tasksupervisor.commons.utils.StringUtil;
import cn.sccl.tasksupervisor.dao.QuartzDAO;
import cn.sccl.tasksupervisor.service.SchedulerService;

@Service("schedulerService")
public class SchedulerServiceImpl extends ApplicationObjectSupport implements
		SchedulerService, Serializable {

	private static final long serialVersionUID = -9116728244052512966L;

	public final Logger log = LoggerFactory
			.getLogger(SchedulerServiceImpl.class);

	private Scheduler scheduler;

	private QuartzDAO quartzDAO;

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Autowired
	public void setQuartzDAO(@Qualifier("quartzDAO") QuartzDAO quartzDAO) {
		this.quartzDAO = quartzDAO;
	}

	// 新增job
	public void addJob(String jobName, String groupName, String groovyFileName,
			Map<String, Object> paras) {
		jobName = StringUtil.getUUIDName(jobName);
		String jobClassName = "cn.sccl.tasksupervisor.job.impl.GroovyJobImpl";
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		if (StringUtil.isBlank(jobClassName)) {
			log.error("jobClassName can't be null!");
			throw new RuntimeException("jobClassName can't be null!");
		}
		if (StringUtil.isBlank(groovyFileName)) {
			log.error("groovyFileName can't be null!");
			throw new RuntimeException("groovyFileName can't be null!");
		}
		Job jobClass = null;
		try {
			jobClass = (Job) Class.forName(jobClassName).newInstance();
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
		JobDetail jobDetail = null;
		jobDetail = new JobDetail(jobName, groupName, jobClass.getClass());
		JobDataMap jobDataMap = new JobDataMap();
		if (paras != null && paras.isEmpty()) {
			jobDataMap.putAll(paras);
		}
		jobDataMap.put("GROOVY_FILE_NAME", groovyFileName);
		jobDetail.setJobDataMap(jobDataMap);
		try {
			scheduler.addJob(jobDetail, true);
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	// 新增触发器
	public void addTrigger(String triggerName, String cronExpression,
			String groupName, String jobName) {
		CronExpression cron = null;
		try {
			cron = new CronExpression(cronExpression);
		} catch (ParseException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
		addTrigger(triggerName, cron, groupName, jobName);
	}

	// 触发器真实新增方法
	private void addTrigger(String triggerName, CronExpression cronExpression,
			String groupName, String jobName) {
		triggerName = StringUtil.getUUIDName(triggerName);
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		if (StringUtil.isBlank(jobName)) {
			log.error("jobName can't be null");
			throw new RuntimeException("jobName can't be null");
		}
		try {
			CronTrigger cronTrigger = new CronTrigger(triggerName, groupName,
					jobName, groupName);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 创建一个触发器,定时启动,每隔n秒启动一次，定时停止
	public void addTrigger(String triggerName, long n, String groupName,
			String jobName, Date startTime, Date endTime) {
		if (startTime == null) {
			startTime = new Date();
		}
		addTrigger(triggerName, startTime, endTime,
				SimpleTrigger.REPEAT_INDEFINITELY, n * 1000L, groupName,
				jobName);
	}

	// 触发器真实新增方法
	private void addTrigger(String triggerName, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String groupName,
			String jobName) {
		triggerName = StringUtil.getUUIDName(triggerName);
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		if (StringUtil.isBlank(jobName)) {
			log.error("jobName can't be null");
			throw new RuntimeException("jobName can't be null");
		}
		try {
			SimpleTrigger SimpleTrigger = new SimpleTrigger(triggerName,
					groupName, jobName, groupName, startTime, endTime,
					repeatCount, repeatInterval);
			scheduler.scheduleJob(SimpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 返回所有triggers
	public List<Map<String, Object>> getQrtzTriggers() {
		return quartzDAO.getQrtzTriggers();
	}

	// 返回所有jobs
	public List<Map<String, Object>> getQrtzJobs() {
		return quartzDAO.getOrtzJobs();
	}

	// 暂停trigger
	public void pauseTrigger(String triggerName, String groupName) {
		if (StringUtil.isBlank(triggerName)) {
			throw new RuntimeException(
					"triggerName can't be null when pauseTrigger!");
		}
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		try {
			scheduler.pauseTrigger(triggerName, groupName);// 停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 暂停job
	public void pauseJob(String jobName, String groupName) {
		if (StringUtil.isBlank(jobName)) {
			throw new RuntimeException("jobName can't be null when pauseJob!");
		}
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		try {
			scheduler.pauseJob(jobName, groupName); // 停止任务
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 重启trigger
	public void resumeTrigger(String triggerName, String groupName) {
		if (StringUtil.isBlank(triggerName)) {
			throw new RuntimeException(
					"triggerName can't be null when resumeTrigger!");
		}
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		try {
			scheduler.resumeTrigger(triggerName, groupName);// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 重启job
	public void resumeJob(String jobName, String groupName) {
		if (StringUtil.isBlank(jobName)) {
			throw new RuntimeException("jobName can't be null when resumeJob!");
		}
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		try {
			scheduler.resumeJob(jobName, groupName); // 重启任务
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 删除trigger
	public boolean removeTrigger(String triggerName, String groupName) {
		if (StringUtil.isBlank(triggerName)) {
			throw new RuntimeException(
					"triggerName can't be null when removeTrigger!");
		}
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		try {
			scheduler.pauseTrigger(triggerName, groupName);// 停止触发器
			return scheduler.unscheduleJob(triggerName, groupName);// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 删除job
	public boolean removeJob(String jobName, String groupName) {
		if (StringUtil.isBlank(jobName)) {
			throw new RuntimeException("jobName can't be null when removeJob!");
		}
		if (StringUtil.isBlank(groupName)) {
			groupName = Scheduler.DEFAULT_GROUP;
		}
		try {
			scheduler.pauseJob(jobName, groupName);// 停止任务
			return scheduler.deleteJob(jobName, groupName);// 删除job
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

}
