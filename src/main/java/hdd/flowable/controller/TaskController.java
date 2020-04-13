package hdd.flowable.controller;

import hdd.flowable.config.Const;
import hdd.flowable.entity.Holiday;
import hdd.flowable.service.HolidayService;
import hdd.flowable.service.MyTaskService;
import hdd.flowable.service.ProcessDefService;
import hdd.flowable.util.Parametermap;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessDefService processDefService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private org.flowable.engine.TaskService rowtaskService;
    @Autowired
    HolidayService holidayService;
    Logger logger = LoggerFactory.getLogger(TaskController.class);


    /**
     * 任务列表页面
     * @param request
     * @return
     */
    @RequestMapping(value = "taskListPage")
    public Object taskListPage(HttpServletRequest request) {
        return "page/task/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object contentpagelist(HttpServletRequest request,Model model) {
        Parametermap pm = new Parametermap(request);
        UserEntityImpl user = (UserEntityImpl) request.getSession().getAttribute(Const.SESSION_USER);
        pm.put("rows",myTaskService.taskListPage(user.getId()));
        return pm;
    }

    /**
     * 跳转任务处理页面，携带相关业务表单数据
     * @param model
     * @param taskId
     * @return
     */
    @RequestMapping("/taskHandler")
    public String startFormPage(Model model, String taskId) {
        Task task = rowtaskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String bussinessKey = processInstance.getBusinessKey();//业务主键
        Holiday holiday = holidayService.selectByPrimaryKey(bussinessKey);
        Map<String, Object> map = new HashMap<>();
        map.put("holiday",holiday);
        System.out.println(map);
        model.addAttribute("taskId", taskId);
        model.addAllAttributes(map);
        return "page/task/taskHandler";
    }

//    @RequestMapping("/taskHandler")
//    public String startFormPage(Model model, String taskId) {
//        Task task = rowtaskService.createTaskQuery().taskId(taskId).singleResult();
//        String executionId = task.getExecutionId();
//        Map<String, Object> variables = runtimeService.getVariables(executionId);
//        System.out.println(variables);
//        model.addAttribute("taskId", taskId);
//        model.addAllAttributes(variables);
//        return "page/task/taskHandler";
//    }

    /**
     * 完成任务
     * @param model
     * @param taskId
     * @param request
     * @return
     */
    @RequestMapping("/complete")
    public ModelAndView completeTask(Model model, String taskId,HttpServletRequest request) {
//        Parametermap pm = new Parametermap(request);
//        pm.remove("taskId");
        rowtaskService.complete(taskId);
        return new ModelAndView("redirect:taskListPage");
    }


    @RequestMapping(value = "taskHandleredListPage")
    public Object taskHandleredListPage(HttpServletRequest request) {
        return "page/task/taskHandleredList";
    }

    /**
     * 已办任务列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/taskHandleredList")
    @ResponseBody
    public Object taskHandleredList(Model model,HttpServletRequest request) {
        Parametermap pm = new Parametermap(request);
        UserEntityImpl user = (UserEntityImpl) request.getSession().getAttribute(Const.SESSION_USER);
        pm.put("rows", myTaskService.queryByUserIdPage(user.getId()));
        return pm;
    }

//    @Autowired
//    TaskService taskService;
//
//    /**
//     * 用户任务查询
//     * @param assignee
//     * @return
//     */
//    @RequestMapping(value = "/getTask/{assignee}")
//    public List<String> getTask(@PathVariable(value = "assignee") String assignee){
//        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
//        List<String> list = new ArrayList<>();
//        taskList.forEach(task -> list.add(task.getId()));
//        //taskService.complete(taskList.get(0).getId()); //完成用户任务
//        return list;
//    }





}
