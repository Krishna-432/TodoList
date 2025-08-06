package com.example.todo.service;

import com.example.todo.entity.TodoList;
import com.example.todo.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {
    // 1. Declare the dependency as final for immutability
    private final TodoListRepository repo;

    // 2. Create a public constructor for dependency injection
    public TodoListService(TodoListRepository repo) {
        this.repo = repo;
    }

    public List<TodoList> getAllTodos() {
        return repo.findAll();
    }

    public Optional<TodoList> getTodoById(Long id) {
        return repo.findById(id);
    }

    public Optional<TodoList> findByTitle(String title){
        return repo.findByTitle(title);
    }

    public TodoList addTodo(TodoList todoList) {
        // The object returned by save() is already the complete, persisted entity.
        return repo.save(todoList);
    }

    public TodoList updateTodo(Long id, TodoList updatedTodo) {
        TodoList todo = repo.findById(id).orElseThrow(() -> new RuntimeException("Todo not found!"));

        todo.setTitle(updatedTodo.getTitle());
        todo.setStatus(updatedTodo.isStatus());
        todo.setPriority(updatedTodo.getPriority());

        return repo.save(todo);
    }

    public void deleteTodo(Long id) {
        repo.deleteById(id);
    }
}

