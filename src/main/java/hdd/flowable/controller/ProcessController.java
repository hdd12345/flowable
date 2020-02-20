package hdd.flowable.controller;

import hdd.flowable.entity.ActProcessType;
import hdd.flowable.service.ActProcessTypeService;
import hdd.flowable.service.ProcessDefService;
import hdd.flowable.util.Parametermap;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    ActProcessTypeService actProcessTypeService;
    @Autowired
    HistoryService historyService;
    @Autowired
    TaskService taskService;

    @Autowired
    ProcessDefService processDefService;

    /**
     * 已部署流程列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/uploadToView")
    public String uploadToView(Model model) {
        List<ActProcessType> list = actProcessTypeService.selectAll();
        model.addAttribute("actProcessTypes", list);
        return "page/process/editcontentpage";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object contentpagelist(Model model, HttpServletRequest request) {
        Parametermap pm = new Parametermap(request);
        pm.put("rows", processDefService.queryPageAllProcessDef(pm));
        return pm;
    }

    /**
     * 根据实例查询，实例结束则查询绘制的流程图
     * @param response
     * @param request
     * @param processInstanceId  流程实例id
     * @throws IOException
     */
    @RequestMapping("/showActivityedimageDetailPage")
    public void showActivityedimageDetailPage(HttpServletResponse response, HttpServletRequest request, String processInstanceId) throws IOException {


        String processDefinitionId ="";
        List<String> highLightedActivities=new ArrayList<String>();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task==null) {
            HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId=hp.getProcessDefinitionId();
        }else {
            processDefinitionId = task.getProcessDefinitionId();
            highLightedActivities.add(task.getTaskDefinitionKey());
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new 	DefaultProcessDiagramGenerator();


        //InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities);
        InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities,true);
        OutputStream out= response.getOutputStream();
        copyPic(in,out);
    }

    @RequestMapping(value = "/listView", method = RequestMethod.GET)
    public Object listView(Model model) {
        List<ActProcessType> selectAll = actProcessTypeService.selectAll();
        System.out.println(selectAll);
        model.addAttribute("actProcessTypes", selectAll);
        return "page/process/list";
    }

    @RequestMapping("/startProcessInstance")
    public Object startProcessInstance(Model model,String processId,String processCategory,HttpServletRequest request) {
        Parametermap pm = new Parametermap(request);
        ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(pm.get("processId").toString(), pm);
        System.out.println("startProcessInstanceById:"+startProcessInstanceById);
        return new ModelAndView("redirect:listView");
    }
    //Request URL: http://127.0.0.1:9100/process/imageDetailPage?processId=leave:1:13c3f4b2-34c9-11e8-bdbd-4a18e9fe68f7
    @RequestMapping(value = "/showActivityimageDetailPage", method = RequestMethod.GET)
    public void imageDetailPage(String  taskId,HttpServletResponse response) throws IOException {
        //model.addAttribute("imageSrc", "/process/showImage?processId="+processId);
        //showActivityimageDetailPage

        Task task = taskService.createTaskQuery()
                .taskId(taskId).singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator
                =new DefaultProcessDiagramGenerator();

        List<String> highLightedActivities=new ArrayList<>();
        highLightedActivities.add(task.getTaskDefinitionKey());
        //InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities);
        InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities,true);
        OutputStream out = response.getOutputStream();
        copyPic(in,out);
    }
    @RequestMapping(value = "/imageDetailPage", method = RequestMethod.GET)
    public Object imageDetailPage(String  processId,Model model) {
        model.addAttribute("imageSrc", "/process/showImage?processId="+processId);
        return "page/process/imageDetailPage";
    }
    //http://127.0.0.1:9100/process/showImage?processId=leave:1:13c3f4b2-34c9-11e8-bdbd-4a18e9fe68f7

    @RequestMapping(value = "/showImage", method = RequestMethod.GET)
    public void showImage(String  processId,Model model,HttpServletRequest request,HttpServletResponse response) throws Throwable {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator
                =new DefaultProcessDiagramGenerator();
        List<String> highLightedActivities=new ArrayList<>();
        //InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities);
        InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities,true);
        OutputStream out = response.getOutputStream();
        copyPic(in,out);
    }

    @RequestMapping("/startFormPage")
    public String startFormPage(Model model,String processId,String processCategory) {
        model.addAttribute("processId", processId);
        return "page/process/startForm";
    }

    private void copyPic(InputStream in, OutputStream out) {
        try {
            IOUtils.copy(in, out);
        } catch (IOException e) {
        }finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    @RequestMapping(value = "deploy")
    @ResponseBody
    public Deployment getUser() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("com/shareniu/flowable/controller/leave.bpmn").deploy();
        return deploy;
    }

    @RequestMapping(value = "/upload")
    public ModelAndView upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
//actProcessTypes
        Parametermap pm = new Parametermap(request);
        upload(request, file, pm);
        return new ModelAndView("redirect:listView");
    }

    /**
     * 流程部署
     * @param request
     * @param file
     * @param pm
     * @throws Exception
     */
    private void upload(HttpServletRequest request, MultipartFile file, Parametermap pm) throws Exception {
        System.out.println("开始上传文件了");
        request.setCharacterEncoding("UTF-8");
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            String type = filename.indexOf(".") != -1
                    ? filename.substring(filename.lastIndexOf(".") + 1, filename.length())
                    : null;
            if (type != null) {
                if (type.endsWith("zip")) {
                    ZipInputStream zs=new ZipInputStream(file.getInputStream());
                    repositoryService
                            .createDeployment()
                            .name(pm.get("name").toString())
                            .category(pm.get("category").toString())
                            .addZipInputStream(zs)
                            .deploy();

                }else if(type.endsWith("bpmn")|| type.endsWith("xml")) {
                    InputStream inputStream=file.getInputStream();
                    repositoryService.createDeployment()
                            .name(pm.get("name").toString())
                            .category(pm.get("category").toString())
                            .addInputStream(filename, inputStream)
                            .deploy();
                }
            }
        }
    }

//    /**
//     * 流程部署
//     * @return
//     * @throws FileNotFoundException
//     */
//    @RequestMapping(value = "/deploy")
//    @ResponseBody
//    public Deployment deploy() throws FileNotFoundException {
//        //Deployment deployment = repositoryService.createDeployment().addClasspathResource("leave.bpmn20.xml").deploy();
//        String resourceName= "hdd/flowable/ch5/leave.bpmn";
//        InputStream inputStream=new FileInputStream("E:\\IDEA\\leave.bpmn20.xml");
//        Deployment deployment = repositoryService.createDeployment()
//                .addInputStream(resourceName, inputStream)
//                .category("category1")//分类
//                .name("这是一个请假流程1")//名称
//                .key("test2")
//                .deploy();
//        return deployment;
//    }
//
//    /**
//     * 流程实例启动
//     * @param key
//     * @return
//     */
//    @RequestMapping(value = "/start/{key}")
//    @ResponseBody
//    public String startProcess(@PathVariable(value = "key") String key){
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
//        System.out.println(processInstance);
//        return "succeed";
//    }

}
