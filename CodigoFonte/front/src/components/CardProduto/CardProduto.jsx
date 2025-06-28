import styles from './CardProduto.module.css'
import { Link } from 'react-router-dom'

export default function CardProduto({produto}) {
  return (
    <div className={styles.container} data-cy="card-produto">
        <img src={produto.urlImagem} alt="" className={styles.imagem}/>
        <p className={styles.nome}>{produto.nome}</p>
        <p className={styles.descricaoBreve}>{produto.descricao}</p>
        <p className={styles.valor}>R$ {produto.preco.toFixed(2)}</p>
        <Link to={`/produto/${produto.id}`} className={styles.botao} data-cy="comprar">comprar</Link>
    </div>
  )
}