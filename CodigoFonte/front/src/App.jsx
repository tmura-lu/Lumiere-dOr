import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'; 
import Home from './pages/Home/Home.jsx'
import Login from './pages/Login/Login.jsx';
import DetalhesProduto from './pages/DetalhesProduto/DetalhesProduto.jsx';

function App() {
  return (
    <Router>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/registro" element={<Login/>}/>
            <Route path="/produto" element={<DetalhesProduto/>}/>
        </Routes>
    </Router>
  )
}

export default App