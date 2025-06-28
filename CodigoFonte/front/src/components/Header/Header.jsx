import styles from './Header.module.css'
import { Link } from 'react-router-dom';
import { LuUser } from "react-icons/lu";
import { FiShoppingBag } from "react-icons/fi";
import { useState, useEffect } from 'react';

function Header() {
  const [usuarioLogado, setUsuarioLogado] = useState(false);

  useEffect(() => {
    const usuario = JSON.parse(localStorage.getItem("usuario"));
    setUsuarioLogado(!!(usuario && usuario.id)); // true se tiver id
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.containerTitleIcon}>
        <h1><Link to="/" className={styles.titulo}>Lumière d'Or</Link></h1>

        <div className={styles.containerIcon}>
          <Link className={styles.icon} to={usuarioLogado ? "/dados-usuario" : "/login"}><LuUser /></Link>
          <Link className={styles.icon} to="/carrinho"><FiShoppingBag /></Link>
        </div>
      </div>

      <ul>
        <li><Link to="/categoria/Alianças" className={styles.link}>Alianças</Link></li>
        <li><Link to="/categoria/Correntes" className={styles.link}>Correntes</Link></li>
        <li><Link to="/categoria/Relógios" className={styles.link}>Relógios</Link></li>
        <li><Link to="/categoria/Pulseiras" className={styles.link}>Pulseiras</Link></li>
        <li><Link to="/categoria/Óculos" className={styles.link}>Óculos</Link></li>
        <li><Link to="/categoria/Brincos" className={styles.link}>Brincos</Link></li>
      </ul>
    </div>
  )
}

export default Header