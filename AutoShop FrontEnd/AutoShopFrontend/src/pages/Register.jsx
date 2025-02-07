import React, { useState } from "react";
import { Container, TextField, Button, Typography, Box, MenuItem } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Register.css";
import carImage1 from '../assets/car1.jpg';
import carImage2 from '../assets/car2.jpg';
import Footer from '../components/Footer';
import { registerUser } from "../services/authService"; // Импортираме API функцията

const sellerTypes = [
  { value: "PRIVATE_SELLER", label: "Private Seller" },
  { value: "CAR_DEALERSHIP", label: "Car Dealership" }
];

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: "",
    region: "",
    city: "",
    type: "PRIVATE_SELLER",
    dealership: {
      name: "",
      dateOfCreation: "",
      logoImageName: "",
      address: "",
      logoImageType: ""
    }
  });

  const [sellerType, setSellerType] = useState("PRIVATE_SELLER");
  const [error, setError] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const handleSellerTypeChange = (event) => {
    setSellerType(event.target.value);
    setFormData((prevData) => ({
      ...prevData,
      type: event.target.value
    }));
  };

  const handleDealershipChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      dealership: {
        ...prevData.dealership,
        [name]: value
      }
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await registerUser(formData);
      console.log("Registration successful:", response);
      alert("Registration successful!");
      navigate("/home"); // Пренасочване след успешна регистрация
    } catch (error) {
      console.error("Registration error:", error);
      setError("Registration failed. Please try again.");
    }
  };

  return (
    <Container maxWidth={false} className="register-container">
      <Box className="header">CarsHub</Box>
      <Box className="main-content">


       {/* Left Car Image */}
       <Box className="car-placeholder">
          <img src={carImage1} alt="Car 1" className="car-image" />
        </Box>

        <Box className={`register-box ${sellerType === 'CAR_DEALERSHIP' ? 'expanded' : ''}`}>

          <Typography variant="h5">Create an Account</Typography>
          <form onSubmit={handleSubmit}>
            <TextField label="First Name" name="firstName" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            <TextField label="Last Name" name="lastName" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            <TextField label="Email" name="email" type="email" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            <TextField label="Password" name="password" type="password" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            <TextField label="Phone Number" name="phoneNumber" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            <TextField label="Region" name="region" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            <TextField label="City" name="city" variant="outlined" fullWidth margin="normal" onChange={handleChange} />
            
            <TextField
              label="Seller Type"
              select
              variant="outlined"
              fullWidth
              margin="normal"
              name="type"
              value={sellerType}
              onChange={handleSellerTypeChange}
            >
              {sellerTypes.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </TextField>

            {sellerType === "CAR_DEALERSHIP" && (
              <>
                <TextField label="Dealership Name" name="name" variant="outlined" fullWidth margin="normal" onChange={handleDealershipChange} />
                <TextField label="Date of Creation" name="dateOfCreation" type="date" variant="outlined" fullWidth margin="normal" InputLabelProps={{ shrink: true }} onChange={handleDealershipChange} />
                <TextField label="Address" name="address" variant="outlined" fullWidth margin="normal" onChange={handleDealershipChange} />
              </>
            )}

            {error && <Typography color="error">{error}</Typography>}

            <Button type="submit" variant="contained" color="primary" fullWidth className="register-button">
              REGISTER
            </Button>
          </form>
          <Typography variant="body2" className="login-text">
            Already have an account? <Link to="/login" style={{ color: "blue" }}>Login</Link>
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

export default Register;
