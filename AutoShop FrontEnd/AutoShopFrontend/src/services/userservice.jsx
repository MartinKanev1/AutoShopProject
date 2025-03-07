import axios from "axios";

const API_URL = "http://localhost:8080/api/users";

export const getUserInfo = async () => {
  try {
    const token = localStorage.getItem("jwt"); 
    const userId = localStorage.getItem("userId");
    const response = await axios.get(`${API_URL}/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Failed to fetch user info:", error);
    throw error;
  }
};

export const getUserInfo1 = async (userId) => {
  try {
    const token = localStorage.getItem("jwt");
    if (!userId) throw new Error("❌ No userId provided!");

    const response = await axios.get(`${API_URL}/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error(`❌ Failed to fetch user info for userId ${userId}:`, error);
    throw error;
  }
};


export const getUserIdByDealershipId = async (dealershipId) => {
  try {
    const token = localStorage.getItem("jwt");
    const response = await axios.get(`${API_URL}/dealership/${dealershipId}/userId`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data; 
  } catch (error) {
    console.error(`❌ Failed to fetch userId for dealership ${dealershipId}:`, error);
    throw error;
  }
};


export const getDealershipIdByUserId = async (userId) => {
  try {
    const token = localStorage.getItem("jwt");
    const response = await axios.get(`${API_URL}/${userId}/dealershipId`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data; 
  } catch (error) {
    console.error(`❌ Failed to fetch dealershipId for user ${userId}:`, error);
    throw error;
  }
};



export const getUserCarDealershipLogoUrl = () => {
  const userId = localStorage.getItem("userId"); 
  if (!userId) return null; 
  return `http://localhost:8080/api/cardealerships/user/${userId}/logo`; 
};


export const fetchUserCarDealershipLogo = async () => {
  try {
    const token = localStorage.getItem("jwt");
    const userId = localStorage.getItem("userId");
    if (!userId) return null;

    const response = await axios.get(`http://localhost:8080/api/cardealerships/user/${userId}/logo`, {
      headers: {
        Authorization: `Bearer ${token}`, 
      },
      responseType: "blob", 
    });

    return URL.createObjectURL(response.data); 
  } catch (error) {
    console.error("❌ Failed to load image:", error);
    return null;
  }
};


export const fetchUserCarDealershipLogo1 = async (userId) => {
  try {
    const token = localStorage.getItem("jwt");
    if (!userId) {
      console.error("❌ fetchUserCarDealershipLogo called with null userId!");
      return null;
    }

    const response = await axios.get(`http://localhost:8080/api/cardealerships/user/${userId}/logo`, {
      headers: {
        Authorization: `Bearer ${token}`, 
      },
      responseType: "blob", 
    });

    const logoUrl = URL.createObjectURL(response.data);
    console.log(`✅ Successfully loaded logo for userId ${userId}:`, logoUrl);
    return logoUrl;
  } catch (error) {
    console.error(`❌ Failed to load image for userId ${userId}:`, error);
    return null;
  }
};







export const updateUserInfo = async (updatedUserData, logoImage) => {
  try {
    const token = localStorage.getItem("jwt"); 
    const userId = localStorage.getItem("userId"); 

    
    const formData = new FormData();

    
    formData.append("userDTO", new Blob([JSON.stringify(updatedUserData)], { type: "application/json" }));
    
    
    if (logoImage) {
      formData.append("logoImage", logoImage);
    }

    
    const response = await axios.put(`${API_URL}/${userId}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error("❌ Failed to update user:", error.response?.status, error.response?.data);
    throw error;
  }
};




export const deleteUser = async () => {
  try {
    const token = localStorage.getItem("jwt");
    const userId = localStorage.getItem("userId");
    
    await axios.delete(`${API_URL}/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
        
    localStorage.removeItem("jwt");
    localStorage.removeItem("userId");
    localStorage.removeItem("userEmail");

    return true; 
  } catch (error) {
    console.error("❌ Failed to delete user:", error.response?.status, error.response?.data);
    throw error;
  }
};