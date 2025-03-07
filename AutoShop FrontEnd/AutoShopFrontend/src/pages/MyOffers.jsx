import React from "react";
import Header from "../components/Header";
import MyOfferList from "../components/MyOffersList";

const MyOffers = () => {
    return(
        <>
        <Header showRightButtons = {false}/>
        <MyOfferList/>
        </>
    );
};

export default MyOffers;