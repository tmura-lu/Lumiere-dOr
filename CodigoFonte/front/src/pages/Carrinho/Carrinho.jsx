import { useEffect, useState } from "react";
import Header from "../../components/Header/Header"
import Footer from "../../components/Footer/Footer"
import styles from './Carrinho.module.css'
import { Link } from "react-router-dom"

export default function Carrinho() {
    const [itens, setItens] = useState([]);
    const [total, setTotal] = useState(0);

    useEffect(() => {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario || !usuario.id) {
            alert("Usuário não está logado");
            return;
        }

        async function fetchCarrinho() {
            try {
                // 1. Buscar o carrinho do usuário
                const resCarrinho = await fetch(`http://localhost:8080/carrinhos/usuario/${usuario.id}`);
                const carrinho = await resCarrinho.json();

                setItens(carrinho.itens || []);

                // 2. Buscar o total do carrinho
                const resTotal = await fetch(`http://localhost:8080/carrinhos/${carrinho.id}/total`);
                const dataTotal = await resTotal.json();
                setTotal(dataTotal.total);
            } catch (error) {
                console.error("Erro ao buscar carrinho:", error);
                alert("Erro ao carregar o carrinho");
            }
        }

        fetchCarrinho();
    }, []);

    async function removerItem(produtoId) {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario || !usuario.id) {
            alert("Usuário não está logado");
            return;
        }

        try {
            const res = await fetch(`http://localhost:8080/carrinhos/${usuario.id}/remover`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ produtoId, quantidade: 1 }) // Remove 1 unidade
            });

            if (res.ok) {
                // Atualiza a lista de itens e total
                const carrinhoAtualizado = await res.json();
                setItens(carrinhoAtualizado.itens || []);

                const resTotal = await fetch(`http://localhost:8080/carrinhos/${carrinhoAtualizado.id}/total`);
                const dataTotal = await resTotal.json();
                setTotal(dataTotal.total);
                alert("Item removido com sucesso!");
            } else {
                const err = await res.json();
                alert(err.error || "Erro ao remover item");
            }
        } catch (err) {
            console.error("Erro ao remover item:", err);
            alert("Erro ao remover item");
        }
    }

    return (
        <div className={styles.container}>
            <Header />

            <div className={styles.mainContent}>
                <div className={styles.container1}>
                    <h1 className={styles.titulo}>Seu Carrinho</h1>

                    <div className={styles.containerProdutos}>
                        {itens.length === 0 ? (
                            <p className={styles.vazio}>Seu carrinho está vazio.</p>
                        ) : (
                            itens.map((item, index) => (
                                <div key={index} className={styles.produto}>
                                    <img
                                        src={item.produto.urlImagem}
                                        alt={item.produto.nome}
                                        className={styles.img}
                                    />
                                    <div className={styles.info}>
                                        <p className={styles.nome}>{item.produto.nome}</p>
                                        <p className={styles.descricao}>{item.produto.descricao}</p>
                                        <p className={styles.valorProduto}>R$ {item.produto.preco.toFixed(2)}</p>
                                        <p className={styles.descricao}>Qtd: {item.quantidade}</p>
                                    </div>
                                    <button className={styles.botaoRemover} onClick={() => removerItem(item.produto.id)} data-cy="botao-remover">Remover</button>
                                </div>
                            ))
                        )}
                    </div>

                </div>

                <div className={styles.container2}>
                    <p className={styles.texto}>Total</p>
                    <p className={styles.valor}>R$ {parseFloat(total).toFixed(2)}</p>
                    <Link href="" className={styles.botao}>Fechar Pedido</Link>
                </div>
            </div>


            <Footer />
        </div>
    )
}