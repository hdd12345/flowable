package hdd.flowable.service;


import hdd.flowable.entity.ProcessDefEntity;
import hdd.flowable.util.Parametermap;

import java.util.List;

public interface ProcessDefService {
	   /**
     * 查询所有已经部署的流程
     * @param pm
     * @return
     */
    public List<ProcessDefEntity> queryPageAllProcessDef(Parametermap pm);
}
