import styles from './MenuPerfil.module.css'
import { Link } from 'react-router-dom'


export default function MenuPerfil(){
    return(
        <div className={styles.container}>
            <p className={styles.titulo}>Opções</p>

            <div className={styles.navbar}>
                <Link className={styles.link}>Dados do Usuário</Link>
                <Link className={styles.link}>Endereços</Link>
                <Link className={styles.link}>Pedidos</Link>
            </div>

            <Link className={styles.botao}>sair</Link>
        </div>
    )
}