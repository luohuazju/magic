package cn.sccl.tasksupervisor.commons.utils;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	/**
	 * 非空  不等于 ""," ",null
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 空 等于 ""," ",null
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 在原有name的后面加上"&UUID"
	 * @param orgName
	 * @return
	 */
	public static String getUUIDName(String orgName) {
		String uuidName = "";
		if (isBlank(orgName)) {
			uuidName = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			uuidName = orgName + "&" + UUID.randomUUID().toString();
		}
		return uuidName;
	}

	/**
	 * 将用户拼装的name&UUID变为name
	 * 
	 * @param uuidName
	 * @return
	 */
	public static String getOrgName(String uuidName) {
		String orgName = "";
		if (StringUtils.indexOf(uuidName, "&") != -1) {
			orgName = StringUtils.substringBefore(uuidName, "&");
		} else {
			orgName = uuidName;
		}
		return orgName;
	}

}
