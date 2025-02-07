import React, { useState } from "react";
import { Container, TextField, Button, Typography, Box } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Login.css";
import carImage1 from "../assets/car1.jpg";
import carImage2 from "../assets/car2.jpg";
import Footer from "../components/Footer";
import { loginUser } from "../services/authService"; // Импортираме login API

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const response = await loginUser(email, password);
      console.log("Login successful:", response);
      alert("Login successful!");
      navigate("/home"); // Пренасочване след успешен login
    } catch (error) {
      console.error("Login error:", error);
      setError("Invalid email or password. Please try again.");
    }
  };

  return (
    <Container maxWidth={false} className="login-container">
      <Box className="header">CarsHub</Box>

      <Box className="main-content">
        {/* Left Car Image */}
        <Box className="car-placeholder">
          <img src={carImage1} alt="Car 1" className="car-image" />
        </Box>

        {/* Login Form */}
        <Box className="login-box">
          <Typography variant="h5">Welcome to CarsHub! Please log in.</Typography>
          <form onSubmit={handleLogin}>
            <TextField 
              label="Email" 
              variant="outlined" 
              fullWidth 
              margin="normal" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)} 
            />
            <TextField 
              label="Password" 
              type="password" 
              variant="outlined" 
              fullWidth 
              margin="normal" 
              value={password} 
              onChange={(e) => setPassword(e.target.value)} 
            />
            {error && <Typography color="error">{error}</Typography>}
            <Button type="submit" variant="contained" color="primary" fullWidth className="login-button">
              LOGIN
            </Button>
          </form>
          <Typography variant="body2" className="register-text">
            Don't have an account? <Link to="/register">Register</Link>
          </Typography>
        </Box>

        {/* Right Car Image */}
        <Box className="car-placeholder">
          <img src={carImage2} alt="Car 2" className="car-image" />
        </Box>
      </Box>

      <Footer showFooter={true} />
    </Container>
  );
};

export default Login;
