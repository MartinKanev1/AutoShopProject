import React from 'react';
import DealershipCard from './DealershipCard';

const TestPage = () => {
    return (
        <div style={{ display: 'flex', justifyContent: 'center', padding: '20px' }}>
            <DealershipCard 
                name="Luxury Auto Sales" 
                city="Los Angeles" 
                logoUrl="https://example.com/logo.png"
            />
        </div>
    );
};

export default TestPage;
