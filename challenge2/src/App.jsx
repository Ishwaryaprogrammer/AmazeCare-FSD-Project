import { useState } from 'react'
import { Route, Routes } from 'react-router-dom'
import UserList from './components/UserList'
import PageNotFound from './components/PageNotFound'
import AddUser from './components/AddUser'
import Navbar from "./components/Navbar"
function App() {

  return (
    <div>
      <div>
        <Navbar />
      </div>
      <Routes>
        <Route path="/" element={<UserList />}> </Route>
        <Route path="/add-user" element={<AddUser />}>   </Route>
        <Route path="*" element={<PageNotFound />}>   </Route>

      </Routes>
    </div>

  )
}

export default App
