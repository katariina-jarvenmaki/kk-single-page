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

    <div className="container">

      <button 
        onClick={() => {
          localStorage.removeItem("jwt");
          navigate("/");
        }} 
        className="logout">
        Logout
      </button>

      <div className="section text-red-500 text-xl">

        <h2>Dashboard</h2>

        {error && <p className="error">{error}</p>}

        {data && (
          <div className="overflow-x-auto mt-4">
            <table className="min-w-full border border-highlight-1">
              <thead className="bg-highlight-1 text-white">
                <tr>
                  <th className="px-4 py-2 border border-highlight-1 text-left">ID</th>
                  <th className="px-4 py-2 border border-highlight-1 text-left">Name</th>
                  <th className="px-4 py-2 border border-highlight-1 text-left">Category</th>
                </tr>
              </thead>
              <tbody>
                {data.map((item: any) => (
                  <tr key={item.id} className="bg-white hover:bg-yellow-50">
                    <td className="px-4 py-2 border border-highlight-1">{item.id}</td>
                    <td className="px-4 py-2 border border-highlight-1">{item.name}</td>
                    <td className="px-4 py-2 border border-highlight-1">{item.category}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}        

      </div>

    </div>

  );
};

export default Dashboard;
