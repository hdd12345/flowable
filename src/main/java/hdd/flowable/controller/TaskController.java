package hdd.flowable.controller;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    /**
     * 用户任务查询
     * @param assignee
     * @return
     */
    @RequestMapping(value = "/getTask/{assignee}")
    public List<String> getTask(@PathVariable(value = "assignee") String assignee){
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
        List<String> list = new ArrayList<>();
        taskList.forEach(task -> list.add(task.getId()));
        //taskService.complete(taskList.get(0).getId()); //完成用户任务
        return list;
    }

}
