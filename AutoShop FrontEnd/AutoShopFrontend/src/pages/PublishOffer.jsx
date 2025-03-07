import React, { useState } from 'react';
import { CreateOffer } from '../services/OffersService'; 
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
  
  const [imagePreview, setImagePreview] = useState(null);

  const [loading, setLoading] = useState(false); 
  const [message, setMessage] = useState(null); 

  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setOfferData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setOfferData((prevData) => ({
        ...prevData,
        image: file,
      }));
  
      
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };
  

  
  const handleSubmit = async (e) => {
    e.preventDefault(); 
    setLoading(true);
    setMessage(null);

    try {
      
      const { image, ...offerDTO } = offerData;

      
      await CreateOffer(offerDTO, image);

      
      setMessage({ type: 'success', text: 'Offer published successfully!' });

      
      setOfferData({
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

      setImagePreview(null); 

    } catch (error) {
      
      setMessage({ type: 'error', text: 'Failed to publish offer. Please try again.' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Header showRightButtons={false} />
      <div className="publish-offer-container">
        <div className="offer-box">
          <form onSubmit={handleSubmit}> 
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
  <div className="image-preview">
    {imagePreview ? <img src={imagePreview} alt="Preview" style={{ width: "100%", maxHeight: "200px", objectFit: "cover" }} /> : "Image Preview"}
  </div>
  <input type="file" name="image" accept="image/*" onChange={handleImageChange} />
</div>

            </div>

            {/* ✅ Show messages */}
            {message && (
              <p className={`message ${message.type === 'success' ? 'success' : 'error'}`}>
                {message.text}
              </p>
            )}

            {/* ✅ Disable button when loading */}
            <button type="submit" className="publish-offer-button" disabled={loading}>
              {loading ? 'Publishing...' : 'Publish Offer'}
            </button>
          </form>
        </div>
      </div>
      <Footer showFooter={true} />
    </>
  );
};

export default PublishOffer;
