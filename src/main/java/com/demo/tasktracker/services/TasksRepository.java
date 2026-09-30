package com.demo.tasktracker.services;

import com.demo.tasktracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TasksRepository extends JpaRepository<Task, Integer> {
}
