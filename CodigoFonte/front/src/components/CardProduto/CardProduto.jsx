import styles from './CardProduto.module.css'
import { Link } from 'react-router-dom'
import produto from "../../assets/produto-teste.webp"

export default function CardProduto() {
  return (
    <div className={styles.container}>
        <img src={produto} alt="" className={styles.imagem}/>
        <p className={styles.nome}>óculos x</p>
        <p className={styles.descricaoBreve}>Descrição breve do produto</p>
        <p className={styles.valor}>R$ 999.999,00</p>
        <Link className={styles.botao}>comprar</Link>
    </div>
  )
}