package hdd.flowable.service.impl;

import hdd.flowable.dao.TaskDao;
import hdd.flowable.entity.TaskAPIData;
import hdd.flowable.entity.TaskData;
import hdd.flowable.service.MyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyTaskServiceImpl implements MyTaskService {
	@Autowired
	private TaskDao taskDao;
	@Override
	public List<TaskData> taskListPage(String userId) {
		return taskDao.queryByUserIdListPage(userId);
	}
	
	@Override
	public List<TaskAPIData> queryByUserIdPage(String userId) {
		return taskDao.queryByUserIdPage(userId);
	}

}
