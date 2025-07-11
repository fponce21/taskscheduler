package com.taskscheduler.repository;

import java.util.UUID;
import com.taskscheduler.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}
