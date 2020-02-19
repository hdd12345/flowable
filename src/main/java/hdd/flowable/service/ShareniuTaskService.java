package hdd.flowable.service;


import hdd.flowable.entity.TaskAPIData;
import hdd.flowable.entity.TaskData;

import java.util.List;

public interface ShareniuTaskService {

    List<TaskData> taskListPage(String userId);

    public List<TaskAPIData> queryByUserIdPage(String userId);

    
}
