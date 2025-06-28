import { useState } from 'react';
import { motion } from 'framer-motion';
import style from './FormularioLoginRegistro.module.css'
import PasswordInput from '../../components/Inputs/PasswordInput'
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';
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


export default function FormularioLoginRegistro() {
    const [isRegistering, setIsRegistering] = useState(false);
    const [nome, setNome] = useState('');
    const [telefone, setTelefone] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (isRegistering) {
            if (!nome.trim() || !telefone.trim() || !email.trim() || !senha.trim()) {
                alert("Por favor, preencha todos os campos para registrar.");
                return;
            }
            try {
                const res = await fetch('http://localhost:8080/usuarios/create', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ nome, email, senha, tipo: "CLIENTE", cpf, telefone}),
                });

                if (res.ok) {
                    alert('Registrado com sucesso!');
                    setIsRegistering(false);
                    setNome('');
                    setTelefone('');
                    setEmail('');
                    setSenha('');
                } else {
                    const data = await res.json();
                    alert(data.error || 'Erro ao registrar');
                }
            } catch (err) {
                console.error(err);
                alert('Erro na requisição');
            }
        } else {
            // Login (verifica usuário na lista)
            try {
                const res = await fetch('http://localhost:8080/usuarios/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ email, senha })
                });

                if (res.ok) {
                    const usuario = await res.json();
                    localStorage.setItem('usuario', JSON.stringify(usuario));
                    alert('Login efetuado!');
                    navigate('/'); // Redireciona para a home ou tela desejada
                } else {
                    const errorMsg = await res.text();
                    setErro(errorMsg || 'Email ou senha inválidos');
                }
            } catch (err) {
                console.error(err);
                alert('Erro na requisição');
            }
        }
    };

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

            <motion.form className={style.formContainer} onSubmit={handleSubmit}>
                {isRegistering && (
                    <motion.label
                        className={style.label}
                        initial={{ opacity: 0, y: -10 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.6 }}
                    >Nome
                        <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu nome completo' sx={{ marginTop: ".5rem" }} value={nome} onChange={(e) => setNome(e.target.value)} />
                    </motion.label>
                )}

                {isRegistering && (
                    <motion.label
                        className={style.label}
                        initial={{ opacity: 0, y: -10 }}
                        animate={{ opacity: 1, y: 0 }}
                    >Telefone
                        <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu telefone no formato: (99) 9 9999-9999' sx={{ marginTop: ".5rem" }} value={telefone} onChange={(e) => setTelefone(e.target.value)} />
                    </motion.label>
                )}

                {isRegistering && (
                    <motion.label
                        className={style.label}
                        initial={{ opacity: 0, y: -10 }}
                        animate={{ opacity: 1, y: 0 }}
                    >CPF:
                        <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu cpf no formato: 000.000.000-00' sx={{ marginTop: ".5rem" }} value={cpf} onChange={(e) => setCpf(e.target.value)} />
                    </motion.label>
                )}

                <label className={style.label}>E-mail
                    <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{ marginTop: ".5rem" }} value={email} onChange={(e) => setEmail(e.target.value)} />
                </label>

                <label className={style.label}>Senha
                    <PasswordInput value={senha} onChange={(e) => setSenha(e.target.value)} />
                </label>

                <button type="submit" className={style.botao}>{isRegistering ? "Registrar" : "Entrar"}</button>
            </motion.form>

            <p className={style.text}>{isRegistering ? "Já tem uma conta? " : "Não tem uma conta? "}<a href="#" className={style.link} onClick={(e) => { e.preventDefault(); setIsRegistering(!isRegistering); }}>{" "}{isRegistering ? " Fazer login" : " Cadastre-se"}</a></p>
        </motion.div>
    )
}