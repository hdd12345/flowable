package hdd.flowable.service.impl;

import hdd.flowable.dao.ProcessDefDao;
import hdd.flowable.entity.ProcessDefEntity;
import hdd.flowable.service.ProcessDefService;
import hdd.flowable.util.Parametermap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessDefServiceImpl implements ProcessDefService {
	@Autowired
	private ProcessDefDao ProcessDefDao;
	@Override
	public List<ProcessDefEntity> queryPageAllProcessDef(Parametermap pm) {
		return ProcessDefDao.queryPageAllProcessDefPage(pm);
	}

}
