package com.task.todoapp.dao;

public enum TaskStatus {

        NOT_STARTED,   // Task is acknowledged but not started
        IN_PROGRESS,   // Task is currently being worked on
        COMPLETED,     // Task is finished
        ON_HOLD,       // Task is paused and will be resumed later
        CANCELLED      // Task is terminated and will not be completed
}
