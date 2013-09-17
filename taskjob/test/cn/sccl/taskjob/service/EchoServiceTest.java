package cn.sccl.taskjob.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:job-context.xml" })
public class EchoServiceTest {

	@Resource
	private EchoService echoService;

	public void setEchoService(EchoService echoService) {
		this.echoService = echoService;
	}

	@Test
	public void test() {
		echoService.echoHello("test", "try");
	}

}
