import React, { useState, useEffect } from "react";
import DealershipCard from "./DealershipCard";
import CarDealershipService from "../services/CarDealershipService";

const DealershipList = () => {
    const [dealerships, setDealerships] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDealerships = async () => {
            try {
                const data = await CarDealershipService.getAllDealerships();
                setDealerships(data);
            } catch (error) {
                console.error("❌ Error fetching dealerships:", error);
                setError("Failed to load dealerships. Please try again.");
            } finally {
                setLoading(false);
            }
        };

        fetchDealerships();
    }, []);

    
    const filteredDealerships = dealerships.filter((dealer) =>
        dealer.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) {
        return <p style={{ textAlign: "center", fontSize: "18px" }}>Loading dealerships...</p>;
    }

    if (error) {
        return <p style={{ textAlign: "center", fontSize: "18px", color: "red" }}>{error}</p>;
    }

    return (
        <div style={{ padding: "20px" }}>
            {/* 🔍 Search Input */}
            <input
                type="text"
                placeholder="Search for a dealership..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{
                    width: "100%",
                    maxWidth: "400px",
                    padding: "10px",
                    fontSize: "16px",
                    border: "1px solid #ccc",
                    borderRadius: "5px",
                    display: "block",
                    margin: "0 auto 20px auto",
                }}
            />

            {/* Dealership List */}
            <div style={{
                display: "flex",
                flexWrap: "wrap",
                gap: "20px",
                justifyContent: "center",
            }}>
                {filteredDealerships.length > 0 ? (
                    filteredDealerships.map((dealer) => (
                        <DealershipCard
                            key={dealer.dealershipId}
                            id={dealer.dealershipId}
                            name={dealer.name}
                            city={dealer.address}
                        />
                    ))
                ) : (
                    <p style={{ textAlign: "center", fontSize: "18px" }}>No dealerships found.</p>
                )}
            </div>
        </div>
    );
};

export default DealershipList;
