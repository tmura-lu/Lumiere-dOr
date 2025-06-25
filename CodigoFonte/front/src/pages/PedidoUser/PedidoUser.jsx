import DetalhesPedido from "../../components/DetalhesPedido/DetalhesPedido"
import Header from "../../components/Header/Header"
import Footer from "../../components/Footer/Footer"
import styles from './PedidoUser.module.css'
import SetaGoBack from "../../components/SetaGoBack/SetaGoBack"

function PedidoUser() {
  return (
    <div>
        <Header/>

        <SetaGoBack/>
        
        <DetalhesPedido/>

        <Footer/>

    </div>
  )
}

export default PedidoUser