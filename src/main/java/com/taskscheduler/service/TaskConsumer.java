package com.taskscheduler.service;

import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.taskscheduler.model.Task;
import com.taskscheduler.model.Task.Status;
import com.taskscheduler.repository.TaskRepository;

/**
 * TaskConsumer listens to the RabbitMQ queue and processes incoming tasks.
 * Includes idempotent checks to prevent duplicate processing.
 */
@Component // ✅ Changed from @Service to @Component (you can still use @Service if you prefer)
public class TaskConsumer {

    private static final Logger log = LogManager.getLogger(TaskConsumer.class);

    private final TaskRepository taskRepository;

    public TaskConsumer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Receives messages from the queue and handles task execution.
     *
     * @param task The task received from the queue (automatically deserialized)
     */
    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void consume(Task task) {
        UUID taskId = task.getId();
        log.info("📥 Received task from queue with ID: {}", taskId);

        Optional<Task> existing = taskRepository.findById(taskId);
        if (existing.isPresent() && Status.COMPLETED.equals(existing.get().getStatus())) {
            log.warn("⚠️ Task {} already completed. Skipping processing.", taskId);
            return;
        }

        try {
            log.info("🔧 Processing task: {}...", task.getName());

            Thread.sleep(1000); // Simulated task execution

            task.setStatus(Status.COMPLETED);
            taskRepository.save(task);
            log.info("✅ Task {} completed successfully.", taskId);

        } catch (Exception e) {
            log.error("❌ Failed to process task {}: {}", taskId, e.getMessage());

            task.setStatus(Status.FAILED);
            taskRepository.save(task);
        }
    }
}
