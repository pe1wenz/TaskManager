import {useEffect, useState} from 'react';
import axios from 'axios';
// import cors from 'cors'
//
// app.use(cors());

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  const API_URL = process.env.REACT_APP_API_URL;

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {
    const response = await axios.get(API_URL);
    setTasks(response.data);
  };

  const createTask = async (e) => {
    e.preventDefault();

    const task = {
      title,
      description,
      completed: false
    };

    try {
      await axios.post(API_URL, task);
      setTitle('');
      setDescription('');
      loadTasks();
    } catch (error) {
      console.error("Error creating task:", error);
      alert("Failed to create task. Please try again.");
    }
  };

  const deleteTask = async (id) => {

    try {
      await axios.delete(`${API_URL}/${id}`);
      loadTasks();
    } catch (error) {
      console.error("Error deleting task:", error);
      alert("Failed to delete task. Please try again.");
    }
  }

  const tootleCompleted = async (task) => {
    const updatedTask = {
      ...task,
      completed: !task.completed
    };

    try {
      await axios.put(`${API_URL}/${task.id}`, updatedTask);
      loadTasks();
    } catch (error) {
      console.error("Error deleting task:", error);
      alert("Failed to delete task. Please try again.");
    }
  }

  return (
      <div style={{padding: "20px"}}>
        <h1>Task Manager</h1>

        <form onSubmit={createTask}>
          <input
              type="text"
              placeholder="Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
          /><br/><br/>
          <input
              type="text"
              placeholder="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
          /><br/><br/>
          <button type="submit">Create Task</button>
        </form>

        <h2>Task List</h2>
        <ul>
          {tasks.map(task => (
              <li key={task.id} style={{padding: "5px"}}>
                Title: {task.title} - {task.description}
                <br/>
                Completed: {task.completed ? "Yes" : "No"}
                <br/>
                <button onClick={() => tootleCompleted(task)}>
                  {task.completed ? "Mark Incomplete" : "Mark Complete"}
                </button>
                <br/>
                <br/>
                <button onClick={() => deleteTask(task.id)}>Delete</button>

              </li>
          ))}
        </ul>
      </div>
  );
}

export default App;
