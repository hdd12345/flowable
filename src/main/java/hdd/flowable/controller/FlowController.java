package hdd.flowable.controller;

import hdd.flowable.service.FlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "flow")
public class FlowController {

    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(FlowController.class);
    /**
     * 流程处理服务
     */
    @Autowired
    private FlowService flowService;

    @RequestMapping("/create")
    public Map<String,String> createFlow(HttpServletRequest request){
        Map<String,String> res =new HashMap<>();

        //String flowPath = ClassUtils.getDefaultClassLoader().getResource("static\\bpmn") + "\\test.bpmn20.xml";
        String flowPath = "D:\\idea-workspace\\flowable\\target\\classes\\static\\bpmn\\test.bpmn20.xml";

        if (null == flowService.createFlow(flowPath)){
            res.put("msg","创建流程失败");
            res.put("res","0");
            return res;
        }
        res.put("msg","创建流程成功");
        res.put("res","1");
        return res;
    }
}



