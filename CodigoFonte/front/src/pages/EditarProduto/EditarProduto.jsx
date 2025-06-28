import React, { useEffect, useState } from 'react'
import styles from './EditarProduto.module.css'
import NavBarAdm from '../../components/NavBarAdm/NavBarAdm'
import { Link, useNavigate, useParams } from "react-router-dom"
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';

const CssTextField = styled(TextField)({
    '& .MuiInputBase-input': {
        color: '#111111',
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

export default function EditarProduto() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [dadosProduto, setDadosProduto] = useState({
        nome: '',
        tipo: '',
        descricao: '',
        preco: '',
        estoque: '',
        urlImagem: ''
    });

    // Buscar os dados do produto ao carregar a página
    useEffect(() => {
        fetch(`http://localhost:8080/produtos/${id}`)
            .then(res => res.json())
            .then(data => {
                setDadosProduto(data);
            })
            .catch(err => {
                console.error("Erro ao buscar produto:", err);
                alert("Erro ao carregar dados do produto.");
            });
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setDadosProduto(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/produtos/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dadosProduto)
            });

            if (response.ok) {
                alert("Produto atualizado com sucesso!");
                navigate("/produtos-adm");
            } else {
                alert("Erro ao atualizar produto.");
            }
        } catch (error) {
            console.error("Erro:", error);
            alert("Erro ao atualizar produto.");
        }
    };

    return (
        <div className={styles.container}>
            <NavBarAdm />
            <div className={styles.mainContent}>
                <h1 className={styles.titulo}>Editar Produto</h1>

                <form className={styles.containerCampos} onSubmit={handleSubmit}>
                    <label className={styles.label}>Imagem do Produto
                        <CssTextField name="urlImagem" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosProduto.urlImagem} onChange={handleChange} />
                    </label>
                    <label className={styles.label}>Nome
                        <CssTextField name="nome" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosProduto.nome} onChange={handleChange} />
                    </label>
                    <label className={styles.label}>Tipo
                        <CssTextField name="tipo" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosProduto.tipo} onChange={handleChange} />
                    </label>
                    <label className={styles.label}>Quantidade
                        <CssTextField name="estoque" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosProduto.estoque} onChange={handleChange} />
                    </label>
                    <label className={styles.label}>Preço
                        <CssTextField name="preco" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosProduto.preco} onChange={handleChange} />
                    </label>
                    <label className={styles.label}>Descrição
                        <CssTextField name="descricao" variant="outlined" sx={{ marginTop: ".5rem" }} value={dadosProduto.descricao} onChange={handleChange} />
                    </label>

                    <button type="submit" className={styles.btnCadastrar}>Salvar</button>
                    <Link to="/produtos-adm" className={styles.btnCancelar}>Cancelar</Link>
                </form>
            </div>
        </div>
    )
}