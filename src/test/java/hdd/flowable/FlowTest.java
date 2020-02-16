package hdd.flowable;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FlowTest {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;


    @Test
    public void run() throws FileNotFoundException, XMLStreamException {
        ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
        //获取配置类
        /*ProcessEngineConfiguration processEngineConfiguration = pe.getProcessEngineConfiguration();
        repositoryService = processEngineConfiguration.getRepositoryService();
        runtimeService = processEngineConfiguration.getRuntimeService();
        taskService = processEngineConfiguration.getTaskService();*/

        //部署流程
        deploy(repositoryService);
        //启动流程
        //start(runtimeService);
//        //获取用户任务
//        String taskId = getTask(taskService,"hdd");
//        //完成用户任务
//        taskService.complete(taskId);
    }

    /**
     * 部署流程
     * @param repositoryService
     * @return
     */
    private static Deployment deploy(RepositoryService repositoryService) throws FileNotFoundException, XMLStreamException {
        //DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //Deployment deployment = deploymentBuilder.addClasspathResource("hdd/flowable/leave.bpmn20.xml").deploy();
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream inputStream=new FileInputStream(new File("E:\\IDEA\\测试流程3.bpmn20.xml"));
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
        BpmnModel model = bpmnXMLConverter.convertToBpmnModel(reader);

        List<Process> processes = model.getProcesses();
        Process curProcess = null;
        if (CollectionUtils.isEmpty(processes)) {
            return null;
        }

        curProcess = processes.get(0);

        inputStream=new FileInputStream(new File("E:\\IDEA\\测试流程3.bpmn20.xml"));
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name("TEST_FLOW_3").key("test3")
                .addInputStream(curProcess.getName(),inputStream);

        Deployment deployment= deploymentBuilder.deploy();
        return deployment;
    }

    /**
     * 启动流程
     * @param runtimeService
     */
    private static void start(RuntimeService runtimeService){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test2");
        System.out.println(processInstance);
    }

    /**
     * 查询待办任务
     * @param taskService
     * @param assignee 该任务的执行用户id
     * @return 任务id
     */
    private static String getTask(TaskService taskService,String assignee){
        Task task = taskService.createTaskQuery().taskAssignee(assignee).singleResult();
        return task.getId();
    }
}
