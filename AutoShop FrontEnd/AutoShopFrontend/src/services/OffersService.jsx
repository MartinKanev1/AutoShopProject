import axios from "axios";

const API_URL = "http://localhost:8080/api/offers";

export const CreateOffer = async (OfferData, Image) => {
    try {
      const token = localStorage.getItem("jwt"); 
      const userId = localStorage.getItem("userId"); 
  
      
      const formData = new FormData();
  
      
      formData.append("offerDTO", new Blob([JSON.stringify(OfferData)], { type: "application/json" }));
      
      
      
      formData.append("Image", Image);
      
  
      
      const response = await axios.post(`${API_URL}/${userId}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      });
  
      return response.data;
    } catch (error) {
      console.error("❌ Failed to create an offer:", error.response?.status, error.response?.data);
      throw error;
    }
  };

  export const getAllOffers = async () => {
    try {
        const token = localStorage.getItem("jwt");  
        const response = await axios.get(API_URL, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error("❌ Error fetching offers:", error);
        throw error;
    }
};

export const getOfferOwnerId = async (offerId) => {
    try {
        const token = localStorage.getItem("jwt");
        console.log(`🔵 Fetching ownerId for offerId: ${offerId}`); 

        const response = await axios.get(`${API_URL}/${offerId}/owner`, {
            headers: { Authorization: `Bearer ${token}` },
        });

        console.log(`🟢 API Response for offer ${offerId}:`, response.data); 

        return response.data; 
    } catch (error) {
        console.error(`❌ Error fetching ownerId for offer ${offerId}:`, error);
        return null;
    }
};


export const getOffersByUser = async (userId) => {
    try {
        
        const token = localStorage.getItem("jwt"); 
        const response = await axios.get(`http://localhost:8080/api/users/${userId}/offers`, {
            headers: { Authorization: `Bearer ${token}` }, 
        });

        console.log("🟢 User Offers Response:", response.data); 

        return response.data; 
    } catch (error) {
        console.error("❌ Error fetching user offers:", error);
        return []; 
    }
};


export const reportOffer = async (offerId, reportData) => {
    try {
        const token = localStorage.getItem("jwt");

        const response = await axios.post(`${API_URL}/${offerId}/report`, reportData, {
            headers: { 
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            }, 
        });

        return response.data; 
    } catch (error) {
        console.error("❌ Error reporting the offer:", error);
        return null; 
    }
};

export const searchOffers = async (userId, criteria) => {
    try {

        const token = localStorage.getItem("jwt"); 

        const filteredCriteria = Object.fromEntries(
            Object.entries(criteria).filter(([_, v]) => v !== "" && v !== null)
        );
        console.log("➡️ Body:", JSON.stringify(filteredCriteria, null, 2));
        

      const response = await axios.post(`${API_URL}/search`, filteredCriteria, {
        params: { userId },
        headers: { "Content-Type": "application/json", Authorization: `Bearer ${token}`, },
      });
      return response.data;
    } catch (error) {
      console.error("❌ Error searching offers:", error);
      throw error;
    }
  };





  export const GetOfferImage = async (offerId) => {
    try {
        const token = localStorage.getItem("jwt"); 

        const response = await axios.get(`${API_URL}/${offerId}/image`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
            responseType: "blob", 
        });

        const imageUrl = URL.createObjectURL(response.data);
        console.log("✅ Offer image loaded successfully:", imageUrl);
        return imageUrl;
    } catch (error) {
        console.error("❌ Failed to load image:", error);
        return null; 
    }
};


  export const UpdateOffer = async (offerId, offerData, image) => {
    try {
        const token = localStorage.getItem("jwt"); 

        
        const formData = new FormData();

        
        formData.append("offerDTO", new Blob([JSON.stringify(offerData)], { type: "application/json" }));

        
        if (image) {
            formData.append("Image", image);
        }

        
        const response = await axios.put(`${API_URL}/${offerId}`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                Authorization: `Bearer ${token}`,
            },
        });

        return response.data;
    } catch (error) {
        console.error("❌ Failed to update the offer:", error.response?.status, error.response?.data);
        throw error;
    }
};

export const DeleteOffer = async (offerId) => {
  try {
      const token = localStorage.getItem("jwt"); 

      await axios.delete(`${API_URL}/${offerId}`, {
          headers: {
              Authorization: `Bearer ${token}`,
          },
      });

      console.log(`✅ Offer with ID ${offerId} deleted successfully.`);
  } catch (error) {
      console.error("❌ Failed to delete the offer:", error.response?.status, error.response?.data);
      throw error;
  }
};


export const GetOfferById = async (offerId) => {
  try {
      const token = localStorage.getItem("jwt"); 

      const response = await axios.get(`${API_URL}/${offerId}`, {
          headers: {
              Authorization: `Bearer ${token}`,
          },
      });

      return response.data;
  } catch (error) {
      console.error("❌ Failed to fetch the offer:", error.response?.status, error.response?.data);
      throw error;
  }
};

export const getSearchHistory = async (userId) => {
    try {
        const token = localStorage.getItem("jwt"); 

        const response = await axios.get(`http://localhost:8080/api/search-history/${userId}`, {
            headers: { 
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        });

        console.log("📜 Fetched search history:", response.data); 
        return response.data;
    } catch (error) {
        console.error("❌ Error fetching search history:", error.response ? error.response.data : error.message);
        return []; 
    }
};


export const getAllReports = async() => {
    try {
        const token = localStorage.getItem("jwt");

        const response = await axios.get(`${API_URL}/allreports`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("📜 Fetched reports:", response.data); 
        return response.data;

    } catch (error) {
        console.log("error fetching reports");
        return [];
    }
};

export const getOfferIdByReportId = async (reportId) => {
    try {
        const token = localStorage.getItem("jwt");
        const response = await axios.get(`${API_URL}/${reportId}/offer`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        console.log("📜 Fetched offerId:", response.data); 
        return response.data; 

    } catch (error) {
        console.error("❌ Error fetching offerId:", error);
        return null; 
    }
};
