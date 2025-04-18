import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import './styles/styles.sass';
import './styles/tailwind.css';

import Home from './pages/Home';
import Dashboard from './pages/Dashboard'

function App() {
  const [username, setUsername] = useState<string | null>(null);

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    if (storedUsername && storedUsername !== "undefined") {
      setUsername(storedUsername);
    }
  }, []);

  return (
    <Router>
      <nav style={{ padding: '1rem', background: '#eee', display: 'flex', justifyContent: 'space-between' }}>
        <div>
          <Link to="/" style={{ marginRight: '1rem' }}>Home</Link>
          <Link to="/dashboard">Dashboard</Link>
        </div>
      </nav>
      <div id="hello-user">
        {username && username !== "undefined" && <span>Hello, {username}!</span>}
      </div>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
