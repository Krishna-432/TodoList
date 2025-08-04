import React from 'react';

function TaskItem({ task, onUpdateTask, onDeleteTask }) {
  const completeTask = () => {
    const updatedTask = { ...task, completed: !task.completed };
    onUpdateTask(task.id, updatedTask);
  };

  const handleUpdateClick = () => {
    const newTitle = prompt('Update task title:', task.title);
    if (newTitle !== null && newTitle.trim() !== '') {
      onUpdateTask(task.id, { ...task, title: newTitle });
    }
  };

  return (
    <li className="task-item">
      <span style={{ textDecoration: task.completed ? 'line-through' : 'none' }}>
        {task.title} - {task.priority.toUpperCase()}
      </span>
      <div className="task-actions">
        <button className="update-btn" onClick={handleUpdateClick}>
          Update
        </button>
        <button
          className={`complete-btn ${task.completed ? 'uncomplete-btn' : ''}`}
          onClick={completeTask}
        >
          {task.completed ? 'Uncomplete' : 'Complete'}
        </button>
        <button className="delete-btn" onClick={() => onDeleteTask(task.id)}>
          Delete
        </button>
      </div>
    </li>
  );
}

export default TaskItem;