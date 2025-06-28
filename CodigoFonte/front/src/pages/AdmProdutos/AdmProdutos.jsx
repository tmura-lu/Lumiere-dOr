import React from 'react'
import NavBarAdm from '../../components/NavBarAdm/NavBarAdm'
import { useEffect, useState } from 'react';
import CardProdutoAdm from '../../components/CardProdutoAdm/CardProdutoAdm';
import styles from './AdmProdutos.module.css'
import { Link } from 'react-router-dom';

export default function AdmProdutos() {
    const [produtos, setProdutos] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/produtos`)
            .then(res => res.json())
            .then(data => {
                setProdutos(data);
            })
            .catch(err => console.error(err))
    }, []);

    const handleDeleteProduto = (idProduto) => {
        setProdutos(prev => prev.filter(p => p.id !== idProduto));
    };

    return (
        <div className={styles.container}>
            <NavBarAdm />

            <div className={styles.mainContent}>
                <h1 className={styles.titulo}>Produtos</h1>

                <Link to="/adicionar-produto" className={styles.botaoAdicionar}>Adicionar Produto</Link>

                <div className={styles.containerProdutos}>
                    {produtos.length > 0 ? (
                        produtos.map(produto => (
                            <CardProdutoAdm key={produto.id} produto={produto} onDelete={handleDeleteProduto}/>
                        ))
                    ) : (
                        <p className={styles.msgVazia}>Nenhum produto registrado no sistema :(</p>
                    )}
                </div>
            </div>
        </div>
    )
}
