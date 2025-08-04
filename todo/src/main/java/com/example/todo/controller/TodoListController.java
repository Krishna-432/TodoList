package com.example.todo.controller;

import com.example.todo.entity.TodoList;
import com.example.todo.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/todos")
@RestController
public class TodoListController {
    @Autowired
    private TodoListService service;

    @GetMapping
    public List<TodoList> getAllTodos() {
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoList getTodoById(@PathVariable Long id) {
        return service.getTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with ID: " + id));
    }

    @GetMapping("/title/{title}")
    public TodoList findByTitle(@PathVariable String title){
        return service.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Todo not found by Title" + title));
    }

    @PostMapping
    public TodoList addTodo(@RequestBody TodoList todoList) {
        return service.addTodo(todoList);
    }

    @PutMapping("/{id}")
    public TodoList updateTodo(@PathVariable Long id , @RequestBody TodoList todoList){
        return service.updateTodo(id,todoList);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id){
        service.deleteTodo(id);
    }
}
