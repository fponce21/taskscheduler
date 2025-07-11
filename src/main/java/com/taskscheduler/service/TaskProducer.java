package com.taskscheduler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taskscheduler.model.Task;

/**
 * TaskProducer sends new tasks to RabbitMQ for async processing.
 */
@Service
public class TaskProducer {

    private static final Logger log = LogManager.getLogger(TaskProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    public TaskProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes a Task to RabbitMQ using configured exchange and routing key.
     *
     * @param task the task to send
     */
    public void sendTask(Task task) {
        log.info("📤 Sending task {} to RabbitMQ...", task.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, task);
        log.info("✅ Task {} published successfully.", task.getId());
    }
}
