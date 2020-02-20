package hdd.flowable.controller;

import hdd.flowable.config.Const;
import hdd.flowable.service.ActProcessTypeService;
import hdd.flowable.service.MyTaskService;
import hdd.flowable.service.ProcessDefService;
import hdd.flowable.util.Parametermap;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ActProcessTypeService processTypeService;
    @Autowired
    private ProcessDefService processDefService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private org.flowable.engine.TaskService rowtaskService;
    Logger logger = LoggerFactory.getLogger(TaskController.class);


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

    @RequestMapping("/taskHandler")
    public String startFormPage(Model model, String taskId) {
        Task task = rowtaskService.createTaskQuery().taskId(taskId).singleResult();
        String executionId = task.getExecutionId();
        Map<String, Object> variables = runtimeService.getVariables(executionId);
        System.out.println(variables);
        model.addAttribute("taskId", taskId);
        model.addAllAttributes(variables);
        return "page/task/taskHandler";
    }

    @RequestMapping("/complete")
    public ModelAndView completeTask(Model model, String taskId,HttpServletRequest request) {
        Parametermap pm = new Parametermap(request);
        pm.remove("taskId");
        rowtaskService.complete(taskId, pm);
        return new ModelAndView("redirect:taskListPage");
    }


    @RequestMapping(value = "taskHandleredListPage")
    public Object taskHandleredListPage(HttpServletRequest request) {

        /// flowable-web/src/main/resources/templates/page/task
        return "page/task/taskHandleredList";
    }

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
