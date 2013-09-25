package cn.sccl.tasksupervisor.dao;

import java.util.List;
import java.util.Map;

public interface QuartzDAO {
 
	// 返回所有triggers
	public List<Map<String, Object>> getQrtzTriggers();

	// 返回所有jobs
	public List<Map<String, Object>> getOrtzJobs();

}
