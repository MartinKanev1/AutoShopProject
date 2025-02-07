import React, { useState } from 'react';
import { Link } from 'react-router-dom'; // Импортирай Link
import { AppBar, Toolbar, IconButton, Typography, Box, Drawer, List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import SearchIcon from '@mui/icons-material/Search';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import StoreIcon from '@mui/icons-material/Store';
import HistoryIcon from '@mui/icons-material/History';
import HomeIcon from '@mui/icons-material/Home';
import '../styles/Header.css';

const Header = ({ showRightButtons = true }) => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [historyOpen, setHistoryOpen] = useState(false);

  const toggleDrawer = (open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setMenuOpen(open);
  };


  const toggleHistoryDrawer = (open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setHistoryOpen(open);
  };

  return (
    <>
      <AppBar position="static" className="header" sx={{ width: '100vw', backgroundColor: 'black' }}>
        <Toolbar sx={{ minHeight: '60px !important' }}>
          {/* Menu Button */}
          <IconButton edge="start" className="menu-button" color="inherit" aria-label="menu" onClick={toggleDrawer(true)}>
            <MenuIcon />
          </IconButton>

          {/* Logo */}
          <Typography variant="h6" className="logo" sx={{ fontSize: '20px' }}>
            CarsHub
          </Typography>

          {/* Right Icons */}
          {/* Right Icons - Conditional Rendering */}
          {showRightButtons && (
          <Box className="header-icons">
            <IconButton color="inherit">
              <FavoriteBorderIcon />
            </IconButton>
            <IconButton color="inherit" onClick={toggleHistoryDrawer(true)}>
              <HistoryIcon />
              </IconButton>
            <IconButton color="inherit">
              <SearchIcon />
            </IconButton>
          </Box>
          )}
        </Toolbar>
      </AppBar>

      {/* Drawer (Side Menu) */}
      <Drawer anchor="left" open={menuOpen} onClose={toggleDrawer(false)}>
        <Box sx={{ width: 250, backgroundColor: 'black', height: '100vh', color: 'white' }}>
          <List>
            <ListItem button component={Link} to="/publish-offer">
              <ListItemIcon><AddCircleIcon sx={{ color: 'white' }} /></ListItemIcon>
              <ListItemText primary="Publish Offer" />
            </ListItem>
            <ListItem button component={Link} to="/login">
              <ListItemIcon><ExitToAppIcon sx={{ color: 'white' }} /></ListItemIcon>
              <ListItemText primary="Logout" />
            </ListItem>
            <ListItem button component={Link} to="/car-dealerships">
              <ListItemIcon><StoreIcon sx={{ color: 'white' }} /></ListItemIcon>
              <ListItemText primary="Car Dealerships" />
            </ListItem>
            <ListItem button component={Link} to="/my-account">
              <ListItemIcon><AccountCircleIcon sx={{ color: 'white' }} /></ListItemIcon>
              <ListItemText primary="My Account" />
            </ListItem>
            <ListItem button component={Link} to="/my-offers">
              <ListItemIcon><DirectionsCarIcon sx={{ color: 'white' }} /></ListItemIcon>
              <ListItemText primary="My Offers" />
            </ListItem>
            <ListItem button component={Link} to="/home">
            <ListItemIcon><HomeIcon sx={{ color: 'white' }} /></ListItemIcon>
            <ListItemText primary="Home" />
            </ListItem>
          </List>
        </Box>
      </Drawer>

      

       {/* Search History Drawer */}
      <Drawer anchor="right" open={historyOpen} onClose={toggleHistoryDrawer(false)}>
        <Box sx={{ width: 300, backgroundColor: 'white', height: '100vh', color: 'black', padding: '20px' }}>
          <Typography variant="h6">Search History</Typography>
          <List>
            <ListItem button>
              <ListItemText primary="Recent Search 1" />
            </ListItem>
            <ListItem button>
              <ListItemText primary="Recent Search 2" />
            </ListItem>
            <ListItem button>
              <ListItemText primary="Recent Search 3" />
            </ListItem>
          </List>
        </Box>
      </Drawer>


    </>
  );
};

export default Header;
