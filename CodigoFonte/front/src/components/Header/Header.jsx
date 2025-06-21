import styles from './Header.module.css'
import { Link } from 'react-router-dom'; 
import { LuUser } from "react-icons/lu";
import { FiShoppingBag } from "react-icons/fi";

function Header() {
  return (
    <div className={styles.container}>
        <div className={styles.containerTitleIcon}>
            <h1><Link className={styles.titulo}>Lumière d'Or</Link></h1>
            
            <div className={styles.containerIcon}>
                <Link className={styles.icon}><LuUser /></Link>
                <Link className={styles.icon}><FiShoppingBag/></Link>
            </div>
        </div>

        <ul>
            <li><Link className={styles.link}>Alianças</Link></li>
            <li><Link className={styles.link}>Correntes</Link></li>
            <li><Link className={styles.link}>Relógios</Link></li>
            <li><Link className={styles.link}>Pulseiras</Link></li>
            <li><Link className={styles.link}>Óculos</Link></li>
            <li><Link className={styles.link}>Brincos</Link></li>
        </ul>
    </div>
  )
}

export default Header