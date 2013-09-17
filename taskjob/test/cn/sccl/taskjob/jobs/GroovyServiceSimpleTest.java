package cn.sccl.taskjob.jobs;

import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.junit.Test;

import cn.sccl.tasksupervisor.commons.base.BaseGroovyTest;
import cn.sccl.tasksupervisor.job.GroovyJob;

public class GroovyServiceSimpleTest extends BaseGroovyTest{

	@Test
	public void testExecuteInternal() throws CompilationFailedException,
			IOException, InstantiationException, IllegalAccessException {
		GroovyJob job = this.getGroovyJob("GroovyServiceSimple.groovy");
		job.execute(null);
	}

}
