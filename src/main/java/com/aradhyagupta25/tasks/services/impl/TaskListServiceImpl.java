package com.aradhyagupta25.tasks.services.impl;

import com.aradhyagupta25.tasks.domain.dto.TaskListDto;
import com.aradhyagupta25.tasks.domain.entities.TaskList;
import com.aradhyagupta25.tasks.repositories.TaskListRepository;
import com.aradhyagupta25.tasks.services.TaskListService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }


    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {

        if(null != taskList.getId()) {
            throw new IllegalArgumentException("Task list already has an ID!");
        }
        if( null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title is missing!");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id); // returns if exist or Optional.empty().
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null ==  taskList.getId()) {
            throw new IllegalArgumentException("Task list must have an ID!");
        }
        if(!Objects.equals(taskList.getId(), taskListId)) {
            throw new IllegalArgumentException("Attempting to change task list ID!, this is not permitted!");
        }

        TaskList exisingTaskList =  taskListRepository.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("Task list not found!"));

        exisingTaskList.setTitle(taskList.getTitle());
        exisingTaskList.setDescription(taskList.getDescription());
        exisingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(exisingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }


}
