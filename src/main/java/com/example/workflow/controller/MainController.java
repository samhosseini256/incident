package com.example.workflow.controller;

import com.example.workflow.service.ProjectService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("get-incidents-{processInstanceId}")
    public List<IncidentModel> getIncidents(@PathVariable String processInstanceId){

//        repositoryService.getProcessDiagramLayout()

        Incident incident = runtimeService.createIncidentQuery().processInstanceId(processInstanceId).singleResult();

        Optional<IncidentModel> any = Stream.of(incident).map(in -> new IncidentModel(in.getId(), in.getIncidentTimestamp(), in.getIncidentType(), in.getExecutionId(), in.getActivityId(),
                in.getProcessInstanceId(), in.getProcessDefinitionId(), in.getCauseIncidentId(), in.getRootCauseIncidentId(), in.getConfiguration(),
                in.getIncidentMessage(), in.getTenantId(), in.getJobDefinitionId(), in.getHistoryConfiguration(), in.getFailedActivityId(), in.getAnnotation())).findAny();
        return List.of(any.get());
    }

}