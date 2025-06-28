import styles from './BotaoCarrinho.module.css'
import { Link } from 'react-router-dom'; 
import { FiShoppingBag } from "react-icons/fi";

function BotaoCarrinho() {
  return (
    <Link to="/carrinho" className={styles.icon}><FiShoppingBag size={40}/></Link>
  )
}

export default BotaoCarrinho