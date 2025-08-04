import React, { useState } from 'react';

function TaskForm({ onAddTask }) {
  const [newTaskTitle, setNewTaskTitle] = useState('');
  const [newPriority, setNewPriority] = useState('LOW');

  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent default form submission behavior
    if (newTaskTitle.trim() === '') return;
    const newTask = {
      title: newTaskTitle,
      priority: newPriority,
      completed: false,
    };
    onAddTask(newTask);
    setNewTaskTitle(''); // Clear input field after submission
  };

  return (
    <form onSubmit={handleSubmit} className="input-group">
      <input
        type="text"
        value={newTaskTitle}
        onChange={(e) => setNewTaskTitle(e.target.value)}
        placeholder="New task..."
      />
      <select value={newPriority} onChange={(e) => setNewPriority(e.target.value)}>
        <option value="HIGH">High</option>
        <option value="MEDIUM">Medium</option>
        <option value="LOW">Low</option>
      </select>
      <button type="submit">Add Task</button>
    </form>
  );
}

export default TaskForm;