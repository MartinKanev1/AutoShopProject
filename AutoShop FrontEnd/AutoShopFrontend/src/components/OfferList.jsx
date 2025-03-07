import React, { useState, useEffect } from "react";
import OfferCard from "./OfferCard";
import { getAllOffers, searchOffers } from "../services/OffersService";


const OfferList = ({ criteria = {} }) => {
    const [offers, setOffers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    

    


    useEffect(() => {
        const fetchOffers = async () => {
            setLoading(true);
            setError(null);

            try {
                const userId = localStorage.getItem("userId");
                const hasSearchCriteria = Object.values(criteria).some((val) => val !== "");
                console.log("🛠 Checking criteria on page load:", criteria);
                let data;

                if (hasSearchCriteria) {
                    console.log("📌 Calling searchOffers()");
                data = await searchOffers(userId, criteria); 
                } else {
                    data = await getAllOffers();
                }

                setOffers(data);
                console.log("✅ API Response:", data);
            } catch (error) {
                console.error("❌ Error fetching offers:", error);
                setError("Failed to load offers. Please try again.");
            } finally {
                setLoading(false);
            }
        };

        fetchOffers();
    }, [criteria]); 



    
    
    

    

    if (loading) {
        return <p style={{ textAlign: "center", fontSize: "18px" }}>Loading offers...</p>;
    }

    if (error) {
        return <p style={{ textAlign: "center", fontSize: "18px", color: "red" }}>{error}</p>;
    }

    return (
        <div style={{ padding: "20px" }}>
            {/* Offer List */}
            <div style={{
                display: "flex",
                flexWrap: "wrap",
                gap: "20px",
                justifyContent: "center",
            }}>
                {offers.length > 0 ? (
                    offers.map((offer) => (
                        <OfferCard 
                            key={offer.id} 
                            id={offer.offerId} 
                            title={offer.title} 
                            brand={offer.brand} 
                            model={offer.model} 
                            price={offer.price}
                            
                            
                            
                        />
                    ))
                ) : (
                    <p style={{ textAlign: "center", fontSize: "18px" }}>No offers found.</p>
                )}
            </div>
        </div>
    );
};

export default OfferList;
