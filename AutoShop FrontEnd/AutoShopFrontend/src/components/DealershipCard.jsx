import React, { useEffect, useState } from "react";
import CarDealershipService from "../services/CarDealershipService";
import defaultImage from "../assets/test-logo.png"; 
import { useNavigate } from "react-router-dom";


const DealershipCard = ({ id, name, city }) => {
    const navigate = useNavigate();
    

    const [imageSrc, setImageSrc] = useState(defaultImage); 

    useEffect(() => {
        const loadImage = async () => {
            try {
                const logoUrl = await CarDealershipService.fetchDealershipLogo(id);
                if (logoUrl) {
                    setImageSrc(logoUrl); 
                }
            } catch (error) {
                console.error(`❌ Failed to load logo for ${name}`, error);
            }
        };

        loadImage();
    }, [id]);

    return (
        <div onClick={() => navigate(`/dealership-info/${id}`)}
        style={{
            width: "220px",
            border: "1px solid black",
            borderRadius: "8px",
            overflow: "hidden",
            margin: "10px",
            textAlign: "center",
            backgroundColor: "#fff",
            boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.1)",
            cursor: "pointer"
        }}>
            {/* Logo Section */}
            <div style={{
                height: "140px",
                backgroundColor: "#f0f0f0",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
            }}>
                <img
                    src={imageSrc}
                    alt={name}
                    onError={(e) => (e.target.src = defaultImage)} 
                    style={{ maxWidth: "100%", maxHeight: "100%" }}
                />
            </div>

            {/* Details Section */}
            <div style={{ padding: "10px", borderTop: "1px solid black" }}>
                <h3 style={{ margin: "5px 0", fontSize: "16px" }}>{name}</h3>
                <p style={{ margin: "0", color: "gray", fontSize: "14px" }}>{city}</p>
            </div>
        </div>
    );
};

export default DealershipCard;
