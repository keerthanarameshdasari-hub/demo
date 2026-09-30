package com.demo.tasktracker.controllers;

import com.demo.tasktracker.models.Task;
import com.demo.tasktracker.models.TaskDto;
import com.demo.tasktracker.models.TaskPriority;
import com.demo.tasktracker.models.TaskStatus;
import com.demo.tasktracker.services.TasksRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TasksRepository tasksRepository;

    public TaskController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping({"","/"})
    public String showTaskList(Model model){
        List<Task> tasks = tasksRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("tasks", tasks);
        return "tasks/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        TaskDto taskDto=new TaskDto();
        model.addAttribute("taskDto", taskDto);
        return "tasks/CreateTask";
    }

    @PostMapping("/create")
    public String createTask(@Valid @ModelAttribute TaskDto taskDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "tasks/CreateTask";
        }

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
        task.setPriority(TaskPriority.valueOf(taskDto.getPriority()));
        task.setDueDate(LocalDate.parse(taskDto.getDueDate()));

        tasksRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id){
        try{
            Task task= tasksRepository.findById(id).get();
            model.addAttribute("task", task);

            TaskDto taskDto = new TaskDto();
            taskDto.setTitle(task.getTitle());
            taskDto.setDescription(task.getDescription());
            taskDto.setStatus(String.valueOf(task.getStatus()));
            taskDto.setPriority(String.valueOf(task.getPriority()));
            taskDto.setDueDate(String.valueOf(task.getDueDate()));

            model.addAttribute("taskDto",taskDto);
        } catch (Exception e) {
            System.out.println("Exception: "+ e.getMessage());
            return "redirect:/products";
        }
        return "tasks/EditTask";
    }

    @PostMapping("/edit")
    public String updateTask(Model model,@RequestParam int id, @Valid @ModelAttribute TaskDto taskDto,BindingResult bindingResult){
        try{
            Task task= tasksRepository.findById(id).get();
            model.addAttribute("task",task);

            if (bindingResult.hasErrors()){
                return "tasks/EditTask";
            }
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
            task.setPriority(TaskPriority.valueOf(taskDto.getPriority()));
            task.setDueDate(LocalDate.parse(taskDto.getDueDate()));

            tasksRepository.save(task);
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());;
        }

        return "redirect:/tasks";
    }

    @GetMapping("/delete")
    public String deleteTask(@RequestParam int id){
        try {
            Task task =tasksRepository.findById(id).get();
            tasksRepository.delete(task);
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
        return "redirect:/tasks";
    }
}
