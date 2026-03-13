package com.aradhyagupta25.tasks.controllers;

import com.aradhyagupta25.tasks.domain.dto.TaskDto;
import com.aradhyagupta25.tasks.domain.entities.Task;
import com.aradhyagupta25.tasks.mappers.TaskMapper;
import com.aradhyagupta25.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(
            @PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskdto) {
        Task createTask = taskService.createTask(
                taskListId,
                taskMapper.fromDto(taskdto)
        );
        return taskMapper.toDto(createTask);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ){
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @GetMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskdto
    ){
        Task updatedTask = taskService.updateTask(
                taskListId,
                taskId,
                taskMapper.fromDto(taskdto)
        );
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ){
        taskService.deleteTask(taskListId, taskId);
    }
 }
