import {Link} from 'react-router-dom'
import { FaArrowLeftLong } from "react-icons/fa6";
import styles from './SetaGoBack.module.css'

export default function SetaGoBack() {
  return (
    <Link className={styles.botao}>
        <FaArrowLeftLong color='black' size={32}/>
        Voltar
    </Link>
  )
}
