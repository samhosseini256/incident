package com.example.workflow.controller;

import com.example.workflow.service.ProjectService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
public class MainController {

    record IncidentModel (
            String id,
            Date incidentTimestamp,
            String incidentType,
            String executionId,
            String activityId,
            String processInstanceId,
            String processDefinitionId,
            String causeIncidentId,
            String rootCauseIncidentId,
            String configuration,
            String incidentMessage,
            String tenantId,
            String jobDefinitionId,
            String historyConfiguration,
            String failedActivityId,
            String annotation
    ){}

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @GetMapping("get-incidents-{processInstanceId}")
    public List<IncidentModel> getIncidents(@PathVariable String processInstanceId){

//        repositoryService.getProcessDiagramLayout()

        Incident incident = runtimeService.createIncidentQuery().processInstanceId(processInstanceId).singleResult();

        Optional<IncidentModel> any = Stream.of(incident).map(in -> new IncidentModel(in.getId(), in.getIncidentTimestamp(), in.getIncidentType(), in.getExecutionId(), in.getActivityId(),
                in.getProcessInstanceId(), in.getProcessDefinitionId(), in.getCauseIncidentId(), in.getRootCauseIncidentId(), in.getConfiguration(),
                in.getIncidentMessage(), in.getTenantId(), in.getJobDefinitionId(), in.getHistoryConfiguration(), in.getFailedActivityId(), in.getAnnotation())).findAny();
        return List.of(any.get());
    }


    @GetMapping("/get-{id}")
    public String getModel(@PathVariable String id) throws IOException {

        Task task = taskService.createTaskQuery().taskId("4d07d4c3-021d-11ee-8a94-7c8bca06c3f6").singleResult();

        InputStream processDiagram = repositoryService.getProcessDiagram(id);
        InputStream processModel = repositoryService.getProcessModel(id);


        JsonNode bpmnData = new ObjectMapper().readTree(processModel);
        String bpmn20XmlString = bpmnData.get("bpmn20Xml").asText();
        InputStream bpmn20XMLStream = new ByteArrayInputStream(bpmn20XmlString.getBytes(StandardCharsets.UTF_8));
//
//        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(bpmn20XMLStream);
//        return bpmnModelInstance;
//        return new String(bpmnModelInstance.toString())

        byte[] processBytes = IOUtils.toByteArray(processModel);
        String processXml = new String(processBytes, StandardCharsets.UTF_8);

//        return bpmnModelInstance.toString();



//        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(id);
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(bpmn20XMLStream);
        Collection<FlowNode> flowNodes = bpmnModelInstance.getModelElementsByType(FlowNode.class);

        List<String> activeActivityIds = runtimeService.getActiveActivityIds(task.getExecutionId());

        for (FlowNode flowNode : flowNodes) {
            if (activeActivityIds.contains(flowNode.getId())) {
                flowNode.setId(flowNode.getId() + "-current");
                flowNode.setName(flowNode.getName() + " (Current)");
            }
        }



        return processXml;
    }

}

/*
BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);


5. با استفاده از getModelElementsByType()، تمام المان های مرحله در مدل Bpmn را جستجو کنید:


Collection<FlowNode> flowNodes = bpmnModelInstance.getModelElementsByType(FlowNode.class);
 */


/*
String processDefinitionId = processDefinition.getId();


4. با استفاده از getDiagramResourceName()، نام فایل دیاگرام فرایند را دریافت کنید:


String diagramResourceName = processDefinition.getDiagramResourceName();


5. با استفاده از getDeploymentId()، شناسه Deployment فرایند را دریافت کنید:


String deploymentId = processDefinition.getDeploymentId();


6. با استفاده از getResourceAsStream()، فایل دیاگرام فرایند را به صورت InputStream دریافت کنید:


InputStream diagramStream = repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
 */