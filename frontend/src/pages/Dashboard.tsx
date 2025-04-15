import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

// Import styles
import '../styles/styles.sass';

// Dashboard-view
const Dashboard = () => {

  const [data, setData] = useState<any>(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {

    // Get Jwt token from localStorage
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/");
      return;
    }

    // Axios data request
    axios.get('/api/data', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setData(res.data))
      .catch(() => {
        setError("Unauthorized or error");
        navigate("/");
      });
  }, [navigate]);

  // Dashboard-view
  return (

    <div id="dashboard">

      <button 
        onClick={() => {
          localStorage.removeItem("jwt");
          navigate("/");
        }} 
        className="logout">
        Logout
      </button>

      <div id="dashboard-container">
        <h2>Dashboard</h2>
        {error && <p className="error">{error}</p>}
        <pre>{data && JSON.stringify(data, null, 2)}</pre>
      </div>

    </div>

  );
};

export default Dashboard;
