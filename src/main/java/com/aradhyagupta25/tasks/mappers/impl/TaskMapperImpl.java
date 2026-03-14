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
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.descriptio(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                (LocalDateTime) null,
                null,
                null
        );
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
