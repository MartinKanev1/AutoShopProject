import React, { useState } from 'react';

const SearchDealerships = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [dealerships, setDealerships] = useState([]); 

    
    const filteredDealerships = dealerships.filter(dealership =>
        dealership.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div style={{ padding: '20px', maxWidth: '400px', margin: 'auto' }}>
            <input
                type="text"
                placeholder="Search for a dealership..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{
                    width: '100%',
                    padding: '10px',
                    fontSize: '16px',
                    border: '1px solid #ccc',
                    borderRadius: '5px'
                }}
            />
            
        </div>
    );
};

export default SearchDealerships;
