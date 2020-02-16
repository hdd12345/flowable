package hdd.flowable.service;

import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * 流程服务类
 */
public interface FlowService {

    //部署工作流
    Deployment createFlow(String filePath);
    //启动工作流
    ProcessInstance startFlow(String processKey, Map<String,Object> paras);
}


