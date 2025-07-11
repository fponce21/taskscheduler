package com.taskscheduler.controller;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskscheduler.dto.TaskDTO;
import com.taskscheduler.model.Task;
import com.taskscheduler.service.TaskService;

import jakarta.validation.Valid;

/**
 * REST controller exposing endpoints for creating and retrieving tasks.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger log = LogManager.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * POST endpoint to create a new task.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskRequest) {
        log.info("📤 [API] Request to create task: {}", taskRequest.getName());
        Task task = taskService.createTask(taskRequest);
        return ResponseEntity.ok(task);
    }

    /**
     * GET endpoint to return all tasks.
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("📄 [API] Fetching all tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    /**
     * GET endpoint to return a task by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        log.info("🔍 [API] Fetching task by ID: {}", id);
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("⚠️ Task with ID {} not found.", id);
                    return ResponseEntity.notFound().build();
                });
    }
}