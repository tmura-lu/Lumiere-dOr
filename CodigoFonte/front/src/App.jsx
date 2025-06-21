import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'; 
import Home from './pages/Home/Home.jsx'
import Login from './pages/Login/Login.jsx';

function App() {
  return (
    <Router>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login/>}/>
        </Routes>
    </Router>
  )
}

export default App