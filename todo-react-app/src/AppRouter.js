import { Box } from '@material-ui/core'
import React from 'react'
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import App from './App'
import Copyright from './Copyright'
import Login from './Login'

function AppRouter() {
  return (
    <BrowserRouter>
        <div>
            <Routes>
                <Route path='/login' element={<Login />} />
                <Route path='/' element={<App />} />
            </Routes>
        </div>
        <div>
            <Box mt={5}>
                <Copyright />
            </Box>
        </div>
    </BrowserRouter>
  )
}

export default AppRouter