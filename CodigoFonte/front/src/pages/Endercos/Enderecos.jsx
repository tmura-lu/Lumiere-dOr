import Header from "../../components/Header/Header"
import Footer from "../../components/Footer/Footer"
import styles from './Enderecos.module.css'
import MenuPerfil from "../../components/MenuPerfil/MenuPerfil"
import { Link } from "react-router-dom"

export default function Enderecos() {
  return (
    <div className={styles.container}>
        <Header/>
        
        <div className={styles.mainContent}>
            <MenuPerfil/>
            
            <div className={styles.enderecos}>
                <p className={styles.titulo}>Endereços</p>

                <div className={styles.cardEndereco}>
                    <p className={styles.idEndereco}>Endereço 1</p>
                    <p className={styles.texto}>Rua Mamante Vitorino, 44 - Aquenta Sol</p>
                    <p className={styles.texto}>Lavras - MG</p>
                    <p className={styles.texto}>37202-876</p>

                    <div className={styles.containerBotoes}>
                        <Link className={styles.botao}>Excluir</Link>
                        <Link className={styles.botao}>Editar</Link>
                    </div>
                </div>

                <Link className={styles.botao2}>adicionar endereco</Link>
            </div>
        </div>

        <Footer/>
    </div>
  )
}
