package com.example.workflow.model;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.runtime.Incident;

import java.util.Date;

@Getter
@Setter
public class IncidentModelMy {

    private String id;
    private Date incidentTime;
    private String incidentType;
    private String executionId;
    private String failedActivityId;
    private String processInstanceId;
    private String incidentMessage;

    public IncidentModelMy(Incident incident) {
        this.id = incident.getId();
        this.incidentTime = incident.getIncidentTimestamp();
        this.incidentType = incident.getIncidentType();
        this.executionId = incident.getExecutionId();
        this.failedActivityId = incident.getActivityId();
        this.processInstanceId = incident.getProcessInstanceId();
        this.incidentMessage = incident.getIncidentMessage();
    }
}
