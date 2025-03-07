import React from 'react';
import Header from '../components/Header';
import ReportList from '../components/ReportsList';

const Adminpage = () => {



    return (
       <>
       <Header showRightButtons = {false}/>
       
       <div style={{ textAlign: "center", marginTop: "20px" }}>
               <h2 style={{ fontSize: "24px", fontWeight: "bold" }}>Reports List</h2>
           </div>
       <ReportList/>
       
       
       </>
   );
};


export default Adminpage;