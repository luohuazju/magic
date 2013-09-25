package cn.sccl.tasksupervisor.commons.base;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import cn.sccl.tasksupervisor.job.GroovyJob;

import com.sillycat.easygroovyplugin.utils.SystemConfiguration;

public class BaseGroovyTest {

	public GroovyJob getGroovyJob(String groovyFileName)
			throws CompilationFailedException, IOException,
			InstantiationException, IllegalAccessException {
		ClassLoader cl = this.getClass().getClassLoader();
		GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
		String jobrootPath = SystemConfiguration.getString("job.file.path");
		String jobfileName = jobrootPath + groovyFileName;
		Class<?> groovyClass = groovyCl.parseClass(new File(jobfileName));
		GroovyJob gJob = (GroovyJob) groovyClass.newInstance();
		return gJob;
	}

}
