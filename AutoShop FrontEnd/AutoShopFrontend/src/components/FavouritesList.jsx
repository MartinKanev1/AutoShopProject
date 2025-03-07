import React, { useState, useEffect } from "react";
import OfferCard from "./OfferCard";
import { GetUserFavourites } from "../services/FavouritesService";
import { GetOfferById } from "../services/OffersService";


const FavouritesList = () => {
    const [offers, setOffers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    

   

    useEffect(() => {
        const fetchOffers = async () => {
            try {
                
                const offerIds = await GetUserFavourites();
                if (!offerIds || offerIds.length === 0) {
                    setOffers([]); 
                    return;
                }

                
                const offerPromises = offerIds.map(id => GetOfferById(id));
                const offerData = await Promise.all(offerPromises);

                setOffers(offerData);
            } catch (error) {
                console.error("❌ Error fetching offers:", error);
                setError("Failed to load offers. Please try again.");
            } finally {
                setLoading(false);
            }
        };

        fetchOffers();
    }, []);
    
    
    

    

    if (loading) {
        return <p style={{ textAlign: "center", fontSize: "18px" }}>Loading favourite offers...</p>;
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
                    <p style={{ textAlign: "center", fontSize: "18px" }}>You haven't added any offers to favourites yet.</p>
                )}
            </div>
        </div>
    );
};

export default FavouritesList;
