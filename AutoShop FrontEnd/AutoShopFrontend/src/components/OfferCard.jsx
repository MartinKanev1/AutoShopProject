import React, { useState, useEffect } from "react";
import { Heart, Edit } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { GetOfferImage, getOfferOwnerId } from "../services/OffersService";
import { AddToFavourites, RemoveFromFavourites, GetUserFavourites } from "../services/FavouritesService"; 


const OfferCard = ({ id, title, brand, model, price ,showHeart = true, onEdit }) => {
    const navigate = useNavigate();
    const [imageSrc, setImageSrc] = useState(null);
    const [ownerId, setOwnerId] = useState(null);
    const [isOwner, setIsOwner] = useState(false);
    const [currentUserId, setCurrentUserId] = useState(null);
    const [isFavorited, setIsFavorited] = useState(false);

    useEffect(() => {
        const storedUserId = localStorage.getItem("userId");
        if (storedUserId) {
            setCurrentUserId(storedUserId);
        }
    }, []);

    useEffect(() => {
        const checkOwnership = async () => {
            try {
                const fetchedOwnerId = await getOfferOwnerId(id);
                setOwnerId(fetchedOwnerId);
                setIsOwner(String(fetchedOwnerId) === String(currentUserId));
            } catch (error) {
                console.error(`Error fetching ownerId for offer ${id}:`, error);
            }
        };

        if (currentUserId !== null) {
            checkOwnership();
        }
    }, [id, currentUserId]);

    useEffect(() => {
        const loadImage = async () => {
            try {
                const logoUrl = await GetOfferImage(id);
                if (logoUrl) {
                    setImageSrc(logoUrl);
                }
            } catch (error) {
                console.error("Failed to load image", error);
            }
        };

        loadImage();
    }, [id]);


    useEffect(() => {
        const fetchFavorites = async () => {
            try {
                const favorites = await GetUserFavourites();
                setIsFavorited(favorites.includes(id));
            } catch (error) {
                console.error("Error checking if offer is favorited:", error);
            }
        };

        fetchFavorites();
    }, [id]);

    const handleFavoriteToggle = async (e) => {
        e.stopPropagation(); 

        try {
            if (isFavorited) {
                const success = await RemoveFromFavourites(id);
                if (success) {
                    setIsFavorited(false);
                }
            } else {
                const success = await AddToFavourites(id);
                if (success) {
                    setIsFavorited(true);
                }
            }
        } catch (error) {
            console.error("Error toggling favorite:", error);
        }
    };



    return (
        <div 
            onClick={() => navigate(`/view-offer/${id}`)}
            style={{
                width: "240px",
                border: "1px solid black",
                borderRadius: "8px",
                overflow: "hidden",
                margin: "10px",
                textAlign: "center",
                backgroundColor: "#fff",
                boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.1)",
                cursor: "pointer",
                position: "relative"
            }}
        >
            {/* Image Section (Covers Full Width & Height) */}
            <div style={{
                height: "200px", 
                position: "relative", 
                overflow: "hidden",
            }}>
                <img
                    src={imageSrc}
                    alt={title}
                    style={{ width: "100%", height: "100%", objectFit: "cover" }}
                />

                {/* Buttons Positioned Over Image */}
                {showHeart && (
                    <Heart 
                    onClick={handleFavoriteToggle}
                        style={{ 
                            position: "absolute", 
                            top: "10px", 
                            right: "10px", 
                            cursor: "pointer", 
                            background: "white",
                            padding: "6px",
                            borderRadius: "50%",
                            boxShadow: "0px 0px 5px rgba(0,0,0,0.2)"
                        }}
                        // color="red"
                        color={isFavorited ? "red" : "black"}
                        fill={isFavorited ? "red" : "none"}
                    />
                )}
                {isOwner && (
                    <Edit 
                        style={{ 
                            position: "absolute", 
                            top: "10px", 
                            left: "10px", 
                            cursor: "pointer",
                            background: "white",
                            padding: "6px",
                            borderRadius: "50%",
                            boxShadow: "0px 0px 5px rgba(0,0,0,0.2)"
                        }}
                        onClick={(e) => {
                            navigate(`/edit-offer/${id}`);
                            e.stopPropagation();
                            onEdit && onEdit(id);
                        }}
                    />
                )}
            </div>

            {/* Details Section */}
            <div style={{ padding: "10px", borderTop: "1px solid black" }}>
                <h3 style={{ margin: "5px 0", fontSize: "17px" }}>{title}</h3>
                <p style={{ margin: "0", color: "gray", fontSize: "16px" }}>{brand + " " + model}</p>
                
                <p style={{ margin: "0", color: "gray", fontSize: "16px" }}>{price + " $ "}</p>
            </div>
        </div>
    );
};

export default OfferCard;
