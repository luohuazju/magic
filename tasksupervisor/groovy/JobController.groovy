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
@RequestMapping("/job.do")
class JobController extends BaseController{

	@Autowired
	SchedulerService schedulerService;
	
	@RequestMapping(params = "method=list")
	public ModelAndView list() throws IOException {
		ModelAndView view = new ModelAndView("jobList");
		return view;
	}
	
	@RequestMapping(params = "method=listjson")
	public ModelAndView listjson(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ModelAndView view = new ModelAndView("jsonView");
		System.out.println(request.getParameter("page")); 
		List<Map<String, Object>> jobs = schedulerService.getQrtzJobs();
		String jo = this.getPageJonString(jobs);
		view.addObject("json",jo.toLowerCase());
		return view;
	}
	
	@RequestMapping(params = "method=edit")
	public ModelAndView edit() throws IOException {
		ModelAndView view = new ModelAndView("jobEdit");
		return view;
	}
	
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jobName = request.getParameter("display_name");
		String groovyFileName = request.getParameter("job_class_name");
		schedulerService.addJob(jobName, null, groovyFileName, null);
	}
	
	@RequestMapping(params = "method=cmd")
	public void cmd(HttpServletRequest request,
	HttpServletResponse response) throws IOException {
		String action = request.getParameter("action");
		String jobName = request.getParameter("id");
		if(StringUtil.isNotBlank(action) && StringUtil.isNotBlank(jobName)){
			jobName = URLDecoder.decode(jobName,"UTF-8");
			if("pause".equals(action)){
				//暂停任务
				schedulerService.pauseJob(jobName, null);
				response.getWriter().println(0);
			}else if("resume".equals(action)){
				//重启任务
				schedulerService.resumeJob(jobName, null);
				response.getWriter().println(0);
			}else if("remove".equals(action)){
				//删除任务
				schedulerService.removeJob(jobName, null);
				response.getWriter().println(0);
			}
		}
	}

}