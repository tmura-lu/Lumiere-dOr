import HeaderSemNav from "../../components/HeaderSemNav/HeaderSemNav"
import FooterInferior from "../../components/FooterInferior/FooterInferior"
import styles from './LoginAdm.module.css'
import PasswordInput from '../../components/Inputs/PasswordInput'
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';
import { Link } from 'react-router-dom';

const CssTextField = styled(TextField)({
    '& .MuiInputBase-input': {
        color: '#111111', // Cor do texto digitado no campo
        fontFamily: "Raleway"
    },
    '& .MuiOutlinedInput-root': {
        '& fieldset': {
        borderColor: 'rgba(17, 17, 17, 0.6)',
        borderRadius: '10px'
        },
        '&:hover fieldset': {
        borderColor: 'rgba(17, 17, 17, 0.8) !important',   
        },
        '&.Mui-focused fieldset': {
        borderColor: 'rgba(17, 17, 17, 0.8) !important',
        },
    },
});

export default function LoginAdm() {
  return (
    <div className={styles.container}>
        <HeaderSemNav/>

        <div className={styles.content}>
            <form className={styles.formContainer}>
                <h1 className={styles.titulo}>Login</h1>
                <p className={styles.texto}>Administrador</p>
                <label className={styles.label}>E-mail
                    <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{marginTop:".5rem"}}/>
                </label>
                <label className={styles.label}>Senha
                    <PasswordInput/>
                </label>

                <Link className={styles.botao}>Entrar</Link>
            </form>
        </div>
        <FooterInferior/>
    </div>
  )
}