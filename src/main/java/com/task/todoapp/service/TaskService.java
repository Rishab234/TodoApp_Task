package com.task.todoapp.service;

import com.task.todoapp.dao.Task;
import com.task.todoapp.dao.TaskStatus;
import com.task.todoapp.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository ;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> getById(Integer id) {
        return taskRepository.findById(id);
    }

    public List<Task> getByStatus(TaskStatus status){
        return taskRepository.findByStatus(status);
    }
    public List<Task> getByName(String userName) {
        return  taskRepository.findByUserName(userName);
    }
    public List<Task> getByTitle(String title){
        return taskRepository.findByTitle(title);
    }

    //crud
    public void addTask(Task task) {

        taskRepository.save(task);
    }

    public Optional<Task> deleteTaskById(Integer id) {
        Optional<Task> task = getById(id);

        if(task.isPresent()) {
            taskRepository.deleteById(id);
            return task;
        }
        else
            return Optional.empty();
    }

    public Boolean updateTaskById(Task task,Integer id){

        Optional<Task> taskOptional = getById(id);
        if(taskOptional.isPresent()) {
            Task newTask = taskOptional.get();

            if(task.getTitle()!=null)
                newTask.setTitle(task.getTitle());
            if(task.getUserName()!=null)
                newTask.setUserName(task.getUserName());
            if(task.getStatus() != null)
                newTask.setStatus(task.getStatus());

            taskRepository.save(newTask);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Task> getOverdueTasks(LocalDateTime now) {
        return taskRepository.findByDueDateBefore(now);
    }






}
