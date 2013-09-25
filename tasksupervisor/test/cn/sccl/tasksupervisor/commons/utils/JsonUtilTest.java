package cn.sccl.tasksupervisor.commons.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JsonUtilTest {
	
	@Test
	public void getJacksonJsonString() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("1", 1);
		map.put("2", 1);
		map.put("3", 1);
		map.put("4", 1);
		map.put("5", 1);
		Assert.assertNotNull(JsonUtil.getJacksonJsonString(map));
		System.out.println(JsonUtil.getJacksonJsonString(map));
	}

}
