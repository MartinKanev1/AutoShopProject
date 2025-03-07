import React, { useState, useEffect } from "react";
import { getAllReports } from "../services/OffersService";
import ReportsCard from "./ReportsCard"; 

const ReportList = () => {
    const [reports, setReports] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchReports = async () => {
            try {
                const data = await getAllReports();
                setReports(data);
            } catch (error) {
                console.error("❌ Error fetching reports:", error);
                setError("Failed to load reports. Please try again.");
            } finally {
                setLoading(false);
            }
        };
            
        fetchReports();
    }, []);

    if (loading) {
        return <p style={{ textAlign: "center", fontSize: "18px" }}>Loading reports...</p>;
    }

    if (error) {
        return <p style={{ textAlign: "center", fontSize: "18px", color: "red" }}>{error}</p>;
    }

    return (
        <div className="p-4 space-y-4">
            {reports.length > 0 ? (
                reports.map((report) => (
                    <ReportsCard key={report.reportId} report={report} />
                ))
            ) : (
                <p style={{ textAlign: "center", fontSize: "18px" }}>No reports found.</p>
            )}
        </div>
    );
};

export default ReportList;
