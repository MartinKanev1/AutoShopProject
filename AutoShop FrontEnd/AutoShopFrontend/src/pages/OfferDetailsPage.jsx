import React from "react";
import { useState, useEffect } from "react";
import Header from "../components/Header";
import { useParams, useNavigate } from "react-router-dom";
import { GetOfferById, GetOfferImage, getOfferOwnerId } from "../services/OffersService";
import '../styles/OfferDetails.css';
import {  getDealershipIdByUserId, getUserInfo1 } from "../services/userservice";
import { Link } from "react-router-dom";
import { reportOffer } from "../services/OffersService";

const OfferDetails = () => {
    const { offerId } = useParams();
    const navigate = useNavigate();
  
  
    const [offerData, setOfferData] = useState({
      title: '',
      description: '',
      status: '',
      brand: '',
      category: '',
      model: '',
      yearOfCreation: '',
      fuel: '',
      price: '',
      mileage: '',
      gear: '',
      color: '',
      power: '',
      doorCount: '',
      image: null,
    });


 const [imagePreview, setImagePreview] = useState(null);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [userData, setUserData] = useState(null);
  const [userId, setUserId] = useState(null);
  const [dealershipId, setDealershipId] = useState(null);

  const [isReporting, setIsReporting] = useState(false);
  const [reportReason, setReportReason] = useState("");
  const [isReadOnly, setIsReadOnly] = useState(true);


useEffect(() => {
    const fetchOfferDetails = async () => {
      try {
        const offer = await GetOfferById(offerId);
        setOfferData(offer);
         

        
        const imageUrl = await GetOfferImage(offerId);
        console.log("Fetching Image from URL:", imageUrl);
        if (imageUrl) {
          setImagePreview(imageUrl);
        }
      } catch (error) {
        setMessage({ type: 'error', text: 'Failed to load offer details.' });
      }
    };

    fetchOfferDetails();
  }, [offerId]);


  useEffect(() => {
    const fetchUserData = async () => {
        try {
            const fetchedUserId = await getOfferOwnerId(offerId);
            console.log("Fetched User ID:", fetchedUserId);
            setUserId(fetchedUserId);
        } catch (err) {
            setMessage({ type: 'error', text: 'Failed to load user data.' });
        }
    };
    fetchUserData();
}, [offerId]);



useEffect(() => {
  const fetchDealershipId = async () => {
    try {
      const fetchId = await getDealershipIdByUserId(userId);
      setDealershipId(fetchId);
    } catch(err) {
      setMessage({type: 'error', text: 'Failed to getdealershipId'});
    }
  };
  fetchDealershipId();
}, [userId]);



useEffect(() => {
    const fetchUserDetails = async () => {
        if (!userId) return;

        try {
            const userdata = await getUserInfo1(userId);
            console.log("Fetched User Data:", userdata);
            setUserData(userdata);
        } catch (err) {
            setMessage({ type: 'error', text: 'Failed to load user details.' });
        } finally {
            setLoading(false);
        }
    };

    fetchUserDetails();
}, [userId]);


const handleReportSubmit = async () => {
  if (!reportReason.trim()) {
      alert("Please enter a report reason.");
      return;
  }

  const userId = localStorage.getItem("userId"); 

  if (!userId) {
      alert("User not found. Please log in again.");
      return;
  }

  try {
      await reportOffer(offerId, { reason: reportReason, userId: parseInt(userId) });
      alert("Report submitted successfully!");
      setIsReporting(false);
      setReportReason("");
  } catch (error) {
      alert("Failed to submit report.");
  }
};

 
 

  return (
    <>
        <Header showRightButtons={false} />
        <div className="offer-container">
  <div className="offer-box1">
    <form>
      <div className="offer-content1">
        {/* Left Section - Vehicle Details */}
        
        <div className="offer-details">
        <p><strong>Title:</strong> {offerData.title}</p>
      <p><strong>Description:</strong> {offerData.description}</p>
      <p><strong>Status:</strong> {offerData.status}</p>
      <p><strong>Price:</strong> {offerData.price} $</p>

          <div className="vehicle-details">
            <p><strong>Category:</strong> {offerData.category}</p>
            <p><strong>Brand:</strong> {offerData.brand}</p>
            <p><strong>Gearbox:</strong> {offerData.gear}</p>
            <p><strong>Model:</strong> {offerData.model}</p>
            <p><strong>Color:</strong> {offerData.color}</p>
            <p><strong>Year:</strong> {offerData.yearOfCreation}</p>
            <p><strong>Doors:</strong> {offerData.doorCount}</p>
            <p><strong>Fuel:</strong> {offerData.fuel}</p>
            <p><strong>Mileage:</strong> {offerData.mileage} km</p>
            <p><strong>Power:</strong> {offerData.power} HP</p>
          </div>

          <div className="report-section">
                                    {!isReporting ? (
                                        <button 
                                            className="report-button"
                                            onClick={() => setIsReporting(true)}
                                        >
                                            Report
                                        </button>
                                    ) : (
                                        <div className="report-form">
                                            <textarea
                                                placeholder="Enter report reason..."
                                                value={reportReason}
                                                onChange={(e) => setReportReason(e.target.value)}
                                            />
                                            <div className="report-buttons">
                                                <button onClick={handleReportSubmit}>Submit</button>
                                                <button onClick={() => setIsReporting(false)}>Cancel</button>
                                            </div>
                                        </div>
                                    )}
                                </div>
        </div>

        



        {/* Right Section - Image Preview */}
        <div className="image-upload">
          <div className="image-preview">
            {imagePreview ? (
              <img src={imagePreview} alt="Preview" />
            ) : (
              <p>No Image Available</p>
            )}
          </div>
          <div className="seller-info">
    {userData ? (
        userData.type === "CAR_DEALERSHIP" && userData.carDealership ? (
            <>
                
                
                <Link to={`/dealership-info/${dealershipId}`}>
    <button className="dealership-button">
        <strong>Dealership:</strong> {userData.carDealership?.name || "N/A"}
    </button>
</Link>



                <p><strong>Phone Number:</strong> {userData.phoneNumber || "N/A"}</p>
                <p><strong>Address:</strong> {userData.region || "N/A"}, {userData.carDealership?.address || "N/A"}</p>
            </>
        ) : userData.type === "PRIVATE_SELLER" ? (
            <>
                <p><strong>Seller Name:</strong> {userData.name || "N/A"}</p>
                <p><strong>Phone Number:</strong> {userData.phoneNumber || "N/A"}</p>
                <p><strong>Region:</strong> {userData.region || "N/A"}</p>
            </>
        ) : (
            <p>User type not recognized.</p>
        )
    ) : (
        <p>Loading user details...</p>
    )}
    

</div>



        </div>
        {/* ✅ Report Section */}
        
      </div>

      
      
    </form>
  </div>
</div>

    </>
);
};

export default OfferDetails;