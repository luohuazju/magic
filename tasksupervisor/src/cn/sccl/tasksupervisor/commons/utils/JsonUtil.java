package cn.sccl.tasksupervisor.commons.utils;

import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

	private final static Logger logger = LoggerFactory
			.getLogger(JsonUtil.class);

	/**
	 * trans json use jackson-1.4.1
	 * 
	 * @param item
	 * @return
	 */
	public static String getJacksonJsonString(Object item) {
		JsonFactory jf = new JsonFactory();
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator gen = jf.createJsonGenerator(sw);
			new ObjectMapper().writeValue(gen, item);
			gen.flush();
			return sw.toString();
		} catch (Exception e) {
			logger.error("jackson error: ", e);
			return "";
		}
	}
}
