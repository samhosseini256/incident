package com.example.workflow.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class TestServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            throw new RuntimeException();
        } catch (Exception ex) {
            execution.createIncident("this is incident Type", "this isConfig", "this is message");
        }
    }
}
