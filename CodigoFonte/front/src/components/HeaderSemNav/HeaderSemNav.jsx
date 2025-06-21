import styles from './HeaderSemNav.module.css'
import { Link } from 'react-router-dom';

function HeaderSemNav() {
  return (
    <div className={styles.container}>
        <Link className={styles.titulo}>Lumière d'Or</Link>
    </div>
  )
}

export default HeaderSemNav