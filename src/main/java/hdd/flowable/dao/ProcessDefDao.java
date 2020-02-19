package hdd.flowable.dao;


import hdd.flowable.entity.ProcessDefEntity;
import hdd.flowable.util.Parametermap;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */
public interface ProcessDefDao {
    /**
     * 查询所有已经部署的流程
     * @param pm
     * @return
     */
    public List<ProcessDefEntity> queryPageAllProcessDefPage(Parametermap pm);

}
