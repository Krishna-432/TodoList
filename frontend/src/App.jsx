import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './style.css';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';

function App() {
  const [tasks, setTasks] = useState([]);
  const API_URL = 'http://localhost:8080/api/todos';

  // Fetch all tasks from the backend on component mount
  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await axios.get(API_URL);
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  const addTask = async (newTask) => {
    try {
      await axios.post(API_URL, newTask);
      fetchTasks(); // Refresh the list
    } catch (error) {
      console.error('Error adding task:', error);
    }
  };

  const updateTask = async (id, updatedTask) => {
    try {
      await axios.put(`${API_URL}/${id}`, updatedTask);
      fetchTasks(); // Refresh the list
    } catch (error) {
      console.error('Error updating task:', error);
    }
  };

  const deleteTask = async (id) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      fetchTasks(); // Refresh the list
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  return (
    <div className="app-container">
      <h1>To-Do List</h1>
      <TaskForm onAddTask={addTask} />
      <TaskList tasks={tasks} onUpdateTask={updateTask} onDeleteTask={deleteTask} />
    </div>
  );
}

export default App;