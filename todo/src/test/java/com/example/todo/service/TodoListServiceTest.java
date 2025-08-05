package com.example.todo.service;

import com.example.todo.entity.TodoList;
import com.example.todo.repository.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoListServiceTest {

    @Mock
    private TodoListRepository repo;

    @InjectMocks
    private TodoListService service;

    private TodoList sampleTodo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);   // Initializes @Mock and @InjectMocks
        sampleTodo = TodoList.builder()
                .id(1L)
                .title("Test Todo")
                .status(false)
                .priority(com.example.todo.entity.Priority.HIGH)
                .build();
    }
    @Test
    void testGetAllTodos() {
            List<TodoList> mockList = List.of(sampleTodo);
            when(repo.findAll()).thenReturn(mockList);

            // Act
            List<TodoList> result = service.getAllTodos();

            // Assert
            assertEquals(1, result.size());
            assertEquals("Test Todo", result.get(0).getTitle());
            verify(repo, times(1)).findAll();
    }

    @Test
    void testGetTodoById() {
        when(repo.findById(1L)).thenReturn(Optional.of(sampleTodo));

        Optional<TodoList> result = service.getTodoById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Todo", result.get().getTitle());
        verify(repo).findById(1L);
    }

    @Test
    void testFindByTitle() {
        when(repo.findByTitle("Test Todo")).thenReturn(Optional.of(sampleTodo));

        Optional<TodoList> result = service.findByTitle("Test Todo");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repo).findByTitle("Test Todo");
    }


    @Test
    void testAddTodo() {
        // 1. Arrange: Define what the mock repository should do when 'save' is called.
        when(repo.save(sampleTodo)).thenReturn(sampleTodo);

        // 2. Act: Call the service method you want to test.
        TodoList result = service.addTodo(sampleTodo);

        // 3. Assert: Check that the result is what you expect.
        assertNotNull(result);
        assertEquals("Test Todo", result.getTitle());

        // 4. Verify: Ensure that the 'save' method was called exactly once on the repository.
        verify(repo).save(sampleTodo);
    }

    @Test
    void testUpdateTodo() {
        TodoList updated = TodoList.builder()
                .title("Updated Todo")
                .status(true)
                .priority(com.example.todo.entity.Priority.MEDIUM)
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        TodoList result = service.updateTodo(1L, updated);

        assertEquals("Updated Todo", result.getTitle());
        assertTrue(result.isStatus());
        assertEquals(com.example.todo.entity.Priority.MEDIUM, result.getPriority());
        verify(repo).save(any());
    }

    @Test
    void testDeleteTodo() {
        Long id = 1L;
        doNothing().when(repo).deleteById(id);

        service.deleteTodo(id);

        verify(repo).deleteById(id);
    }
}
