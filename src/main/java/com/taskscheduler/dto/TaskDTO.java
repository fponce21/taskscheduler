package com.taskscheduler.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for accepting safe task creation input.
 */
public class TaskDTO {

    @NotBlank(message = "Task name must not be empty")
    private String name;
    
    @NotBlank(message = "Schedule must not be empty")
    private String schedule;
    
    private String executionNode;
    

    public TaskDTO() {}

    public TaskDTO(String name, String schedule,String executionNode) {
        this.name = name;
        this.schedule=schedule;
        this.executionNode=executionNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getExecutionNode() {
		return executionNode;
	}

	public void setExecutionNode(String executionNode) {
		this.executionNode = executionNode;
	}
}
