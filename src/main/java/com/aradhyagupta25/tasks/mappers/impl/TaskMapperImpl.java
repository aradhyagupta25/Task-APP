package com.aradhyagupta25.tasks.mappers.impl;

import com.aradhyagupta25.tasks.domain.dto.TaskDto;
import com.aradhyagupta25.tasks.domain.entities.Task;
import com.aradhyagupta25.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component      // marked it as a bean for injection.
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.id());
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setDueDate(taskDto.dueDate());
        task.setStatus(taskDto.status());
        task.setPriority(taskDto.priority());
        return task;
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }

}
