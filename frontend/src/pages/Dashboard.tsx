import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Dashboard = () => {
  const [data, setData] = useState<any>(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/");
      return;
    }

    axios.get('/api/data', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setData(res.data))
      .catch(() => {
        setError("Unauthorized or error");
        navigate("/");
      });
  }, [navigate]);

  return (
    <div style={{ padding: '2rem' }}>
        <button
          onClick={() => {
            localStorage.removeItem("jwt");
            navigate("/");
          }}
          style={{
            position: "absolute",
            top: "1rem",
            right: "1rem",
            padding: "0.5rem 1rem",
            backgroundColor: "#e63946",
            color: "#fff",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer"
          }}
        >
          Logout
      </button>
      <h2>Dashboard</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <pre>{data && JSON.stringify(data, null, 2)}</pre>
    </div>
  );
};

export default Dashboard;
