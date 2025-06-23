import {useState} from 'react';
import { motion } from 'framer-motion';
import style from './FormularioLoginRegistro.module.css'
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


export default function FormularioLoginRegistro() {
    const [isRegistering, setIsRegistering] = useState(false);

    return (
        <motion.div
        className={style.container}
        initial={{ opacity: 0, x: -50 }}
        animate={{ opacity: 1, x: 0 }}
        exit={{ opacity: 0, x: 50 }}
        transition={{ duration: 0.5, ease: "easeInOut" }}
        layout
        >
            <motion.div
            key={isRegistering ? "register" : "login"} 
            initial={{ opacity: 0, rotateY: 45 }}
            animate={{ opacity: 1, rotateY: 0 }}
            exit={{ opacity: 0, rotateY: -45 }}
            transition={{ duration: .6, ease: "easeInOut" }}
            >
                <h1 className={style.titulo}>{isRegistering ? "Registro" : "Login"}</h1>
            </motion.div>

            <motion.form className={style.formContainer}>
                <label className={style.label}>E-mail
                    <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{marginTop:".5rem"}}/>
                </label>

                <label className={style.label}>Senha
                    <PasswordInput/>
                </label>

                {isRegistering && (
                    <motion.label 
                    className={style.label}
                    initial={{ opacity: 0, y: -10 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.6 }}
                    >Confirmar Senha
                        <PasswordInput />
                    </motion.label>
                )}

                <Link className={style.botao}>{isRegistering ? "Registrar" : "Entrar"}</Link>
            </motion.form>

            <p className={style.text}>{isRegistering ? "Já tem uma conta? " : "Não tem uma conta? "}<a href="#" className={style.link} onClick={(e) => { e.preventDefault(); setIsRegistering(!isRegistering);}}>{" "}{isRegistering ? " Fazer login" : " Cadastre-se"}</a></p>
        </motion.div>
    )
}