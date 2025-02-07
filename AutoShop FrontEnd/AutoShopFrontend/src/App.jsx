import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login"; // Импортираме страницата за вход
import Register from './pages/Register';
import Home from './pages/Home';
import PublishOffer from "./pages/PublishOffer";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<h1>Home Page</h1>} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/publish-offer" element={<PublishOffer />} />
        
      </Routes>
    </Router>
  );
}

export default App;
