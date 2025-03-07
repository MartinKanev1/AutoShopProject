import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { GetOfferById, UpdateOffer, DeleteOffer, GetOfferImage } from '../services/OffersService';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/publishoffer.css';

const EditOffer = () => {
  const { offerId } = useParams();
  const navigate = useNavigate();

  
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

  
  const [isReadOnly, setIsReadOnly] = useState(true);
  const [originalData, setOriginalData] = useState(null); 

  
  useEffect(() => {
    const fetchOfferDetails = async () => {
      try {
        const offer = await GetOfferById(offerId);
        setOfferData(offer);
        setOriginalData(offer); 

        
        const imageUrl = await GetOfferImage(offerId);
        if (imageUrl) {
          setImagePreview(imageUrl);
        }
      } catch (error) {
        setMessage({ type: 'error', text: 'Failed to load offer details.' });
      }
    };

    fetchOfferDetails();
  }, [offerId]);

  
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
      await UpdateOffer(offerId, offerDTO, image);

      setMessage({ type: 'success', text: 'Offer updated successfully!' });
      setIsReadOnly(true); 
      setOriginalData(offerData); 
    } catch (error) {
      setMessage({ type: 'error', text: 'Failed to update offer. Please try again.' });
    } finally {
      setLoading(false);
    }
  };

  
  const handleCancel = () => {
    setOfferData(originalData); 
    setIsReadOnly(true); 
  };

  
  const handleDeleteOffer = async () => {
    if (!window.confirm("Are you sure you want to delete this offer? This action cannot be undone.")) {
      return;
    }

    setLoading(true);
    setMessage(null);

    try {
      await DeleteOffer(offerId);
      setMessage({ type: 'success', text: 'Offer deleted successfully!' });

      setTimeout(() => {
        navigate('/home'); 
      }, 1500);
    } catch (error) {
      setMessage({ type: 'error', text: 'Failed to delete offer. Please try again.' });
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
                <input type="text" name="title" placeholder="Title" value={offerData.title} onChange={handleChange} required className="full-width" readOnly={isReadOnly} />
                <textarea name="description" placeholder="Description" value={offerData.description} onChange={handleChange} required className="full-width" rows="3" readOnly={isReadOnly} />
                <input type="text" name="status" placeholder="Status" value={offerData.status} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="category" placeholder="Category" value={offerData.category} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="brand" placeholder="Brand" value={offerData.brand} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="gear" placeholder="Gearbox" value={offerData.gear} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="model" placeholder="Model" value={offerData.model} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="color" placeholder="Color" value={offerData.color} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="yearOfCreation" placeholder="Year of Creation" value={offerData.yearOfCreation} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="doorCount" placeholder="Number of Doors" value={offerData.doorCount} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="fuel" placeholder="Fuel Type" value={offerData.fuel} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="mileage" placeholder="Mileage (km)" value={offerData.mileage} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="price" placeholder="Price" value={offerData.price} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
                <input type="text" name="power" placeholder="Power (HP)" value={offerData.power} onChange={handleChange} required className="half-width" readOnly={isReadOnly} />
              </div>

              {/* ✅ Image Upload Section */}
              <div className="image-upload">
                <div className="image-preview">
                  {imagePreview ? (
                    <img src={imagePreview} alt="Preview" style={{ width: "100%", maxHeight: "200px", objectFit: "cover" }} />
                  ) : (
                    <p>No Image Available</p>
                  )}
                </div>
                {!isReadOnly && <input type="file" name="image" accept="image/*" onChange={handleImageChange} />}
              </div>
            </div>

            {/* ✅ Buttons */}
            <div className="button-group">
              {isReadOnly ? (
                <button type="button" className="edit-button" onClick={() => setIsReadOnly(false)}>
                  Edit
                </button>
              ) : (
                <>
                  <button type="submit" className="save-button" disabled={loading}>Save Changes</button>
                  <button type="button" className="cancel-button" onClick={handleCancel}>Cancel</button>
                </>
              )}
              <button type="button" className="delete-offer-button" onClick={handleDeleteOffer}>Delete Offer</button>
            </div>
          </form>
        </div>
      </div>
      <Footer showFooter={true} /> 
    </>
  );
};

export default EditOffer;
