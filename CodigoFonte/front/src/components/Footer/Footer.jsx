import styles from './Footer.module.css'
import { Link } from 'react-router-dom'; 
import { CiMail } from "react-icons/ci";
import { FiMapPin } from "react-icons/fi";
import { IoCallOutline } from "react-icons/io5";
import { FaInstagram } from "react-icons/fa";
import { CiFacebook } from "react-icons/ci";
import { FaWhatsapp } from "react-icons/fa";

function Footer(){
    return(
    <div className={styles.container}>
        <div className={styles.content}>
            <h1 className={styles.logoFooter}>Lumière d'Or</h1>

            <div style={{marginRight: '6rem'}}>
                <h2 className={styles.subtitulo}>Explorar</h2>

                <Link className={styles.explorar}>Início</Link>
                <Link className={styles.explorar}>Carrinho</Link>
            </div>

            <div style={{marginRight: '1rem'}}>
                <h2 className={styles.subtitulo}>Contato</h2>

                <p className={styles.texto}><CiMail size={30} style={{marginRight: '.5rem'}}/>email@email.com</p>
                <p className={styles.texto}><IoCallOutline size={30} style={{marginRight: '.5rem'}}/>99 99999-9999</p>
                <p className={styles.texto}><FiMapPin size={30} style={{marginRight: '.5rem'}}/>endereco</p>

                <div className={styles.socialMediaIcons}>
                    <FaInstagram size={40} style={{color: '#FFFAFA'}} />
                    <CiFacebook size={40} style={{color: '#FFFAFA'}} />
                    <FaWhatsapp size={40} style={{color: '#FFFAFA'}} />
                </div>
            </div>
        </div>

        <p className={styles.ref}>2025 Lumière d'Or | By Projeto Final</p>
    </div>
    )
}

export default Footer