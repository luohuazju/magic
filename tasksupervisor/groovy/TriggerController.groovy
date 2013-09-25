package cn.sccl.tasksupervisor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sccl.tasksupervisor.commons.utils.StringUtil;
import cn.sccl.tasksupervisor.commons.utils.JsonUtil;
import java.net.URLDecoder;
import cn.sccl.tasksupervisor.service.SchedulerService;
import cn.sccl.tasksupervisor.commons.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/trigger.do")
class TriggerController extends BaseController{
	
	@Autowired
	SchedulerService schedulerService;
	
	@RequestMapping(params = "method=list")
	public ModelAndView list() throws IOException {
		ModelAndView view = new ModelAndView("triggerList");
		return view;
	}
	
	@RequestMapping(params = "method=listjson")
	public ModelAndView listjson(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView("jsonView");
		System.out.println(request.getParameter("page")); 
		List<Map<String, Object>> triggers = schedulerService.getQrtzTriggers();
		String jo = this.getPageJonString(triggers);
		view.addObject("json",jo.toLowerCase());
		return view;
	}
	
	@RequestMapping(params = "method=jobselect")
	public ModelAndView jobselect() throws IOException {
		ModelAndView view = new ModelAndView("selectView");
		List<Map<String, Object>> jobs = schedulerService.getQrtzJobs();
		view.addObject("items",jobs);
		return view;
	}
	
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request,
	HttpServletResponse response) throws IOException {
		String triggerName = request.getParameter("display_name");
		String cronExpression = request.getParameter("trigger_name");
		String jobName = request.getParameter("display_job_name");
		schedulerService.addTrigger(triggerName, cronExpression, null, jobName);
	}
	
	@RequestMapping(params = "method=cmd")
	public void cmd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String action = request.getParameter("action");
		String triggerName = request.getParameter("id");
		if(StringUtil.isNotBlank(action) && StringUtil.isNotBlank(triggerName)){
			triggerName = URLDecoder.decode(triggerName,"UTF-8");
			if("pause".equals(action)){
				//暂停任务
				schedulerService.pauseTrigger(triggerName, null);
				response.getWriter().println(0);
			}else if("resume".equals(action)){
				//重启任务
				schedulerService.resumeTrigger(triggerName, null);
				response.getWriter().println(0);
			}else if("remove".equals(action)){
				//删除任务
				schedulerService.removeTrigger(triggerName, null);
				response.getWriter().println(0);
			}
		}
	}

}