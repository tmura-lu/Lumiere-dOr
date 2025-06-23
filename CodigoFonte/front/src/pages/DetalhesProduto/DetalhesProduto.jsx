import Footer from "../../components/Footer/Footer"
import Header from "../../components/Header/Header"
import produto from "../../assets/produto-teste.webp"
import styles from "./DetalhesProduto.module.css"

export default function DetalhesProduto(){
  return (
    <div className={styles.container}>
        <Header/>
        
        <div className={styles.content}>
            <img src={produto} alt="" className={styles.imgProduto}/>
            <div className={styles.textContent}>
                <h1 className={styles.titulo}>Produto X</h1>
                <p className={styles.descricao}>Encante-se com a sofisticação atemporal desta joia cuidadosamente elaborada para destacar sua beleza única. Produzida com materiais de alta qualidade, esta peça combina design refinado e acabamento impecável, ideal para ocasiões especiais ou para elevar o seu estilo no dia a dia.</p>
                <p className={styles.valor}>R$ 999.999,00</p>
                <a href="#" className={styles.botao}>Adicionar ao carrinho</a>
            </div>
        </div>

        <Footer/>
    </div>
  )
}