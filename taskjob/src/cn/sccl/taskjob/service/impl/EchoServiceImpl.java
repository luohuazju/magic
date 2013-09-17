package cn.sccl.taskjob.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.sccl.taskjob.service.EchoService;

@Service("echoService")
public class EchoServiceImpl implements EchoService, Serializable {

	private static final long serialVersionUID = 122323233244334343L;

	private final Logger logger = LoggerFactory
			.getLogger(EchoServiceImpl.class);

	public void echoHello(String triggerName, String group) {
		logger.info("triggerName:" + triggerName + ",group:" + group
				+ " echo hello!");
	}

}
