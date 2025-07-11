package com.taskscheduler.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.taskscheduler.dto.TaskDTO;
import com.taskscheduler.model.Task;
import com.taskscheduler.repository.TaskRepository;
import com.taskscheduler.model.Task.Status;


/**
 * Service class that encapsulates task creation and retrieval logic.
 */
@Service
public class TaskService {

    private static final Logger log = LogManager.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final TaskProducer taskProducer;

    public TaskService(TaskRepository taskRepository, TaskProducer taskProducer) {
        this.taskRepository = taskRepository;
        this.taskProducer = taskProducer;
    }

    /**
     * Creates a new task and sends it to RabbitMQ for processing.
     *
     * @param dto Task creation data
     * @return saved Task entity
     */
    public Task createTask(TaskDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setSchedule(dto.getSchedule());
        task.setStatus(Status.PENDING);
        task.setExecutionNode(dto.getExecutionNode());
        task = taskRepository.save(task);

        log.info("📝 Created task with ID: {}", task.getId());

        taskProducer.sendTask(task);
        log.info("🚀 Task {} sent to RabbitMQ.", task.getId());

        return task;
    }

    /**
     * Returns a list of all tasks in the database.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves a task by ID if it exists.
     */
    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }
}
