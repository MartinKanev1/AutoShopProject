import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; 
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
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import { getUserInfo, getDealershipIdByUserId } from '../services/userservice';
import { useNavigate } from 'react-router-dom';
import { getSearchHistory } from '../services/OffersService';
import '../styles/Header.css';

const Header = ({setCriteria, fetchFilteredOffers, showRightButtons = true, showSearchOnly = false }) => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [historyOpen, setHistoryOpen] = useState(false);
  const [user, setUser] = useState(null);
  const [searchHistory, setSearchHistory] = useState([]);
  const navigate = useNavigate();

  const [dealershipId, setDealershipId] = useState(null);
  const [isAdmin, setAdmin] = useState(false);

  useEffect(() => {
    fetchHistory();
    const fetchUserData = async () => {
      try {
        const userId = localStorage.getItem('userId');
        
  
        const storedUser = await getUserInfo();
        if (storedUser) {
          setUser(storedUser);
        }

        if (storedUser?.role === 'Admin') {
          setAdmin(true);
        }
  
        if (storedUser?.type === 'CAR_DEALERSHIP') {
          const dealership = await getDealershipIdByUserId(userId);
          setDealershipId(dealership);
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };
  
    fetchUserData(); 
  
  }, []);



  const handleNavigate = () => {
    navigate("/my-favourites-offers"); 
 };


  const handleMyOffersClick = () => {
    if (!user) {
      navigate('/login'); 
      return;
    }
  
    if (user.type === 'PRIVATE_SELLER') {
      navigate('/my-offers'); 
    } else if (user.type === 'CAR_DEALERSHIP' && dealershipId) {
      navigate(`/dealership-info/${dealershipId}`); 
    }
  };

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

    if (open) {
      fetchHistory(); 
  }
  };


  const fetchHistory = async () => {
    try {
      const userId = localStorage.getItem('userId'); 
      if (!userId) return; 

      const history = await getSearchHistory(userId);
      console.log("📜 Search History from API:", history);

      
      const parsedHistory = history.map((item) => ({
        ...item,
        searchCriteria: JSON.parse(item.searchCriteria), 
      }));

      setSearchHistory(parsedHistory);
    } catch (error) {
      console.error("❌ Error fetching search history:", error);
    }
  };

  const restoreSearch = (search) => {
    console.log("🔄 Restoring search:", search);
    setCriteria(search);
    fetchFilteredOffers();
    setHistoryOpen(false); 
  };


  

  return (
    <>
      <AppBar position="sticky" className="header" sx={{ width: '100vw', backgroundColor: 'black' }}>
        <Toolbar sx={{ minHeight: '60px !important' }}>
          {/* Menu Button */}
          <IconButton edge="start" className="menu-button" color="inherit" aria-label="menu" onClick={toggleDrawer(true)}>
            <MenuIcon />
          </IconButton>

          {/* Logo */}
          <Typography variant="h6" className="logo" sx={{ fontSize: '20px' }}>
            CarsHub
          </Typography> 

          {/* Right Icons - Conditional Rendering */}
      {(showRightButtons || showSearchOnly) && (
      <Box className="header-icons">
     {showRightButtons && (
      <>
        <IconButton color="inherit"  onClick={handleNavigate}>
          <FavoriteBorderIcon />
        </IconButton>
        <IconButton color="inherit" onClick={toggleHistoryDrawer(true)}>
          <HistoryIcon />
        </IconButton>
      </>
      )}
    {/* <IconButton color="inherit">
      <SearchIcon />
    </IconButton> */}
  </Box>
)}
        </Toolbar>
      </AppBar>

      
      <Drawer anchor="left" open={menuOpen} onClose={toggleDrawer(false)}>
  <Box 
    sx={{ 
      width: 250, 
      backgroundColor: 'black', 
      height: '100vh', 
      color: 'white', 
      display: 'flex', 
      flexDirection: 'column'
    }}
  >
    {/* Top Menu Items */}
    <List sx={{ flexGrow: 1 }}>
      <ListItem button component={Link} to="/publish-offer">
        <ListItemIcon><AddCircleIcon sx={{ color: 'white' }} /></ListItemIcon>
        <ListItemText primary="Publish Offer" />
      </ListItem>
      <ListItem button component={Link} to="/view-all-dealerships">
        <ListItemIcon><StoreIcon sx={{ color: 'white' }} /></ListItemIcon>
        <ListItemText primary="Car Dealerships" />
      </ListItem>
      <ListItem button component={Link} to="/my-account">
        <ListItemIcon><AccountCircleIcon sx={{ color: 'white' }} /></ListItemIcon>
        <ListItemText primary="My Account" />
      </ListItem>
      <Box sx={{ cursor: "pointer" }}>
  <ListItem button onClick={handleMyOffersClick} component="div">
    <ListItemIcon>
      <DirectionsCarIcon sx={{ color: 'white' }} />
    </ListItemIcon>
    <ListItemText primary="My Offers" />
  </ListItem>
</Box>
      <ListItem button component={Link} to="/home">
        <ListItemIcon><HomeIcon sx={{ color: 'white' }} /></ListItemIcon>
        <ListItemText primary="Home" />
      </ListItem>
      {isAdmin && (
        <ListItem button component={Link} to="/admin">
          <ListItemIcon><AdminPanelSettingsIcon sx={{ color: 'white' }} /></ListItemIcon>
          <ListItemText primary="Admin" />
        </ListItem>
      )}
    </List>

    {/* Logout Button Pinned to Bottom */}
    <List sx={{ marginTop: 'auto' }}>
      <ListItem button component={Link} to="/login" sx={{ position: 'absolute', bottom: 10, left: 0, width: '100%' }}>
        <ListItemIcon><ExitToAppIcon sx={{ color: 'white' }} /></ListItemIcon>
        <ListItemText primary="Logout" />
      </ListItem>
    </List>
  </Box>
</Drawer>


      

       

      {/* Search History Drawer */}
      <Drawer anchor="right" open={historyOpen} onClose={toggleHistoryDrawer(false)}>
        <Box sx={{ width: 300, backgroundColor: 'white', height: '100vh', color: 'black', padding: '20px' }}>
          <Typography variant="h6">Search History</Typography>
          <List>
            {searchHistory.length > 0 ? (
              searchHistory.map((item, index) => (
                <ListItem key={index} button onClick={() => restoreSearch(item.searchCriteria)}>
                  <ListItemText
                    primary={Object.entries(item.searchCriteria)
                      .filter(([key, val]) => val !== "" && val !== null)
                      .map(([key, val]) => `${key}: ${val}`)
                      .join(", ")}
                  />
                </ListItem>
              ))
            ) : (
              <ListItem>
                <ListItemText primary="No recent searches found." />
              </ListItem>
            )}
          </List>
        </Box>
      </Drawer>


    </>
  );
};

export default Header;
