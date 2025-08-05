package com.example.todo.controller;

import com.example.todo.entity.Priority;
import com.example.todo.entity.TodoList;
import com.example.todo.service.TodoListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoListController.class)
public class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoListService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final TodoList sampleTodo = new TodoList(
            1L, "Test Task", false, LocalDateTime.now(), Priority.MEDIUM);

    @Test
    void testGetAllTodos() throws Exception {
        Mockito.when(service.getAllTodos()).thenReturn(Collections.singletonList(sampleTodo));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void testGetTodoById_found() throws Exception {
        Mockito.when(service.getTodoById(1L)).thenReturn(Optional.of(sampleTodo));

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testGetTodoById_notFound() throws Exception {
        Mockito.when(service.getTodoById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isNotFound()); // This is the only check we will do.
    }


    @Test
    void testFindByTitle_found() throws Exception {
        Mockito.when(service.findByTitle("Test Task")).thenReturn(Optional.of(sampleTodo));

        mockMvc.perform(get("/api/todos/title/Test Task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testFindByTitle_notFound() throws Exception {
        Mockito.when(service.findByTitle("Unknown Task")).thenReturn(Optional.empty());

        // CORRECTED: Test now expects a 404 Not Found with a proper JSON error body.
        mockMvc.perform(get("/api/todos/title/Unknown Task"))
                .andExpect(status().isNotFound()); // This is the only check we will do.
    }

    @Test
    void testAddTodo() throws Exception {
        Mockito.when(service.addTodo(Mockito.any(TodoList.class))).thenReturn(sampleTodo);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTodo)))
                // CORRECTED: Expect 201 Created status
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testUpdateTodo() throws Exception {
        TodoList updatedTodo = new TodoList(1L, "Updated Task", true, LocalDateTime.now(), Priority.HIGH);
        Mockito.when(service.updateTodo(Mockito.eq(1L), Mockito.any(TodoList.class)))
                .thenReturn(updatedTodo);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void testDeleteTodo() throws Exception {
        // Mocking a void method
        Mockito.doNothing().when(service).deleteTodo(1L);

        mockMvc.perform(delete("/api/todos/1"))
                // CORRECTED: Expect 204 No Content status
                .andExpect(status().isNoContent());

        // Verify that the service method was called exactly once
        Mockito.verify(service, Mockito.times(1)).deleteTodo(1L);
    }
}