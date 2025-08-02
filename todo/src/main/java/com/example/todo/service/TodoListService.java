package com.example.todo.service;

import com.example.todo.entity.TodoList;
import com.example.todo.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
    @Autowired
    TodoListRepository repo;

    public void addTodo(TodoList todoList) {
        repo.save(todoList);
    }

    public List<TodoList> getAll() {
        return repo.findAll();
    }
}

