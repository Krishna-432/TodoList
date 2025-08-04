package com.example.todo.repository;

import com.example.todo.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    @Query("SELECT t FROM TodoList t WHERE t.title = :title")
    Optional<TodoList> findByTitle(@Param("title") String title);
}
