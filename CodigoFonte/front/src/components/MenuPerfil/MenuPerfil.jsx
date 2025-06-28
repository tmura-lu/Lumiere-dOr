import styles from './MenuPerfil.module.css'
import { Link, useNavigate } from 'react-router-dom'


export default function MenuPerfil() {
    const navigate = useNavigate();

    function handleLogout() {
        localStorage.removeItem('usuario');  // Remove os dados do usuário
        navigate('/login');                  // Redireciona para a tela de login
    }

    return (
        <div className={styles.container}>
            <p className={styles.titulo}>Opções</p>

            <div className={styles.navbar}>
                <Link className={styles.link}>Dados do Usuário</Link>
                <Link className={styles.link}>Endereços</Link>
                <Link className={styles.link}>Pedidos</Link>
            </div>

            <button onClick={handleLogout} className={styles.botao}>Sair</button>
        </div>
    )
}