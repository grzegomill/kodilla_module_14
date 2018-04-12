package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") //@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1/tasks")

public class TaskController {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {

        return taskMapper
                .mapToTaskDtoList(
                        dbService
                                .getAllTasks());
    }

    @GetMapping(value = "/{id}")
    public TaskDto getTask(@PathVariable(value = "id") Long taskId) throws TaskNotFoundException {

        return taskMapper
                .mapToTaskDto(
                        dbService
                                .getTaskById(taskId)
                                .orElseThrow(TaskNotFoundException::new));
    }


    @PostMapping
    public void createTask(@RequestBody TaskDto taskDto) {

        dbService
                .saveTask(
                        taskMapper
                                .mapToTask(taskDto));
    }


    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {

        return taskMapper
                .mapToTaskDto(
                        dbService
                                .saveTask(
                                        taskMapper.mapToTask(taskDto)));
    }


    @DeleteMapping(value = "/{id}")
    public void deleteTask(@PathVariable(value = "id") Long taskId) throws TaskNotFoundException {

        dbService.deleteTask(taskId);

    }

}
