import React, { useState } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/publishoffer.css';

const PublishOffer = () => {
  const [offerData, setOfferData] = useState({
    title: '',
    description: '',
    status: '',
    brand: '',
    category: '',
    model: '',
    yearOfCreation: '',
    fuel: '',
    price: '',
    mileage: '',
    gear: '',
    color: '',
    power: '',
    doorCount: '',
    image: null,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setOfferData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    setOfferData((prevData) => ({
      ...prevData,
      image: e.target.files[0],
    }));
  };

  return (
    <>
      <Header showRightButtons={false} />
      <div className="publish-offer-container">
        <div className="offer-box">
          
          <div className="offer-content">
            <div className="offer-details grid-container">
              <input type="text" name="title" placeholder="Title" value={offerData.title} onChange={handleChange} required className="full-width" />
              <textarea name="description" placeholder="Description" value={offerData.description} onChange={handleChange} required className="full-width" rows="3" />
              <input type="text" name="status" placeholder="Status" value={offerData.status} onChange={handleChange} required className="half-width" />
              <input type="text" name="category" placeholder="Category" value={offerData.category} onChange={handleChange} required className="half-width" />
              <input type="text" name="brand" placeholder="Brand" value={offerData.brand} onChange={handleChange} required className="half-width" />
              <input type="text" name="gear" placeholder="Gearbox" value={offerData.gear} onChange={handleChange} required className="half-width" />
              <input type="text" name="model" placeholder="Model" value={offerData.model} onChange={handleChange} required className="half-width" />
              <input type="text" name="color" placeholder="Color" value={offerData.color} onChange={handleChange} required className="half-width" />
              <input type="text" name="yearOfCreation" placeholder="Year of Creation" value={offerData.yearOfCreation} onChange={handleChange} required className="half-width" />
              <input type="text" name="doorCount" placeholder="Number of Doors" value={offerData.doorCount} onChange={handleChange} required className="half-width" />
              <input type="text" name="fuel" placeholder="Fuel Type" value={offerData.fuel} onChange={handleChange} required className="half-width" />
              <input type="text" name="mileage" placeholder="Mileage (km)" value={offerData.mileage} onChange={handleChange} required className="half-width" />
              <input type="text" name="price" placeholder="Price" value={offerData.price} onChange={handleChange} required className="half-width" />
              <input type="text" name="power" placeholder="Power (HP)" value={offerData.power} onChange={handleChange} required className="half-width" />
            </div>
            <div className="image-upload">
              <div className="image-preview">Image Preview</div>
              <input type="file" name="image" accept="image/*" onChange={handleImageChange} />
            </div>
          </div>
          <button type="submit" className="publish-offer-button">Publish Offer</button>
        </div>
      </div>
      <Footer showFooter={true} />
    </>
  );
};

export default PublishOffer;