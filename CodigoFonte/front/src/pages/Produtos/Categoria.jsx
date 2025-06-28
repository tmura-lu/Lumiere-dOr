import Header from '../../components/Header/Header'
import Footer from '../../components/Footer/Footer'
import BotaoCarrinho from '../../components/BotaoCarrinho/BotaoCarrinho'
import styles from './Categoria.module.css'
import CardProduto from '../../components/CardProduto/CardProduto'
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

export default function Produtos() {
  const { nomeCategoria } = useParams();
  const [produtos, setProdutos] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/produtos`)
    .then(res => res.json())
    .then(data => {
      console.log('nomeCategoria', nomeCategoria);
      const filtrados = data.filter(p => p.tipo === nomeCategoria);
      setProdutos(filtrados);
    })
    .catch(err => console.error(err))
  }, [nomeCategoria]);

  return (
    <div className={styles.container}>
      <Header/>
      <div className={styles.mainContent}>
        <h1 className={styles.titulo}>{nomeCategoria}</h1>

        <div className={styles.containerProdutos}>
          {produtos.length > 0 ? (
            produtos.map(produto => (
              <CardProduto key={produto.id} produto={produto} />
            ))
          ) : (
            <p className={styles.msgVazia}>Nenhum produto encontrado para essa categoria :(</p>
          )}
        </div>

      </div>
      <BotaoCarrinho/>
      <Footer/>
    </div>
  )
}