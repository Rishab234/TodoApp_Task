package com.task.todoapp.repo;

import com.task.todoapp.dao.Task;
import com.task.todoapp.dao.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByUserName(String userName);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByTitle(String title);

    @Modifying
    @Transactional
    @Query(value = "SELECT * from TASK where due_Date<?1 and status!='CANCELLED'",nativeQuery = true)
    List<Task> findByDueDateBefore(LocalDateTime now);
}
