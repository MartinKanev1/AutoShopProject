import React from "react";
import Header from "../components/Header";
import FavouritesList from "../components/FavouritesList";

const Favourites = () => {
    return(
        <>
        <Header showRightButtons = {false}/>
        <FavouritesList/>
        </>
    );
};

export default Favourites;