package com.example.workflow.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.incident.IncidentHandling;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;

public class TestServiceTask implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) {

//        Execution delegateExecution = runtimeService.createExecutionQuery().processInstanceId("4d047960-021d-11ee-8a94-7c8bca06c3f6").singleResult();


//        execution.
//
//        try {
//            throw new RuntimeException();
//        } catch (Exception ex) {
//            execution.createIncident("this is incident Type", "this isConfig", "this is message");
//        }

//        protected String processDefinitionId;
//        protected String activityId;
//        protected String executionId;
//        protected String configuration;
//        protected String tenantId;
//        protected String jobDefinitionId;
//        protected String historyConfiguration;
//        protected String failedActivityId;

        IncidentContext context= new IncidentContext();
        context.setProcessDefinitionId(execution.getProcessDefinitionId());
        context.setActivityId(execution.getCurrentActivityId());
        context.setExecutionId(execution.getId());
        context.setConfiguration("configuration");
        context.setTenantId(execution.getTenantId());
        context.setJobDefinitionId(execution.getProcessDefinitionId());
        context.setHistoryConfiguration("historyConfiguration");
        context.setFailedActivityId(execution.getCurrentActivityId());


        IncidentHandling.createIncident("this is incident Type", context, "this is message");

    }


//    @Override
//    public void notify(DelegateTask delegateTask) {
//
//
//        delegateTask.getExecution().createIncident("this is incident Type2", "this isConfig2", "this is message2");
//    }



    private void createIncident(DelegateExecution execution){
        execution.createIncident("this is incident Type", "this isConfig", "this is message");



    }


}
