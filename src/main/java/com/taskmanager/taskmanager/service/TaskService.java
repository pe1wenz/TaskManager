package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask){
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public List<Task> getTaskByCompletedStatus(Boolean completed){
        if (completed == null){
            return taskRepository.findAll();
        }
        else {
            return taskRepository.findByCompleted(completed);
        }
    }

    public Page<Task> getTasksPaged(int page, int size){
        return taskRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Task> getTasksFilteredAndPaged(Boolean completed, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if (completed == null){
            return taskRepository.findAll(pageable);
        }else{
            return taskRepository.findByCompleted(completed, pageable);
        }
    }

    public Page<Task> getTasksFilteredAndPaged(
            Boolean completed,
            int page,
            int size,
            String sortBy
            ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        if (completed == null){
            return taskRepository.findAll(pageable);
        }else{
            return taskRepository.findByCompleted(completed, pageable);
        }
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
