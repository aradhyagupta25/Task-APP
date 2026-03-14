package com.aradhyagupta25.tasks.domain.dto;

import com.aradhyagupta25.tasks.domain.entities.TaskPriority;
import com.aradhyagupta25.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(      // java records automatically provides constructors, getters, equals, hashcode, two string and immutability.
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
)
{




}
