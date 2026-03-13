package com.aradhyagupta25.tasks.mappers;

import com.aradhyagupta25.tasks.domain.dto.TaskDto;
import com.aradhyagupta25.tasks.domain.entities.Task;
import org.hibernate.type.descriptor.jdbc.TimestampUtcAsJdbcTimestampJdbcType;

public interface TaskMapper {

    Task fromDto(TaskDto dto);  // takes a taskDto and returns a Task.

    TaskDto toDto(Task task);

}
