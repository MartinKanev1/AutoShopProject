import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getUserIdByDealershipId, getUserInfo1, fetchUserCarDealershipLogo1 } from "../services/UserService"; 
import defaultImage from "../assets/test-logo.png";
import OfferforDealershipList from "./OffersListforDealership";

const DealershipDetails = () => {
    const { dealershipId } = useParams();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [imageSrc, setImageSrc] = useState(defaultImage); 
    const [Id, StoreId] = useState(null); 


    useEffect(() => {
        const loadUserDetails = async () => {
            try {
                
                const userId = await getUserIdByDealershipId(dealershipId);
                console.log(`✅ Found userId: ${userId} for dealershipId: ${dealershipId}`);
                StoreId(userId);

                
                const userData = await getUserInfo1(userId); 
                setUser(userData);

                const logoUrl = await fetchUserCarDealershipLogo1(userId);
                if (logoUrl) {
                    setImageSrc(logoUrl); 
                }

            } catch (error) {
                setError("Failed to load dealership details.");
            } finally {
                setLoading(false);
            }
        };

        loadUserDetails();
    }, [dealershipId]);

    if (loading) return <p style={{ textAlign: "center" }}>Loading...</p>;
    if (error) return <p style={{ textAlign: "center", color: "red" }}>{error}</p>;
    if (!user) return <p style={{ textAlign: "center" }}>No details available.</p>;

    return (
        <>
        <div style={{
            display: "flex",
            border: "2px solid black",
            padding: "20px",
            maxWidth: "800px",
            margin: "20px auto",
            borderRadius: "10px",
            backgroundColor: "#fff",
            boxShadow: "4px 4px 10px rgba(0,0,0,0.1)"
        }}>

            {/* Left Side: Dealership Logo */}
            <div style={{
                width: "150px",
                height: "150px",
                border: "1px solid black",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                marginRight: "20px",
                backgroundColor: "#f0f0f0"
            }}>
                <img
                    src={imageSrc}  
                    alt={user.carDealership?.name || "Dealership Logo"}
                    style={{ width: "100%", height: "100%", objectFit: "cover" }}
                />
            </div>

            {/* Right Side: User & Dealership Info */}
            <div style={{ flex: 1 }}>

                <h2>{user.carDealership?.name || "No Dealership Name"}</h2>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Phone:</strong> {user.phoneNumber}</p>

                {/* City, Region, Address */}
                <div style={{
                    display: "flex",
                    justifyContent: "space-between",
                    marginTop: "20px"
                }}>
                    <p><strong>City:</strong> {user.city}</p>
                    <p><strong>Region:</strong> {user.region}</p>
                    <p><strong>Address:</strong> {user.carDealership.address}</p>
                </div>
            </div>
            
        </div>
         <OfferforDealershipList userId={Id} />
        </>
    );
};

export default DealershipDetails;
