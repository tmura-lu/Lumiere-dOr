import { useNavigate } from 'react-router-dom'
import { FaArrowLeftLong } from "react-icons/fa6";
import styles from './SetaGoBack.module.css'

export default function SetaGoBack() {
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.removeItem('usuario');  // Remove os dados do usuário
    navigate('/');                  // Redireciona para a tela de login
  }

  return (
    <button className={styles.botao} onClick={handleLogout}>
      <FaArrowLeftLong color='white' size={32} />
      Sair
    </button>
  )
}
