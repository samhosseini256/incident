package com.example.workflow.controller;

import com.example.workflow.model.IncidentModelMy;
import com.example.workflow.service.ProjectService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
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

    @GetMapping("get-incidents-{processInstanceId}")
    public List<IncidentModel> getIncidents(@PathVariable String processInstanceId){


        Incident incident = runtimeService.createIncidentQuery().processInstanceId(processInstanceId).singleResult();

        Optional<IncidentModel> any = Stream.of(incident).map(in -> new IncidentModel(
                in.getId(),
                in.getIncidentTimestamp(),
                in.getIncidentType(),
                in.getExecutionId(),
                in.getActivityId(),
                in.getProcessInstanceId(),
                in.getProcessDefinitionId(),
                in.getCauseIncidentId(),
                in.getRootCauseIncidentId(),
                in.getConfiguration(),
                in.getIncidentMessage(),
                in.getTenantId(),
                in.getJobDefinitionId(),
                in.getHistoryConfiguration(),
                in.getFailedActivityId(),
                in.getAnnotation())).findAny();
        return List.of(any.get());


    }

    @GetMapping("get-my-incidents-{processInstanceId}")
    public List <IncidentModelMy> getMyIncidents(@PathVariable String processInstanceId){

        List <Incident> incidents = runtimeService.createIncidentQuery().processInstanceId(processInstanceId).list();
        List <IncidentModelMy> incidentModelMyList = new ArrayList<>();
        for (Incident incident:incidents ){
            incidentModelMyList.add(new IncidentModelMy(incident));
        }
        return incidentModelMyList;
    }


    @GetMapping("/get-{processInstanceId}")
    public String getModel(@PathVariable String processInstanceId) throws IOException {

        String processDefinitionId = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getProcessDefinitionId();

        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);

        Collection<FlowNode> flowNodes = bpmnModelInstance.getModelElementsByType(FlowNode.class);
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        for (FlowNode flowNode : flowNodes) {
            if (activeActivityIds.contains(flowNode.getId())) {
                flowNode.setName(flowNode.getName() + " (Current)");
            }
        }

        String bpmnModelFileString = Bpmn.convertToString(bpmnModelInstance);
        return bpmnModelFileString;
    }
}