package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/")

public class TaskController {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskMapper taskMapper;


    @RequestMapping(method = RequestMethod.GET, value = "tasks")
    public List<TaskDto> getTasks() {

        return taskMapper
                .mapToTaskDtoList(
                        dbService
                                .getAllTasks());
    }


    @RequestMapping(method = RequestMethod.GET, value = "task/{id}")
    public TaskDto getTask(@PathVariable(value = "id") Long taskId) throws TaskNotFoundException {

        return taskMapper
                .mapToTaskDto(
                        dbService
                                .getTaskById(taskId)
                                .orElseThrow(TaskNotFoundException::new));
    }


    @RequestMapping(method = RequestMethod.POST, value = "task", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {

        dbService
                .saveTask(
                        taskMapper
                                .mapToTask(taskDto));
    }


    @RequestMapping(method = RequestMethod.PUT, value = "task", consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {

        return taskMapper
                .mapToTaskDto(
                        dbService
                                .saveTask(
                                        taskMapper.mapToTask(taskDto)));
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "task/{id}")
    public void deleteTask(@PathVariable(value = "id") Long taskId) throws TaskNotFoundException {

        dbService.deleteTask(taskId);


    }


}
