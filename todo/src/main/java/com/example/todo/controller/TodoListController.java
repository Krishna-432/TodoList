package com.example.todo.controller;

import com.example.todo.entity.TodoList;
import com.example.todo.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/todos")
@RestController
public class TodoListController {
    // 1. Declare the dependency as final. This makes it immutable.
    private final TodoListService service;

    // 2. Create a public constructor to accept dependencies.
    // As of Spring 4.3, if a class has only one constructor, the @Autowired
    // annotation is no longer necessary. Spring will use it automatically.
    public TodoListController(TodoListService service) {
        this.service = service;
    }

    @GetMapping
    public List<TodoList> getAllTodos() {
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoList getTodoById(@PathVariable Long id) {
        return service.getTodoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with ID: " + id));
    }

    @GetMapping("/title/{title}")
    public TodoList findByTitle(@PathVariable String title){
        return service.findByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with title: " + title));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoList addTodo(@RequestBody TodoList todoList) {
        return service.addTodo(todoList);
    }

    @PutMapping("/{id}")
    public TodoList updateTodo(@PathVariable Long id , @RequestBody TodoList todoList){
        return service.updateTodo(id,todoList);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id){
        service.deleteTodo(id);
    }
}
