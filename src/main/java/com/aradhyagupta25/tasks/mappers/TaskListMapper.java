package com.aradhyagupta25.tasks.mappers;

import com.aradhyagupta25.tasks.domain.dto.TaskListDto;
import com.aradhyagupta25.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);

}
