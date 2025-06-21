import HeaderSemNav from '../../components/HeaderSemNav/HeaderSemNav'
import FooterInferior from '../../components/FooterInferior/FooterInferior'
import styles from './Login.module.css'
import PasswordInput from '../../components/Inputs/PasswordInput'

function Login() {
  return (
    <div className={styles.container}>
        <HeaderSemNav/>

        <PasswordInput/>

        <FooterInferior/>
    </div>
  )
}

export default Login