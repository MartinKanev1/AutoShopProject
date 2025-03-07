import React, { useState, useEffect } from "react";
import "../styles/CarSearchForm.css";



const CarSearchForm = ({ criteria, setCriteria, onSearch }) => {
  
  
  const [localCriteria, setLocalCriteria] = useState(criteria);

  useEffect(() => {
    setLocalCriteria(criteria);
  }, [criteria]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setLocalCriteria((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSearch = (e) => {
    e.preventDefault();
    setCriteria(localCriteria); 
    
    if (onSearch) {
      onSearch(); 
  } else {
      console.error("❌ onSearch is undefined in CarSearchForm!");
  }
  };


  const handleClear = () => {
    console.log("🧹 Clearing search fields...");

    
    const emptyCriteria = {
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
    };

    setLocalCriteria(emptyCriteria); 
    setCriteria(emptyCriteria); 
    onSearch(); 
};



  return (
    <form className="car-search-form" onSubmit={handleSearch}>

      <input type="text" name="brand" placeholder="Brand" value={localCriteria.brand} onChange={handleChange} className="small-input" />

      <input type="text" name="model" placeholder="Model" value={localCriteria.model} onChange={handleChange} className="small-input" />

      <select name="fuel" value={localCriteria.fuel} onChange={handleChange} className="small-input">
      <option value="">Fuel</option>
        <option value="Petrol">Petrol</option>
        <option value="Diesel">Diesel</option>
        
      </select>

      <select name="gear" value={localCriteria.gear} onChange={handleChange} className="small-input">
        <option value="">Gear</option>
        <option value="Manual">Manual</option>
        <option value="Automatic">Automatic</option>
        
      </select>

      <input type="text" name="color" placeholder="Color" value={localCriteria.color} onChange={handleChange} className="small-input" />

      <input type="text" name="doorCount" placeholder="Doors" value={localCriteria.doorCount} onChange={handleChange} className="small-input" />

      <input type="text" name="minPrice" placeholder="Min Price" value={localCriteria.minPrice} onChange={handleChange} className="small-input" />

      <input type="text" name="maxPrice" placeholder="Max Price" value={localCriteria.maxPrice} onChange={handleChange} className="small-input" />

      <input type="text" name="minMileage" placeholder="Min Mileage" value={localCriteria.minMileage} onChange={handleChange} className="small-input" />

      <input type="text" name="maxMileage" placeholder="Max Mileage" value={localCriteria.maxMileage} onChange={handleChange} className="small-input" />

      <input type="text" name="minPower" placeholder="Min Power" value={localCriteria.minPower} onChange={handleChange} className="small-input" />

      <input type="text" name="maxPower" placeholder="Max Power" value={localCriteria.maxPower} onChange={handleChange} className="small-input" />

      <input type="text" name="minYear" placeholder="Min Year" value={localCriteria.minYear} onChange={handleChange} className="small-input" />

      <input type="text" name="maxYear" placeholder="Max Year" value={localCriteria.maxYear} onChange={handleChange} className="small-input" />

      <select name="posted by" value={localCriteria.isDealer} onChange={handleChange} className="small-input">
      <option value="">All</option>
        <option value="true">Private Sellers</option>
        <option value="false">CarDealerships</option>
      </select> 

      <button type="submit" className="search-button" >Search </button>
      <button type="button" className="clear-button" onClick={handleClear}>Clear</button> 
      
    </form>
  );
};

export default CarSearchForm;