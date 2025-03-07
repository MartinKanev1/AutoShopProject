import React from 'react';
import Header from '../components/Header';
import DealershipDetails from "../components/DealershipDetails";



const DealershipsInfo = () => {
      
    return (
        <>
        <Header showRightButtons={false}  />
            <DealershipDetails/>
    
        </>
    );
 };

 export default DealershipsInfo;