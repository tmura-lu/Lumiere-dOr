import Header from "../../components/Header/Header"
import Footer from "../../components/Footer/Footer"
import MenuPerfil from "../../components/MenuPerfil/MenuPerfil"
import styles from './DadosUsuario.module.css'
import { Link } from "react-router-dom"
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';
import { useEffect, useState } from "react";

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

export default function DadosUsuario() {
    const [dadosUsuario, setDadosUsuario] = useState({
        nome: "",
        email: "",
        cpf: "",
        telefone: ""
    });

    const [dadosOriginais, setDadosOriginais] = useState(null);

    useEffect(() => {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario || !usuario.id) {
            alert("Usuário não está logado");
            return;
        }

        async function fetchDados() {
            try {
                const res = await fetch(`http://localhost:8080/usuarios/${usuario.id}`);
                const data = await res.json();
                setDadosUsuario(data);
                setDadosOriginais(data); // Armazena os dados originais
            } catch (error) {
                console.error("Erro ao buscar dados do usuário:", error);
                alert("Erro ao carregar dados do usuário");
            }
        }

        fetchDados();
    }, []);

    function handleChange(e) {
        const { name, value } = e.target;
        setDadosUsuario((prev) => ({ ...prev, [name]: value }));
    }

    async function salvarAlteracoes() {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        try {
            const res = await fetch(`http://localhost:8080/usuarios/${usuario.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dadosUsuario)
            });
            if (res.ok) {
                alert("Dados atualizados com sucesso!");
                setDadosOriginais(dadosUsuario); // Atualiza os dados originais
            } else {
                alert("Erro ao atualizar dados.");
            }
        } catch (err) {
            console.error("Erro ao salvar alterações:", err);
            alert("Erro ao salvar alterações.");
        }
    }

    function cancelarEdicao() {
        setDadosUsuario(dadosOriginais); // Restaura os dados originais
    }

    return (
        <div className={styles.container}>
            <Header />

            <div className={styles.mainContent}>
                <MenuPerfil />

                <div className={styles.dados}>
                    <p className={styles.titulo}>Dados do Usuário</p>

                    <div className={styles.containerCampos}>
                        <label className={styles.label}>Nome
                            <CssTextField name="nome" id="outlined-basic" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosUsuario.nome} onChange={handleChange}/>
                        </label>

                        <label className={styles.label}>E-mail
                            <CssTextField name="email" id="outlined-basic" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosUsuario.email} onChange={handleChange}/>
                        </label>

                        <label className={styles.label}>CPF
                            <CssTextField name="cpf" id="outlined-basic" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosUsuario.cpf} onChange={handleChange} disabled/>
                        </label>

                        <label className={styles.label}>Telefone
                            <CssTextField name="telefone" id="outlined-basic" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosUsuario.telefone} onChange={handleChange}/>
                        </label>
                    </div>

                    <div className={styles.containerBotoes}>
                        <Link className={styles.botaoCinza} onClick={cancelarEdicao}>Cancelar</Link>
                        <Link className={styles.botaoPreto} onClick={salvarAlteracoes}>Salvar</Link>
                    </div>
                </div>
            </div>


            <Footer />
        </div>
    )
}