package hdd.flowable.service.impl;

import hdd.flowable.dao.ActProcessTypeMapper;
import hdd.flowable.entity.ActProcessType;
import hdd.flowable.service.ActProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("actProcessTypeServiceImpl")
public class ActProcessTypeServiceImpl implements ActProcessTypeService {

	@Autowired
	ActProcessTypeMapper actProcessTypeMapper;
	@Override
	public List<ActProcessType> selectAll() {
		return actProcessTypeMapper.selectAll();
	}

}
