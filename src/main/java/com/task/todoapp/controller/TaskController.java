package com.task.todoapp.controller;

import com.task.todoapp.Producer.MessageProducer;
import com.task.todoapp.dao.Task;
import com.task.todoapp.dao.TaskStatus;
import com.task.todoapp.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    MessageProducer messageProducer;

    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody Task task) {

        taskService.addTask(task);
        return new ResponseEntity<>("Task Created",HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTask() {
        List<Task> task=taskService.getAll();
        return new ResponseEntity<>(task,HttpStatus.OK);
    }


    @DeleteMapping(value = {"/delete/{id}","/delete/"})
    public ResponseEntity<Object> deleteTask(@Valid @PathVariable @NotNull Integer id ) {

        Optional<Task> task=taskService.deleteTaskById(id);
        if(task.isEmpty()){
            return new ResponseEntity<>("Task not found",HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(task,HttpStatus.OK);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTaskById(@PathVariable  Integer id, @RequestBody  Task task){
        boolean isUpdated=taskService.updateTaskById(task,id);

        if(isUpdated){
            return new ResponseEntity<>("Task Updated Successfully!!",HttpStatus.OK);
        }
        else{
             return new ResponseEntity<>("Task not found for the given id "+task.getId(),HttpStatus.NOT_FOUND);

        }
    }



    @GetMapping(value = "/status", params = "status")
    public ResponseEntity<Object> getTaskByStatus(@RequestParam  List<TaskStatus> status){
        List<List<Task>> tasks=new ArrayList<>();
        for(TaskStatus t:status){
            tasks.add(taskService.getByStatus(t));

        }
        if(tasks.isEmpty()){
            return new ResponseEntity<>("Task not found",HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping(value = "/name", params = "name")
    public ResponseEntity<?> getTaskByName(@RequestParam List<String> name){
        List<List<Task>> tasks=new ArrayList<>();

        for(String str:name){
            tasks.add(taskService.getByName(str));
        }

        if(tasks.isEmpty()){
            return new ResponseEntity<>("Task not found by name "+name,HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/title", params = "title")
    public ResponseEntity<Object> getTaskByTitle(@RequestParam List<String> title){
        List<List<Task>> tasks=new ArrayList<>();

        for(String str:title){
            tasks.add(taskService.getByTitle(str));
        }

        if(tasks.isEmpty()){
            return new ResponseEntity<>("Task not found by title "+title,HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }





}
