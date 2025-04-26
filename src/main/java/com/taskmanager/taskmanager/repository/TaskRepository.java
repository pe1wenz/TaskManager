package com.taskmanager.taskmanager.repository;


import com.taskmanager.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(boolean completed);

    Page<Task> findByCompleted(boolean completed, Pageable pageable);

    Page<Task> findAll(Pageable pageable);
}
