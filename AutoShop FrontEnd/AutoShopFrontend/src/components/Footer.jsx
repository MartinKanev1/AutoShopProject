import React from 'react';
import { Box, Typography } from '@mui/material';

const Footer = ({ showFooter = true }) => {
  if (!showFooter) return null;

  return (
    <Box 
      className="footer" 
      sx={{ 
        width: '100vw', 
        maxWidth: '100%', 
        display: 'flex', 
        justifyContent: 'space-between', 
        alignItems: 'center', 
        padding: '10px 20px', 
        backgroundColor: 'black', 
        color: 'white',
        position: 'fixed',
        bottom: 0,
        left: 0,
        boxSizing: 'border-box',
        overflow: 'hidden'
      }}
    >
      <Typography variant="body2">Email: carshub@gmail.com</Typography>
      <Typography variant="body2" sx={{ fontWeight: 'bold' }}>® CarsHub</Typography>
      <Typography variant="body2">Phone: +359 88 696 9420</Typography>
    </Box>
  );
};

export default Footer;