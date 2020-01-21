package hdd.flowable.service;

import org.flowable.engine.repository.Deployment;

/**
 * 流程服务类
 */
public interface FlowService {

    /**
     * 部署工作流
     */
    Deployment createFlow(String filePath);
}


