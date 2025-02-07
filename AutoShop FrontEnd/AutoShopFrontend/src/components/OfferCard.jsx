import React from "react";
import { Heart } from "lucide-react";

const OfferCard = ({ offer }) => {
  return (
    <div className="border rounded-2xl shadow-lg p-4 bg-white w-72 relative">
      {/* Favorite Icon */}
      <button className="absolute top-2 right-2 bg-white p-1 rounded-full shadow-md">
        <Heart className="text-gray-600" size={18} />
      </button>
      
      {/* Image */}
      <img
        src={offer.imageUrl}
        alt={offer.title}
        className="w-full h-40 object-cover rounded-lg"
      />

      {/* Price */}
      <span className="absolute bottom-36 right-2 bg-white shadow-md px-3 py-1 rounded-md text-lg font-bold">
        {offer.price} лв.
      </span>

      {/* Title */}
      <h3 className="text-lg font-semibold mt-3">{offer.title}</h3>

      {/* Details */}
      <p className="text-gray-600 text-sm">{offer.year}, {offer.fuel}, {offer.mileage} km</p>
      
      {/* Dealership */}
      {offer.dealership && (
        <p className="text-gray-500 text-sm">{offer.dealership}</p>
      )}
    </div>
  );
};

export default OfferCard;