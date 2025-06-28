import React from 'react'
import styles from './NavBarAdm.module.css'
import { Link } from 'react-router-dom'
import { MdAttachMoney } from "react-icons/md";
import { FaBoxOpen } from "react-icons/fa";
import SetaGoBack from '../SetaGoBack/SetaGoBack';

export default function NavBarAdm() {
  return (
    <div className={styles.container}>
        <h1 className={styles.logo}>Lumière d'Or</h1>
        <p className={styles.subtitulo}>Administrador</p>

        <Link className={styles.botao}><MdAttachMoney color='white' size={28}/>Vendas</Link>
        <Link to="produtos-adm" className={styles.botao}><FaBoxOpen color='white' size={28}/>Produtos</Link>

        <SetaGoBack/>
    </div>
  )
}
