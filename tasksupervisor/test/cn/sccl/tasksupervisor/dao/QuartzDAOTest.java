package cn.sccl.tasksupervisor.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class QuartzDAOTest {

	@Resource
	private QuartzDAO quartzDAO;

	public void setQuartzDAO(QuartzDAO quartzDAO) {
		this.quartzDAO = quartzDAO;
	}

	@Test
	public void testGetQrtzTriggers() {
		List<Map<String, Object>> list = quartzDAO.getQrtzTriggers();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void testGetQrtzJobs() {
		List<Map<String, Object>> list = quartzDAO.getOrtzJobs();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

}
