import Header from '../../components/Header/Header'
import Footer from '../../components/Footer/Footer'
import BotaoCarrinho from '../../components/BotaoCarrinho/BotaoCarrinho'
import styles from './Categoria.module.css'
import CardProduto from '../../components/CardProduto/CardProduto'

export default function Produtos() {
  return (
    <div className={styles.container}>
      <Header/>
      <div className={styles.mainContent}>
        <h1 className={styles.titulo}>Categoria</h1>

        <div className={styles.containerProdutos}>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
          <CardProduto/>
        </div>

      </div>
      <BotaoCarrinho/>
      <Footer/>
    </div>
  )
}