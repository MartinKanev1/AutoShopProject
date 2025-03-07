import axios from "axios";

const API_URL = "http://localhost:8080/api/cardealerships";

const CarDealershipService = {
    getAllDealerships: async () => {
        try {
            const token = localStorage.getItem("jwt");  
            const response = await axios.get(API_URL, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        } catch (error) {
            console.error("❌ Error fetching dealerships:", error);
            throw error;
        }
    },


    getDealershipById: async (dealershipId) => {
        try {
            const token = localStorage.getItem("jwt");
            const response = await axios.get(`${API_URL}/${dealershipId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            return response.data;
        } catch (error) {
            console.error(`❌ Error fetching dealership details for ID ${dealershipId}:`, error);
            throw error;
        }
    },

    fetchDealershipLogo: async (dealershipId) => {
        try {
            const token = localStorage.getItem("jwt");
            if (!dealershipId) {
                console.error(`❌ Invalid dealership ID: ${dealershipId}`);
                return null;
            }
    
            console.log(`📢 Making API request for dealership ID: ${dealershipId}`);
    
            const response = await axios.get(`http://localhost:8080/api/cardealerships/${dealershipId}/logo`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                responseType: "blob", 
            });
    
            console.log(`✅ API Response for dealership ID ${dealershipId}:`, response);
    
            
            if (!response || !response.data) {
                console.error(`❌ No data received for dealership ID: ${dealershipId}`);
                return null;
            }
    
            const imageUrl = URL.createObjectURL(response.data);
            console.log(`✅ Generated image URL:`, imageUrl);
    
            return imageUrl; 
        } catch (error) {
            console.error(`❌ Failed to load logo for dealership ${dealershipId}:`, error);
            return null;
        }
    },
};    

export default CarDealershipService;
