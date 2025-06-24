import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'; 
import Home from './pages/Home/Home.jsx'
import Login from './pages/Login/Login.jsx';
import DetalhesProduto from './pages/DetalhesProduto/DetalhesProduto.jsx';
import LoginAdm from './pages/LoginAdm/LoginAdm.jsx'
import Categoria from './pages/Produtos/Categoria.jsx';

function App() {
  return (
    <Router>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/registro" element={<Login/>}/>
            <Route path="/produto" element={<DetalhesProduto/>}/>
            <Route path="/adm" element={<LoginAdm/>}/>
            <Route path="/produtos/:nomeCategoria" element={<Categoria/>}/>
        </Routes>
    </Router>
  )
}

export default App