import React from 'react';
import Header from '../components/Header';
import SearchDealerships from '../components/DealershipSearch';
import DealershipList from '../components/DealershipList';

const Dealerships = () => {
      
    return (
        <>
        <><Header showRightButtons={false} showSearchOnly={false} />
            {/* <SearchDealerships /> */}
            </><DealershipList />
        </>
    );
 };

 export default Dealerships;