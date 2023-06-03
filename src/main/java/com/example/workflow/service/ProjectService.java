package com.example.workflow.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class ProjectService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;


    //get running diagram
    public void getDiagram(Task task){


        InputStream processDiagram = repositoryService.getProcessDiagram(task.getProcessDefinitionId());
        InputStream processModel = repositoryService.getProcessModel(task.getProcessDefinitionId());



//        JsonNode bpmnData = new ObjectMapper().readTree(response.body().byteStream());
//        String bpmn20XmlString = bpmnData.get("bpmn20Xml").asText();
//        InputStream bpmn20XMLStream = new ByteArrayInputStream(bpmn20XmlString.getBytes(StandardCharsets.UTF_8));


//        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(bpmn20XMLStream);





    }


}
