package com.example.workflow.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class TestServiceTask implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) {

//        IncidentContext context= new IncidentContext();
//        context.setProcessDefinitionId(execution.getProcessDefinitionId());
//        context.setActivityId(execution.getCurrentActivityId());
//        context.setExecutionId(execution.getId());
//        context.setConfiguration("configuration");
//        context.setTenantId(execution.getTenantId());
//        context.setJobDefinitionId(execution.getProcessDefinitionId());
//        context.setHistoryConfiguration("historyConfiguration");
//        context.setFailedActivityId(execution.getCurrentActivityId());
//
        IncidentEntity newIncident = IncidentEntity.createAndInsertIncident("failedJob", context, "failedJob");
        newIncident.createRecursiveIncidents();
        newIncident.

        try {
            throw new RuntimeException();
        } catch (Exception ex) {
            execution.createIncident("rUUnTIME","ConFiGuraTion","MesSaGe");
            execution.resolveIncident("");
        }

    }


//    @Override
//    public void notify(DelegateTask delegateTask) {
//
//
//        delegateTask.getExecution().createIncident("this is incident Type2", "this isConfig2", "this is message2");
//        delegateTask.getExecution().resolveIncident("");
//    }

}
