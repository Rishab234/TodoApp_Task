package com.task.todoapp.scheduler;

import com.task.todoapp.Producer.MessageProducer;
import com.task.todoapp.dao.Task;
import com.task.todoapp.dao.TaskStatus;
import com.task.todoapp.dto.TaskDto;
import com.task.todoapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DueDateScheduler {

    @Autowired
    TaskService taskService;

    @Autowired
    MessageProducer messageProducer;

    private static final Logger LOGGER= LoggerFactory.getLogger(DueDateScheduler.class);

    @Scheduled(cron = "${scheduler.overdue.cron.expression}")
    public void checkDueDate() {
        LOGGER.info("Checking due date");
        LocalDateTime now = LocalDateTime.now();
        List<TaskDto> overDueTasks=taskService.getOverdueTasks(now);
        if(!overDueTasks.isEmpty()) {
            LOGGER.info("Sending Overdue Task to Producer");
            messageProducer.sendMessage(overDueTasks);
        }
    }
}
