import React from 'react';
import Header from '../components/Header';
import CarSearchForm from '../components/CarSearchForm';
import OfferList from '../components/OfferList';
import { useState } from 'react';
import '../styles/Home.css';

const Home = () => {

  
    const [criteria, setCriteria] = useState({
      brand: "",
      model: "",
      fuel: "",
      gear: "",
      color: "",
      doorCount: "",
      isDealer: "",
      minPrice: "",
      maxPrice: "",
      minMileage: "",
      maxMileage: "",
      minPower: "",
      maxPower: "",
      minYear: "",
      maxYear: "",
    });



  return (
    <div>
      <Header setCriteria={setCriteria}/>
      <main className="home-container">
        
      {/* <CarSearchForm />
        
        <OfferList showHeart = {false}/> */}

<CarSearchForm criteria={criteria} setCriteria={setCriteria} />
        
        {/* Pass criteria to OfferList so it knows when to fetch filtered offers */}
        <OfferList criteria={criteria} />

      </main>
    </div>
  );
};

export default Home;
