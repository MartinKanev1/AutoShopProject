import React from 'react';
import Header from '../components/Header';
import CarSearchForm from '../components/CarSearchForm';
import '../styles/Home.css';

const Home = () => {
  return (
    <div>
      <Header />
      <main className="home-container">
        
      <CarSearchForm />
        {/* Тук ще добавим още секции по-късно */}
      </main>
    </div>
  );
};

export default Home;
