package com.example.todo.controller;

import com.example.todo.entity.TodoList;
import com.example.todo.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/todos")
@RestController
public class TodoListController {
    @Autowired
    TodoListService service;

    @PostMapping("/add")
    public void addTodo(@RequestBody TodoList todoList) {
        service.addTodo(todoList);
        System.out.println("Title added successfully!!");
    }

    @GetMapping("/getall")
    public List<TodoList> getAll() {
        return service.getAll();
    }
}
