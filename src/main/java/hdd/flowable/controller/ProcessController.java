package hdd.flowable.controller;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;

    /**
     * 流程部署
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/deploy")
    public Deployment deploy() throws FileNotFoundException {
        //Deployment deployment = repositoryService.createDeployment().addClasspathResource("leave.bpmn20.xml").deploy();
        String resourceName= "hdd/flowable/ch5/leave.bpmn";
        InputStream inputStream=new FileInputStream("E:\\IDEA\\leave.bpmn20.xml");
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream(resourceName, inputStream)
                .category("category1")//分类
                .name("这是一个请假流程1")//名称
                .key("test2")
                .deploy();
        return deployment;
    }

    /**
     * 流程实例启动
     * @param key
     * @return
     */
    @RequestMapping(value = "/start/{key}")
    public String startProcess(@PathVariable(value = "key") String key){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        System.out.println(processInstance);
        return "succeed";
    }

}
