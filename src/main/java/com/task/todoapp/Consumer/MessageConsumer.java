package com.task.todoapp.Consumer;

import com.task.todoapp.Producer.MessageProducer;
import com.task.todoapp.dao.Task;
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
    public void consume(Task task) {
        LOGGER.info("Consuming task: {}", task);
        taskService.updateTaskById(task,task.getId());
        System.out.println(task);
        return;
    }
}
