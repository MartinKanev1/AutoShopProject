import React, { useState } from "react";
import "../styles/CarSearchForm.css";

const CarSearchForm = () => {
  const [criteria, setCriteria] = useState({
    brand: "",
    model: "",
    fuel: "",
    gear: "",
    color: "",
    doorCount: "",
    isDealer: null,
    minPrice: "",
    maxPrice: "",
    minMileage: "",
    maxMileage: "",
    minPower: "",
    maxPower: "",
    minYear: "",
    maxYear: "",
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setCriteria((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  return (
    <form className="car-search-form">

      <input type="text" name="brand" placeholder="Brand" value={criteria.brand} onChange={handleChange} className="small-input" />

      <input type="text" name="model" placeholder="Model" value={criteria.model} onChange={handleChange} className="small-input" />

      <select name="fuel" value={criteria.fuel} onChange={handleChange} className="small-input">
      <option value="null">Fuel</option>
        <option value="Petrol">Petrol</option>
        <option value="Diesel">Diesel</option>
        
      </select>

      <select name="gear" value={criteria.gear} onChange={handleChange} className="small-input">
        <option value="null">Gear</option>
        <option value="Manual">Manual</option>
        <option value="Automatic">Automatic</option>
        
      </select>

      <input type="text" name="color" placeholder="Color" value={criteria.color} onChange={handleChange} className="small-input" />

      <input type="text" name="doorCount" placeholder="Doors" value={criteria.doorCount} onChange={handleChange} className="small-input" />

      <input type="text" name="minPrice" placeholder="Min Price" value={criteria.minPrice} onChange={handleChange} className="small-input" />

      <input type="text" name="maxPrice" placeholder="Max Price" value={criteria.maxPrice} onChange={handleChange} className="small-input" />

      <input type="text" name="minMileage" placeholder="Min Mileage" value={criteria.minMileage} onChange={handleChange} className="small-input" />

      <input type="text" name="maxMileage" placeholder="Max Mileage" value={criteria.maxMileage} onChange={handleChange} className="small-input" />

      <input type="text" name="minPower" placeholder="Min Power" value={criteria.minPower} onChange={handleChange} className="small-input" />

      <input type="text" name="maxPower" placeholder="Max Power" value={criteria.maxPower} onChange={handleChange} className="small-input" />

      <input type="text" name="minYear" placeholder="Min Year" value={criteria.minYear} onChange={handleChange} className="small-input" />

      <input type="text" name="maxYear" placeholder="Max Year" value={criteria.maxYear} onChange={handleChange} className="small-input" />

      <select name="posted by" value={criteria.isDealer} onChange={handleChange} className="small-input">
      <option value="null">All</option>
        <option value="true">Private Sellers</option>
        <option value="false">CarDealerships</option>
      </select>
      
    </form>
  );
};

export default CarSearchForm;