package cn.sccl.tasksupervisor.commons.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.sccl.tasksupervisor.commons.utils.JsonUtil;

public class BaseController {

	public String getPageJonString(List<Map<String, Object>> datas) {
		String jo = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1); //当前页
		map.put("total", 2); //总页数
		map.put("records", datas.size()); //记录数
		
		
		for(Map<String, Object> item : datas){
			item.put("id", item.get("job_name"));
		}
		map.put("rows", datas);
		jo = JsonUtil.getJacksonJsonString(map);
		return jo;
	}

}
