package com.task.todoapp.dto;

import com.task.todoapp.dao.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {


    private Integer id;

    private String userName;


    private String title;

    private TaskStatus status=TaskStatus.NOT_STARTED;
}
