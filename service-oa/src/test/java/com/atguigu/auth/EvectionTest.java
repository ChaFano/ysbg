package com.atguigu.auth;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @Auther: 茶凡
 * @ClassName EvectionTest
 * @date 2023/8/10 16:03
 * @Description 工作流引擎测试
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class EvectionTest {


    // activiti的资源管理类
    @Autowired
    RepositoryService repositoryService;


    /**
     *  1、部署 bpmn 文件 压缩包格式部署 一般是上传 流程图 点击发布就开始部署
     */
    @Test
    public void deployProcessByZIP(){

        // 定义zip 输入流
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("process/evection.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        // 流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("出差流程申请")
                .deploy();
        System.out.println("流程部署id:" + deployment.getId());
        System.out.println("流程部署名称:" + deployment.getName());

    }

    // Activiti的流程运行管理类。可以从这个服务类中获取很多关于流程执行相关的信息
    @Autowired
    RuntimeService runtimeService;
    /**
     * 2、启动流程实例
     */
    @Test
    public void StartProcess(){
        // 根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process");

        // 输出内容
        System.out.println("BusinessKey: " + processInstance.getBusinessKey());
        System.out.println("getName: " + processInstance.getName());
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());

    }

    @Autowired
    TaskService taskService;
    /**
     * 3、任务查询 当前个人该执行的任务
     * 流程启动后，任务的负责人就可以查询自己当前需要处理的任务，查询出来的任务都是该用户的待办任务。
     */
    @Test
    public void FindPersonalTaskList() {
        //任务负责人
//        String assignee = "zhangsan";
        String assignee = "jerry";
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("process")
                .taskAssignee(assignee)//只查询该任务负责人的任务
                .list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }

    }

    /**
     * 4、完成任务处理
     * 例如张三登录进来查看自己的待处理任务 步骤 3  点击同意或拒绝 执行 执行该流程
     */

    @Test
    public void completTask(){
        // 返回一个任务对象
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process") //流程Key
                .taskAssignee("zhangsan")  //要查询的负责人
                .singleResult();

        // 完成任务,参数：任务id
        taskService.complete(task.getId());
    }

    /**
     *
     * 上述 四个步骤 部署、启动、查询、处理 对于一个 工作流任务 的基本处理
     * 部署 一般由管理员操作
     * 启动 一般由员工发起申请
     * 查询和处理 一般由员工的上级领导 查看和批复
     */


    /**
     * 5、查询流程定义
     */
    @Test
    public void queryProcessDefinition(){

       // 得到ProcessDefinitionQuery 对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //  查询出当前所有的流程定义
        //  条件：processDefinitionKey =evection
        //  orderByProcessDefinitionVersion 按照版本排序
        //  desc倒叙
        //  list 返回集合
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .orderByProcessDefinitionVersion()
                .desc().list();

        for (ProcessDefinition processDefinition : processDefinitionList){
            System.out.println("流程定义 id="+processDefinition.getId());
            System.out.println("流程定义 name="+processDefinition.getName());
            System.out.println("流程定义 key="+processDefinition.getKey());
            System.out.println("流程定义 Version="+processDefinition.getVersion());
            System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
        }




    }

    /**
     * 6、流程历史信息的擦好看
     */
    @Autowired
    HistoryService historyService;

    /**
     * 查询某个流程实例的执行流程过程
     */
    @Test
    public void findProcessedTaskList() {

        // 获取 actinst表的查询对象
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        // 查询 actinst表，条件：根据 InstanceId 查询
        //instanceQuery.processInstanceId("2501");

        //查询 actinst表，条件：根据 DefinitionId 查询
        instanceQuery.processDefinitionId("process:2:520ed424-3757-11ee-85f5-b4a9fc8ac964");

        // 增加排序操作,orderByHistoricActivityInstanceStartTime 根据开始时间排序 asc 升序
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        // 查询所有内容
        List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
        // 输出
        for (HistoricActivityInstance hi : activityInstanceList) {
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println(hi.getProcessInstanceId());
            System.out.println("<==========================>");
        }

    }

    /**
     * 查询某个用户操作过的流程
     */
    @Test
    public void findProcessTaskByName(){
        //张三已处理过的历史任务
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee("zhangsan").finished().list();
        for (HistoricTaskInstance historicTaskInstance : list) {
            System.out.println("流程实例id：" + historicTaskInstance.getProcessInstanceId());
            System.out.println("任务id：" + historicTaskInstance.getId());
            System.out.println("任务负责人：" + historicTaskInstance.getAssignee());
            System.out.println("任务名称：" + historicTaskInstance.getName());
        }
    }


    /**
     * 启动流程实例，添加 businessKey 这个key 一般和用户绑定
     */
    @Test
    public void addBusinessKey(){
//        1、得到ProcessEngine
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        2、得到RunTimeService
//        RuntimeService runtimeService = processEngine.getRuntimeService();

//        1、启动流程实例，同时还要指定业务标识businessKey，也就是出差申请单id，这里是1001
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("process","1001");
//        2、输出processInstance相关属性
        System.out.println("业务id=="+processInstance.getBusinessKey());

    }

    /**
     * 查询流程实例
     */
    @Test
    public void queryProcessInstance() {

        String processDefinitionKey = "process";
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .list();

        for (ProcessInstance processInstance : processInstanceList){
            System.out.println("----------------------------");
            System.out.println("流程实例id ：" + processInstance.getProcessInstanceId());
            System.out.println("流程名称：" + processInstance.getProcessDefinitionName());
            System.out.println("所属流程定义id ：" + processInstance.getProcessDefinitionId());
            System.out.println("是否执行完成 ：" + processInstance.isEnded());
            System.out.println("是否挂起 ：" + processInstance.isSuspended());
            System.out.println("当前活动标识 ：" + processInstance.getActivityId());
        }
    }

    /**
     * 全部流程实例挂起与激活
     */
    @Test
    public void SuspendAllProcessInstance(){
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("process")
                .singleResult();

        // 得到当前流程定义的实例是否都为暂停状态
        boolean suspended = processDefinition.isSuspended();
        // 流程定义id
        String processDefinitionId = processDefinition.getId();

        if(suspended){

            // 如果是暂停，可以执行激活操作 ,参数1 ：流程定义id ，参数2：是否激活，参数3：激活时间
            repositoryService.activateProcessDefinitionById(processDefinitionId,
                    true,
                    null
            );
            System.out.println("流程定义："+processDefinitionId+",已激活");
        }else{
            // 如果是激活状态，可以暂停，参数1 ：流程定义id ，参数2：是否暂停，参数3：暂停时间
            repositoryService.suspendProcessDefinitionById(processDefinitionId,
                    true,
                    null);
            System.out.println("流程定义："+processDefinitionId+",已挂起");
        }

    }

}
