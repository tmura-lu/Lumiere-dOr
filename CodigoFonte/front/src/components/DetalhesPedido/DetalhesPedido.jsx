import styles from './DetalhesPedido.module.css'
import { Link } from 'react-router-dom'
import oculos from "../../assets/produto-teste.webp"

export default function DetalhesPedido() {
    return (
        <div className={styles.mainContent}>
            <div className={styles.container1}>
                <h1 className={styles.titulo}>Pedido nº xxx-xx</h1>

                <div className={styles.containerProdutos}>
                    <div className={styles.produto}>
                        <img src={oculos} className={styles.img} />
                        <div className={styles.info}>
                            <p className={styles.nome}>óculos x</p>
                            <p className={styles.descricao}>Descrição breve do produto</p>
                            <p className={styles.valorProduto}>R$ 999.999,00</p>
                        </div>
                    </div>

                    <div className={styles.produto}>
                        <img src={oculos} className={styles.img} />
                        <div className={styles.info}>
                            <p className={styles.nome}>óculos x</p>
                            <p className={styles.descricao}>Descrição breve do produto</p>
                            <p className={styles.valorProduto}>R$ 999.999,00</p>
                        </div>
                    </div>

                    <div className={styles.produto}>
                        <img src={oculos} className={styles.img} />
                        <div className={styles.info}>
                            <p className={styles.nome}>óculos x</p>
                            <p className={styles.descricao}>Descrição breve do produto</p>
                            <p className={styles.valorProduto}>R$ 999.999,00</p>
                        </div>
                    </div>

                    <div className={styles.produto}>
                        <img src={oculos} className={styles.img} />
                        <div className={styles.info}>
                            <p className={styles.nome}>óculos x</p>
                            <p className={styles.descricao}>Descrição breve do produto</p>
                            <p className={styles.valorProduto}>R$ 999.999,00</p>
                        </div>
                    </div>
                </div>
            </div>

            <div className={styles.container2}>
                <p className={styles.texto}>Total</p>
                <p className={styles.valor}>R$ 999.999,00</p>
                <Link href="" className={styles.botao}>Fechar Pedido</Link>
            </div>

            <div className={styles.container3}>
                <p className={styles.texto2}><b className={styles.textoDestaque}>Forma de Pagamento:</b> Cartão de Crédito</p>
                <p className={styles.texto2}><b className={styles.textoDestaque}>Data do Pedido:</b> 01/01/2025</p>
            </div>
        </div>
    )
}