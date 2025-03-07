import React from "react";
import "../styles/ReportCard.css"; 
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getOfferIdByReportId, DeleteOffer } from "../services/OffersService";


const ReportsCard = ({ report }) => {

    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    

    const handleViewOffer = async () => {
        setLoading(false);
        const offerId = await getOfferIdByReportId(report.reportId);
        setLoading(false);
    
        if (offerId) {
          navigate(`/view-offer/${offerId}`);
        } else {
          alert("❌ Offer not found for this report.");
        }
      };

      const handleDeleteOffer = async () => {
          if (!window.confirm("Are you sure you want to delete this offer? This action cannot be undone.")) {
            return;
          }
      
          setLoading(true);
          setMessage(null);
      
          try {
            const offerId = await getOfferIdByReportId(report.reportId);
            await DeleteOffer(offerId);
            setMessage({ type: 'success', text: 'Offer deleted successfully!' });
      
            setTimeout(() => {
              navigate('/home');
            }, 1500);
          } catch (error) {
            setMessage({ type: 'error', text: 'Failed to delete offer. Please try again.' });
          } finally {
            setLoading(false);
          }
        };



  return (
    <div className="report-card">
      {/* Left Side: Reason */}
      <span className="report-reason">{report.reason}</span>

      {/* Right Side: Buttons */}
      <div className="button-group">
        <button className="button button1" onClick={handleViewOffer} >View Offer</button>
        <button className="button button2"  onClick={handleDeleteOffer}>Delete Offer</button>
      </div>
    </div>
  );
};

export default ReportsCard;
