import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'

import { Provider } from 'react-redux'
import { PersistGate } from 'redux-persist/integration/react';
import storeOject from './store.js'


ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={storeOject.store}>
      <PersistGate loading={null} persistor={storeOject.persistor}>
        <App />
      </PersistGate>
    </Provider>
  </React.StrictMode>,
)
