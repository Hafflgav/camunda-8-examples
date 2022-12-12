package com.camunda8.examples;

import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.client.lifecycle.ZeebeClientLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class ProcessStarter {

    private static Logger log = LoggerFactory.getLogger(ExamplesApplication.class);

    @Autowired
    private ZeebeClientLifecycle client;

    @Scheduled(fixedRate = 5000L)
    public void startProcesses() {
        if (!client.isRunning()) {
            return;
        }

        final ProcessInstanceEvent event =
                client
                        .newCreateInstanceCommand()
                        .bpmnProcessId("Process_MessageCorrelation")
                        .latestVersion()
                        .variables("{\"businessKey\": \"" + UUID.randomUUID().toString() + "\",\"b\": \"" + new Date().toString() + "\"}")
                        .send()
                        .join();

        log.info("started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
                event.getProcessDefinitionKey(), event.getBpmnProcessId(), event.getVersion(), event.getProcessInstanceKey());
    }
}
