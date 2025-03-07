import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login"; // Импортираме страницата за вход
import Register from './pages/Register';
import Home from './pages/Home';
import PublishOffer from "./pages/PublishOffer";
import UserInfo from "./pages/UserInfo";
import AllDealership from "./pages/AllDealerships"
import DealershipsInfo from "./pages/DealershipInfo";
import EditOffer from "./pages/EditOffer";
import MyOffers from "./pages/MyOffers"
import Favourites from "./pages/Favourites";
import OfferDetails from "./pages/OfferDetailsPage";
import Adminpage from "./pages/AdminPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<h1>Home Page</h1>} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/publish-offer" element={<PublishOffer />} />
        <Route path="/my-account" element={<UserInfo />} />
        <Route path="/view-all-dealerships" element={<AllDealership />} />
        <Route path="/dealership-info/:dealershipId" element={<DealershipsInfo />} />
        <Route path="/edit-offer/:offerId" element={<EditOffer />} />
        <Route path="/my-offers" element={<MyOffers />} />
        <Route path="/my-favourites-offers" element={<Favourites />} />
        <Route path="/view-offer/:offerId" element={<OfferDetails />} />
        <Route path="/admin" element={<Adminpage />} />

      </Routes>
    </Router>
  );
}

export default App;
