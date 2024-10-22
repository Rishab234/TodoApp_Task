package com.task.todoapp.service;

import com.task.todoapp.dao.Task;
import com.task.todoapp.dao.TaskStatus;
import com.task.todoapp.dto.TaskDto;
import com.task.todoapp.repo.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository ;

    @Autowired
    ModelMapper modelMapper;

    public List<TaskDto> getAll() {
        return convertListTaskToDto(taskRepository.findAll());
    }

    public Optional<TaskDto> getById(Integer id) {
        return (convertTaskToDto(taskRepository.findById(id)));
    }


    public List<TaskDto> getByStatus(TaskStatus status){
        return convertListTaskToDto(taskRepository.findByStatus(status));
    }
    public List<TaskDto> getByName(String userName) {
        return  convertListTaskToDto(taskRepository.findByUserName(userName));
    }
    public List<TaskDto> getByTitle(String title){
        return convertListTaskToDto(taskRepository.findByTitle(title));
    }

    //crud
    public void addTask(TaskDto taskDto) {

        taskRepository.save(converDtoToTask(taskDto));
    }

    public Optional<TaskDto> deleteTaskById(Integer id) {
        Optional<TaskDto> task = getById(id);

        if(task.isPresent()) {
            taskRepository.deleteById(id);
            return task;
        }
        else
            return Optional.empty();
    }

    public Boolean updateTaskById(TaskDto task,Integer id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()) {
            Task existingTask = taskOptional.get();

            if(task.getTitle()!=null)
                existingTask.setTitle(task.getTitle());
            if(task.getUserName()!=null)
                existingTask.setUserName(task.getUserName());
            if(task.getStatus() != null)
                existingTask.setStatus(task.getStatus());
            taskRepository.save(existingTask);
            return true;
        }
        else{
            return false;
        }
    }

    public List<TaskDto> getOverdueTasks(LocalDateTime now) {
        return convertListTaskToDto(taskRepository.findByDueDateBefore(now));
    }

    public Task converDtoToTask(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

    public Optional<TaskDto> convertTaskToDto(Optional<Task> tasks) {
        return tasks.map(task -> modelMapper.map(task, TaskDto.class));
    }

    public List<TaskDto> convertListTaskToDto(List<Task> tasks) {
        return tasks.stream().map(task -> modelMapper.map(task,TaskDto.class)).toList();
    }








}
