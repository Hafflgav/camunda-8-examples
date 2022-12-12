package com.camunda8.examples;

import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@EnableAsync
@Service
public class ExampleMessageCorrelation {

    public static Logger log = LoggerFactory.getLogger(ExamplesApplication.class);

    @Bean(name = "threadPoolTaskExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsynchThread::");
        executor.initialize();
        return executor;
    }

    @Async
    public void correlateMessage(ZeebeClient client, String businessKey) throws InterruptedException {
        Thread.sleep(2000L);
        Map<String, Object> variables = new HashMap();
        variables.put("Response","I have returned");

        log.info("We try to correlate a message with businessKey='{}", businessKey);

        client.newPublishMessageCommand()
                .messageName("returnMessage")
                .correlationKey(businessKey)
                .variables(variables)
                .send();
    }
}

