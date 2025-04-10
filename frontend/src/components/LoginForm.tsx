import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try {
      const res = await fetch('/api/public/token');
      if (!res.ok) throw new Error("Login failed");
      const data = await res.json();
      localStorage.setItem("jwt", data.token);
      navigate("/dashboard");
    } catch (err) {
      setError("Login failed. Check credentials.");
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: 'auto', padding: '2rem' }}>
      <h2>Login</h2>
      <input
        type="text"
        placeholder="Username"
        value={username}
        disabled
        style={{ display: 'block', marginBottom: '1rem', width: '100%' }}
        onChange={e => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        disabled
        style={{ display: 'block', marginBottom: '1rem', width: '100%' }}
        onChange={e => setPassword(e.target.value)}
      />
      <button onClick={login}>Login</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default LoginForm;
