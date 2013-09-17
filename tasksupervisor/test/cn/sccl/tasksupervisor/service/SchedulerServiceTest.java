package cn.sccl.tasksupervisor.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class SchedulerServiceTest {

	@Resource
	private SchedulerService schedulerService;

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	
	@Test
	public void getQrtzJobs(){
		List<Map<String, Object>> jobs = schedulerService.getQrtzJobs();
		Assert.assertNotNull(jobs);
	}

	public void testJob() {
		// 添加了job
		schedulerService.addJob("helloJob", null,
				"cn.sccl.tasksupervisor.jobs.HelloJob", null);
		// 查询job
		List<Map<String, Object>> jobs = schedulerService.getQrtzJobs();
		Assert.assertNotNull(jobs);
		Assert.assertTrue(jobs.size() > 0);

		String jobName = (String) jobs.get(0).get("job_name");
		// 删除job
		boolean flag = schedulerService.removeJob(jobName, null);
		Assert.assertTrue(flag);
	}

	public void testTrigger() {
		schedulerService.addJob("helloJob", null,
				"cn.sccl.tasksupervisor.jobs.HelloJob", null);
		// 查询job
		List<Map<String, Object>> jobs = schedulerService.getQrtzJobs();
		Assert.assertNotNull(jobs);
		Assert.assertTrue(jobs.size() > 0);

		String jobName = (String) jobs.get(0).get("job_name");

		// 每20秒中执行调试一次
		schedulerService.addTrigger("TimesTrigger", "0/10 * * ? * * *", null,
				jobName);

		// Date startTime = DateUtil.parseYYYYMMddHHmmss("2009-12-15 20:14:00");
		// Date endTime = DateUtil.parseYYYYMMddHHmmss("2009-12-15 20:15:00");

		long endTime = System.currentTimeMillis() + 40000L;

		// 开始执行调度，结束执行调试

		// 开始执行调度，执行5次结束
		schedulerService.addTrigger("StartTimesTrigger", 5, null, jobName,
				new Date(), new Date(endTime));

		// 查询triggers
		List<Map<String, Object>> triggers = schedulerService.getQrtzTriggers();
		Assert.assertNotNull(triggers);
		Assert.assertTrue(jobs.size() > 0);

		// 删除triggers 不删除，跑跑测试下
		// for(int i = 0;i<triggers.size();i++){
		// String triggerName = (String)triggers.get(i).get("trigger_name");
		// boolean triggerFlag = schedulerService.removeTrigger(triggerName,
		// null);
		// Assert.assertTrue(triggerFlag);
		// }

		// 删除job 被引用了删除不了？
		// boolean jobFlag = schedulerService.removeJob(jobName, null);
		// Assert.assertTrue(jobFlag);
	}

}
