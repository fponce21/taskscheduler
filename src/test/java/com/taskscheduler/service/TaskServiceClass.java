//package com.taskscheduler.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.taskscheduler.dto.TaskDTO;
//import com.taskscheduler.model.Task;
//import com.taskscheduler.model.Task.Status;
//import com.taskscheduler.repository.TaskRepository;
//
///**
// * Unit tests for TaskService business logic using JUnit and Mockito.
// */
//class TaskServiceTest {
//
//    private TaskRepository taskRepository;
//    private TaskProducer taskProducer;
//    private TaskService taskService;
//
//    @BeforeEach
//    void setUp() {
//        taskRepository = mock(TaskRepository.class);
//        taskProducer = mock(TaskProducer.class);
//        taskService = new TaskService(taskRepository, taskProducer);
//    }
//
//    @Test
//    void testCreateTask_savesAndPublishes() {
//        // Given
//        TaskDTO dto = new TaskDTO("Test Task");
//        
//        Task taskToSave = new Task();
//        taskToSave.setName(dto.getName());
//        taskToSave.setStatus(Status.PENDING);
//
//        Task savedTask = new Task();
//        savedTask.setName(dto.getName());
//        savedTask.setStatus(Status.PENDING);
//        savedTask.setId(UUID.randomUUID());
//
//        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
//
//        // When
//        Task result = taskService.createTask(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("Test Task", result.getName());
//        verify(taskRepository).save(any(Task.class));
//        verify(taskProducer).sendTask(savedTask);
//    }
//
//    @Test
//    void testGetAllTasks_returnsTaskList() {
//        // Given
//        List<Task> mockTasks = List.of(
//                new Task(),
//                new Task()
//        );
//        when(taskRepository.findAll()).thenReturn(mockTasks);
//
//        // When
//        List<Task> result = taskService.getAllTasks();
//
//        // Then
//        assertEquals(2, result.size());
//        assertEquals("Task 1", result.get(0).getName());
//    }
//
//    @Test
//    void testGetTaskById_whenFound_returnsTask() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Task task = new Task();
//        
//        task.setId(id);
//        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
//
//        // When
//        Optional<Task> result = taskService.getTaskById(id);
//
//        // Then
//        assertTrue(result.isPresent());
//        assertEquals("Sample", result.get().getName());
//    }
//
//    @Test
//    void testGetTaskById_whenNotFound_returnsEmpty() {
//        UUID id = UUID.randomUUID();
//        when(taskRepository.findById(id)).thenReturn(Optional.empty());
//
//        Optional<Task> result = taskService.getTaskById(id);
//
//        assertFalse(result.isPresent());
//    }
//}