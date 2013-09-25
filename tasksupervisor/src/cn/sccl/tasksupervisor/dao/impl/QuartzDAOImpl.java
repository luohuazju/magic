package cn.sccl.tasksupervisor.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Repository;

import cn.sccl.tasksupervisor.commons.base.BaseDAO;
import cn.sccl.tasksupervisor.commons.constants.Constant;
import cn.sccl.tasksupervisor.commons.utils.StringUtil;
import cn.sccl.tasksupervisor.dao.QuartzDAO;

@Repository("quartzDAO")
public class QuartzDAOImpl extends BaseDAO implements QuartzDAO, Serializable {

	private static final long serialVersionUID = 8064852764208468228L;

	@SuppressWarnings("unchecked")
	//返回所有的任务
	public List<Map<String, Object>> getOrtzJobs() {
		List<Map<String, Object>> results = getJdbcTemplate().queryForList(
				"select * from QRTZ_JOB_DETAILS");
		String temp = null;
		for (Map<String, Object> map : results) {
			temp = MapUtils.getString(map, "job_name");
			map.put("display_name", StringUtil.getOrgName(temp));
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	// 返回所有triggers
	public List<Map<String, Object>> getQrtzTriggers() {
		List<Map<String, Object>> results = getJdbcTemplate().queryForList(
				"select * from QRTZ_TRIGGERS order by start_time");
		long val = 0;
		String temp = null;
		for (Map<String, Object> map : results) {
			temp = MapUtils.getString(map, "trigger_name");
			map.put("display_name", StringUtil.getOrgName(temp));
			
			temp = MapUtils.getString(map, "job_name");
			map.put("display_job_name", StringUtil.getOrgName(temp));
			
			val = MapUtils.getLongValue(map, "next_fire_time");
			if (val > 0) {
				map.put("next_fire_time", DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}
			val = MapUtils.getLongValue(map, "prev_fire_time");
			if (val > 0) {
				map.put("prev_fire_time", DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}
			val = MapUtils.getLongValue(map, "start_time");
			if (val > 0) {
				map.put("start_time", DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}
			val = MapUtils.getLongValue(map, "end_time");
			if (val > 0) {
				map.put("end_time", DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}
			map.put("status", Constant.status.get(MapUtils.getString(map,
					"trigger_state")));
		}
		return results;
	}

}
