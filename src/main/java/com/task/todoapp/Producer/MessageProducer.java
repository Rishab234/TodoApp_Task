package com.task.todoapp.Producer;

import com.task.todoapp.dao.Task;
import com.task.todoapp.dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageProducer.class);


    public void sendMessage(TaskDto task) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, task);
    }

    public void sendMessage(List<TaskDto> tasks) {
        LOGGER.info("Sending " + tasks.size() + " tasks to Queue");
        for(TaskDto task:tasks){
            rabbitTemplate.convertAndSend(exchangeName, routingKey, task);
        }
    }

}
