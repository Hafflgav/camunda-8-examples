package com.camunda8.examples;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
@EnableZeebeClient
@Deployment(resources = {"classpath:/BPMN/MessageCorrelation.bpmn", "classpath:/BPMN/MessageStartEvent.bpmn"})
public class ExamplesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}
}
