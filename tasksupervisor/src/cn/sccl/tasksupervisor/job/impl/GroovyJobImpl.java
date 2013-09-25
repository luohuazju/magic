package cn.sccl.tasksupervisor.job.impl;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.Serializable;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.sccl.tasksupervisor.commons.utils.StringUtil;
import cn.sccl.tasksupervisor.job.GroovyJob;

import com.sillycat.easygroovyplugin.utils.SystemConfiguration;

public class GroovyJobImpl extends QuartzJobBean implements Serializable {

	private static final long serialVersionUID = 6557774313433857280L;

	private final Logger logger = LoggerFactory.getLogger(GroovyJobImpl.class);

	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		JobDataMap dataMap = null;
		String librootPath = "";
		String groovyFileName = "";
		if (jobexecutioncontext != null) {
			dataMap = jobexecutioncontext.getJobDetail().getJobDataMap();
			groovyFileName = dataMap.getString("GROOVY_FILE_NAME");
		}
		ClassLoader cl = this.getClass().getClassLoader();
		GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
		librootPath = SystemConfiguration.getString("lib.file.path");
		if (StringUtil.isNotBlank(librootPath)) {
			String[] librootPaths = librootPath.split(";");
			if (librootPaths != null && librootPaths.length > 0) {
				for (int i = 0; i < librootPaths.length; i++) {
					String s = librootPaths[i];
					groovyCl.addClasspath(s);
				}
			}
		}
		String jobrootPath = SystemConfiguration.getString("job.file.path");
		String jobfileName = jobrootPath + groovyFileName;
		try {
			Class groovyClass = groovyCl.parseClass(new File(jobfileName));
			GroovyJob service = (GroovyJob) groovyClass.newInstance();
			service.execute(null);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
