package cn.sccl.tasksupervisor.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.sccl.taskjob.service.EchoService;
import cn.sccl.tasksupervisor.job.GroovyJob;
import cn.sccl.taskjob.service.impl.EchoServiceImpl;
import java.util.Map;

public class GroovyServiceSimple implements GroovyJob, Serializable {

	private static final long serialVersionUID = 122323233244334343L;

	private final Logger logger = LoggerFactory
			.getLogger(GroovyServiceSimple.class);

	public void execute(Map<String,Object> map) {
		String triggerName = "default";
		String groupName = "default";
		if(map != null && !map.isEmpty()){
			triggerName = map.get("triggerName");
			groupName = map.get("groupName");
		}
		EchoService service = new EchoServiceImpl();
		service.echoHello(triggerName, groupName);
	}

}
