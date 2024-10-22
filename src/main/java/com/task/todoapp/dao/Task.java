package com.task.todoapp.dao;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(indexes = {@Index(name = "idx_username", columnList = "userName")})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    private String title;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    @Column(name = "due_date",columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dueDate;


    @PrePersist
    private void setDefaultTask(){
        if(this.status==null){
            this.status=TaskStatus.NOT_STARTED;
        }
        if(this.dueDate==null){
            this.dueDate=LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                '}';
    }

}
