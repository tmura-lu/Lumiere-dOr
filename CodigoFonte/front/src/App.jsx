import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'; 
import Home from './pages/Home/Home.jsx';
import Login from './pages/Login/Login.jsx';
import DetalhesProduto from './pages/DetalhesProduto/DetalhesProduto.jsx';
import LoginAdm from './pages/LoginAdm/LoginAdm.jsx';
import Categoria from './pages/Produtos/Categoria.jsx';
import Carrinho from './pages/Carrinho/Carrinho.jsx';
import DadosUsuario from './pages/DadosUsuario/DadosUsuario.jsx'
import PedidoUser from './pages/PedidoUser/PedidoUser.jsx'
import PedidoAdm from './pages/PedidoAdm/PedidoAdm.jsx';
import Enderecos from './pages/Endercos/Enderecos.jsx';

function App() {
  return (
    <Router>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/registro" element={<Login/>}/>
            <Route path="/produto" element={<DetalhesProduto/>}/>
            <Route path="/adm" element={<LoginAdm/>}/>
            <Route path="/categoria/:nomeCategoria" element={<Categoria/>}/>
            <Route path="/carrinho" element={<Carrinho/>}/>
            <Route path="/dados-usuario" element={<DadosUsuario/>}/>
            <Route path="/pedido" element={<PedidoUser/>}/>
            <Route path="/pedido-adm" element={<PedidoAdm/>}/>
            <Route path="/enderecos" element={<Enderecos/>}/>
        </Routes>
    </Router>
  )
}

export default App