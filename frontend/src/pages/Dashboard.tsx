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

  // Seach conditions
  const [category, setCategory] = useState("");
  const [sortBy, setSortBy] = useState("");
  const [sortOrder, setSortOrder] = useState("asc");
  const [search, setSearch] = useState("");

  useEffect(() => {

    // Get Jwt token from localStorage
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/");
      return;
    }

    // Search parameters
    const params: any = {};
    if (category) params.category = category;
    if (sortBy) params.sortBy = sortBy;
    if (sortOrder) params.sortOrder = sortOrder;
    if (search) params.name = search;
    
    // Axios data request
    axios.get('/api/data', {
      headers: { Authorization: `Bearer ${token}` },
      params
    })
    .then(res => {
      console.log("Fetched data:", res.data);
      setData(res.data);
    })
    .catch(() => {
      setError("Unauthorized or error");
      navigate("/");
    });
}, [navigate, search, category, sortBy, sortOrder]);

  // Dashboard-view
  return (
    <div className="container">

      <button 
        onClick={() => {
          localStorage.removeItem("jwt");
          localStorage.removeItem("username");
          /* Return to home */
          window.location.reload();
          navigate("/");
        }} 
        className="logout">
        Logout
      </button>
  
      <div className="section">

        <h2>Dashboard</h2>

        <div className="dashboard-options flex flex-col md:flex-row gap-4 mb-4 items-center">
          {/* Search */}
          <input
            type="text"
            placeholder="Search by name..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="px-3 py-2 border rounded-md"
          />
    
          {/* Category */}
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            className="px-3 py-2 border rounded-md"
          >
            <option value="">All Categories</option>
            <option value="System">System</option>
            <option value="Language">Language</option>
            <option value="Filetype">Filetype</option>
          </select>
    
          {/* Sorting */}
          <select
            value={sortBy}
            onChange={(e) => setSortBy(e.target.value)}
            className="px-3 py-2 border rounded-md"
          >
            <option value="">Sort By</option>
            <option value="id">ID</option>
            <option value="name">Name</option>
            <option value="category">Category</option>
          </select>
    
          {/* Sort order */}
          <select
            value={sortOrder}
            onChange={(e) => setSortOrder(e.target.value)}
            className="px-3 py-2 border rounded-md"
          >
            <option value="asc">Ascending</option>
            <option value="desc">Descending</option>
          </select>
        </div>
    
        {/* Tässä se renderöinti taulukolle */}
        {error && <p className="error">{error}</p>}
    
        {data ? (
          data.length > 0 ? (
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
          ) : (
            <p className="text-gray-500 mt-4">No data found.</p>
          )
        ) : (
          <p className="text-gray-500 mt-4">Loading...</p>
        )}
      </div>

    </div>  
  );

};

export default Dashboard;
