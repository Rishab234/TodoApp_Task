package com.task.todoapp.Consumer;

import com.task.todoapp.Producer.MessageProducer;
import com.task.todoapp.dao.Task;
import com.task.todoapp.dao.TaskStatus;
import com.task.todoapp.dto.TaskDto;
import com.task.todoapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @Autowired
    TaskService taskService;

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageConsumer.class);


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void updateStatus(TaskDto taskDto) {
        LOGGER.info("Consuming task with id: {} from Queue",taskDto.getId() );
        taskDto.setStatus(TaskStatus.CANCELLED);
        taskService.updateTaskById(taskDto,taskDto.getId());
        LOGGER.info("Task Status Updated and Saved to database");
    }
}
