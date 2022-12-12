package com.camunda8.examples;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.lifecycle.ZeebeClientLifecycle;
import org.checkerframework.checker.units.qual.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@EnableAsync
public class ExampleJobWorkers {

    @Autowired
    private ZeebeClientLifecycle client;

    @Autowired
    private ExampleMessageCorrelation messageCorrelation;

    private static Logger log = LoggerFactory.getLogger(ExamplesApplication.class);

    @JobWorker(type = "sendMessage", autoComplete = true)
    public void doSomeWork(final ActivatedJob job, @Variable String businessKey) throws InterruptedException {
        log.info("message is being send by workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}' and businessKey='{}",
                job.getProcessDefinitionKey(), job.getBpmnProcessId(), job.getProcessDefinitionVersion(), job.getProcessInstanceKey(), businessKey);

        messageCorrelation.correlateMessage(client, businessKey);
    }
}
