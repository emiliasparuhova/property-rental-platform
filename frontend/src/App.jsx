import React from 'react'
import { Route, Routes, BrowserRouter as Router } from "react-router-dom"
import './App.css'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import NavBar from './components/NavBar'
import RegistrationPage from './pages/RegistrationPage'
import "bootstrap/dist/css/bootstrap.min.css";
import ProfilePage from './pages/ProfilePage'
import { ToastContainer } from 'react-toastify';
import AccountPage from './pages/AccountPage'
import AdvertsPage from './pages/AdvertsPage'
import AdvertsDetailsPage from './pages/AdvertDetailsPage'
import ManageAdvertsPage from './pages/ManageAdvertsPage'
import CreateAdvertPage from './pages/CreateAdvertPage'
import ContactLandlordPage from './pages/ContactLandlordPage'
import ChatPage from './pages/ChatPage'
import UserChatsPage from './pages/UserChatsPage'
import FavouriteAdvertsPage from './pages/FavouriteAdvertsPage'
import OAuthLoginPage from './pages/OAuthLoginPage'


function App() {
  return (
    <div className="App">
      <Router>
        <ToastContainer />
        <NavBar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/registration" element={<RegistrationPage />} />
          <Route path="/users/:id" element={<ProfilePage />}/>
          <Route path="/account" element={<AccountPage />}/>
          <Route path="/adverts" element={<AdvertsPage />}/>
          <Route path="/adverts/:id" element={<AdvertsDetailsPage />}/>
          <Route path="/my-adverts" element={<ManageAdvertsPage />}/>
          <Route path="/create-advert" element={<CreateAdvertPage />}/>
          <Route path="/contact-landlord" element={<ContactLandlordPage />}/>
          <Route path="/chat/:id" element={<ChatPage />}/>
          <Route path="/my-chats" element={<UserChatsPage />}/>
          <Route path="/favourite-adverts" element={<FavouriteAdvertsPage/>} />
          <Route path="/oauth-login" element={<OAuthLoginPage/>} />
        </Routes>
      </Router>
    </div>  
  )
}

export default App
