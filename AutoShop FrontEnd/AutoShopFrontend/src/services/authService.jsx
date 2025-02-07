import axios from "axios";

const API_URL = "http://localhost:8080/auth";

export const registerUser = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/register`, userData, {
            headers: {
                "Content-Type": "application/json",
            },
        });
        if (response.data.token) {
            localStorage.setItem("jwt", response.data.token); // Запазване на JWT в LocalStorage
        }
        return response.data;
    } catch (error) {
        console.error("Registration failed:", error);
        throw error;
    }
};



export const loginUser = async (email, password) => {
    try {
        const response = await axios.post(`${API_URL}/login`, { email, password }, {
            headers: { "Content-Type": "application/json" }
        });

        if (response.data.token) {
            localStorage.setItem("jwt", response.data.token); // Запазване на JWT токена
        }

        return response.data;
    } catch (error) {
        console.error("Login failed:", error);
        throw error;
    }
};

