import React, { useState, useEffect } from 'react';
import { Container, TextField, Typography, Box, Button, CircularProgress } from "@mui/material";
import "../styles/UserInfo.css";
import defaultLogo from "../assets/test-logo.png";
import Header from '../components/Header';
import Footer from '../components/Footer';
import { getUserInfo, updateUserInfo, deleteUser } from '../services/userservice';
import { useNavigate } from 'react-router-dom'; 
import { getUserCarDealershipLogoUrl } from "../services/userservice";
import { fetchUserCarDealershipLogo } from "../services/userservice"; 

const UserInfo = () => {
  const [isReadOnly, setIsReadOnly] = useState(true);
  const [userData, setUserData] = useState(null);
  const [originalData, setOriginalData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [updating, setUpdating] = useState(false);
  const [deleting, setDeleting] = useState(false); 
  const navigate = useNavigate(); 
  const [logoImage, setLogoImage] = useState(null);

  const [imageSrc, setImageSrc] = useState(defaultLogo);

useEffect(() => {
  const loadImage = async () => {
    const logoUrl = await fetchUserCarDealershipLogo(); 
    if (logoUrl) {
      setImageSrc(logoUrl); 
    }
  };

  loadImage();
}, []);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const data = await getUserInfo();
        setUserData(data);
        setOriginalData(data);
      } catch (err) {
        setError("Failed to load user data.");
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUserData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  
  const handleSave = async () => {
    setIsReadOnly(true);
    setUpdating(true);
    try {
      const updatedUser = await updateUserInfo(userData, logoImage);
      setUserData(updatedUser);
      setOriginalData(updatedUser);
      setLogoImage(null); 
    } catch (error) {
      setError("Failed to update user data.");
    } finally {
      setUpdating(false);
    }
  };

  
  const handleDelete = async () => {
    if (!window.confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
      return;
    }

    setDeleting(true);
    try {
      await deleteUser();
      console.log("✅ User deleted successfully!");
      navigate("/login"); 
    } catch (error) {
      console.error("❌ Error deleting user:", error);
      setError("Failed to delete user.");
    } finally {
      setDeleting(false);
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setLogoImage(file);
  };

  const handleCancel = () => {
    setUserData(originalData);
    setIsReadOnly(true);
    setLogoImage(null); 
  };

  if (loading) {
    return (
      <Container className="user-info-container">
        <CircularProgress />
        <Typography>Loading user data...</Typography>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="user-info-container">
        <Typography color="error">{error}</Typography>
      </Container>
    );
  }

  return (
    <>
      <Header showRightButtons={false} />
      <Container className="user-info-container">
        <Typography variant="h5" className="title">User Information</Typography>

        <Box className="user-column">
          <TextField 
            label="First Name" 
            name="firstName" 
            value={userData?.firstName || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
          <TextField 
            label="Last Name" 
            name="lastName" 
            value={userData?.lastName || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
          <TextField 
            label="Email" 
            name="email" 
            value={userData?.email || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
          <TextField 
            label="Phone" 
            name="phoneNumber" 
            value={userData?.phoneNumber || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
          <TextField 
            label="Region" 
            name="region" 
            value={userData?.region || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
          <TextField 
            label="City" 
            name="city" 
            value={userData?.city || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
        </Box>

        <Box className="user-seller-type-info">
          <TextField 
            label="Seller Type" 
            name="type" 
            value={userData?.type || ""} 
            onChange={handleChange} 
            fullWidth 
            margin="normal" 
            InputProps={{ readOnly: isReadOnly }} 
          />
          {userData?.type === "CAR_DEALERSHIP" && userData?.carDealership && (
            <Box className="cardealership-info">
              <TextField 
                label="Dealership Name" 
                name="carDealership.name" 
                value={userData.carDealership.name || ""} 
                onChange={(e) => 
                  setUserData((prev) => ({
                    ...prev, 
                    carDealership: { ...prev.carDealership, name: e.target.value }
                  }))
                } 
                fullWidth 
                margin="normal" 
                InputProps={{ readOnly: isReadOnly }} 
              />
              <TextField 
                label="Date of Creation" 
                name="carDealership.dateOfCreation" 
                value={userData.carDealership.dateOfCreation || ""} 
                fullWidth 
                margin="normal" 
                InputProps={{ readOnly: isReadOnly }} 
              />
              <TextField 
                label="Address" 
                name="carDealership.address" 
                value={userData.carDealership.address || ""} 
                onChange={(e) => 
                  setUserData((prev) => ({
                    ...prev, 
                    carDealership: { ...prev.carDealership, address: e.target.value }
                  }))
                } 
                fullWidth 
                margin="normal" 
                InputProps={{ readOnly: isReadOnly }} 
              />
              <Box className="car-image-box">
    <img src={imageSrc} alt="Dealership Logo" className="car-image" />
  </Box>



{!isReadOnly && (
  <input type="file" accept="image/*" onChange={handleFileChange} />
)}
            </Box>
          )}
        </Box>

        <Box className="button-group">
          {isReadOnly && (
            <Button 
              variant="contained" 
              color="primary" 
              className="edit-button" 
              onClick={() => setIsReadOnly(false)}
            >
              EDIT
            </Button>
          )}

          {!isReadOnly && (
            <>
              <Button 
                variant="contained" 
                color="success" 
                className="save-button" 
                onClick={handleSave}
                disabled={updating}
              >
                {updating ? <CircularProgress size={24} /> : "SAVE CHANGES"}
              </Button>

              <Button 
                variant="outlined" 
                color="secondary" 
                className="cancel-button" 
                onClick={handleCancel}
                disabled={updating}
              >
                CANCEL
              </Button>
            </>
          )}

          <Button 
            variant="contained" 
            color="error" 
            className="delete-button" 
            onClick={handleDelete}
            disabled={deleting}
          >
            {deleting ? <CircularProgress size={24} /> : "DELETE"}
          </Button>
        </Box>
      </Container>
      <Footer showFooter={true} />
    </>
  );
};

export default UserInfo;
