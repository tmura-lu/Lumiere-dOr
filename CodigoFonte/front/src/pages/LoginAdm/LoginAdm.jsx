import HeaderSemNav from "../../components/HeaderSemNav/HeaderSemNav"
import FooterInferior from "../../components/FooterInferior/FooterInferior"
import styles from './LoginAdm.module.css'
import PasswordInput from '../../components/Inputs/PasswordInput'
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

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
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const navigate = useNavigate();

    async function handleLogin(e)  {
        e.preventDefault();

        try {
            const res = await fetch("http://localhost:8080/usuarios/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, senha })
            });

            const data = await res.json();

            if (!res.ok) {
                alert(data.error || "Erro no login");
                return;
            }

            // Checa o tipo de usuário
            if (data.tipo === "FUNCIONARIO") {
                localStorage.setItem("usuario", JSON.stringify(data));
                // Redireciona para a área do admin
                navigate('/produtos-adm'); 
            } else {
                alert("Acesso restrito: apenas administradores podem entrar aqui.");
            }

        } catch (err) {
            console.error("Erro ao fazer login:", err);
            alert("Erro na requisição de login");
        }
    }

    return (
        <div className={styles.container}>
            <HeaderSemNav />

            <div className={styles.content}>
                <form className={styles.formContainer} onSubmit={handleLogin}>
                    <h1 className={styles.titulo}>Login</h1>
                    <p className={styles.texto}>Administrador</p>
                    <label className={styles.label}>E-mail
                        <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{ marginTop: ".5rem" }} value={email} onChange={(e) => setEmail(e.target.value)}/>
                    </label>
                    <label className={styles.label}>Senha
                        <PasswordInput value={senha} onChange={(e) => setSenha(e.target.value)}/>
                    </label>

                    <button type="submit" className={styles.botao}>Entrar</button>
                </form>
            </div>
            <FooterInferior />
        </div>
    )
}